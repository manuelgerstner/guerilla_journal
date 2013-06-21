package controllers;

import models.Article;
import play.mvc.Controller;

public class ArticleController extends Controller {

	public static void index() {
		if (session.get("loggedin").equals("false"))
			redirect("/");

		render();
	}

	public static void createArticle(String author, String title,
			String summary, String entry, String headerPicUrl) {

		// TODO: sanity check + tags

		Article article = new Article(author, title, summary, entry,
				headerPicUrl);
		// redirect to main page

		article.save();

		getArticle(article.id); // forward to article
	}

	public static void rateArticle(long articleId, int score, String category) {
		Article article = Article.find("id", articleId).first();
		// TODO: rate article, persist, return new scores for the category in
		// JSON using renderJSON()
	}

	public static void getArticle(long id) {
		Article article = Article.find("id", id).first();
		if (article != null)
			render("ArticleController/detail.html", article);
		// not found
		else
			notFound();
	}

}
