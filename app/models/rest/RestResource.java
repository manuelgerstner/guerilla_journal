package models.rest;

import play.db.Model;

public interface RestResource {

	public AbstractRestResource convert(Model model);

}
