import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;


@OnApplicationStart
public class Bootstrap extends Job {
    private static boolean done = false;

    public void doJob() {
        try {
            if (!Bootstrap.done) {
                Fixtures.loadModels("articles.yml");
                Bootstrap.done = true;
            }
        } catch (Exception e) {
            // i don't fucking care...
        }
    }

}
