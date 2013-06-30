package controllers;

import models.Article;

/**
 * Created with IntelliJ IDEA.
 * User: Dave
 * Date: 6/30/13
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Ratings extends CRUD {
    // Constants used in baysian averaging, do not necessarily need to update them
    // see http://en.wikipedia.org/wiki/Bayesian_average
    public static int avgNoOfRating = 2;
    public static float avgStyleRating = 2.5f;
    public static float avgNonAlRating = 2.5f;
    public static float avgOverallRating = 2.5f;

    public enum Type {
        STYLE, NONALIGN, OVERALL
    }

    // timestamp of 1/1/2013
    public static final long timeOffset = 1357076357l;

    public static float getFreshness(Article article) {
        return (article.getPostedAt().getTime() - Ratings.timeOffset) / 45000;
    }

    public static float getBayesAvg(float total,int ratingsCount, Type type) {
        float avg = 0f;
        switch (type) {
            case STYLE:
                avg = total + Ratings.avgNoOfRating*Ratings.avgStyleRating;
                break;
            case NONALIGN:
                avg = total + Ratings.avgNoOfRating*Ratings.avgNonAlRating;
                break;
            case OVERALL:
                avg = total + Ratings.avgNoOfRating*Ratings.avgOverallRating;
                break;
        }
        return avg/(ratingsCount+Ratings.avgNoOfRating);
    }


}
