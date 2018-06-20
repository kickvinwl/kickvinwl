package dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import manager.MatchdayPointsCalculater;
import org.apache.commons.lang3.ObjectUtils;
import persistence.MatchTipPersistenceService;
import persistence.TeamPersistenceService;
import resources.*;
import util.DBInitializer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KickVinWlApplication extends Application<KickVinWlConfiguration> {


    public static void main(String[] args) throws Exception {
        new KickVinWlApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<KickVinWlConfiguration> bootstrap) {
        super.initialize(bootstrap);
        // HTTP Proxy Settings
        System.setProperty("http.proxyHost", "172.28.2.5");
        System.setProperty("http.proxyPort", "9090");
        System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");

        // HTTPS Proxy Settings
        System.setProperty("https.proxyHost", "172.28.2.5");
        System.setProperty("https.proxyPort", "9090");

    }

    @Override
    public void run(KickVinWlConfiguration configuration, Environment environment) throws Exception {
        MatchTipPersistenceService.getInstance();

        DBInitializer.dropDatabase();
        DBInitializer.init();
        DBInitializer.genUsers();
        DBInitializer.genMatches();

        final TipResource tipResource = new TipResourceImpl();
        environment.jersey().register(tipResource);

        final Resource resource = new Resource();
        environment.jersey().register(resource);

        final Login login = new Login();
        environment.jersey().register(login);

        final UserResource userResource = new UserResourceImpl();
        environment.jersey().register(userResource);

        final SearchResource searchResource = new SearchResourceImpl();
        environment.jersey().register(searchResource);

        final BundesligaResource bundesligaResource = new BundesligaResourceImpl();
        environment.jersey().register(bundesligaResource);

        final LeaderboardResource leaderboardResource = new LeaderboardResourceImpl();
        environment.jersey().register(leaderboardResource);
    }


    private void schedulingJobs() {
        final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                MatchdayPointsCalculater.getInstance().updateUserPointsMatchday();
            }
        }, 5, 5, TimeUnit.MINUTES);
    }
}
