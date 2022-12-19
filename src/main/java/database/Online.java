package database;

import model.DialogType;
import view.MainWindow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static controller.DialogCommands.*;

public class Online extends Database {

    public Online(String ip, String port, String schema) {
        super(true, "jdbc:mysql://" + ip + ":" + port + "/" + schema);
        setCredentials("admin", "ManchaT3!");
        this.randomKeyword = "RAND()";
        connect();
        boolean updatesAvailable = getUpdates();
        if (updatesAvailable) System.out.println("Updates are available");
    }

    public boolean getUpdates() {
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                String query = "SELECT * FROM UpdatesInfo ORDER BY ID DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(query);

                float queryVersion = MainWindow.config.getDatabaseVersion();
                while (resultSet.next())
                    queryVersion = resultSet.getFloat("Version");

                if (queryVersion > MainWindow.config.getDatabaseVersion()) {
                    String message = """
                        Adatb\u00E1zis friss\u00EDt\u00E9s el\u00E9rhet\u0151.
                        Szeretn\u00E9d let\u00F6lteni?
                        """;
                    String errorTitle = "Friss\u00EDt\u00E9s el\u00E9rhet\u0151";
                    MainWindow.dialog.showYesNoDialog(errorTitle, message, DialogType.QUESTION, new DoNothingCommand(), new OfflineModeCommand());

                    if (!MainWindow.config.isOfflineMode()) {
                        doUpdates();
                        return true;
                    } else {
                        System.out.println("oh");
                    }
                }
            } catch (SQLException e) {
                String errorMessage = """
                        Kapcsol\u00F3d\u00E1s az online adatb\u00E1zishoz sikertelen.
                        Szeretn\u00E9d \u00E1t\u00E1ll\u00EDtani az alkalmaz\u00E1st offline m\u00F3dba?
                        """ + e.toString();
                String errorTitle = "Kapcsol\u00F3d\u00E1si hiba";
                MainWindow.dialog.showYesNoDialog(errorTitle, errorMessage, DialogType.QUESTION, new OfflineModeCommand(), new ExitCommand());
                connected = false;
            }
        }

        return false;
    }

    public void doUpdates() {
        String query = """
                SELECT\s
                    Infinitivo, Presento, Pasado
                FROM
                    ConjugaMe.Verbo
                WHERE
                    Status = 'New'\s
                INTO OUTFILE 'database/downloaded.csv'\s
                FIELDS ENCLOSED BY '"'\s
                TERMINATED BY ';'\s
                ESCAPED BY '"'\s
                LINES TERMINATED BY '\\r\\n';
                """;

        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query);
        } catch (SQLException e) {
            String errorMessage = """
                        Kapcsol\u00F3d\u00E1s az online adatb\u00E1zishoz sikertelen.
                        Szeretn\u00E9d \u00E1t\u00E1ll\u00EDtani az alkalmaz\u00E1st offline m\u00F3dba?
                        """ + e.toString();
            String errorTitle = "Kapcsol\u00F3d\u00E1si hiba";
            MainWindow.dialog.showYesNoDialog(errorTitle, errorMessage, DialogType.QUESTION, new OfflineModeCommand(), new ExitCommand());
            connected = false;
        }
    }

}
