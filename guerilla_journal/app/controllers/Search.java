package controllers;

import java.util.List;

import models.Article;
import play.Logger;
import play.modules.search.Query;
import play.mvc.Controller;

/**
 * Search controller for basic fulltext search with lucene.
 * 
 * @author Christine
 */
public class Search extends Controller {
        /**
         * Search for given query string with basic lucene search.         
         * This method searches in author, title, summary and entry
         * for exactly the given string.
         * @param query Querystring as given by user
         */
	public static void search(String query) {
		Logger.info("Got Query: " + query);
                String request = parseSearchStringToRequest(query);
                Logger.info("Request after parse " + request);
		Query q = play.modules.search.Search.search(request, Article.class);
		List<Article> articles = q.fetch();
		Logger.info("Found " + articles.size() + " fitting article(s).");
        renderArgs.put("user",Users.getUser());
		render(articles, query);
	}   
        
        /**
         * Parse search string into lucene-conform string.
         * @param query Querystring as given by user
         * @return parsing string as necessary for starting a query.
         */
        public static String parseSearchStringToRequest(String query){            
            String optTag = " or ";
            String andTag = " and ";
            String newQuery = "";
            String escapedQuery ="";
            String[] queryParts;
            Logger.info("Start parsing");
            //case 1: necessary to find all params in one of the fields
            if(query.contains(andTag)){ 
               query = query.replaceAll(andTag, " && ");
               escapedQuery = "(" + query + ")";
               newQuery += "author: " + escapedQuery + " title: " + escapedQuery
				+ " summary: " + escapedQuery + " entry: " + escapedQuery;            
               return newQuery;
            }
            // case 2: give all results which contain one of the given params
            if (query.contains(optTag)){
                queryParts = query.split(optTag);                
                for (String part : queryParts){
                    escapedQuery += "\""+ part + "\" ";
                }
                escapedQuery = "(" + escapedQuery + ")";
            }
            //Take the whole input as one string. Only when this string matches the article will be shown.
            else{
                escapedQuery = "\""+ query + "\""; 
            }
            
            newQuery += "author: " + escapedQuery + "  title: " + escapedQuery
				+ " summary: " + escapedQuery + " entry: " + escapedQuery;
            
            return newQuery;
        }
}
