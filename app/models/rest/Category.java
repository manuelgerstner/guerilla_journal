package models.rest;

import java.util.HashMap;
import java.util.Map;

import play.db.Model;

public class Category extends AbstractRestResource implements RestResource {

	public String categoryName;
	public String restUrl;
	public String webUrl;

	public Category convert(Model model) {
		models.Category category = (models.Category) model;
		this.categoryName = category.name;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryName", categoryName);

		this.restUrl = this.buildRestUrl("rest.REST.findByCategory", params);
		this.webUrl = this.buildWebUrl("Categories.renderByCategory", params);

		return this;
	}
}
