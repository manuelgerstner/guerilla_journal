package controllers;

import models.User;
import play.Logger;

public class Profiles extends CRUD {

	public static void index() {
		User currentUser = Users.getUser();
		// TODO use User
		Logger.info("Show profile of user " + currentUser.name);
        renderArgs.put("user",currentUser);
		render();
	}

	public static void getUserProfileByTwitterHandle(String twitterHandle) {
		// get user by twitterHandle
		User user = User.find("twitterHandle", twitterHandle).first();
		if (user == null) {
			notFound();
		} else {
			render("Profiles/publicProfile.html", user);
		}
	}

}
