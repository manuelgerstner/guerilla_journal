package controllers;

import models.Picture;
import models.Article;
import play.mvc.*;

public class ArticleController extends Controller {

    public static void index() {
        render();
    }
    public static void pictures(){
    	render();
    }

    public static void uploadPicture(Picture picture, String author, String summary, String entry) {
    	Article art = new Article(author,summary,entry,picture);
    	art.save();
    	index();
    	}
    
    public static void getPicture(long id) {
    	Article art = Article.findById(id);
    	Picture picture=art.picture;
    	response.setContentTypeIfNotSet(picture.image.type());
    	renderBinary(picture.image.get());
    	}
}
