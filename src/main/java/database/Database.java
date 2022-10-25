package database;

import model.DialogType;
import view.MainWindow;

import java.sql.*;

abstract class Database {

    public boolean onlineFlag;
    protected String location;
    protected Connection connection;

    public boolean connected = false;
    protected String username;
    protected String password;

    protected String query;
    protected ResultSet resultSet;

    public Database(boolean onlineFlag, String location) {
        this.onlineFlag = onlineFlag;
        this.location = location;
    }

    public void setCredentials(String username, String password) {
        if (onlineFlag) {
            this.username = username;
            this.password = password;
        }
    }

    public void connect() {
        try {
            if (onlineFlag) connection = DriverManager.getConnection(location, username, password);
            else connection = DriverManager.getConnection(location);
            connected = true;
        } catch (SQLException e) {
            MainWindow.dialog.showDialog("Kapcsolódási hiba", "Kapcsolódás a" + (onlineFlag ? "z online " : " ")
                    + "hálozathoz, sikertelen.\n" + e.toString(), DialogType.WARNING);
            connected = false;
        }
    }

    public void testDatabase(String table, String column) {
        try (Statement statement = connection.createStatement()) {
            query = "SELECT * FROM " + table;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String text = resultSet.getString(column);
                System.out.println(text);
            }
        } catch (SQLException e) {
            MainWindow.dialog.showDialog("Test error", "Test on" + (onlineFlag ? " online " : " ")
                    + " database has failed.\n" + e.toString(), DialogType.ERROR);
        }
    }
}
