package controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.UpdateOperations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import models.Cart;
import models.Product;
import models.ShippingAddress;
import models.User;
import play.cache.Cache;
import play.libs.F;
import play.mvc.WebSocket;
import utils.Mongo;

public class CartManager {

	public WebSocket<String> cart() {

		return new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {
						BasicDBObject itemDo = (BasicDBObject) JSON.parse(event);
						String username = itemDo.getString("username");

						Cart cart = null;
						
						if (Cache.get(username) != null) {
							cart = (Cart) Cache.get(username);
							System.out.println("From cache.");
						} else {
							cart = Mongo.datastore().createQuery(Cart.class).field("username").equal(username).get();
							System.out.println("From db.");
						}

						ObjectMapper mapper = new ObjectMapper();

						BasicDBObject cartDo = new BasicDBObject();
						try {
							if (cart != null) {
								cartDo = (BasicDBObject) JSON.parse(mapper.writeValueAsString(cart));
							} else {
								cartDo = new BasicDBObject();
							}
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
						
						int i = 0;
						if (cartDo.get("items") != null && !((List<BasicDBObject>)cartDo.get("items")).isEmpty()) {
							for (BasicDBObject item : (List<BasicDBObject>) cartDo.get("items")) {
								item.put("_id", cart.getItems().get(i).getId().toString());
								item.remove("username");
								item.remove("category");
								item.remove("sub_category");
								item.remove("features");
								item.remove("specifications");
								item.remove("items_in_stock");
								item.remove("cities_for_delivery");
								i++;
							}
							cartDo.remove("_id");
						} else {
							cartDo = new BasicDBObject();
						}

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

						ObjectId productId = new ObjectId(itemDo.getString("product_id"));
						
						SingleResultCallback<Document> callback = new SingleResultCallback<Document>() {
						    @Override
						    public void onResult(final Document document, final Throwable t) {
								Cart cart = null;
								
								if (Cache.get(username) != null) {
									cart = (Cart) Cache.get(username);
									System.out.println("From cache.");
								} else {
									cart = Mongo.datastore().createQuery(Cart.class).field("username").equal(username).get();
									System.out.println("From db.");
								}

								BasicDBObject productDo = (BasicDBObject) JSON.parse(document.toJson());
								productDo.put("_id", document.getObjectId("_id").toString());
								Product product = Mongo.getEntityFromJson(productDo.toJson(), Product.class);
								if (cart != null) {
									List<Product> items = cart.getItems();

									items.add(product);
									Double total = cart.getTotal();
									total += product.getPricing().getSellingPrice();
									UpdateOperations<Cart> updatedCartItem = Mongo.datastore()
											.createUpdateOperations(Cart.class).set("items", items).set("total", total);

									Mongo.datastore().update(cart, updatedCartItem);
									Cache.set(username, cart);
								} else {
									String status = "incomplete";
									User userInfo = Mongo.datastore().createQuery(User.class).field("username").equal(username)
											.get();
									List<Product> products = new ArrayList<Product>();
									products.add(product);

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
									Cache.set(username, cart);
								}
								String result = new BasicDBObject("result", "success").toJson();
								out.write(result);
						    }
						};
						
						Mongo.asyncDatabase().getCollection("products").find(new Document("_id", productId)).first(callback);
						
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
						BasicDBObject itemDo = (BasicDBObject) JSON.parse(event);
						String username = itemDo.getString("username");

						ObjectId productId = new ObjectId(itemDo.getString("product_id"));
						
						SingleResultCallback<Document> callback = new SingleResultCallback<Document>() {
						    @Override
						    public void onResult(final Document document, final Throwable t) {
								Cart cart = null;
								
								if (Cache.get(username) != null) {
									cart = (Cart) Cache.get(username);
									System.out.println("From cache.");
								} else {
									cart = Mongo.datastore().createQuery(Cart.class).field("username").equal(username).get();
									System.out.println("From db.");
								}

								BasicDBObject productDo = (BasicDBObject) JSON.parse(document.toJson());
								productDo.put("_id", document.getObjectId("_id").toString());
								Product product = Mongo.getEntityFromJson(productDo.toJson(), Product.class);
								if (cart != null) {
									Double total = cart.getTotal();
									List<Product> items = cart.getItems();

									Iterator<Product> iter = items.iterator();
									while (iter.hasNext()) {
										Product miniProduct = iter.next();
										if (miniProduct.getId().toString().equals(product.getId().toString())) {
											iter.remove();
											total -= product.getPricing().getSellingPrice();
											break;
										}
									}
									Mongo.asyncDatabase().getCollection("products").updateOne(new Document("_id", document.getObjectId("_id")), new Document("$set", Document.parse(Mongo.getEntityAsJson(product))),
										    new SingleResultCallback<UpdateResult>() {
										        @Override
										        public void onResult(final UpdateResult result, final Throwable t) {
										            System.out.println("Product removed");
										        }
										    });
									String productAddedString = new BasicDBObject("result", "success").toJson();
									out.write(productAddedString);
									Cache.set(username, cart);
								} else {
									out.write(new BasicDBObject("result", "Cart empty.").toJson());
								}
								
								String result = new BasicDBObject("result", "success").toJson();
								out.write(result);
						    }
						};
						
						Mongo.asyncDatabase().getCollection("products").find(new Document("_id", productId)).first(callback);
						BasicDBObject cartDo = (BasicDBObject) com.mongodb.util.JSON.parse(event);
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

						Cart cart = null;
						
						if (Cache.get(username) != null) {
							cart = (Cart) Cache.get(username);
							System.out.println("From cache.");
						} else {
							cart = Mongo.datastore().createQuery(Cart.class).field("username").equal(username).get();
							System.out.println("From db.");
						}
						
						ObjectMapper mapper = new ObjectMapper();
						try {
							cartDo = (BasicDBObject) JSON.parse(mapper.writeValueAsString(cart));

							int i = 0;
							for (BasicDBObject item : (List<BasicDBObject>) cartDo.get("items")) {
								item.put("_id", cart.getItems().get(i).getId().toString());
								i++;
							}

							cartDo.remove("_id");
						} catch (JsonProcessingException e1) {
							e1.printStackTrace();
						}
						cartDo.remove("_id");
						List<BasicDBObject> items = (List<BasicDBObject>) cartDo.get("items");
						for (BasicDBObject item : items) {
							item.remove("image");
							item.remove("username");
							item.remove("category");
							item.remove("sub_category");
							item.remove("features");
							item.remove("specifications");
							item.remove("items_in_stock");
							item.remove("cities_for_delivery");
						}
						cartDo.put("items", items);

						String QUEUE_NAME = "orders";
						ConnectionFactory factory = new ConnectionFactory();
					    String uri = play.Configuration.root().getString("rabbitmq.uri");
					    String user = play.Configuration.root().getString("rabbitmq.user");
					    String password = play.Configuration.root().getString("rabbitmq.password");
						try {
							factory.setUri("amqp://" + user + ":" + password + "jaguar.rmq.cloudamqp.com/" + user);
						} catch (KeyManagementException
								| NoSuchAlgorithmException | URISyntaxException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						factory.setUsername(user);
						factory.setPassword(password);
						try {
							Connection connection = factory.newConnection();
							Channel channel = connection.createChannel();
							channel.queueDeclare(QUEUE_NAME, false, false, false, null);
							String message = cartDo.toJson();

							channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
							channel.close();
							connection.close();
						} catch (IOException | TimeoutException e) {
							e.printStackTrace();
						}
						
						Cache.remove(username);
						
						Mongo.datastore().delete(cart);
						
						String productAddedString = new BasicDBObject("result", "success").toJson();
						out.write(productAddedString);
					}
				});
			}
		};
	}

}
