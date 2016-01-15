package controllers;

import java.util.List;

import models.Address;
import models.CardDetails;
import models.User;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.WebSocket;
import utils.Mongo;

import com.mongodb.BasicDBObject;

public class Signup extends Controller {

	public WebSocket<String> signup() {

		return new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in,
					WebSocket.Out<String> out) {
				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {
						Object o = com.mongodb.util.JSON.parse(event);
						User newUser = new User();
						BasicDBObject signupInfo = (BasicDBObject) o;
						String email = signupInfo.getString("email");
						newUser.setEmail(email);
						String userName = signupInfo.getString("username");
						newUser.setUsername(userName);
						List<User> users = Mongo.datastore()
								.createQuery(User.class).field("email")
								.equal(email).field("username").equal(userName)
								.asList();

						
						if (users != null && !users.isEmpty()) {
							out.write(new BasicDBObject("result", "User already exists.").toJson());
						} else {
							newUser.setPassword(signupInfo
									.getString("password"));
							newUser.setRole(signupInfo.getString("role"));
							newUser.setFirstname(signupInfo
									.getString("firstname"));
							newUser.setLastname(signupInfo
									.getString("lastname"));
							
							BasicDBObject addressDBObject = (BasicDBObject) com.mongodb.util.JSON
									.parse(signupInfo.get("address").toString());
							newUser.setAddress(new Address(addressDBObject
									.getString("street"), addressDBObject
									.getString("city"), addressDBObject
									.getString("state"), Long.parseLong(addressDBObject
									.getString("zip"))));
							newUser.setMobileNumber(Long.parseLong(signupInfo
									.getString("mobile_number")));
							if (signupInfo.get("card_details") != null) {
								BasicDBObject cardDetailsDBObject = (BasicDBObject) com.mongodb.util.JSON
										.parse(signupInfo.get("card_details")
												.toString());
								newUser.setCardDetails(new CardDetails(
										cardDetailsDBObject.getString("card_number") != null ? Long.parseLong(cardDetailsDBObject.getString("card_number")) : null,
										cardDetailsDBObject
												.getString("cardholder_name"),
										cardDetailsDBObject.getString("expiry"),
										cardDetailsDBObject.getString("card_type")));
							}

							Mongo.datastore().save(newUser);
							out.write(new BasicDBObject("result", "success").toJson());
						}
					}
				});
			}
		};
	}
}
