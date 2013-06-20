package models;

import play.db.jpa.Model;

public class Tag extends Model {

	private String tag;

	public final String getTag() {
		return tag;
	}

	public final void setTag(String tag) {
		this.tag = tag;
	}
}
