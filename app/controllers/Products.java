package controllers;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import play.libs.F;
import models.Product;
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

						Object o = JSON.parse(event);
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
}
