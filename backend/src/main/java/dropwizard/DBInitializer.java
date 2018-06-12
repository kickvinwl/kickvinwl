package dropwizard;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.League;
import persistence.DatabaseDefaultData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBInitializer {

    private static String url = "jdbc:mysql://localhost";
    private static String username = "root";
    private static String password = "";

    public static void init() {
        String sqlString = "CREATE DATABASE IF NOT EXISTS kickvinwl";
        runstatement(sqlString);
        setupTables();
        
        DatabaseDefaultData.getInstance().generatetData();
    }

    public static void dropDatabase() {
        String sqlString = "DROP DATABASE kickvinwl";
        runstatement(sqlString);
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

    private static void setupLeague(){
        League l = new League();
        l.set
    }

}
