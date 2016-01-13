package controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import play.libs.F;
import models.Pricing;
import models.Product;
import models.Specifications;
import play.mvc.Controller;
import play.mvc.WebSocket;
import utils.Mongo;

public class Products extends Controller {

	public WebSocket<String> products() {
		return new WebSocket<String>() {

			public void onReady(WebSocket.In<String> in,
					WebSocket.Out<String> out) {

				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {

						Object o = com.mongodb.util.JSON.parse(event);
						BasicDBObject merchantInfo = (BasicDBObject) o;
						String username = merchantInfo.getString("username");
						ObjectMapper mapper = new ObjectMapper();
						List<Product> products = Mongo.datastore()
								.createQuery(Product.class).field("username")
								.equal(username).asList();
						BasicDBList productJsons = new BasicDBList();
						for (Product product : products) {
							try {
								productJsons.add((DBObject) com.mongodb.util.JSON
										.parse(mapper
												.writeValueAsString(product)));
							} catch (JsonProcessingException e) {
								e.printStackTrace();
							}
						}
						out.write(new BasicDBObject("products", productJsons)
								.toJson());

					}
				});

			}
		};
	}

	public WebSocket<String> editProduct() {
		return new WebSocket<String>() {

			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {

				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {
						Object o = com.mongodb.util.JSON.parse(event);
						BasicDBObject productInfo = (BasicDBObject) o;

						ObjectId id = productInfo.getObjectId("_id");

						Query<Product> query = Mongo.datastore().createQuery(Product.class).field("_id").equal(id);

						UpdateOperations<Product> operation = Mongo.datastore().createUpdateOperations(Product.class)
								.set("name", productInfo.getString("name"));

						operation.set("username", productInfo.getString("username"));
						operation.set("category", productInfo.getString("category"));
						operation.set("sub_category", productInfo.getString("sub_category"));

						BasicDBObject pricingDBObject = (BasicDBObject) com.mongodb.util.JSON
								.parse(productInfo.get("pricing").toString());

						operation.set("pricing", new Pricing(pricingDBObject.getDouble("cost_price"),
								pricingDBObject.getDouble("discount"), pricingDBObject.getDouble("selling_price")));
						operation.set("features", productInfo.get("features"));

						byte[] image = productInfo.get("images").toString().getBytes();
						operation.set("image", image);

						BasicDBObject scpecificationsDBObject = (BasicDBObject) com.mongodb.util.JSON
								.parse(productInfo.get("specifications").toString());

						operation.set("specifications", new Specifications(scpecificationsDBObject.getString("brand"),
								scpecificationsDBObject.getString("model_no"),
								scpecificationsDBObject.getString("color"), scpecificationsDBObject.getString("size")));
						operation.set("itemsInStock", productInfo.getInt("items_in_stock"));
						operation.set("citiesForDelivery", (productInfo.get("cities_for_delivery")));

						Mongo.datastore().update(query, operation);

						out.write(new BasicDBObject("result", "success").toString());
					}
				});

			}
		};
	}
}
