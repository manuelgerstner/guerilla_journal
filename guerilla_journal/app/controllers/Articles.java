package controllers;

import models.Article;
import models.Rating;
import models.Tag;
import models.User;
import play.Logger;

/**
 * Articles Controller
 * 
 * @author dbusser
 * 
 *         takes care of the creation and rating of articles
 * 
 */
public class Articles extends CRUD {

	/**
	 * this page is not accessible to non-loggedin users
	 */
	public static void index() {
		if (session.get("loggedin").equals("false")) {
			Logger.info("Redirect, not logged in.");
			redirect("/");
		}
		render();
	}

	/**
	 * 
	 * @param author
	 *            - author's name
	 * @param title
	 *            - title of the article
	 * @param summary
	 *            - short summary of the article
	 * @param entry
	 *            - actual content of the article, html formatted
	 * @param headerPicUrl
	 *            - link to header img provided by filepicker
	 * @param category
	 *            - article category, @See Categories.java
	 */
	public static void createArticle(String author, String title,
			String summary, String entry, String headerPicUrl, String category,
			String rawTags) {
		// TODO: sanity check + tags

		// Validation
		validation.required(title).message(
				"What would your article be without a proper title?");
		validation
				.required(entry)
				.message(
						"Have some thoughts on something? Type it out! Currently your post is empty.");
		validation
				.required(summary)
				.message(
						"To make your article more interesting we need a short summary from you.");
		validation
				.minSize(title, 5)
				.message(
						"A good title has a huge effect on the perception of your article. Think of something a little longer!");
		validation
				.minSize(entry, 100)
				.message(
						"You're post is not even as long as a tweet! There must be more you can tell us.");
		validation
				.minSize(summary, 20)
				.message(
						"The summary is essential for readers to know what you are talking about. Be more specific, please!");
		validation
				.required(rawTags)
				.message(
						"Tagging your article will increase its visibility on the front page. Please think of at least one tag!");
		// If there are errors, no article will be created. Form shows again
		// with errors
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			index(); // send to article creation page
		} else {
			// validation passed, create new article
			Article article = new Article(author, title, summary, entry,
					headerPicUrl, category);
			// add non-existing tags only
			for (Tag tag : Tags.extractTags(rawTags)) {
				article.getTags().add(tag);
			}

			article.save();
			Logger.info("Article " + article.getTitle() + " stored in DB.");
			getArticle(article.id); // forward to newly created article
		}
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
	 * calculate the Bayesian average of each rating category
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

	// public static void findTagged(String tag) {
	// List<Article> articles = Tags.findTaggedWith(tag);
	// render("Application/index.html", articles);
	// }

}
