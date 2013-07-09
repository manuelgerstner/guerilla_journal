package controllers;

import java.util.List;

import models.Article;
import models.Category;
import play.Logger;

@CRUD.For(Category.class)
public class Categories extends CRUD {

	public static void renderByCategory(String categoryName) {
		Category category = Category.find("name", categoryName).first();
		if (category == null) {
			Logger.error("Unknown category by the name of " + categoryName);
			notFound();
		}

		List<Article> articleList = findByCategory(category);

		Logger.info("Show articles in category " + categoryName);
        renderArgs.put("user",Users.getUser());
		render("Categories/index.html", articleList, categoryName);

	}

	public static List<Article> findByCategory(Category category) {
		return Article.find("category", category).fetch();
	}

	public static List<Category> getCategories() {
		return Category.findAll();
	}
}
