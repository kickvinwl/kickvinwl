package dropwizard;

import entities.League;
import entities.Match;
import entities.Matchday;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import manager.MatchDayManager;
import persistence.*;
import manager.MatchdayPointsCalculater;
import resources.*;
import util.DBInitializer;
import util.Schedular;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

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
        LeaderboardPersistenceService.getInstance();

        DBInitializer.init();

        AchievementsChecker ac = new AchievementsChecker();
        ac.check();

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


        final NewsfeedResource newsfeedResource = new NewsfeedResourceImpl();
        environment.jersey().register(newsfeedResource);

        final LeaderboardResource leaderboardResource = new LeaderboardResourceImpl();
        environment.jersey().register(leaderboardResource);
        schedulingJobs();
    }


    private void schedulingJobs() {
        Schedular schedular = new Schedular();

        Runnable setCurrentMatchDay = new Runnable() {
            @Override
            public void run() {
                League bl1 = LeaguePersistenceService.getInstance().getCurrentLeagueByLeagueId("bl1");
                try {
                    Matchday currentMatchday = new MatchDayManager(bl1).getCurrentMatchday();
                    MatchdayPersistenceService.getInstance().save(currentMatchday);
                    bl1.setCurrentMatchday(currentMatchday);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable pointsCalculator = new Runnable() {
            @Override
            public void run() {
                //TODO calc für user
                MatchdayPointsCalculater.getInstance().updateUserPointsMatchday();
            }
        };

        schedular.addMatchEndSchedul(setCurrentMatchDay);
        schedular.addMatchEndSchedul(pointsCalculator);

    }
}
