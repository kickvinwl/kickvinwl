package dropwizard;

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
    }

}
