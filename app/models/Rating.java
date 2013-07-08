package models;

import javax.persistence.*;
import play.db.jpa.Model;
/**
 *
 * @author Christine
 */
@Entity
public class Rating extends Model{

    public int writingStyle;
    public int nonAlignment;
    public int overall;

    @ManyToOne
    public Article article;

    @ManyToOne
    public User user;

    public String toString(){
        return "By " +user.name+ " on " + article.title + " rating: " +writingStyle +" "+nonAlignment+" "+overall;
    }
}
