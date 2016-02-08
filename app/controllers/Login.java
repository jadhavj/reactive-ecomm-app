package controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import models.User;
import play.Play;
import play.libs.F;
import play.mvc.*;
import utils.Mongo;

import java.util.List;

/**
 * Created by jujadhav on 11/1/16.
 */
public class Login extends Controller {
	
	public WebSocket<String> login() {
		return new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
				in.onMessage(new F.Callback<String>() {
					public void invoke(String event) {
						Object o = JSON.parse(event);
						BasicDBObject loginInfo = (BasicDBObject) o;
						String username = loginInfo.getString("username");
						String password = loginInfo.getString("password");

						List<User> users = Mongo.datastore().createQuery(User.class).field("username").equal(username)
								.field("password").equal(password).asList();
						if (users != null && !users.isEmpty()) {
							BasicDBObject login = new BasicDBObject();
							login.put("login", "valid");
							login.put("role", users.get(0).getRole());
							login.put("name",users.get(0).getFirstname());
							login.put("username", users.get(0).getUsername());
							out.write(login.toJson());
						} else {
							BasicDBObject login = new BasicDBObject();
							login.put("login", "invalid");
							out.write(login.toJson());
						}

					}
				});
			}
		};
	}
}
