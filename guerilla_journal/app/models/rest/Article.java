package models.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.Comment;
import models.Tag;
import play.db.Model;

public class Article extends AbstractRestResource implements RestResource {

	public Long id;
	public String author;
	public String title;
	public String summary;
	public String entry;
	public String category;
	public float rank;
	public float avgScore;
	public Set<Tag> tags;
	private String restUrl;
	private String weburl;
    private String imgUrl;

	public List<Comment> comments;

	// public List<String> comments;

	public Article convert(Model model) {
		models.Article article = (models.Article) model;
		this.id = article.id;
		this.author = article.author;
		this.category = article.category.name;
		this.entry = article.entry;
		this.summary = article.summary;
		this.rank = article.rank;
		this.tags = article.tags;
		this.avgScore = article.avgScore;
        this.title = article.title;
        this.imgUrl = article.getHeaderPicUrl();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		this.restUrl = this.buildRestUrl("rest.REST.getArticle", params);
		this.weburl = this.buildWebUrl("Articles.getArticle", params);
		return this;
	}

}
