package models.rest;

import play.db.Model;

/**
 * RestResource Interface
 * 
 * @author dbusser
 *         All Rest Resources should implement this
 *         Used to capsulate information from database models and only add
 *         what's needed to the REST models
 */
public interface RestResource {

	/**
	 * 
	 * @param model
	 *            - the jpa model
	 * @return - jpa model converted to REST model
	 */
	public AbstractRestResource convert(Model model);

}
