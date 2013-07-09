import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Article;
import models.Tag;
import models.User;

import org.junit.Test;

import play.mvc.Http.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import org.junit.*;

import controllers.Tags;
import controllers.Users;

public class ApplicationTest extends FunctionalTest {

	@Test
	public void testThatIndexPageWorks() {
		Response response = GET("/");
        assertStatus(302, response);
	//	assertContentType("text/html", response);
	//	assertCharset(play.Play.defaultWebEncoding, response);
	}
	
	
	/**
	 * 
	 */
	@Test
	public void testProfilePageWorks(){
		
		Users users = new Users();
		User user = users.getUser();
	    User knownUser = User.find("name", "dummyuser1").first();
	    if (knownUser != null) { // check if we already know this user
	    	while (User.find("session",Scope.Session.current().getId()).first()!=null ){
	    		User user2 = User.find("session",Scope.Session.current().getId()).first();
	    		user2.delete();
	    	}
	        if (users.isGuest(user)) {
	            user.delete();  // if so delete the guest user created for him
	        } else {
	            user.session = null;
	            user.secret = null;
	            user.token = null;
	            user.save();
	        }
	        user = knownUser; // and work on the know User db record from now on
	    }
		user.name="dummyuser1";
		user.twitterHandle="dummyuser1";
	    user.session = Scope.Session.current().getId();
		user.loggedIn = true;
		user.token ="frhiefe";
		user.secret="rhirehfrei";
		Scope.Session.current().put("loggedin", user.loggedIn);
		user.save();

		Response response = GET("/profile");
        assertStatus(200, response);
	}

	@Test
	public void createValidArticle() {
	    Map<String, String> params = new HashMap();
	    params.put("author", "dummyuser1"); 
	    params.put("authorScreenName", "dummyuser1"); 
	    params.put("title", "dummy article title"); 
	    params.put("summary", "just a test article summary needs 20 lenght"); 
	    params.put("entry", "the Entry that isnt supposed to make much sense but is supposed to be very looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong"); 
	    params.put("headerPicUrl", "http://www.erfolgreiche-diät.de/wp-content/uploads/2012/07/test.gif"); 
	    params.put("category", "World");
	    params.put("rawTags", "Sport"); 

     //   Result result = callAction(controllers.routes.ref.Application.index());
	    //	    Response response = POST("@{Articles.createArticle(\"dummyuser1\", \"dummyuser1\", \"dummy article title\", \"just a test article\",\"this is the entry, but it isnt supposed to make much sense\",\"http://www.erfolgreiche-diät.de/wp-content/uploads/2012/07/test.gif\", \"World\", \"Sport Fußball");
	    Response response = POST("/Articles/createArticle",params);
	   // Response response = POST("/articles/createArticle?author=dummyuser1&authorScreenName=dummyuser1&title=dummy article title&summary=just a test article&entry=this is the entry, but it isnt supposed to make much sense&headerPicUrl=http://www.erfolgreiche-diät.de/wp-content/uploads/2012/07/test.gif&category=World&rawTags=Sport Fußball");
	    assertStatus(302, response);
	    assertHeaderEquals("Location", "/detail/", response);
	  //  response.
	
	    /**
	     * Tests if the Average rating increases when an article is upvoted
	     */
	}
@Test
	public void testRateArticle(){
    
	Users users = new Users();
	User user = users.getUser();
    User knownUser = User.find("name", "dummyuser1").first();
    if (knownUser != null) { // check if we already know this user
        if (users.isGuest(user)) {
            user.delete();  // if so delete the guest user created for him
        } else {
            user.session = null;
            user.secret = null;
            user.token = null;
            user.save();
        }
        user = knownUser; // and work on the know User db record from now on
    }
	user.name="dummyuser1";
	user.twitterHandle="dummyuser1";
    user.session = Scope.Session.current().getId();
	user.token ="frhiefe";
	user.secret="rhirehfrei";
	user.loggedIn = true;
	user.save();
	Scope.Session.current().put("loggedin", true);

		long lon = 1;
		Article retrieved = Article.findById(lon);
		float help = retrieved.getAvgStyle();

	    Map<String, String> params = new HashMap();
	    params.put("articleId", "1");
	    params.put("score", "5");
	    params.put("category", "writingStyle");
	    Response response = POST("/Articles/rateArticle",params);
        assertStatus(200, response);
		Article retrieved2 = Article.findById(lon);
		float help2 = retrieved2.getAvgStyle();
	    assertTrue( help2 >= help );
	}

/**
 * Tests the search function, before and after adding a new entry, comparing if the result is +1
 */
@Test
public void testSearchFunction (){

    Map<String, String> params = new HashMap();
    params.put("query", "Bayern");
    Response response = POST("/Search/search",params);
	List<Article> articles = (List<Article>) renderArgs("articles");
	Article article =  new Article ("dummyuser1", "dummyuser1","dummy article title about Bayern",
			"just a test article for search function", "this is the entry, but it isnt supposed to make much sense",
			"http://www.erfolgreiche-diät.de/wp-content/uploads/2012/07/test.gif", "World");
	article.save();
	Response response2 = POST("/Search/search",params);
	List<Article> articles2 = (List<Article>) renderArgs("articles");
	assertTrue(articles.size()+1 == articles2.size());
	article.delete();
}


@Test
public void testTagFunction (){

    Map<String, String> params = new HashMap();
    params.put("tagName", "Sport");
    Response response = POST("/tags/renderByTag",params);
	List<Article> articles = (List<Article>) renderArgs("articleList");
	Article article =  new Article ("dummyuser1", "dummyuser1","dummy article title about Bayern",
			"just a test article for search function", "this is the entry, but it isnt supposed to make much sense",
			"http://www.erfolgreiche-diät.de/wp-content/uploads/2012/07/test.gif", "World");
	article.tags.add(Tag.findOrCreateByName("Sport"));
	article.save();
	
    Response response2 = POST("/tags/renderByTag",params);
	List<Article> articles2 = (List<Article>) renderArgs("articleList");
	assertTrue(articles.size()+1 == articles2.size());
	article.delete();
}
}
