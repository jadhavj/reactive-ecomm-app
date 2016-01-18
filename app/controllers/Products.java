package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

import play.libs.F;
import models.Pricing;
import models.Product;
import models.Specifications;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
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
						System.out.println(Mongo.datastore().createQuery(models.Product.class).asList());
						ObjectMapper mapper = new ObjectMapper();
						List<Product> products = Mongo.datastore()
								.createQuery(Product.class).field("username")
								.equal(username).asList();
						BasicDBList productJsons = new BasicDBList();
						for (Product product : products) {
							try {
								BasicDBObject productDo = (BasicDBObject) com.mongodb.util.JSON
										.parse(mapper
												.writeValueAsString(product));
								productDo.put("_id", product.getId().toHexString());
								productJsons.add(productDo);
							} catch (JsonProcessingException e) {
								e.printStackTrace();
							}
						}
						out.write(productJsons.toString());

					}
				});

			}
		};
	}

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

	public Result editProduct() {
		Object o = com.mongodb.util.JSON.parse(request().body().asJson().toString());
		BasicDBObject productInfo = (BasicDBObject) o;

		String idString = productInfo.getString("_id");
		ObjectId id = new ObjectId(idString);

		Query<Product> query = Mongo.datastore().createQuery(Product.class).field("_id").equal(id);

		UpdateOperations<Product> operation = Mongo.datastore().createUpdateOperations(Product.class).set("name",
				productInfo.getString("name"));

		operation.set("username", productInfo.getString("username"));
		operation.set("category", productInfo.getString("category"));
		operation.set("sub_category", productInfo.getString("sub_category"));

		BasicDBObject pricingDBObject = (BasicDBObject) com.mongodb.util.JSON
				.parse(productInfo.get("pricing").toString());

		operation.set("pricing", new Pricing(pricingDBObject.getDouble("cost_price"),
				pricingDBObject.getDouble("discount"), pricingDBObject.getDouble("selling_price")));
		operation.set("features", productInfo.get("features"));

		byte[] image = null;
		image = Base64.getDecoder().decode(productInfo.get("image").toString());
		operation.set("image", image);

		BasicDBObject scpecificationsDBObject = (BasicDBObject) com.mongodb.util.JSON
				.parse(productInfo.get("specifications").toString());

		operation.set("specifications",
				new Specifications(scpecificationsDBObject.getString("brand"),
						scpecificationsDBObject.getString("model_no"), scpecificationsDBObject.getString("color"),
						scpecificationsDBObject.getString("size")));
		operation.set("itemsInStock", productInfo.getInt("items_in_stock"));
		operation.set("citiesForDelivery", (productInfo.get("cities_for_delivery")));

		Mongo.datastore().update(query, operation);

		return ok(new BasicDBObject("result", "success").toString());
	}

	public Result uploadProducts() throws Exception {

		MultipartFormData body = request().body().asMultipartFormData();
		FilePart catalogFile = body.getFiles().get(0);

		if (catalogFile != null) {
			File file = catalogFile.getFile();
			FileInputStream inputStream = new FileInputStream(file);

			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
			@SuppressWarnings("unchecked")
			List<PictureData> pictures = (List<PictureData>) workbook.getAllPictures();

			int i = 0;
			while (iterator.hasNext()) {
				if (i == 0) {
					i++;
					iterator.next();
					continue;
				}

				Row nextRow = iterator.next();
				if (nextRow.getCell(0) == null) {
					break;
				}

				String name = nextRow.getCell(0).getStringCellValue();
				String category = nextRow.getCell(1).getStringCellValue();
				String subCategory = nextRow.getCell(2).getStringCellValue();

				Double costPrice = nextRow.getCell(3).getNumericCellValue();
				Double discount = nextRow.getCell(4).getNumericCellValue();
				Double sellingPrice = nextRow.getCell(5).getNumericCellValue();
				Pricing pricing = new Pricing(costPrice, discount, sellingPrice);

				List<String> features = Arrays.asList(nextRow.getCell(6).getStringCellValue().split(","));

				String brand = nextRow.getCell(8).getStringCellValue();
				String modelNo = nextRow.getCell(9).getStringCellValue();
				String color = nextRow.getCell(10).getStringCellValue();
				String size = nextRow.getCell(11).getStringCellValue();
				Specifications specifications = new Specifications(brand, modelNo, color, size);

				Integer itemsInStock = Double.valueOf(nextRow.getCell(12).getNumericCellValue()).intValue();

				List<String> citiesForDelivery = Arrays.asList(nextRow.getCell(13).getStringCellValue().split(","));

				Product product = new Product(name, "anuja", category, subCategory, pricing, features,
						pictures.get(i - 1).getData(), specifications, itemsInStock, citiesForDelivery);

				Mongo.datastore().save(product);
				i++;
			}

			inputStream.close();
			BasicDBObject result = new BasicDBObject("result", "failure");
			return ok(result.toJson());
		} else {
			flash("error", "Missing file");
		}
		BasicDBObject result = new BasicDBObject("result", "failure");
		return ok(result.toJson());
	}

	public WebSocket<String> search() {
		return new WebSocket<String>() {

			public void onReady(WebSocket.In<String> in,
					WebSocket.Out<String> out) {

				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {

						Object o = com.mongodb.util.JSON.parse(event);
						BasicDBObject merchantInfo = (BasicDBObject) o;
						String searchString = merchantInfo.getString("search_string");
						ObjectMapper mapper = new ObjectMapper();
						List<Product> products = Mongo.datastore()
								.createQuery(Product.class).field("name")
								.contains(searchString).asList();
						BasicDBList productJsons = new BasicDBList();
						for (Product product : products) {
							try {
								BasicDBObject productDo = (BasicDBObject) com.mongodb.util.JSON
										.parse(mapper
												.writeValueAsString(product));
								productDo.put("_id", product.getId().toHexString());
								productJsons.add(productDo);
							} catch (JsonProcessingException e) {
								e.printStackTrace();
							}
						}
						out.write(productJsons.toString());
					}
				});
			}
		};
	}
}
