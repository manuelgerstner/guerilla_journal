package controllers;

import java.util.List;

import models.Comment;
import play.Logger;

/**
 * @author Manuel
 * 
 *         Handles the comments for articles.
 * 
 */
public class Comments extends CRUD {

	/**
	 * Creates a new comment and ads it to the article specified by the id.
	 * 
	 * @param articleId
	 *            - id of the article that the comment belongs to
	 * @param author
	 *            - name of the author of the comment
	 * @param authorTwitterHandle
	 *            - the twitterHandle name that corresponds to the author
	 * @param commentString
	 *            - the string containing the comment text
	 */
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
			Comment comment = new Comment(articleId, author,
					authorTwitterHandle, commentString);

			comment.save();
			Logger.info("Comment stored in DB.");
			Articles.getArticle(articleId);
		}
	}

	/**
	 * Returns a list of comments that belong to the article specified by the
	 * id.
	 * 
	 * @param articleId
	 *            - the articleId of an article
	 * @return - the list of comments with the articleId specified
	 */
	public static List<Comment> getCommentsForArticle(long articleId) {
		return Comment.find("articleId", articleId).fetch();
	}
}
