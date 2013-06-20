package models;

import javax.persistence.*;
import play.db.jpa.Model;
/**
 *
 * @author Christine
 */
@Entity
public class Rating extends Model{
    private int writingStyle;
    private int nonAlignment;
    private int overall;
    
    
    public int getWritingStyle() {
	return writingStyle;
    }
    
    public void setWritingStyle(int writingStyle){
        this.writingStyle = writingStyle;
    }
    
    public int getNonAlignment() {
	return nonAlignment;
    }
    
    public void setnonAlignment(int nonAlignment){
        this.nonAlignment = nonAlignment;
    }
    
    public int getOverall() {
	return overall;
    }
    
    public void setOverall(int overall){
        this.overall = overall;
    }
    
}
