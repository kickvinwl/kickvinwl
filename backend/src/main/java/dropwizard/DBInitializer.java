package dropwizard;

import persistence.AchievementTestData;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBInitializer {

    public static void init() {
        String url = "jdbc:mysql://localhost";
        String username = "root";
        String sqlString = "CREATE DATABASE IF NOT EXISTS kickvinwl";
        System.out.println("setup database");
        try (Connection connection = DriverManager.getConnection(url, username, "");
             PreparedStatement stmt = connection.prepareStatement(sqlString)) {
            System.out.println("executing");
            boolean check = stmt.execute();
            System.out.println(check);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //durch den Aufruf der Factory wird hibernate angesprochen - je nach
        //hibernate.hbm2ddl.auto -Value werden die DB-Tabellen erzeugt oder upgedated
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("kickvinwl");
        emf.createEntityManager();

        //AchievementTestData.generateTestData();
    }

}
