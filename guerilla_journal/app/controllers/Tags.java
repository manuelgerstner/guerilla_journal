package controllers;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import models.Article;
import models.Category;
import models.Tag;

import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.mvc.Controller;

/**
 * Tags Controller
 * 
 * @author dbusser
 * 
 *         handles creation and assignment of tags to articles
 * 
 */
public class Tags extends Controller {

	/**
	 * extracts a set of tags from a raw string
	 * tags will only be created if they don't exist yet
	 * 
	 * @param rawTags
	 *            - string of tags separated by comma
	 * @return a set of unique tags without duplicates
	 */
	public static TreeSet<Tag> extractTags(String rawTags) {
		TreeSet<Tag> tags = new TreeSet<Tag>();
		for (String tag : Arrays.asList(StringUtils.split(rawTags, ","))) {
			tags.add(Tag.findOrCreateByName(tag));
		}
		return tags;
	}

	public static void renderByTag(String tagName) {
		Tag tag = Tag.find("name", tagName).first();
		if (tag == null) {
			Logger.error("Unknown tag by the name of " + tagName);
			notFound();
		}

		List<Article> articleList = findTaggedWith(tagName);

		Logger.info("Show articles tagged with " + tagName);
        renderArgs.put("user",Users.getUser());
		render("Tags/index.html", articleList, tagName);

	}

	/**
	 * find all articles that have been tagged with a tag
	 * 
	 * @param tags
	 *            - array of tags as strings
	 * @return - all articles that have been tagged with the specified tag
	 */
	public static List<Article> findTaggedWith(String tagName) {
		return Article
				.find("select distinct a from Article a join a.tags as t where t.name = ?",
						tagName).fetch();
	}

	/**
	 * find all tags
	 * 
	 * @return a list of Tag objects which contains every tag present in
	 *         database
	 */
	public static List<Tag> getTags() {
		return Tag.findAll();
	}

}
