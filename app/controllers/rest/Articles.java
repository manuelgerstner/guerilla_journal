package controllers.rest;

import models.Article;
import controllers.CRUD;

public class Articles extends CRUD {

	// read only functionality

	public static void getArticles() {
		renderJSON(Article.findAll());
	}

	public static void getArticle(long id) {
		renderJSON(Article.findById(id));
	}

}
