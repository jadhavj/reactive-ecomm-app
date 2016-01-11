package controllers;

import java.util.ArrayList;

import play.mvc.*;
import utils.Mongo;

public class Product extends Controller {

	public WebSocket<String> index() {
		return new WebSocket<String>() {

			public void onReady(WebSocket.In<String> in,
					WebSocket.Out<String> out) {
				ArrayList<models.Product> list = (ArrayList<models.Product>) Mongo
						.datastore().createQuery(models.Product.class).asList();
				out.write(list.get(0).getID());
				out.close();
			}
		};
	}
}
