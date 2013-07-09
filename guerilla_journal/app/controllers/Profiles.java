package controllers;

import models.User;
import play.Logger;
import play.mvc.Controller;

/**
 * @author Manuel
 * 
 *         Handles a users profile view.
 * 
 */
public class Profiles extends Controller {

	public static void index() {
		User currentUser = Users.getUser();
		// TODO use User
		Logger.info("Show profile of user " + currentUser.name);
		renderArgs.put("user", currentUser);
		render();
	}

	/**
	 * Renders a profile for the user specified.
	 * 
	 * @param twitterHandle
	 *            - the username as returned by the Twitter API
	 */
	public static void getUserProfileByTwitterHandle(String twitterHandle) {
		// get user by twitterHandle
		User user = User.find("twitterHandle", twitterHandle).first();
		if (user == null) {
			notFound();
		} else {
			renderArgs.put("user", Users.getUser());
			renderArgs.put("profileUser", user);
			render("Profiles/publicProfile.html");
		}
	}

}
