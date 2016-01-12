package controllers;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mongodb.BasicDBObject;

import play.libs.F;
import models.Pricing;
import models.Product;
import models.Specifications;
import play.mvc.Controller;
import play.mvc.WebSocket;
import utils.Mongo;

public class EditProducts extends Controller {

	public WebSocket<String> editProducts() {
		return new WebSocket<String>() {

			public void onReady(WebSocket.In<String> in,
					WebSocket.Out<String> out) {

				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {

						Object o = com.mongodb.util.JSON.parse(event);
						BasicDBObject productInfo = (BasicDBObject) o;

						String id = productInfo.getString("_id");
						
						Query<Product> query = Mongo.datastore()
								.createQuery(Product.class).field("id")
								.equal(new ObjectId(id));

						UpdateOperations<Product> operation = Mongo.datastore()
								.createUpdateOperations(Product.class)
								.set("name", productInfo.getString("name"));

						Mongo.datastore().update(query, operation);

						operation = operation.set("username",
								productInfo.getString("username"));

						Mongo.datastore().update(query, operation);

						operation = operation.set("category",
								productInfo.getString("category"));

						Mongo.datastore().update(query, operation);

						operation = operation.set("subCategory",
								productInfo.getString("sub_category"));

						Mongo.datastore().update(query, operation);

						BasicDBObject pricingDBObject = (BasicDBObject) com.mongodb.util.JSON
								.parse(productInfo.get("pricing").toString());

						operation = operation.set("pricing", new Pricing(
								pricingDBObject.getDouble("cost_price"),
								pricingDBObject.getDouble("discount"),
								pricingDBObject.getDouble("selling_price")));

						Mongo.datastore().update(query, operation);

						operation = operation.set("features",
								productInfo.get("features"));

						Mongo.datastore().update(query, operation);
			
						byte[] image = productInfo.get("images").toString().getBytes();
						operation = operation.set("image",
								image);

						Mongo.datastore().update(query, operation);

						BasicDBObject scpecificationsDBObject = (BasicDBObject) com.mongodb.util.JSON
								.parse(productInfo.get("specifications")
										.toString());

						operation = operation.set(
								"specifications",
								new Specifications(scpecificationsDBObject
										.getString("brand"),
										scpecificationsDBObject
												.getString("model_no"),
										scpecificationsDBObject
												.getString("color"),
										scpecificationsDBObject
												.getString("size")));

						Mongo.datastore().update(query, operation);

						operation = operation.set("itemsInStock",
								productInfo.getInt("items_in_stock"));

						Mongo.datastore().update(query, operation);

						operation = operation.set("citiesForDelivery",
								(productInfo.get("cities_for_delivery")));

						Mongo.datastore().update(query, operation);

					}
				});

			}
		};
	}
}
