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

            new InitRatings().now();

        } catch (Exception e) {
            Logger.error("Loading of entities failed: " + e.getMessage());
        }
    }

}
