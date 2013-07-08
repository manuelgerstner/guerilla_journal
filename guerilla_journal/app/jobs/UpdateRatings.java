package jobs;

import controllers.util.ArticleUtil;
import models.Article;
import models.Rating;
import play.jobs.Every;
import play.jobs.Job;

import java.util.List;
import play.Logger;

/**
 * @author Dave
 * Hourly job that updates the rating constants for baysian averaging and
 * ranks of all articles accordingly
 */
@Every("1h")
public class UpdateRatings extends Job {
    public void doJob() {
        Logger.info("Starting update on ranks and rating constants...");
        List<Rating> ratings = Rating.findAll();
        ArticleUtil.updateRatingConstants(ratings);
        List<Article> articles = Article.findAll();
        ArticleUtil.updateRanks(articles);
        Logger.info("Updated all rating constants and ranks");
    }
}
