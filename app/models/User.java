package models;

import javax.persistence.Entity;
import play.db.jpa.Model;

/**
 *
 * @author Christine
 */
@Entity
public class User extends Model {
 
    public String email;
    public String name;
    public String token;
    public String secret;
    
    public User(String email, String fullname) {
        this.email = email;
        this.name = fullname;
    }
    public static User findOrCreate(String name) {
        User user = User.find("name", name).first();
        if (user == null) {
            user = new User(name);
        }
        return user;
    }
 
}
