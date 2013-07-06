package controllers;

import models.User;
import play.Logger;
import play.mvc.Controller;

public class Profiles extends Controller {

	public static void index() {
		User currentUser = Users.getUser();
		// TODO use User
		Logger.info("Show profile of user " + currentUser.name);
		render();
	}

	public static void getUserProfileByScreenName(String screenName) {
		// get user by screenName
		User user = User.find("screenName", screenName).first();
		render("Profiles/publicProfile.html", user);
	}

}
