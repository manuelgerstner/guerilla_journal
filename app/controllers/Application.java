package controllers;

import java.util.List;

import models.Article;
import models.User;
import play.mvc.Controller;
import play.Logger;

public class Application extends Controller {

	public static void index() {
		User user = Users.getUser();
		user.session = session.getId();
		user.save();
                
		session.put("loggedin", user.isLoggedIn());
                Logger.info("User has logged in", null);

		Article frontPost = Article.find("order by postedAt desc").first();
		List<Article> olderPosts = Article.find("order by postedAt desc")
				.from(1).fetch(10);
                Logger.info("Got posts for show all view", null);
		render(frontPost, olderPosts);
	}

}