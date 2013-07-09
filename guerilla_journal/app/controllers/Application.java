package controllers;

import java.util.List;

import models.Article;
import models.User;
import play.Logger;
import play.db.jpa.GenericModel;
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

		nextPage(1);
	}

	public static void nextPage(int page) {

		GenericModel.JPAQuery articles = Article.find("order by rank desc ");

		Logger.info("Got posts for show all view");
		renderArgs.put("articles",
				articles.from((page - 1) * 12).fetch(page * 12));
		renderArgs.put("page", page);

		renderArgs.put("hasNext", Article.count() > 12 * (page) ? true : false);
		renderArgs.put("hasPrev", page > 1);
		renderArgs.put("user", Users.getUser());
		renderTemplate("Application/index.html");
	}

}