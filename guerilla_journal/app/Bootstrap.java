import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;


@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
        try {
            if (Arcticle.count() == 0) {
                Fixtures.loadModels("articles.yml");
            }
        } catch (Exception e) {
            // i don't fucking care...
        }
    }

}
