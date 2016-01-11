package controllers;

import play.mvc.WebSocket;
import play.*;
import play.mvc.*;
import views.html.*;

public class Product extends Controller{

public WebSocket<String> index() {
    return new WebSocket<String>() {

        public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
            out.write("prodg");
            out.close();
        }
     };
    }
}

   