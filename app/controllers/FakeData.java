package controllers;

import org.mongodb.morphia.query.Query;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import models.Address;
import models.CardDetails;
import models.Order;
import models.User;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.WebSocket;
import utils.Mongo;

public class FakeData extends Controller {

	public WebSocket<String> createBuyers() {

		return new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {
						Object o = JSON.parse(event);
						BasicDBObject loginInfo = (BasicDBObject) o;
						int userCount = Integer.parseInt(loginInfo.getString("user_count"));
						
						for (int i = 1; i <= userCount; i++) {
							User user = new User();
							user.setUsername("buyer" + i);
							user.setAddress(new Address("street", "city", "state", (long) 123456));
							user.setCardDetails(new CardDetails("1234567890", "user" + 1, "10/10/2017", "visa"));
							user.setEmail("user" + 1 + "domain.com");
							user.setFirstname("first");
							user.setLastname("last");
							user.setMobileNumber((long) 1234567890);
							user.setPassword("password");
							user.setRole("buyer");
							Mongo.datastore().save(user);
						}
						out.write(new BasicDBObject("result","success").toJson());
					}
				});
			}
		};
	}
	
	public WebSocket<String> cleanUp() {

		return new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {
						Object o = JSON.parse(event);
						BasicDBObject loginInfo = (BasicDBObject) o;
						boolean cleanUp = Boolean.parseBoolean(loginInfo.getString("clean_up"));
						if (cleanUp) {
							Query deleteFakeBuyers = Mongo.datastore().createQuery(User.class).field("username").contains("buyer");
							Query deleteFakeOrders = Mongo.datastore().createQuery(Order.class);
							Mongo.datastore().delete(deleteFakeBuyers);
							Mongo.datastore().delete(deleteFakeOrders);
						}
						out.write(new BasicDBObject("result","success").toJson());
					}
				});
			}
		};
	}
}
