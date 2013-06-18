package controllers;

import java.net.URLEncoder;

import controller.*;
import models.*;
import com.google.gson.*;
import java.util.List;
import play.libs.OAuth;
import play.libs.OAuth.ServiceInfo;
import play.libs.WS;
import play.mvc.*;

public class Application extends Controller {

    public static void index() {
        User user = Users.getUser();
        user.session = session.getId();
        user.save();

        session.put("loggedin",user.isLoggedIn());

        Article frontPost = Article.find("order by postedAt desc").first();
        List<Article> olderPosts = Article.find("order by postedAt desc").from(1).fetch(10);
        render(frontPost, olderPosts);
    }    

}