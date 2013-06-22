package controllers;

import models.Article;
import play.Logger;
import play.mvc.Controller;

public class Articles extends Controller {

	public static void index() {
		if (session.get("loggedin").equals("false")) {
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
		if(category.equals("writingStyle")) {
			int writingStyle = article.getWritingStyle();
			int writingStyleCount = article.getWritingStyleCount();
			article.setWritingStyle(writingStyle + score);
			article.setWritingStyleCount(writingStyleCount++);
			Logger.info("Article rated.");
		} else if (category.equals("nonAlignment")) {
			int nonAlignment = article.getNonAlignment();
			int nonAlignmentCount = article.getNonAlignmentCount();
			article.setWritingStyle(nonAlignment + score);
			article.setWritingStyleCount(nonAlignmentCount++);
			Logger.info("Article rated.");
		} else if (category.equals("overall")) {
			int overall = article.getOverall();
			int overallCount = article.getOverallCount();
			article.setWritingStyle(overall + score);
			article.setWritingStyleCount(overallCount++);
			Logger.info("Article rated.");
		} else {
			Logger.error("The article could not be rated");
		}
		// TODO: return new scores for the category in
		// JSON using renderJSON()
	}

	public static void getArticle(long id) {
		Article article = Article.find("id", id).first();
		if (article != null) {
			Logger.info("Show selected article.");
			render("Articles/detail.html", article);
		}
		// not found
		else {
			Logger.warn("Article with this id is not available.");
			notFound();
		}
	}

}
