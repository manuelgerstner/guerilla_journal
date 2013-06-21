package controllers;

import models.Article;
import play.mvc.Controller;

/**
 * 
 * @author Christine
 */
public class ArticleDetailController extends Controller {
	public static void index(long id) {
		Article article = showArticle(id);
		render(article);
	}

	private static Article showArticle(long id) {
		Article article = Article.find("id", id).first();
		if (article != null)
			return article;

		else
			redirect("/");
		return null;
	}

}
