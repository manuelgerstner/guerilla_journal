package models;

import javax.persistence.Entity;

import play.db.jpa.Model;
import play.modules.search.Field;

@Entity
public class Tag extends Model {

	@Field
	public String tagName;

}
