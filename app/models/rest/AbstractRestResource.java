package models.rest;

import java.util.Map;

import play.mvc.Router;
import controllers.CRUD;

public abstract class AbstractRestResource extends CRUD {

	protected String buildRestUrl(String action, Map params) {
		return Router.getFullUrl(action, params);
	}

	protected String buildRestUrl(String action) {
		return Router.getFullUrl(action);
	}

	protected String buildWebUrl(String action, Map params) {
		return Router.getFullUrl(action, params);
	}

	protected String buildWebUrl(String action) {
		return Router.getFullUrl(action);
	}

}
