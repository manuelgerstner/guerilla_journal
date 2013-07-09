package controllers;

import java.util.List;

import models.Article;
import models.Category;
import play.Logger;

/**
 * @author Manuel
 * 
 *         Enables categorizing of articles.
 * 
 */
@CRUD.For(Category.class)
public class Categories extends CRUD {

	/**
	 * Renders a category HTML template containing all articles in the category
	 * specified
	 * 
	 * @param categoryName
	 *            - name of the category
	 */
	public static void renderByCategory(String categoryName) {
		Category category = Category.find("name", categoryName).first();
		if (category == null) {
			Logger.error("Unknown category by the name of " + categoryName);
			notFound();
		}

		List<Article> articleList = findByCategory(category);

		Logger.info("Show articles in category " + categoryName);
		renderArgs.put("user", Users.getUser());
		render("Categories/index.html", articleList, categoryName);

	}

	/**
	 * Returns a list of articles that are in the category specified
	 * 
	 * @param category
	 *            - name of the category
	 * @return - a list of articles that are in the category specified
	 */
	public static List<Article> findByCategory(Category category) {
		return Article.find("category", category).fetch();
	}

	/**
	 * Returns a list of categories currently available
	 * 
	 * @return available categories
	 */
	public static List<Category> getCategories() {
		return Category.findAll();
	}
}
