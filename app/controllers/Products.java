package controllers;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import models.Product;
import play.mvc.Controller;
import play.mvc.WebSocket;
import utils.Mongo;

public class Products extends Controller {

	public WebSocket<String> products() {
		return new WebSocket<String>() {

			public void onReady(WebSocket.In<String> in,
					WebSocket.Out<String> out) {
				ObjectMapper mapper = new ObjectMapper();
				List<Product> products = Mongo.datastore().createQuery(Product.class).asList();
				BasicDBList productJsons = new BasicDBList();
				for (Product product : products) {
					try {
						productJsons.add((DBObject)com.mongodb.util.JSON.parse(mapper.writeValueAsString(product)));
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				}
				out.write(new BasicDBObject("products", productJsons).toJson());
			}
		};
	}
}
