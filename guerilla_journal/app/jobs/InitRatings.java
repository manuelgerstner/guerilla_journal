package jobs;

import models.Rating;
import play.jobs.Job;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dave
 * Date: 7/8/13
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class InitRatings extends Job {

    public void doJob() {
        List<Rating> allRatings = Rating.findAll();
        for (Rating rating : allRatings) {
            rating.article.getRatings().add(rating);
            rating.article.save();
        }
        new UpdateRatings().now();
    }
}
