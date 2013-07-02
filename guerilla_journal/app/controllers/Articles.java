package controllers;

import models.Article;
import models.Rating;
import models.User;
import play.Logger;

public class Articles extends CRUD {

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
		User usr = Users.getUser();
		if (Users.isGuest(usr)) {
			// TODO let the user know it did not work, probably even before the
			// request is sent.
			Logger.info("Rating rejected, not a registered user");
			return;
		}

		Article article = Article.find("id", articleId).first();
		Rating rating = Rating.find(
				"SELECT r FROM Rating r where r.user.id = " + usr.getId()
						+ " AND r.article.id = " + article.getId()).first();

		if (rating == null) {
			rating = new Rating();
			rating.user = usr;
			rating.article = article;
			article.getRatings().add(rating);
		}

		if (category.equals("writingStyle")) {
			rating.writingStyle = score;
			Logger.info("Article writing style rated with " + score);
		} else if (category.equals("nonAlignment")) {
			rating.nonAlignment = score;
			Logger.info("Article non alignment rated with " + score);
		} else if (category.equals("overall")) {
			rating.overall = score;
			Logger.info("Article overall rated with " + score);
		} else {
			Logger.error("The article could not be rated");
			return;
		}
		rating.save();
		updateRank(article);
		// TODO: return new scores for the category in
		// JSON using renderJSON()
	}

	/**
	 * calculate the Baysian average of each rating category
	 * 
	 * @param article
	 */
	private static void updateRank(Article article) {

		float totNonAl = 0;
		float totStyle = 0;
		float totOverall = 0;

		// calculate the sum of each rating category for rank and avgScore
		// calculation
		for (Rating rat : article.getRatings()) {
			totNonAl += rat.nonAlignment;
			totStyle += rat.writingStyle;
			totOverall += rat.overall;
		}

		float avgNonAl = Ratings.getBayesAvg(totNonAl, article.getRatings()
				.size(), Ratings.Type.NONALIGN);
		float avgStyle = Ratings.getBayesAvg(totStyle, article.getRatings()
				.size(), Ratings.Type.STYLE);
		float avgOverall = Ratings.getBayesAvg(totOverall, article.getRatings()
				.size(), Ratings.Type.OVERALL);

		// no we've got the baysian average of the categories
		// as average score we will display the arithmetic avg of the basian
		// avg's
		// TODO reevaluate the baysian average...
		article.avgScore = (float) Math
				.round(((avgNonAl + avgOverall + avgStyle) / 3) * 100) / 100;

		// now lets calculate the rank of the page
		// following reddit's hot rank method
		// see http://amix.dk/blog/post/19588
		float freshness = Ratings.getFreshness(article);
		float order = (float) Math.log10(totNonAl + totOverall + totStyle);
		article.rank = freshness + order;
		article.save();
		Logger.info("Set rank and score for article.");
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
