package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Cart;
import models.Product;
import models.ShippingAddress;
import models.User;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.UpdateOperations;

import play.libs.F;
import play.mvc.WebSocket;
import utils.Mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class CartManager {

	public WebSocket<String> cart() {

		return new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {
						BasicDBObject itemDo = (BasicDBObject) JSON.parse(event);
						String username = itemDo.getString("username");

						Cart cart = Mongo.datastore().createQuery(Cart.class).field("username").equal(username).get();
						ObjectMapper mapper = new ObjectMapper();

						BasicDBObject cartDo = new BasicDBObject();
						try {
							if (cart != null) {
								cartDo = (BasicDBObject) JSON.parse(mapper.writeValueAsString(cart));
							}
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}

						cartDo.remove("_id");

						out.write(cartDo.toJson());
					}
				});
			}
		};
	}

	public WebSocket<String> addToCart() {

		return new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {
						BasicDBObject itemDo = (BasicDBObject) JSON.parse(event);
						String username = itemDo.getString("username");

						Cart cart = Mongo.datastore().createQuery(Cart.class).field("username").equal(username).get();

						ObjectId productId = new ObjectId(itemDo.getString("product_id"));
						Product product = Mongo.datastore().createQuery(Product.class).field("_id").equal(productId)
								.get();

						if (cart != null) {
							List<String> items = cart.getItems();

							items.add(itemDo.getString("product_id"));
							Double total = cart.getTotal();
							total += product.getPricing().getSellingPrice();
							UpdateOperations<Cart> updatedCartItem = Mongo.datastore()
									.createUpdateOperations(Cart.class).set("items", items).set("total", total);

							Mongo.datastore().update(cart, updatedCartItem);
						} else {
							String status = "incomplete";
							User userInfo = Mongo.datastore().createQuery(User.class).field("username").equal(username)
									.get();
							List<String> products = new ArrayList<String>();
							products.add(product.getId().toString());

							Double total = product.getPricing().getSellingPrice();

							cart = new Cart();
							cart.setUsername(username);
							cart.setItems(products);
							cart.setTotal(total);
							cart.setStatus(status);
							cart.setPaymentMode(userInfo.getCardDetails());
							cart.setShippingAddress(new ShippingAddress(userInfo.getFirstname(), userInfo.getLastname(),
									userInfo.getAddress().getStreet(), userInfo.getAddress().getCity(),
									userInfo.getAddress().getState(), userInfo.getAddress().getZip(),
									userInfo.getMobileNumber()));
							Mongo.datastore().save(cart);
						}
						String result = new BasicDBObject("result", "success").toJson();
						out.write(result);
					}
				});
			}
		};
	}

	public WebSocket<String> removeFromCart() {

		return new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {
						BasicDBObject cartDo = (BasicDBObject) com.mongodb.util.JSON.parse(event);

						String username = cartDo.getString("username");
						String productId = cartDo.getString("product_id");

						Cart cart = Mongo.datastore().createQuery(Cart.class).field("username").equal(username).get();

						Product product = Mongo.datastore().createQuery(Product.class).field("_id").equal(new ObjectId(productId))
								.get();

						if (cart != null) {
							Double total = cart.getTotal();
							total -= product.getPricing().getSellingPrice();
							List<String> items = cart.getItems();

							Iterator<String> iter = items.iterator();
							while (iter.hasNext()) {
								String id = iter.next();
								if (id.equals(product.getId().toString())) {
									iter.remove();
									break;
								}
							}
							UpdateOperations<Cart> updatedCartItem = Mongo.datastore()
									.createUpdateOperations(Cart.class).set("items", items).set("total", total);
							Mongo.datastore().update(cart, updatedCartItem);
						} else {
							out.write(new BasicDBObject("result", "Order does not exist").toJson());
						}
						String productAddedString = new BasicDBObject("result", "success").toJson();
						out.write(productAddedString);
					}
				});
			}
		};
	}

	public WebSocket<String> checkout() {

		return new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {
						BasicDBObject cartDo = (BasicDBObject) com.mongodb.util.JSON.parse(event);

						String username = cartDo.getString("username");

						Cart cart = Mongo.datastore().createQuery(Cart.class).field("username").equal(username).get();

						// TODO: RabbitMQ code..
						
						String productAddedString = new BasicDBObject("result", "success").toJson();
						out.write(productAddedString);
					}
				});
			}
		};
	}

}
