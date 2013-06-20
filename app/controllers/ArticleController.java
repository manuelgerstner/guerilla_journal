package controllers;

import models.Article;
import play.mvc.Controller;

public class ArticleController extends Controller {

	public static void index() {
		render();
	}

	public static void createArticle(String author, String title,
			String summary, String entry, String headerPicUrl) {

		// TODO: sanity check

		new Article(author, title, summary, entry, headerPicUrl).save();
		// redirect to main page

		redirect("/");
	}
	//
	// public static void getPicture(long id) {
	// Article art = Article.findById(id);
	// Picture picture = art.picture;
	// if (picture != null) {
	// response.setContentTypeIfNotSet(picture.image.type());
	// renderBinary(picture.image.get());
	// }
	//
	// }
}
