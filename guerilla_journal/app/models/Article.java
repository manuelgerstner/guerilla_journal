package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
import play.modules.search.Field;
import play.modules.search.Indexed;
import controllers.Ratings;
import controllers.Users;

@Entity
@Indexed
public class Article extends Model {
	// necessary to make Fields public to access them with search-module.
	@Field
	public String author;
	@Field
	public String title;
	@Field
	public String summary;
	@Field
	@Lob
	public String entry;
	private Date postedAt;
	private String headerPicUrl;

	@OneToMany
	private List<Rating> ratings;
	public float rank;
	public float avgScore;

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
		this.ratings = new ArrayList<Rating>();
		this.rank = Ratings.getFreshness(this);

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

	public String toString() {
		return author + " - " + title;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
}