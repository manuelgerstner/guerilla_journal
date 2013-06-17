package controllers;

import java.net.URLEncoder;

import models.User;
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

    public static void index(){
        render();
    }

    public static void twitterLogin(){
        String url = "http://twitter.com/statuses/mentions.xml";
        String mentions = "";
        mentions = WS.url(url).oauth(TWITTER, getUser().token, getUser().secret).get().getString();
        render(mentions);

        //redirect here...
    }

    public static void setStatus(String status) throws Exception {
        String url = "http://twitter.com/statuses/update.json?status=" + URLEncoder.encode(status, "utf-8");
        String response = WS.url(url).oauth(TWITTER, getUser().token, getUser().secret).post().getString();
        request.current().contentType = "application/json";
        renderText(response);
    }

    public static void authenticate() {
        // TWITTER is a OAuth.ServiceInfo object
        // getUser() is a method returning the current user
        if (OAuth.isVerifierResponse()) {
            // We got the verifier;
            // now get the access tokens using the request tokens
            OAuth.Response resp = OAuth.service(TWITTER).retrieveAccessToken(
                                      getUser().token, getUser().secret
                                  );
            // let's store them and go back to index
            getUser().token = resp.token; getUser().secret = resp.secret;
            getUser().save();
            index();
        }
        OAuth twitt = OAuth.service(TWITTER);
        Response resp = twitt.retrieveRequestToken();
        // We received the unauthorized tokens
        // we need to store them before continuing
        getUser().token = resp.token; getUser().secret = resp.secret;
        getUser().save();
        // Redirect the user to the authorization page
        redirect(twitt.redirectUrl(resp.token));
    }

    private static User getUser() {
        return User.findOrCreate("guest");
    }

}