package models.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Lob;

import play.db.Model;

public class Comment extends AbstractRestResource implements RestResource {

	public long articleId;
	public String author;
	public String authorScreenName;
	public String commentString;
	public Date postedAt;
	public String restUrl;
	public String webUrl;

	public Comment convert(Model model) {
		models.Comment comment = (models.Comment) model;

		this.articleId = comment.articleId;
		this.author = comment.author;
		this.authorScreenName = comment.authorScreenName;
		this.commentString = comment.commentString;
		this.postedAt = comment.getPostedAt();

		this.restUrl = ""; // not needed
		this.webUrl = ""; // not needed

		return this;
	}
}
