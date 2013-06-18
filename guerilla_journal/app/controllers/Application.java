package controllers;

import java.net.URLEncoder;

import controller.*;
import models.*;
import java.util.List;
import play.libs.OAuth;
import play.libs.OAuth.Response;
import play.libs.OAuth.ServiceInfo;
import play.libs.WS;
import play.mvc.Controller;

public class Application extends Controller {

    private static final ServiceInfo TWITTER = new ServiceInfo(
        "http://twitter.com/oauth/request_token",
        "http://twitter.com/oauth/access_token",
        "http://twitter.com/oauth/authorize",
        "eevIR82fiFK3e6VrGpO9rw",
        "OYCQA6fpsLiMVaxqqm1EqDjDWFmdlbkSYYcIbwICrg"
    );

    public static void index() {

        Article frontPost = Article.find("order by postedAt desc").first();
        List<Article> olderPosts = Article.find("order by postedAt desc").from(1).fetch(10);
        render(frontPost, olderPosts);
    }

    public static void twitterLogin() {
        renderText("www.google.de");
        // String url = "http://twitter.com/statuses/mentions.xml";
        // String mentions = "";
        // mentions = WS.url(url).oauth(TWITTER, getUser().token, getUser().secret).get().getString();
        //render(mentions);

        //redirect here...
    }

    public static void setStatus(String status) throws Exception {
        String url = "http://twitter.com/statuses/update.json?status=" + URLEncoder.encode(status, "utf-8");
        String response = WS.url(url).oauth(TWITTER, getUser().token, getUser().secret).post().getString();
        request.current().contentType = "application/json";
        renderText(response);
    }

    public static void authenticate() {
        User user = getUser();
        
        // TWITTER is a OAuth.ServiceInfo object
        // getUser() is a method returning the current user
        if (OAuth.isVerifierResponse()) {
            // We got the verifier; now get the access token, store it and back to index
            OAuth.Response oauthResponse = OAuth.service(TWITTER).retrieveAccessToken(user.token, user.secret);
            if (oauthResponse.error == null) {
                user.token = oauthResponse.token;
                user.secret = oauthResponse.secret;
                user.save();
            } else {
                //Logger.error("Error connecting to twitter: " + oauthResponse.error);
            }
            index();
        }
        
        OAuth twitt = OAuth.service(TWITTER);
        OAuth.Response oauthResponse = twitt.retrieveRequestToken();
        if (oauthResponse.error == null) {
            // We received the unauthorized tokens in the OAuth object - store it before we proceed
            user.token = oauthResponse.token;
            user.secret = oauthResponse.secret;
            user.save();
            // Redirect the user to the authorization page
            redirect(twitt.redirectUrl(oauthResponse.token));
        } else {
            //Logger.error("Error connecting to twitter: " + oauthResponse.error);
            index();
        }


    }

    // Should return current user
    private static User getUser() {
        return User.findOrCreate("guest", null);
    }

}