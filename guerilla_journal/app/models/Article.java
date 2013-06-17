package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Article extends Model {
    public String author;
    public String title;
    public String summary;
    @Lob
    public String entry;
    public Date postedAt;
    public Picture picture;
  //  public ArrayList <String>tags;
    
    
    public Article(String author, String title, String summary, String entry, Picture picture) {
            super();
            this.author = author;
            this.summary = summary;
            this.entry = entry;
            this.postedAt = new Date();
            this.picture = picture;
    //	this.tags = tags;
            create();
    }


    public String getAuthor() {
            return author;
    }


    public void setAuthor(String author) {
            this.author = author;
    }


    public String getSummary() {
            return summary;
    }


    public void setSummary(String summary) {
            this.summary = summary;
    }


    public String getEntry() {
            return entry;
    }


    public void setEntry(String entry) {
            this.entry = entry;
    }

    public Date getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}