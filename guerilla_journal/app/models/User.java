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
    
    public User(String email, String fullname) {
        this.email = email;
        this.name = fullname;
    }
 
}
