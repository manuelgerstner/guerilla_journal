package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        Article frontPost = Article.find("order by postedAt desc").first();
        List<Article> olderPosts = Article.find("order by postedAt desc").from(1).fetch(10);
        render(frontPost, olderPosts);        
    }

}