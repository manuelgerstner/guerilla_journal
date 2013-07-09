import java.util.HashMap;
import java.util.Map;

import models.Article;
import models.User;

import org.junit.Test;

import controllers.Users;
import play.mvc.Scope;
import play.mvc.Http.Response;
import play.test.FunctionalTest;


public class LoginRequiredTests extends FunctionalTest  {
	
	
	@Test
	public void testProfilePageWorks(){

		Response response = GET("/profile");
        assertStatus(200, response);
	}

	@Test
	public void testRateArticle(){
    
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
	    assertTrue( help2 > help );
	}

}
