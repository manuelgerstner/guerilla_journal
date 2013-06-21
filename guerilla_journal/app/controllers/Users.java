package controllers;

import models.User;
import play.libs.OAuth;
import play.libs.OAuth.ServiceInfo;
import play.libs.WS;
import play.mvc.Router;

import com.google.gson.JsonObject;

public class Users extends CRUD {

	private static final ServiceInfo TWITTER = new ServiceInfo(
			"https://api.twitter.com/oauth/request_token",
			"https://api.twitter.com/oauth/access_token",
			"https://api.twitter.com/oauth/authorize",
			"pwzihITyZssfbuGtAIZk0w",
			"0yy3WiJZESGeDeg2xGJq87I4OwFBSz0lHoBmjZAvEnA");

	public static void authenticate() {

		User user = getUser();

		// TWITTER is a OAuth.ServiceInfo object
		// getUser() is a method returning the current user
		if (OAuth.isVerifierResponse()) {
			// We got the verifier; now get the access token, store it and back
			// to index
			OAuth.Response oauthResponse = OAuth.service(TWITTER)
					.retrieveAccessToken(user.token, user.secret);
			if (oauthResponse.error == null) {
				// store api stuff
				user.token = oauthResponse.token;
				user.secret = oauthResponse.secret;
				// get user info
				String url = "https://api.twitter.com/1.1/account/verify_credentials.json";
				WS.HttpResponse response = WS.url(url)
						.oauth(TWITTER, user.token, user.secret).get();
				JsonObject json = response.getJson().getAsJsonObject();
				String iconUrl = json.get("profile_image_url").getAsString();
				String userName = json.get("name").getAsString();
				String screenName = json.get("screen_name").getAsString();
				// store user info
				user.name = userName;
				user.iconUrl = iconUrl;
				user.screenName = screenName;
				user.save();

				session.put("loggedin", user.isLoggedIn());
				// go back to homepage
				redirect(Router.reverse("Application.index").toString());

			} else {
				System.err.println("Error retrieving twitter access token: "
						+ oauthResponse.error);
			}
		}

		OAuth twitt = OAuth.service(TWITTER);
		OAuth.Response oauthResponse = twitt.retrieveRequestToken();
		if (oauthResponse.error == null) {
			// We received the unauthorized tokens in the OAuth object - store
			// it before we proceed
			user.token = oauthResponse.token;
			user.secret = oauthResponse.secret;
			user.session = session.getId();
			user.save();
			// Redirect the user to the authorization page
			redirect(twitt.redirectUrl(oauthResponse.token));
		} else {
			index();
			System.err.println("Error retrieving twitter request token: "
					+ oauthResponse.error);
		}

	}

	public static User getUser() {
		// get known user
		User user = User.find("session", session.getId()).first();
		return user == null ? User.findOrCreate("guest") : user;
	}

	public static void logout() {
		User user = User.find("session", session.getId()).first();
		session.put("loggedin", false);
		user.session = null;
		user.save();
		redirect("/");
	}

}