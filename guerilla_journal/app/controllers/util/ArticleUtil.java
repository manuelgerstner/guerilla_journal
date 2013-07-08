package controllers.util;

import models.Article;
import models.Rating;
import play.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Extracted the rating logic to an Util class, so it can be accessed from Jobs
 * (controllers can not).
 * @author Dave
 * Date: 7/7/13
 */
public class ArticleUtil {
    // Constants used in baysian averaging, do not necessarily need to update
    // them
    // see http://en.wikipedia.org/wiki/Bayesian_average
    public static int expectedNoOfRating = 2;
    public static float avgStyleRating = 2.5f;
    public static float avgNonAlRating = 2.5f;
    public static float avgOverallRating = 2.5f;

    /**
     * Update all ratings and ranks of a list of Articles
     *
     * @param articles
     */
    public static void updateRanks(List<Article> articles) {
        for(Article art : articles){
            updateRank(art);
        }
    }

    public enum Type {
        STYLE, NONALIGN, OVERALL
    }

    // timestamp of 1/1/2013
    public static final DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
    public static long timeOffset;

    static {
        try {
            timeOffset = df.parse("01/01/2013").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Determines how "new" the article is
     *
     * @param article
     * @return
     */
    public static float getFreshness(Article article) {
        // one day:     24*60*60*1000 = 86 400 000
        return (float) (article.getPostedAt().getTime() - ArticleUtil.timeOffset) / 1100000000;
    }

    /**
     * Determines how "trending" the article is
     *
     * @param article
     * @return
     */
    public static float getTrendingFreshness(Article article) {
        // one day:     24*60*60*1000 = 86 400 000
        return (float) (article.getPostedAt().getTime() - ArticleUtil.timeOffset) / 450000000;
    }

    /**
     * Will return the average rating of an article calculated by some algorithm
     *
     * @param total
     * @param ratingsCount
     * @param type
     * @return
     */
    public static float getAvg(float total, int ratingsCount, Type type) {
        return baysianAvg(total,ratingsCount,type);
    }

    /**
     * Calculate the Baysian average, neutralizes 5 star average produced by a single rating
     * but needs continuous rating updates
     * @param total
     * @param ratingsCount
     * @param type
     * @return
     */
    private static float baysianAvg(float total, int ratingsCount, Type type){
        float avg = 0f;
        switch (type) {
            case STYLE:
                avg = total + expectedNoOfRating * avgStyleRating;
                break;
            case NONALIGN:
                avg = total + expectedNoOfRating * avgNonAlRating;
                break;
            case OVERALL:
                avg = total + expectedNoOfRating * avgOverallRating;
                break;
        }
        return avg / (ratingsCount + expectedNoOfRating);
    }

    /**
     * Simple arithmetic average, not very stable (i.e. may be manipulated by few biased votes)
     * but elimiates the need to update ratings and rank (because we use the hot rank algorithm)
     * @param total
     * @param ratingsCount
     * @param type
     * @return
     */
    private static float aritheticAvg(float total, int ratingsCount, Type type){
        return total/ratingsCount;
    }

    public static float getCategoryAvg(Article article, Type type) {
        float avg = 0f;
        List<Rating> ratings = article.getRatings();
        int ratingPoints = 0;
        int ratingCount = 0;
        for (Rating rating : ratings) {
            switch (type) {
                case STYLE:
                    if (rating.writingStyle != 0) {
                        ratingPoints += rating.writingStyle;
                        ratingCount++;
                    }
                    break;
                case NONALIGN:
                    if (rating.nonAlignment != 0) {
                        ratingPoints += rating.nonAlignment;
                        ratingCount++;
                    }
                    break;
                case OVERALL:
                    if (rating.overall != 0) {
                        ratingPoints += rating.overall;
                        ratingCount++;
                    }
                    break;
            }
        }
        if (ratingPoints != 0 && ratingCount != 0) {
            avg = ratingPoints / ratingCount;
        }
        return avg;
    }

    /**
     * calculate the Bayesian average of each rating category
     * and rank the article with hot rank algorithm
     *
     * @param article
     */
    public static void updateRank(Article article) {

        float totNonAl = 0;
        float totStyle = 0;
        float totOverall = 0;
        int totNA = 0;
        int totSt = 0;
        int totOv = 0;

        // calculate the sum of each rating category for rank and avgScore
        // calculation
        for (Rating rat : article.getRatings()) {
            totNonAl += rat.nonAlignment;
            totStyle += rat.writingStyle;
            totOverall += rat.overall;
            if(rat.nonAlignment > 0) totNA++;
            if(rat.writingStyle > 0) totSt++;
            if(rat.overall > 0) totOv++;
        }

        float avgNonAl = getAvg(totNonAl, totNA, Type.NONALIGN);
        float avgStyle = getAvg(totStyle, totSt, Type.STYLE);
        float avgOverall = getAvg(totOverall, totOv, Type.OVERALL);

        // no we've got the baysian average of the categories
        // as average score we will display the arithmetic avg of the basian
        // avg's
        article.avgScore = (float) Math
                .round(((avgNonAl + avgOverall + avgStyle) / 3) * 100) / 100;

        // now lets calculate the rank of the page
        // following reddit's hot rank method
        // see http://amix.dk/blog/post/19588
        float rawRank = totNonAl + totOverall + totStyle - (3f * (totOv + totNA + totSt)); // offset ratings from -2 to 2
        float sign = rawRank < 0 ? -1 : 1;
        float order = (float) Math.log10(Math.abs(rawRank));
        article.rank = order + sign * getFreshness(article);
        article.trendingRank = order + sign * getTrendingFreshness(article);


        article.save();
        Logger.info("Set rank and score for article.");
    }

    /**
     * Iterate over all ratings to determine the overall ratings averages used in
     * baysian averaging of articles
     *
     * @param ratings
     */
    public static void updateRatingConstants(List<Rating> ratings){
        float newAvgStyle = 0;
        float newAvgNonAlign = 0;
        float newAvgOverall = 0;
        int countStyle = 0;
        int countNA = 0;
        int countOverall =0;
        for(Rating rating: ratings){
            if(rating.writingStyle != 0) {
                newAvgStyle += rating.writingStyle;
                countStyle ++;
            }
            if(rating.nonAlignment != 0) {
                newAvgNonAlign += rating.nonAlignment;
                countNA ++;
            }
            if(rating.overall != 0) {
                newAvgOverall += rating.overall;
                countOverall ++;
            }
        }
        avgStyleRating = newAvgStyle / countStyle;
        avgNonAlRating = newAvgNonAlign / countNA;
        avgOverallRating = newAvgOverall / countOverall;
    }
}
