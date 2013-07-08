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

        GenericModel.JPAQuery articles = Article.find("order by rank desc ");
        // find newest article
		Article frontPost = articles.first();

		// find latest articles
		List<Article> olderPosts = articles.from(1)
				.fetch(10);
		Logger.info("Got posts for show all view");
        renderArgs.put("articles", articles.from(0).fetch(10));
		render();//frontPost, olderPosts);
	}

}