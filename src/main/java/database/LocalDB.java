package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocalDB {

    public static void connect() {
        Connection c = null;

        try {
            String location = "relativePath?";
            c = DriverManager.getConnection(location);
            System.out.println("Connection to the local database has been established.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
