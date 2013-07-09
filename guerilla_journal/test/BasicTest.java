import models.Article;
import models.Comment;

import org.junit.Test;

import controllers.Articles;

import play.test.UnitTest;

public class BasicTest extends UnitTest {

	@Test
	public void aVeryImportantThingToTest() {
		assertEquals(2, 1 + 1);
	}

	@Test
	public void createAndRetrieveArticle (){
		Article article =  new Article ("dummyuser1", "dummyuser1","dummy article title",
			"just a test article", "this is the entry, but it isnt supposed to make much sense",
			"http://www.erfolgreiche-diät.de/wp-content/uploads/2012/07/test.gif", "World");
		Article retrieved = Article.findById(article.id);
		assertEquals(retrieved.author, "dummyuser1");
		assertEquals(retrieved.authorTwitterHandle, "dummyuser1");
		assertEquals(retrieved.title, "dummy article title");
	}
	@Test
	public void createValidatedArticle (){
		Articles arts = new Articles ();
		arts.createArticle("dummyuser1", "dummyuser1", "dummy article title", "just a test article",
				"this is the entry, but it isnt supposed to make much sense", 
				"http://www.erfolgreiche-diät.de/wp-content/uploads/2012/07/test.gif", "World", "sport");
	}
	
	
	@Test
	public void createAndRetrieveComment(){
		//(long articleId, String author, String authorScreenName, String commentString)
		long lon = 1;
		Comment comment = new Comment (lon, "dummyuser1", "dummyuser1", "wow das ist echt spannend!");
		Comment retrieved = Comment.findById(comment.id);
		assertEquals(retrieved.author, "dummyuser1");
		assertEquals(retrieved.authorTwitterHandle, "dummyuser1");
		assertEquals(retrieved.commentString, "wow das ist echt spannend!");
	}
}
