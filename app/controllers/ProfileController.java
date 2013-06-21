package controllers;

import models.User;
import play.mvc.Controller;
import play.Logger;

public class ProfileController extends Controller {

	public static void index() {
		User currentUser = Users.getUser();
		// TODO use User
                Logger.info("Show profile of user " + currentUser.name);
		render();
	}

}
