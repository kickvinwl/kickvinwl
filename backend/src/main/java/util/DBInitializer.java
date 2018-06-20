package util;

import entities.Match;
import entities.Matchday;
import entities.Team;
import entities.User;
import persistence.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    }

    public static void dropDatabase() {
        String sqlString = "DROP DATABASE kickvinwl";
        runstatement(sqlString);
    }

    public static void genUsers() {
        User user = new User("qwertz", "t");

        UserPersistenceService.getInstance().save(user);
    }

    public static void genMatches(){
        Team team1 = new Team();
        team1.setTeamName("Team 1");
        Team team2 = new Team();
        team2.setTeamName("Team 2");
        TeamPersistenceService.getInstance().save(team1);
        TeamPersistenceService.getInstance().save(team2);


        Matchday md = new Matchday();
        md.setMatchday(1);
        MatchdayPersistenceService.getInstance().save(md);

        Match match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        MatchPersistenceService.getInstance().save(match);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        MatchPersistenceService.getInstance().save(match);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        MatchPersistenceService.getInstance().save(match);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        MatchPersistenceService.getInstance().save(match);

        md = new Matchday();
        md.setMatchday(26);
        MatchdayPersistenceService.getInstance().save(md);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        MatchPersistenceService.getInstance().save(match);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        MatchPersistenceService.getInstance().save(match);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        MatchPersistenceService.getInstance().save(match);

        md = new Matchday();
        md.setMatchday(27);
        MatchdayPersistenceService.getInstance().save(md);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        MatchPersistenceService.getInstance().save(match);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        MatchPersistenceService.getInstance().save(match);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        MatchPersistenceService.getInstance().save(match);
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
