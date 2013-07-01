import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {

	public void doJob() {
		try {
			// preload dummy articles, tags etc
			Fixtures.idCache.clear();
			Fixtures.loadModels("entities.yml");
			Logger.info("loaded articles");

		} catch (Exception e) {
			Logger.error("Loading of articles failed: " + e.getMessage());
		}
	}

}
