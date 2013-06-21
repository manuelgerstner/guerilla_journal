package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;

import play.db.jpa.Model;
import controllers.Users;

@Entity
public class Article extends Model {
	private String author;
	private String title;
	private String summary;
	@Lob
	private String entry;
	private Date postedAt;
	private String headerPicUrl;

	private ArrayList<Tag> tags;

	// Rating
	private Integer nonAlignment;
	private Integer nonAlignmentCount;
	private Integer writingStyle;
	private Integer writingStyleCount;
	private Integer overall;
	private Integer overallCount;

	public Article(String author, String title, String summary, String entry,
			String headerPicUrl/* , Set tags */) {
		super();

		this.author = author;
		this.summary = summary;
		this.entry = entry;
		this.postedAt = new Date();
		this.title = title;
		// this.tags = tags;
		this.headerPicUrl = headerPicUrl;

		// rating

		super.create();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public Date getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Date postedAt) {
		this.postedAt = postedAt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeaderPicUrl() {
		return headerPicUrl;
	}

	public void setHeaderPicUrl(String headerPicUrl) {
		this.headerPicUrl = headerPicUrl;
	}

	public static List<Article> getUsersArticles() {
		User currentUser = Users.getUser();
		List<Article> articleList = Article.find(
				"author = '" + currentUser.name + "'").fetch();
		return articleList;
	}

}