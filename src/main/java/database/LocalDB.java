package database;

import java.sql.*;

public class LocalDB {
    String location;
    Connection connection;

    String query;
    ResultSet resultSet;

    public LocalDB(String location) {
        this.location = location;
        connect();
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(location);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connection to the local database has been established.");
    }

    // public void selectStatement(String[] cols, String table) {
    // DEBUG
    public void selectStatement(String table) {
        try (Statement statement = connection.createStatement()) {
            // query = "SELECT " + String.join(",", cols) + "FROM " + table;
            // DEBUG
            query = "SELECT * FROM " + table;
            resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                String text = resultSet.getString("name");
                System.out.println(text);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
