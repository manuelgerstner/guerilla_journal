package controllers;

import java.util.List;
import models.Article;
import play.Logger;
import play.modules.search.Query;
import play.mvc.Controller;

/**
 *Search controller for basic fulltext search with lucene.
 * @author Christine
 */
public class Search extends Controller{
    public static void search(String query) {
        Logger.info("Query " + query);        
        String request = "author:\"" +query  +"\"  title:\""+query+"\" summary:\"" + query + "\" entry:\"" + query + "\"" ;

        Query q = play.modules.search.Search.search(request, Article.class);
        List<Article> articles = q.fetch();
        Logger.info("Found " + articles.size() + " articles.");
        render(articles);
    }
}
