package controllers;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import models.Article;
import models.Tag;

import org.apache.commons.lang.StringUtils;

/**
 * Tags Controller
 * 
 * @author dbusser
 * 
 *         handles creation and assignment of tags to articles
 * 
 */
public class Tags extends CRUD {

	public static final Double MIN_FONT_SIZE = 12.0;
	public static final Double MAX_FONT_SIZE = 30.0;

	/**
	 * calculates the font size for a specific tag
	 * 
	 * @param tagName
	 * @return the tag's font size
	 */
	// public static Double getFontSize(String tagName) {
	// Tag tag = Tag.find("tagName", tagName).first();
	// if (tag != null) {
	//
	// int total = Integer.parseInt(Tag.em()
	// .createNativeQuery("SELECT SUM(t.count) FROM Tag t")
	// .getSingleResult().toString());
	//
	// Double size = (tag.count / total)
	// * ((MAX_FONT_SIZE - MIN_FONT_SIZE) + MIN_FONT_SIZE);
	//
	// return size;
	// }
	//
	// return MIN_FONT_SIZE; // default
	// }

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

	/**
	 * find all articles that have been tagged with a tag
	 * 
	 * @param tags
	 *            - array of tags as strings
	 * @return - all articles that have been tagged with at least one of the
	 *         specified tags
	 */
	public static List<Article> findTaggedWith(String tag) {
		// The tricky part is that we have to use a having count statement to
		// filter only posts that have exactly all tags from the joined view.
		return Article
				.find("select distinct a from Article a join a.tags as t where t.name = :tag group by a.id, a.author, a.title, a.summary, a.entry, a.postedAt having count(t.id) = :size")
				.bind("tag", tag).bind("size", tag.length()).fetch();
	}
}
