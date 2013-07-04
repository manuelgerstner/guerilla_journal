package controllers.rest;

import models.Article;
import controllers.CRUD;
import controllers.Tags;

public class Articles extends CRUD {

	// read only functionality

	public static void getArticles() {
		renderJSON(Article.findAll());
	}

	public static void getArticle(long id) {
		renderJSON(Article.findById(id));
	}

	public static void findByTag(String tag) {
		renderJSON(Tags.findTaggedWith(tag));
	}

}
