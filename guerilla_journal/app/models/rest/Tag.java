package models.rest;

import java.util.HashMap;
import java.util.Map;

import play.db.Model;

public class Tag extends AbstractRestResource implements RestResource {

	public String name;
	private String restUrl;
	private String webUrl;

	public Tag convert(Model model) {
		models.Tag tag = (models.Tag) model;
		this.name = tag.name;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tagName", name);
		this.restUrl = this.buildRestUrl("rest.REST.findByTag", params);
		this.webUrl = this.buildWebUrl("Tags.renderByTag", params);

		return this;
	}
}
