package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Cart;
import models.Product;
import models.ShippingAddress;
import models.User;

import org.mongodb.morphia.query.UpdateOperations;

import play.libs.F;
import play.mvc.WebSocket;
import utils.Mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class CartFunctionality {

	public WebSocket<String> addToCart() {

		return new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in,
					WebSocket.Out<String> out) {
				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {
						Object o = JSON.parse(event);
						Cart cart = new Cart();
						BasicDBObject cartInfo = (BasicDBObject) o;
						String userName = "defaultUser";
						if (cartInfo.getString("username") != null) {
							userName = cartInfo.getString("username");
						}
						String orderID = cartInfo.getString("orderID");
						String orderDate = cartInfo.getString("orderDate");
						Cart order = Mongo.datastore().createQuery(Cart.class)
								.field("orderID").equal(orderID).get();
						List<Product> productList = Mongo.datastore()
								.createQuery(Product.class).field("username")
								.equal(userName).asList();

						String productModelNo = cartInfo.getString("productID");
						if (order != null) {
							List<String> orderItemsList = order.getOrderItems();

							orderItemsList.add(cartInfo.getString("productID"));
							Double total = order.getTotal();
							for (Product p : productList) {
								if (p.getSpecifications().getModelNo()
										.equals(productModelNo)) {
									total = total
											+ p.getPricing().getSellingPrice();
								}
							}
							UpdateOperations<Cart> updatedCartItem = Mongo
									.datastore()
									.createUpdateOperations(Cart.class)
									.set("orderItems", orderItemsList)
									.set("total", total);

							Mongo.datastore().update(order, updatedCartItem);
						} else {
							String status = cartInfo.getString("status");
							User userInfo = Mongo.datastore()
									.createQuery(User.class)
									.filter("username", userName).get();
							List<String> productIDList = new ArrayList<String>();
							Double total = 0.0;
							productIDList.add(productModelNo);
							for (Product p : productList) {
								if (p.getSpecifications().getModelNo()
										.equals(productModelNo)) {
									total = p.getPricing().getSellingPrice();
								}
							}
							cart.setUsername(userName);
							cart.setOrderID(orderID);
							cart.setOrderItems(productIDList);
							cart.setOrderDate(orderDate);
							cart.setTotal(total);
							cart.setStatus(status);
							cart.setPaymentMode(userInfo.getCardDetails());
							cart.setShippingAddress(new ShippingAddress(
									userInfo.getFirstname(), userInfo
											.getLastname(), userInfo
											.getAddress().getStreet(), userInfo
											.getAddress().getCity(), userInfo
											.getAddress().getState(), userInfo
											.getAddress().getZip(), userInfo
											.getMobileNumber()));
							Mongo.datastore().save(cart);
						}
						String productAddedString = new BasicDBObject("result",
								"Product added to cart.").toJson();
						out.write(productAddedString);
					}
				});
			}
		};
	}

	public WebSocket<String> removeFromCart() {

		return new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in,
					WebSocket.Out<String> out) {
				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {
						Object o = com.mongodb.util.JSON.parse(event);
						BasicDBObject cartInfo = (BasicDBObject) o;
						String orderID = cartInfo.getString("orderID");
						String userName = cartInfo.getString("username");
						String productModelNo = cartInfo.getString("productID");
						Cart order = Mongo.datastore().createQuery(Cart.class)
								.field("orderID").equal(orderID)
								.field("username").equal(userName).get();
						List<Product> productList = Mongo.datastore()
								.createQuery(Product.class).field("username")
								.equal(userName).asList();

						System.out.println("before if");
						if (order != null) {
							System.out.println("inside if");
							Double total = order.getTotal();
							for (Product p : productList) {
								if (p.getSpecifications().getModelNo()
										.equals(productModelNo)) {
									total = total
											- p.getPricing().getSellingPrice();
								}
							}
							UpdateOperations<Cart> updatedCartItem = Mongo
									.datastore()
									.createUpdateOperations(Cart.class)
									.removeAll("orderItems", productModelNo)
									.set("total", total);
							Mongo.datastore().update(order, updatedCartItem);

						} else {
							out.write(new BasicDBObject("result",
									"Order does not exist").toJson());
						}
						String productAddedString = new BasicDBObject("result",
								"Product removed from cart.").toJson();
						out.write(productAddedString);
					}
				});
			}
		};
	}

	public static void checkout(Cart cart) {
		// TODO
		/*
		 * Channel channel = RabbitMq.connect(); try { channel.basicPublish("",
		 * RabbitMq.TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
		 * cart.toString() .getBytes()); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * System.out.println(" [x] Sent '" + cart.toString() + "'");
		 */
	}

}
