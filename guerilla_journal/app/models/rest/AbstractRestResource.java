package models.rest;

import java.util.Map;

import play.mvc.Router;
import controllers.CRUD;

/**
 * Abstract Rest
 * 
 * @author dbusser
 *         All Rest Models should subclass this class
 *         includes methods to build REST and Web Endpoints for resources
 */
public abstract class AbstractRestResource extends CRUD {

	/**
	 * creates an accessible REST endpoint
	 * example: localhost:9000/api/articles/1
	 * routes to Articles.getArticle(1)
	 * 
	 * @param action
	 *            - routing action for the rest endpoint
	 * @param params
	 *            - params for rest resources, such as id
	 * @return full REST endpoint URL
	 */
	protected String buildRestUrl(String action, Map params) {
		return Router.getFullUrl(action, params);
	}

	protected String buildRestUrl(String action) {
		return buildRestUrl(action, null);
	}

	protected String buildWebUrl(String action, Map params) {
		return Router.getFullUrl(action, params);
	}

	protected String buildWebUrl(String action) {
		return buildWebUrl(action, null);
	}

}
