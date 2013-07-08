package jobs;

import controllers.Articles;
import controllers.util.ArticleUtil;
import models.Article;
import models.Rating;
import play.Logger;
import play.db.jpa.JPABase;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

import java.util.List;

@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
        try {
            // preload dummy articles, tags etc
            Fixtures.idCache.clear();
            Fixtures.loadModels("entities.yml");
            Logger.info("All Entities loaded");

            List<Rating> allRatings = Rating.findAll();
            for (Rating rating : allRatings) {
                rating.article.getRatings().add(rating);
                rating.article.save();
            }

            List<Article> allArticles = Article.findAll();
            ArticleUtil.updateRanks(allArticles);
            Logger.info("Processed all ratings");

        } catch (Exception e) {
            Logger.error("Loading of entities failed: " + e.getMessage());
        }
    }

}
