package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Pricing;
import models.Product;
import models.Specifications;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Mongo;

import com.mongodb.BasicDBObject;

public class AddProduct extends Controller {

	public Result addProduct() {
		String jsonString = request().body().asJson().toString();
		Object o = com.mongodb.util.JSON.parse(jsonString);

		Product product = new Product();
		BasicDBObject addProductInfo = (BasicDBObject) o;
		// String userName = addProductInfo.getString("username");
		String userName = "anuja";
		product.setUsername(userName);
		List<User> users = Mongo.datastore().createQuery(User.class)
				.field("username").equal(userName).asList();
		if (users != null && users.isEmpty()) {
			String userNotSignedInString = new BasicDBObject("result",
					"You must be signed-in to add a product").toJson();
			System.out.println(userNotSignedInString);
		} else {
			product.setName(addProductInfo.getString("name"));
			product.setCategory(addProductInfo.getString("category"));
			product.setSubCategory(addProductInfo.getString("sub_category"));
			BasicDBObject pricingDBObject = (BasicDBObject) com.mongodb.util.JSON
					.parse(addProductInfo.get("pricing").toString());
			product.setPricing(new Pricing(Double.parseDouble(pricingDBObject
					.getString("cost_price")), Double
					.parseDouble(pricingDBObject.getString("discount")), Double
					.parseDouble(pricingDBObject.getString("selling_price"))));
			List<String> featuresList = new ArrayList<String>();
			for (String s : addProductInfo.get("features").toString()
					.split(",")) {
				featuresList.add(s);
			}
			product.setFeatures(featuresList);
			BasicDBObject specificationsDBObject = (BasicDBObject) com.mongodb.util.JSON
					.parse(addProductInfo.get("specifications").toString());
			product.setSpecifications(new Specifications(specificationsDBObject
					.getString("brand"), specificationsDBObject
					.getString("model_no"), specificationsDBObject
					.getString("color"), specificationsDBObject
					.getString("size")));
			product.setItemsInStock(Integer.parseInt(addProductInfo
					.getString("items_in_stock")));
			List<String> citiesList = new ArrayList<String>();
			for (String s : addProductInfo.get("cities_for_delivery")
					.toString().split(",")) {
				citiesList.add(s);
			}
			product.setCitiesForDelivery(citiesList);
			Mongo.datastore().save(product);
			String productAddedString = new BasicDBObject("result",
					"Product entry added to store.").toJson();
			System.out.println(productAddedString);

		}
		return ok("success");
	}

}
