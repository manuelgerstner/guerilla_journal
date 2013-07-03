package controllers;

import java.util.List;

import models.Article;
import models.Category;
import play.Logger;

@CRUD.For(Category.class)
public class Categories extends CRUD {

	public static void getCategory(String name) {
		Category category = Category.find("name", name).first();
		List<Article> articleList = Article.find("category", category).fetch();
		if (category != null) {
			Logger.info("Show articles in category " + name);
			render("Categories/index.html", articleList);
		}
		// not found
		else {
			Logger.warn("There is no category by the name " + name);
			notFound();
		}
	}
}
