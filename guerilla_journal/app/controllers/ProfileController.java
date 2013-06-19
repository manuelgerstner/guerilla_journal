package controllers;

import models.User;
import play.mvc.Controller;

public class ProfileController extends Controller {
	
	public static void index() {
		User currentUser = Users.getUser();
		
		render();
	}

}
