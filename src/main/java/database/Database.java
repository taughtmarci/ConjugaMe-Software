package database;

import model.DialogType;
import model.VerbBasic;
import view.MainWindow;

import java.sql.*;
import java.util.ArrayList;

abstract class Database {
    public boolean onlineFlag;
    protected String username;
    protected String password;

    protected String location;
    protected Connection connection;
    public boolean connected = false;

    protected static String query;
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

    public ArrayList<VerbBasic> essentialQuery(String table, boolean gerundioFlag, boolean participioFlag, int limit, boolean randomFlag)  {
        ArrayList<VerbBasic> result = new ArrayList<>();
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                query = "select Infinitivo, " + (gerundioFlag ? "Gerundio, " : "'', ") + (participioFlag ? "Participio " : "'' ");
                query += " from " + table + (randomFlag ? " order by rand()" : "")
                        + (limit > 0 ? " limit " + limit : "") + ";";
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    VerbBasic temp = new VerbBasic(resultSet.getString("Infinitivo"));
                    if (gerundioFlag) temp.setGerundio(resultSet.getString("Gerundio"));
                    if (participioFlag) temp.setParticipio(resultSet.getString("Participio"));
                    result.add(temp);
                }
            } catch (SQLException e) {
                MainWindow.dialog.showDialog("Adatbázis lekérdezési hiba", "Sikertelen lekérdezés a"
                        + (onlineFlag ? "z online " : " ") + " adatbázisból.\n" + e.toString(), DialogType.ERROR);
                connected = false;
            }
        }
        return result;
    }

}
