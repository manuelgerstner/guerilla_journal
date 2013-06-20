package models;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;

import play.db.jpa.Model;

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

}