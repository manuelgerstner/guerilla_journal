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

		Logger.info("User is logged in = " + user.loggedIn);

		// GenericModel.JPAQuery articles = Article.find("order by rank desc ");
		// Logger.info("Got posts for show all view");
		// renderArgs.put("articles", articles.from(0).fetch(12));
		// renderArgs.put("page", 1);
		// renderArgs.put("hasMore",Article.count() > 12 ? true : false);
		// render();//frontPost, olderPosts);
		nextPage(1);
	}

	public static void nextPage(int page) {

		GenericModel.JPAQuery articles = Article.find("order by rank desc ");

		Logger.info("Got posts for show all view");
		renderArgs.put("articles",
				articles.from((page - 1) * 12).fetch(page * 12));
		renderArgs.put("page", page);

		renderArgs.put("hasMore", Article.count() > 12 * (page) ? true : false);
		renderArgs.put("user", Users.getUser());
		renderTemplate("Application/index.html");
	}

}