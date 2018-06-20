package util;

import entities.*;
import manager.MatchDayManager;
import persistence.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DBInitializer {

    private static String url = "jdbc:mysql://localhost";
    private static String username = "root";
    private static String password = "";

    public static void init() {
        String sqlString = "CREATE DATABASE IF NOT EXISTS kickvinwl";
        runstatement(sqlString);
        setupTables();
        DatabaseDefaultData.getInstance().generateData();
        setupVoting();
        setupMatchDays();
    }

    public static void dropDatabase() {
        String sqlString = "DROP DATABASE kickvinwl";
        runstatement(sqlString);
    }

    public static void loadTeams() {
        TeamDeserializer td = new TeamDeserializer();
        try {
            List<Team> teams = td.deserializeTeam("https://www.openligadb.de/api/getavailableteams/bl1/2018");
            for (Team team : teams) {
                System.out.println(team.toString());
            }
        } catch (Exception e) {
            System.out.println("===================================");
            System.out.println("ERROR ERROR ERROR ERROR ERROR ERROR");
            System.out.println("===================================");
            e.printStackTrace();
            System.out.println("===================================");
        }
    }

    public static void setupVoting() {

        User u = new User();
        u.setUserName("qwertz");
        u.setSessionKey("abc");
        u.setLastAction(new Date());
        UserPersistenceService.getInstance().save(u);

        Matchday d = new Matchday();
        d.setMatchday(5);
        MatchdayPersistenceService.getInstance().save(d);

        Match m = new Match();
        m.setTeam(TeamPersistenceService.getInstance().getByTeamId(65));
        m.setTeam2(TeamPersistenceService.getInstance().getByTeamId(81));
        MatchPersistenceService.getInstance().save(m);

        MatchTip mt = new MatchTip();
        mt.setOwner(u);
        mt.setTippedMatch(m);
        MatchTipPersistenceService.getInstance().save(mt);
    }

    public static void setupMatchDays() {
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

/*
    public static void generateAchievementTestData() {
        AchievementTestData.generateTestData();
    }
*/
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
