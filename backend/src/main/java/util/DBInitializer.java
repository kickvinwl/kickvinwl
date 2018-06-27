package util;

import entities.*;
import manager.MatchDayManager;
import manager.BundesligaTableManager;
import manager.MatchManager;
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
        String sqlString = "CREATE DATABASE IF NOT EXISTS kickvinwl";
        runstatement(sqlString);
        setupTables();
        TeamPersistenceService.persistTeams();
        League l = genLeague();
        genUsers();
        loadMatchdays();
        genMatches();
        genAchivement();
        generateBundesligaTable();
        loadMatches(l);
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
        List<Team> teams = TeamPersistenceService.getInstance().getAllTeams();

        User us = new User("qwertz_tipper", "t_tipper");
        UserPersistenceService.getInstance().save(us);
        Team team1 = teams.get(0);
        Team team2 = teams.get(1);


        Matchday md = LeaguePersistenceService.getInstance().getCurrentLeagueByLeagueId("bl1").getCurrentMatchday();

        Match match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        match.setGoalsTeam1(1);
        match.setGoalsTeam2(1);
        MatchPersistenceService.getInstance().save(match);

        //MatchTip
        MatchTip matchTip = new MatchTip();
        matchTip.setOwner(us);
        matchTip.setTippedMatch(match);
        matchTip.setGoalsTeam1(1);
        matchTip.setGoalsTeam2(1);
        MatchTipPersistenceService.getInstance().save(matchTip);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        match.setGoalsTeam1(2);
        match.setGoalsTeam2(2);
        MatchPersistenceService.getInstance().save(match);

        //MatchTip
        matchTip = new MatchTip();
        matchTip.setOwner(us);
        matchTip.setTippedMatch(match);
        matchTip.setGoalsTeam1(1);
        matchTip.setGoalsTeam2(1);
        MatchTipPersistenceService.getInstance().save(matchTip);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        match.setGoalsTeam1(2);
        match.setGoalsTeam2(1);
        MatchPersistenceService.getInstance().save(match);

        //MatchTip
        matchTip = new MatchTip();
        matchTip.setOwner(us);
        matchTip.setTippedMatch(match);
        matchTip.setGoalsTeam1(2);
        matchTip.setGoalsTeam2(1);
        MatchTipPersistenceService.getInstance().save(matchTip);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        match.setGoalsTeam1(2);
        match.setGoalsTeam2(1);
        MatchPersistenceService.getInstance().save(match);

        matchTip = new MatchTip();
        matchTip.setOwner(us);
        matchTip.setTippedMatch(match);
        matchTip.setGoalsTeam1(1);
        matchTip.setGoalsTeam2(0);
        MatchTipPersistenceService.getInstance().save(matchTip);

        md = new Matchday();
        md.setMatchday(26);
        MatchdayPersistenceService.getInstance().save(md);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        match.setGoalsTeam1(1);
        match.setGoalsTeam2(0);
        MatchPersistenceService.getInstance().save(match);

        matchTip = new MatchTip();
        matchTip.setOwner(us);
        matchTip.setTippedMatch(match);
        matchTip.setGoalsTeam1(0);
        matchTip.setGoalsTeam2(1);
        MatchTipPersistenceService.getInstance().save(matchTip);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        match.setGoalsTeam1(1);
        match.setGoalsTeam2(0);
        MatchPersistenceService.getInstance().save(match);

        matchTip = new MatchTip();
        matchTip.setOwner(us);
        matchTip.setTippedMatch(match);
        matchTip.setGoalsTeam1(0);
        matchTip.setGoalsTeam2(0);
        MatchTipPersistenceService.getInstance().save(matchTip);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        match.setGoalsTeam1(5);
        match.setGoalsTeam2(0);
        MatchPersistenceService.getInstance().save(match);

        matchTip = new MatchTip();
        matchTip.setOwner(us);
        matchTip.setTippedMatch(match);
        matchTip.setGoalsTeam1(80);
        matchTip.setGoalsTeam2(0);
        MatchTipPersistenceService.getInstance().save(matchTip);

        md = new Matchday();
        md.setMatchday(27);
        MatchdayPersistenceService.getInstance().save(md);

        match = new Match();
        match.setMatchday(md);
        match.setTeam(team1);
        match.setTeam2(team2);
        MatchPersistenceService.getInstance().save(match);

        matchTip = new MatchTip();
        matchTip.setOwner(us);
        matchTip.setTippedMatch(match);
        match.setGoalsTeam1(8);
        match.setGoalsTeam2(9);
        matchTip.setGoalsTeam1(7);
        matchTip.setGoalsTeam2(6);
        MatchTipPersistenceService.getInstance().save(matchTip);

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

    public static void genAchivement()
    {
        AchievementPersistenceService aps = AchievementPersistenceService.getInstance();

        Achievement ach = new Achievement();
        ach.setTitle("Rookie");
        ach.setAchievementDescription("Sie haben es geschafft sich anzumelden");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("Fortuna");
        ach.setAchievementDescription("Erziele einen Punkt");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("I like where this is going");
        ach.setAchievementDescription("Erziele 123 Punkte");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("Spartaaaa");
        ach.setAchievementDescription("Erziele 300 Punkte");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("You cant stop me");
        ach.setAchievementDescription("Erziele 600 Punkte");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("Profitipper");
        ach.setAchievementDescription("Erziele 1234 Punkte");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("Been There� Rocked That");
        ach.setAchievementDescription("Alle Tendenzen an einem Spieltag richtig getippt");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("I knew it");
        ach.setAchievementDescription("Ein perfekt getippter Spieltag");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("A fresh start");
        ach.setAchievementDescription("Ein Spiel richtig getippt");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("Look what I can do!");
        ach.setAchievementDescription("Drei Spiele richtig getippt. (an einem Spieltag)");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("A Star is Born!");
        ach.setAchievementDescription("F�nf Spiele richtig getippt. (an einem Spieltag)");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("Reach for the stars");
        ach.setAchievementDescription("Einen Spieltag als bester getippt");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("Miracles come when you least expect them");
        ach.setAchievementDescription("Spieltag ohne einen einzigen Punkt");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("Legend");
        ach.setAchievementDescription("Gewinnen Sie 5 Tippspiele");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("Master");
        ach.setAchievementDescription("Gewinnen Sie 3 Tippspiele");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("Tippsielsieger");
        ach.setAchievementDescription("Gewinnen Sie ein Tippspiele");
        aps.save(ach);
    }

    public static League genLeague()
    {
        Matchday md = new Matchday();
        md.setMatchday(27);
        MatchdayPersistenceService.getInstance().save(md);

        League l = new League();
        l.setLeagueId("bl1");
        l.setSeason("2017");
        //l.setCurrentMatchday(md);
        LeaguePersistenceService.getInstance().save(l);
        return l;
    }

    public static void loadMatches(League league) {
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

    public static void generateBundesligaTable() {
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

    public static void loadMatchdays() {
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
