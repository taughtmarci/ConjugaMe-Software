package database;

import java.sql.*;

public class OnlineDB {
    String ip;
    String port;
    final String schemaname = "Dictionary";
    final String username = "admin";
    final String password = "ManchaT3!";
    String location;

    Connection connection;

    String query;
    ResultSet resultSet;

    public OnlineDB(String ip, String port) {
        this.location = "jdbc:mysql://" + ip + ":" + port + "/" + schemaname;
        connect();
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(location, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connection to the online database has been established.");
    }

    public void selectStatement(String table) {
        try (Statement statement = connection.createStatement()) {
            query = "SELECT * FROM " + table;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String text = resultSet.getString("sample");
                System.out.println(text);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
