package controllers;

import java.util.List;

import models.Comment;
import play.Logger;

public class Comments extends CRUD {

	public static void createComment(long articleId, String author,
			String authorTwitterHandle, String commentString) {
		// TODO: sanity check + tags

		// Validation
		validation.required(commentString).message(
				"You did not enter anything. Do you want to comment?");
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			Articles.getArticle(articleId);
		} else {
			// validation passed, create new article
			Comment comment = new Comment(articleId, author, authorTwitterHandle,
					commentString);

			comment.save();
			Logger.info("Comment stored in DB.");
			Articles.getArticle(articleId);
		}
	}

	public static List<Comment> getCommentsForArticle(long articleId) {
		return Comment.find("articleId", articleId).fetch();
	}
}
