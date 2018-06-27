package util;

import entities.*;
import manager.MatchDayManager;
import manager.BundesligaTableManager;
import manager.MatchManager;
import manager.TeamManager;
import persistence.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBInitializer {

    private static String url = "jdbc:mysql://localhost";
    private static String username = "root";
    private static String password = "";

    public static void init() {

        League l;

        // database setup
        dropDatabase();
        createDatabase();
        setupTables();

        // load data
        l = generateLeague();
        loadTeams();
        generateUsers();
        loadMatchdays();
        generateAchievement();
        loadBundesligaTable();
        loadMatches(l);
    }

    private static void createDatabase() {
        String sqlString = "CREATE DATABASE IF NOT EXISTS kickvinwl";
        runstatement(sqlString);
    }

    private static void dropDatabase() {
        String sqlString = "DROP DATABASE kickvinwl";
        runstatement(sqlString);
    }

    private static void generateUsers() {
        User user = new User("qwertz", "t");
        UserPersistenceService.getInstance().save(user);
    }

	public static void generateAchievement()
	{
		AchievementPersistenceService aps = AchievementPersistenceService.getInstance();
		if(aps.hasEntries())
			return;

		Achievement ach = new Achievement();          
		ach.setTitle("Rookie");
		ach.setAchievementDescription("Sie haben es geschafft sich anzumelden");
		ach.setAchievementQuery("SELECT u FROM User u");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Fortuna");
		ach.setAchievementDescription("Erziele einen Punkt");
		ach.setAchievementQuery("SELECT mt.owner FROM MatchTip mt GROUP BY mt.owner HAVING SUM(mt.userPoints) > 1");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("I like where this is going");
		ach.setAchievementDescription("Erziele 123 Punkte");
		ach.setAchievementQuery("SELECT mt.owner FROM MatchTip mt GROUP BY mt.owner HAVING SUM(mt.userPoints) > 123");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Spartaaaa");
		ach.setAchievementDescription("Erziele 300 Punkte");
		ach.setAchievementQuery("SELECT mt.owner FROM MatchTip mt GROUP BY mt.owner HAVING SUM(mt.userPoints) > 300");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("You cant stop me");
		ach.setAchievementDescription("Erziele 600 Punkte");
		ach.setAchievementQuery("SELECT mt.owner FROM MatchTip mt GROUP BY mt.owner HAVING SUM(mt.userPoints) > 600");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Profitipper");
		ach.setAchievementDescription("Erziele 1234 Punkte");
		ach.setAchievementQuery("SELECT mt.owner FROM MatchTip mt GROUP BY mt.owner HAVING SUM(mt.userPoints) > 1234");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Been There, Rocked That");
		ach.setAchievementDescription("Alle Tendenzen an einem Spieltag richtig getippt");
		ach.setAchievementQuery("SELECT DISTINCT u FROM Matchday md INNER JOIN Match g ON g.matchday=md.id INNER JOIN MatchTip mt ON g.id=mt.tippedMatch INNER JOIN User u ON u.id=mt.owner GROUP BY md.id, mt.owner HAVING min(mt.userPoints)>0");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("I knew it");
		ach.setAchievementDescription("Ein perfekt getippter Spieltag");
		ach.setAchievementQuery("SELECT DISTINCT u FROM Matchday md INNER JOIN Match g ON g.matchday=md.id INNER JOIN MatchTip mt ON g.id=mt.tippedMatch INNER JOIN User u ON u.id=mt.owner GROUP BY md.id, mt.owner HAVING min(mt.userPoints)=4");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("A fresh start");
		ach.setAchievementDescription("Ein Spiel richtig getippt");
		ach.setAchievementQuery("SELECT DISTINCT u FROM Match g INNER JOIN MatchTip mt ON g.id=mt.tippedMatch INNER JOIN User u ON u.id=mt.owner GROUP BY mt.owner HAVING max(mt.userPoints)=4");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Miracles come when you least expect them");
		ach.setAchievementDescription("Spieltag ohne einen einzigen Punkt");
		ach.setAchievementQuery("SELECT DISTINCT u FROM Match g INNER JOIN MatchTip mt ON g.id=mt.tippedMatch INNER JOIN User u ON u.id=mt.owner GROUP BY mt.owner HAVING max(mt.userPoints)=0");
		aps.save(ach);


		/*
		 * 
		 *     Query works, hibernate Syntax error
		ach = new Achievement();
		ach.setTitle("Look what I can do!");
		ach.setAchievementDescription("Drei Spiele richtig getippt. (an einem Spieltag)");
		ach.setAchievementQuery("SELECT DISTINCT us FROM (SELECT u.id, mt.userPoints FROM Matchday md INNER JOIN Match g ON g.matchday=md.id INNER JOIN MatchTip mt ON g.id=mt.tippedMatch INNER JOIN User u ON u.id=mt.owner WHERE mt.userPoints=4 GROUP BY md.id, mt.owner) AS tbl INNER JOIN User us ON us.id=tbl.id GROUP BY tbl.id HAVING count(tbl.userPoints)>=3");
		aps.save(ach);
		ach = new Achievement();
		ach.setTitle("A Star is Born!");
		ach.setAchievementDescription("Fünf Spiele richtig getippt. (an einem Spieltag)");
		ach.setAchievementQuery("SELECT DISTINCT us FROM (SELECT u.id, mt.userPoints FROM Matchday md INNER JOIN Match g ON g.matchday=md.id INNER JOIN MatchTip mt ON g.id=mt.tippedMatch INNER JOIN User u ON u.id=mt.owner WHERE mt.userPoints=4 GROUP BY md.id, mt.owner) AS tbl INNER JOIN User us ON us.id=tbl.id GROUP BY tbl.id HAVING count(tbl.userPoints)>=5");
		aps.save(ach);
		 *
		 *
		 */


		/*  TODO
		 * 
		ach = new Achievement();
		ach.setTitle("Reach for the stars");
		ach.setAchievementDescription("Einen Spieltag als bester getippt");
		ach.setAchievementQuery("SELECT u.id FROM User u WHERE 1=2");
		aps.save(ach);
		ach = new Achievement();
		ach.setTitle("Legend");
		ach.setAchievementDescription("Gewinnen Sie 5 Tippspiele");
		ach.setAchievementQuery("SELECT u.id FROM User u WHERE 1=2");
		aps.save(ach);
		ach = new Achievement();
		ach.setTitle("Master");
		ach.setAchievementDescription("Gewinnen Sie 3 Tippspiele");
		ach.setAchievementQuery("SELECT u.id FROM User u WHERE 1=2");
		aps.save(ach);
		ach = new Achievement();   
		ach.setTitle("Tippsielsieger");
		ach.setAchievementDescription("Gewinnen Sie ein Tippspiele");
		ach.setAchievementQuery("SELECT u.id FROM User u WHERE 1=2");
		aps.save(ach);
		 *
		 */
	}

    private static League generateLeague()
    {
        League l = new League();
        l.setLeagueId("bl1");
        l.setSeason("2017");
        // TODO set current Matchday
        LeaguePersistenceService.getInstance().save(l);
        return l;
    }

    private static void loadMatches(League league) {
        MatchManager mm = new MatchManager(league);
        try {
            System.out.println("=====================");
            System.out.println("Persisting matches ...");
            mm.persistAllMatchesFromAPI();
            System.out.println("Persisting matches completed !!!");
        } catch (Exception e) {
            System.out.println("Failed to load Matches");
            e.printStackTrace();
        }
    }

    private static void loadBundesligaTable() {
        BundesligaTableManager blmanager = new BundesligaTableManager(
                LeaguePersistenceService.getInstance().getCurrentLeagueByLeagueId("bl1"));
        try {
            blmanager.updateData();
            System.out.println("SUCCESS: BUNDESLIGATABLE LOADED");
        } catch (Exception e) {
            System.out.println("ERROR: BUNDESLIGATABLE-GENERATION");
            e.printStackTrace();
        }
    }

    private static void loadTeams() {
        TeamManager tm = new TeamManager(LeaguePersistenceService.getInstance().getCurrentLeagueByLeagueId("bl1"));
        tm.persistTeams();
    }

    private static void loadMatchdays() {
        League league = LeaguePersistenceService.getInstance().getCurrentLeagueByLeagueId("bl1");
        MatchDayManager mdm = new MatchDayManager(league);
        try {
            List<Matchday> mds = mdm.getMatchDaysFromAPI();
            mds.forEach(matchday -> MatchdayPersistenceService.getInstance().save(matchday));
            System.out.println("Success");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    private static void setupTables() {
        //durch den Aufruf der Factory wird hibernate angesprochen - je nach
        //hibernate.hbm2ddl.auto -Value werden die DB-Tabellen erzeugt oder upgedated
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("kickvinwl");
        emf.createEntityManager();
    }

    private static void runstatement(String sqlString) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = connection.prepareStatement(sqlString)) {
            System.out.println("executing");
            boolean check = stmt.execute();
            System.out.println(check);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
