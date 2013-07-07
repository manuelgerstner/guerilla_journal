package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Comment extends Model {

	public long articleId;
	public String author;
	public String authorScreenName;
	public String commentString;
	private Date postedAt;

	public Comment(long articleId, String author, String authorScreenName,
			String commentString) {
		super();

		this.articleId = articleId;
		this.author = author;
		this.authorScreenName = authorScreenName;
		this.commentString = commentString;
		this.postedAt = new Date();

		super.create();
	}

}
