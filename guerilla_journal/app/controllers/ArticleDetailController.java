package controllers;

import models.Article;
import play.mvc.Controller;
/**
 *
 * @author Christine
 */
public class ArticleDetailController extends Controller{
    public static void index(long id) {
            Article article = showArticle(id);
            render(article);
	}
        
    public static Article showArticle(long id){
        Article article = Article.find("id", id).first();
        return article;
    }

    public static void rateArticle(int writingStyle, int nonAlignment,
                    int overall) {
    }
}
