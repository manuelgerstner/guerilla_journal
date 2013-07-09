package controllers.rest;

import java.util.ArrayList;
import java.util.List;

import models.Article;
import models.Category;
import models.rest.AbstractRestResource;
import models.rest.RestResponse;
import play.Logger;
import controllers.Categories;
import controllers.Comments;
import controllers.Tags;

/**
 * REST Controller
 * 
 * @author dbusser
 * 
 *         handles all requests through the REST API (which is read-only)
 *         options include
 *         - all articles
 *         - article detail view
 *         - articles by tags
 *         - articles by categoriy
 */
public class REST extends AbstractRestResource {

	public static void getArticles() {
		List<AbstractRestResource> restArticles = new ArrayList<AbstractRestResource>();

		List<models.Article> articles = Article.findAll();
		for (models.Article article : articles) {
			models.rest.Article restArticle = new models.rest.Article();
			restArticles.add(restArticle.convert(article));
		}

		renderJSON(new RestResponse(restArticles));
	}

	public static void getArticle(long id) {
		// add comments
		models.Article article = Article.findById(id);
		models.rest.Article restArticle = new models.rest.Article();

		renderJSON(new RestResponse(restArticle.convert(article)));

	}

	public static void findByTag(String tagName) {
		List<AbstractRestResource> restArticles = new ArrayList<AbstractRestResource>();
		List<models.Article> articles = Tags.findTaggedWith(tagName);
		for (models.Article article : articles) {
			models.rest.Article restArticle = new models.rest.Article();
			restArticles.add(restArticle.convert(article));
		}

		renderJSON(new RestResponse(restArticles));
	}

	public static void findByCategory(String categoryName) {
		List<AbstractRestResource> restArticles = new ArrayList<AbstractRestResource>();

		Category category = Category.find("name", categoryName).first();
		if (category == null) {
			Logger.error("Unknown category by the name of " + categoryName);
			notFound();
		}

		List<models.Article> articles = Categories.findByCategory(category);

		for (models.Article article : articles) {
			models.rest.Article restArticle = new models.rest.Article();
			restArticles.add(restArticle.convert(article));
		}
		renderJSON(new RestResponse(restArticles));

	}

	public static void getTags() {
		List<AbstractRestResource> restTags = new ArrayList<AbstractRestResource>();
		List<models.Tag> tags = Tags.getTags();

		for (models.Tag tag : tags) {
			models.rest.Tag restTag = new models.rest.Tag();
			restTags.add(restTag.convert(tag));
		}

		renderJSON(new RestResponse(restTags));
	}

	public static void getCategories() {
		List<AbstractRestResource> restCategories = new ArrayList<AbstractRestResource>();
		List<models.Category> categories = Categories.getCategories();

		for (models.Category category : categories) {
			models.rest.Category restCategory = new models.rest.Category();
			restCategories.add(restCategory.convert(category));
		}

		renderJSON(new RestResponse(restCategories));

	}

	public static void getComments(long articleId) {
		List<AbstractRestResource> restComments = new ArrayList<AbstractRestResource>();
		List<models.Comment> comments = Comments
				.getCommentsForArticle(articleId);

		for (models.Comment comment : comments) {
			models.rest.Comment restComment = new models.rest.Comment();
			restComments.add(restComment.convert(comment));
		}

		renderJSON(new RestResponse(restComments));

	}
}
