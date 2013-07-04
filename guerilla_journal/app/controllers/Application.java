package controllers;

import java.util.List;

import models.Article;
import models.User;
import play.Logger;
import play.mvc.Controller;

public class Application extends Controller {

	/**
	 * initiates the web application takes care of:
	 * - user login
	 * - user session
	 * - front page posts
	 */
	public static void index() {
		User user = Users.getUser();
		user.session = session.getId();
		user.save();

		// store user's login status
		session.put("loggedin", user.loggedIn);
		Logger.info("User is logged in = " + user.loggedIn);

		// find newest article
		Article frontPost = Article.find("order by rank desc").first();

		// find latest articles
		List<Article> olderPosts = Article.find("order by rank desc").from(1)
				.fetch(10);
		Logger.info("Got posts for show all view");
		render(frontPost, olderPosts);
	}

}