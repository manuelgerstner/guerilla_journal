package controllers;

import models.Article;
import play.mvc.Controller;
import play.Logger;

public class ArticleController extends Controller {

	public static void index() {
		if (session.get("loggedin").equals("false")){
                    Logger.info("Redirect, not logged in.");
                    redirect("/");
                }
                render();
	}

	public static void createArticle(String author, String title,
			String summary, String entry, String headerPicUrl) {

		// TODO: sanity check + tags
            
		Article article = new Article(author, title, summary, entry,
				headerPicUrl);
                
		// redirect to main page

		article.save();
                Logger.info("Article " + article.getTitle() + "stored in DB.");
		getArticle(article.id); // forward to article
	}

	public static void rateArticle(long articleId, int score, String category) {
		Article article = Article.find("id", articleId).first();
                Logger.info("Article rated.");
		// TODO: rate article, persist, return new scores for the category in
		// JSON using renderJSON()
	}

	public static void getArticle(long id) {
		Article article = Article.find("id", id).first();
		if (article != null){
                    Logger.info("Show selected article.");
			render("ArticleController/detail.html", article);
                }
                // not found
                else{
                    Logger.warn("Article with this id is not available.");
                    notFound();
                }
	}

}
