package models;

import javax.persistence.Entity;
import play.db.jpa.Model;
import play.data.validation.*;

/**
 *
 * @author Christine
 */
@Entity(name="Users")
public class User extends Model {
 
    @Required
    public String email;
    @Required
    public String name;
    public String token; // oauth
    public String secret; // oauth
    
    public User(String email, String fullname) {
        this.email = email;
        this.name = fullname;
    }
    public static User findOrCreate(String name) {
        User user = User.find("name", name).first();
        if (user == null) {
            user = new User("nomail", name);
        }
        return user;
    }
 
}
