package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Article extends Model {
    public String author;
    public String summary;
    public String entry;
    public Picture picture;
  //  public ArrayList <String>tags;
    
    
	public Article(String author, String summary, String entry, Picture picture) {
		super();
		this.author = author;
		this.summary = summary;
		this.entry = entry;
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




    
}