package controllers;

import models.User;
import play.libs.OAuth;
import play.libs.OAuth.ServiceInfo;
import play.libs.WS;
import play.mvc.Router;
import play.Logger;

import com.google.gson.JsonObject;

/**
 * The Users controller handles all events concerning the User model object.
 * This includes Twitter login, creation and deletion of guest user records
 * in the database and helper methods to check the state of the current User.
 *
 * @autohr David
 */
public class Users extends CRUD {

    private static final ServiceInfo TWITTER = new ServiceInfo(
            "https://api.twitter.com/oauth/request_token",
            "https://api.twitter.com/oauth/access_token",
            "https://api.twitter.com/oauth/authorize",
            "pwzihITyZssfbuGtAIZk0w",
            "0yy3WiJZESGeDeg2xGJq87I4OwFBSz0lHoBmjZAvEnA");

    /**
     * Handel all Twitter login events:
     * -> Unkonwn user tries to log in
     * -> OAuth callback (from Twitter API)
     */
    public static void authenticate() {
        User user = getUser();

        Logger.info("Authenticating user " + user.name);


        // TWITTER is a OAuth.ServiceInfo object
        // getUser() is a method returning the current user
        if (OAuth.isVerifierResponse()) {
            Logger.info("Received Verifier response for user " + user.name);
            // We got the verifier; now get the access token, store it and back
            // to index
            OAuth.Response oauthResponse = OAuth.service(TWITTER).retrieveAccessToken(user.token, user.secret);
            if (oauthResponse.error == null) {
                Logger.info("Retrieved access token for user " + user.name);

                // get user info
                String url = "https://api.twitter.com/1.1/account/verify_credentials.json";
                WS.HttpResponse response = WS.url(url).oauth(TWITTER, oauthResponse.token, oauthResponse.secret).get();
                if (response.getStatus() == 200) {
                    JsonObject json = response.getJson().getAsJsonObject();
                    String iconUrl = json.get("profile_image_url").getAsString();
                    String userName = json.get("name").getAsString();
                    String screenName = json.get("screen_name").getAsString();

                    User knownUser = User.find("name", userName).first();
                    if (knownUser != null) { // check if we already know this user
                        if (isGuest(user)) {
                            user.delete();  // if so delete the guest user created for him
                        } else {
                            user.session = null;
                            user.secret = null;
                            user.token = null;
                            user.save();
                        }
                        user = knownUser; // and work on the know User db record from now on
                    }

                    // store api stuff
                    user.token = oauthResponse.token;
                    user.secret = oauthResponse.secret;

                    // store user info
                    user.name = userName;
                    user.iconUrl = iconUrl;
                    user.screenName = screenName;

                    user.session = session.getId();
                    user.loggedIn = true;
                    user.requestSent = false;

                    user.save();

                    session.put("loggedin", user.loggedIn);
                } else {
                    Logger.error("Error retrieving twitter user data: "
                            + response.getStatus());
                }
                // go back to homepage
                redirect(Router.reverse("Application.index").toString());

            } else {
                Logger.error("Error retrieving twitter user data: "
                        + oauthResponse.error);
            }
        } else if (user.requestSent) {
            // sent authenticate request, but the user declined access to his data => not logged in
            user.token = null;
            user.secret = null;
            user.requestSent = false;
            user.save();
            redirect(Router.reverse("Application.index").toString());
        } else { // guest, have to authenticate
            Logger.info("Generating authentication url for user " + user.name);
            // access not granted
            OAuth twitt = OAuth.service(TWITTER);
            OAuth.Response oauthResponse = twitt.retrieveRequestToken();
            if (oauthResponse.error == null) {
                // We received the unauthorized tokens in the OAuth object - store
                // it before we proceed
                user.token = oauthResponse.token;
                user.secret = oauthResponse.secret;
                user.requestSent = true;
                user.session = session.getId();
                user.save();
                // Redirect the user to the authorization page
                redirect(twitt.redirectUrl(oauthResponse.token));
            } else {
                Logger.error("Error creating Twitter authentication URL: "
                        + oauthResponse.error);
                redirect(Router.reverse("Application.index").toString());
            }
        }


    }

    /**
     * Get the current User identified by session.
     * If the user is not logged in you will receive a guest user, which may be accessed by others.
     * To check if a user is known (i.e. not a guest) call isGuest().
     */
    public static User getUser() {
        // get known user
        User user = User.find("session", session.getId()).first();
        return user == null ? User.findOrCreate("guest" + session.getId()) : user;
    }

    /**
     * Use this to check if a User is known or just a guest
     */
    public static boolean isGuest(User usr) {
        return usr.name.equals("guest" + session.getId()) && !usr.loggedIn;
    }

    public static boolean authorizeRequestSent(User usr) {
        return !usr.loggedIn && usr.token != null && usr.secret != null;
    }

    public static void logout() {
        User user = User.find("session", session.getId()).first();
        user.loggedIn = false;
        session.put("loggedin", false);
        user.token = null;
        user.secret = null;
        user.requestSent = false;
        user.save();
        redirect("/");
    }

}