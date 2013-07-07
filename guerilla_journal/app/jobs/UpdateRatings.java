package jobs;

import controllers.util.ArticleUtil;
import models.Article;
import models.Rating;
import play.jobs.Every;
import play.jobs.Job;

import java.util.List;
import play.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Dave
 * Date: 7/7/13
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
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
