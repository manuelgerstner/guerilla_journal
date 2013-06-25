package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * 
 * @author Christine
 */
@Entity(name = "Users")
public class User extends Model {
	// emails are not provided by twitter api, do we really need them? - david
	// @Required
	// public String email;
	@Required
	public String name;
	public String screenName;
	public String token; 	// oauth
	public String secret; 	// oauth
	public String iconUrl;
	public String session;	

	public User(String fullname) {
		this.name = fullname;
	}

	public static User findOrCreate(String name) {
		User user = User.find("name", name).first();
		if (user == null) {
			user = new User(name);
		}
		return user;
	}

	public String toString() {
		return name + " : " + (screenName == null ? " " : screenName) + " : "
				+ (isLoggedIn() ? "logged in via twitter" : "no twitter login") + " : "
				+ session;
	}

	public boolean isLoggedIn() {
		return token != null && screenName != null && secret != null;
	}
}
