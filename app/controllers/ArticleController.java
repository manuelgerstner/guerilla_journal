package controllers;

import models.Article;
import play.mvc.Controller;

public class ArticleController extends Controller {

	public static void index() {
		render();
	}

	public static void createArticle(String author, String title,
			String summary, String entry, String headerPicUrl) {

		// TODO: sanity check + tags

		new Article(author, title, summary, entry, headerPicUrl).save();
		// redirect to main page

		redirect("/");
	}

	public static void rateArticle(long articleId, int score, String category) {
		Article article = Article.find("id", articleId).first();
		// TODO: rate article, persist, return new scores for the category in
		// JSON using renderJSON()
	}

}
