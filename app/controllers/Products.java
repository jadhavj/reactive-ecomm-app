package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
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
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.FindIterable;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.mongodb.util.JSON;

import play.libs.F;
import play.libs.F.Function;
import play.libs.F.Function0;
import play.libs.F.Promise;
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
						ObjectMapper mapper = new ObjectMapper();
						
						BasicDBList productJsons = new BasicDBList();

						Block<Document> printDocumentBlock = new Block<Document>() {
						    @Override
						    public void apply(final Document product) {
								BasicDBObject productDo = (BasicDBObject) com.mongodb.util.JSON.parse(product.toJson());
								productDo.put("_id", product.getObjectId("_id").toString());
								productJsons.add(productDo);
						    }
						};
						SingleResultCallback<Void> callbackWhenFinished = new SingleResultCallback<Void>() {
						    @Override
						    public void onResult(final Void result, final Throwable t) {
								out.write(productJsons.toString());
						    }
						};
						Document query = new Document();
						query.put("username", username);
						FindIterable<Document> products = Mongo.asyncDatabase().getCollection("products").find(query);
						products.forEach(printDocumentBlock, callbackWhenFinished);
					}
				});

			}
		};
	}

	public Result addProduct() {
		
		BasicDBObject addProductInfo = (BasicDBObject) JSON.parse(request().body().asMultipartFormData().asFormUrlEncoded().get("product")[0]);
		Product product = new Product();
		
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart filePart = body.getFiles().get(0);
		File imageFile = filePart.getFile();
		List<User> users = Mongo.datastore().createQuery(User.class)
				.field("username").equal(addProductInfo.getString("username")).asList();
		if (users != null && users.isEmpty()) {
			String userNotSignedInString = new BasicDBObject("result",
					"You must be signed-in to add a product").toJson();
			System.out.println(userNotSignedInString);
		} else {
			product.setUsername(addProductInfo.getString("username"));
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
			BasicDBObject specificationsDo = (BasicDBObject) com.mongodb.util.JSON
					.parse(addProductInfo.get("specifications").toString());
			product.setSpecifications(new Specifications(specificationsDo
					.getString("brand"), specificationsDo
					.getString("model_no"), specificationsDo
					.getString("color"), specificationsDo
					.getString("size")));
			product.setItemsInStock(Integer.parseInt(addProductInfo
					.getString("items_in_stock")));
			List<String> citiesList = new ArrayList<String>();
			if (addProductInfo.get("cities_for_delivery") != null) {
				for (String s : addProductInfo.get("cities_for_delivery")
						.toString().split(",")) {
					citiesList.add(s);
				}
			}
			product.setCitiesForDelivery(citiesList);
			try {
				FileInputStream in = new FileInputStream(imageFile);
				byte[] imageData = new byte[new Long(imageFile.length()).intValue()];
				in.read(imageData);
				product.setImage(imageData);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Document productDo = Document.parse(Mongo.getEntityAsJson(product));
			
			Mongo.asyncDatabase().getCollection("products").insertOne(productDo, (Void result, final Throwable t) -> System.out.println("Inserted product."));
		}
		return ok(new BasicDBObject("result", "success").toString());
	}

	public Result editProduct() {
		Object o = com.mongodb.util.JSON.parse(request().body().asJson().toString());
		BasicDBObject productInfo = (BasicDBObject) o;

		String idString = productInfo.getString("_id");
		ObjectId id = new ObjectId(idString);

		Product operation = new Product();

		operation.setName(productInfo.getString("name"));
		operation.setUsername(productInfo.getString("username"));
		operation.setCategory(productInfo.getString("category"));
		operation.setSubCategory(productInfo.getString("sub_category"));

		Document pricingDBObject = Document.parse(productInfo.get("pricing").toString());

		operation.setPricing(new Pricing((double) pricingDBObject.getInteger("cost_price"),
				(double) pricingDBObject.getInteger("discount"), (double) pricingDBObject.getInteger("selling_price")));
		operation.setFeatures((List<String>) productInfo.get("features"));

		byte[] image = null;
		image = Base64.getDecoder().decode(productInfo.get("image").toString());
		operation.setImage(image);

		Document specificationsDo = Document.parse(productInfo.get("specifications").toString());

		operation.setSpecifications(new Specifications(specificationsDo.getString("brand"),
						specificationsDo.getString("model_no"), specificationsDo.getString("color"),
						specificationsDo.getString("size")));
		operation.setItemsInStock(Integer.parseInt(productInfo.getString("items_in_stock")));
		operation.setCitiesForDelivery((List<String>) productInfo.get("cities_for_delivery"));
		System.out.println(Mongo.getEntityAsJson(operation));
		Mongo.asyncDatabase().getCollection("products").updateOne(new Document("_id", new ObjectId(idString)), new Document("$set", Document.parse(Mongo.getEntityAsJson(operation))),
			    new SingleResultCallback<UpdateResult>() {
			        @Override
			        public void onResult(final UpdateResult result, final Throwable t) {
			            System.out.println("Updated product.");
			        }
			    });
		
		return ok(new BasicDBObject("result", "success").toString());
	}

	public Promise<Result> uploadProducts() throws Exception {

		Promise<String> promiseOfUploadStatus = Promise.promise(new Function0<String>() {
			public String apply() throws IOException {
				ObjectMapper mapper = new ObjectMapper();
				
				Document user = Document
						.parse(request().body().asMultipartFormData().asFormUrlEncoded().get("sender")[0]);
				
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

						Product product = new Product(name, user.getString("username"), category, subCategory, pricing, features,
								pictures.get(i - 1).getData(), specifications, itemsInStock, citiesForDelivery);

						Mongo.asyncDatabase().getCollection("products").insertOne(Document.parse(mapper.writeValueAsString(product)), (Void result, final Throwable t) -> System.out.println("Inserted product."));
						i++;
					}

					inputStream.close();
					BasicDBObject result = new BasicDBObject("result", "success");
					return result.toJson();
				} else {
					flash("error", "Missing file");
				}
				BasicDBObject result = new BasicDBObject("result", "failure");
				return result.toJson();
			}
		});

		Promise<Result> promiseOfResult = promiseOfUploadStatus.map(new Function<String, Result>() {
			public Result apply(String result) {
				// This would apply once the redeemable promise succeed
				return ok(result);
			}
		});

		return promiseOfResult;
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
						
						BasicDBList productJsons = new BasicDBList();

						Block<Document> printDocumentBlock = new Block<Document>() {
						    @Override
						    public void apply(final Document product) {
								BasicDBObject productDo = (BasicDBObject) com.mongodb.util.JSON.parse(product.toJson());
								productDo.put("_id", product.getObjectId("_id").toString());
								productJsons.add(productDo);
						    }
						};
						SingleResultCallback<Void> callbackWhenFinished = new SingleResultCallback<Void>() {
						    @Override
						    public void onResult(final Void result, final Throwable t) {
								out.write(productJsons.toString());
						    }
						};
						Document query = new Document();
						query.put("name", new Document("$regex", searchString));
						FindIterable<Document> products = Mongo.asyncDatabase().getCollection("products").find(query);
						products.forEach(printDocumentBlock, callbackWhenFinished);
					}
				});
			}
		};
	}
}
