package controllers;

import java.util.List;

import models.Article;
import play.mvc.Controller;

public class Application extends Controller {

	public static void index() {
		Article frontPost = Article.find("order by postedAt desc").first();
		List<Article> olderPosts = Article.find("order by postedAt desc")
				.from(1).fetch(10);
		render(frontPost, olderPosts);
	}

}