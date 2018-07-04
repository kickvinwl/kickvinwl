package dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import persistence.AchievementsChecker;
import persistence.LeaderboardPersistenceService;
import manager.MatchdayPointsCalculator;
import persistence.MatchTipPersistenceService;
import resources.*;
import util.DBInitializer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Die Hauptklasse der Anwendung.
 * Hier werden Grundeinstellungen vorgenommen und der grundlegende Aufbau der Anwendung durchgeführt.
 * Dazu zählen das Aufsetzen einer Datenbank, das erstellen von Testdaten sowie das Laden und Persistieren der notwendigen Daten
 * aus der externen API.
 */
public class KickVinWlApplication extends Application<KickVinWlConfiguration> {

    /**
     * Methode um die Anwendung zu starten
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new KickVinWlApplication().run(args);
    }

    /**
     * Vornehmen von notwendigen Konfiguration im Zusammenhang mit dem gegebenem Proxy der KVWL
     * @param bootstrap
     */
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

    /**
     * Logik zum starten der Anwendung, in welcher die einzelnen Konfigurationsmethoden aufgerufen
     * und die einzelnen Elemente der Webanwendung registriert werden.
     * @param configuration
     * @param environment Umgebung in welcher die Webanwendung betrieben wird. Auf dieser werden die einzelnen Webresourcen registriert.
     * @throws Exception
     */
    @Override
    public void run(KickVinWlConfiguration configuration, Environment environment) throws Exception {
        MatchTipPersistenceService.getInstance();
        LeaderboardPersistenceService.getInstance();

        // Funktion zum aufsetzen der Datenbank und Testdaten beim Applikationsstart
        DBInitializer.init();

        // Funktion zum verteilen von Erfolgen/Achievements beim Applikationsstart (später als reguläre Schedulermethode)
        AchievementsChecker ac = new AchievementsChecker();
        ac.check();

        /*
            Registrieren der einzelnen Resourcen auf dem Web-Environment
         */
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

        // Starten der Scheduler
        schedulingJobs();
    }

    /**
     * Initialisieren und starten der Scheduler
     * - Überprüfung der UserPoints für einen Matchday alle 5 Minuten // TODO change to once per week
     */
    private void schedulingJobs() {
        final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                MatchdayPointsCalculator.getInstance().calculateUserPointsWithTips();
                MatchdayPointsCalculator.getInstance().updateUserPointsMatchday();
            }
        }, 1, 5, TimeUnit.MINUTES);
    }
}
