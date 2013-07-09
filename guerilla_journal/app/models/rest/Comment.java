package models.rest;

import java.util.Date;

import play.db.Model;

public class Comment extends AbstractRestResource implements RestResource {

	public long articleId;
	public String author;
	public String authorTwitterHandle;
	public String commentString;
	public Date postedAt;
	public String restUrl;
	public String webUrl;

	public Comment convert(Model model) {
		models.Comment comment = (models.Comment) model;

		this.articleId = comment.articleId;
		this.author = comment.author;
		this.authorTwitterHandle = comment.authorTwitterHandle;
		this.commentString = comment.commentString;
		this.postedAt = comment.getPostedAt();

		this.restUrl = ""; // not needed
		this.webUrl = ""; // not needed

		return this;
	}
}
