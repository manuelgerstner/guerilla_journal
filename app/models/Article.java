package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
import play.modules.search.Field;
import play.modules.search.Indexed;
import controllers.Users;
import controllers.util.ArticleUtil;

@Entity
@Indexed
public class Article extends Model {
	// necessary to make Fields public to access them with search-module.
	@Field
	public String author;
	@Field
	public String authorScreenName;
	@Field
	public String title;
	@Field
	@Lob
	public String summary;
	@Field
	@Lob
	public String entry;

	@ManyToOne
	public Category category;

	private Date postedAt;
	private String headerPicUrl;

	@OneToMany
	private List<Rating> ratings;
	@OneToMany
	private List<Comment> comments;
	public float rank;
	public float avgScore;

	public int rankInt;
	public int avgScoreInt;

	@ManyToMany(cascade = CascadeType.PERSIST)
	public Set<Tag> tags;

	// avg in single category
	public float getAvgStyle() {
		return ArticleUtil.getCategoryAvg(this, ArticleUtil.Type.STYLE);
	}

	public float getAvgNonAlign() {
		return ArticleUtil.getCategoryAvg(this, ArticleUtil.Type.NONALIGN);
	}

	public float getAvgOverall() {
		return ArticleUtil.getCategoryAvg(this, ArticleUtil.Type.OVERALL);
	}

	public Article(String author, String authorScreenName, String title,
			String summary, String entry, String headerPicUrl, String category) {
		super();

		this.author = author;
		this.authorScreenName = authorScreenName;
		this.summary = summary;
		this.entry = entry;
		this.postedAt = new Date();
		this.title = title;
		this.tags = new TreeSet<Tag>();
		this.headerPicUrl = headerPicUrl;

		// category
		this.category = Category.find("name", category).first();

		// rating
		this.ratings = new ArrayList<Rating>();
		this.rank = ArticleUtil.getFreshness(this);

		// comments
		this.comments = new ArrayList<Comment>();

		super.create();
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public static List<Article> getUsersArticles(String screenName) {
		List<Article> articleList = Article.find(
				"authorScreenName = '" + screenName + "'").fetch();
		return articleList;
	}

	public String toString() {
		return id + " - rank:" + rank + " - " + "rating: " + avgScore + " - "
				+ author + " - " + title;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
}