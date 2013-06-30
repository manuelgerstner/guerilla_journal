import models.Article;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

import java.util.List;

@OnApplicationStart
public class Bootstrap extends Job {

	public void doJob() {
		try {
			if (Article.count() == 0) {
                Fixtures.idCache.clear();
				Fixtures.loadModels("articles.yml");
			}
		} catch (Exception e) {
            Logger.error("Loading of articles failed");
        }
	}

}
