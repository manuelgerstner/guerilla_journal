package models;

import javax.persistence.Entity;

import play.db.jpa.Model;
import play.modules.search.Field;

@Entity
public class Category extends Model {

	@Field
	public String name;
	public String description;
	// For coloring purposes in bootstrap categories get a class
	public String className;

	public String toString() {
		return name;
	}

	public String getDescription() {
		return description;
	}

}
