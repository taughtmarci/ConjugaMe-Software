package database;

import controller.DialogCommands.DoNothingCommand;
import controller.DialogCommands.ExitCommand;
import controller.QuizComponents;
import model.*;
import view.MainWindow;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

abstract class Database {
    public boolean onlineFlag;
    protected String username;
    protected String password;

    protected String location;
    protected Connection connection;
    public boolean connected = false;

    private final String mainTable = "Verbo";
    protected String randomKeyword;

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
            // TODO polírozni :D
            if (onlineFlag) connection = DriverManager.getConnection(location, username, password);
            else connection = DriverManager.getConnection(location);
            connected = true;
        } catch (SQLException e) {
            String errorMessage = "Kapcsol\u00F3d\u00E1s az online adatb\u00E1zishoz sikertelen.\n" +
                    "Szeretn\u00E9d elind\u00EDtani az alkalmaz\u00E1st offline m\u00F3dban?\n";
            String errorTitle = "Kapcsol\u00F3d\u00E1si hiba";
            MainWindow.dialog.showYesNoDialog(errorTitle, errorMessage, DialogType.WARNING, new DoNothingCommand(), new ExitCommand());
            connected = false;
        }
    }

    public ArrayList<Verb> processQuery(String query, QuizComponents components) {
        ArrayList<Verb> result = new ArrayList<>();
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    VerbBasic tempBasic = new VerbBasic(resultSet.getString("Infinitivo"));
                    if (components.isParticipioPresentoSelected()) tempBasic.setPresento(resultSet.getString("Presento"));
                    if (components.isParticipioPasadoSelected()) tempBasic.setPasado(resultSet.getString("Pasado"));

                    Verb temp = new Verb(tempBasic);
                    HashMap<Pronoun, String> tempContent = new HashMap<>();
                    ResultSetMetaData metaData = resultSet.getMetaData();

                    for (Form f : components.getSelectedForms()) {
                        int index = resultSet.findColumn("ID " + f.toString()) + 2;
                        for (int i = 0; i < 7; i++) {
                            Pronoun columnName = Pronoun.fromString(metaData.getColumnName(index + i));
                            tempContent.put(columnName, resultSet.getString(index + i));
                        }
                        temp.appendVerbForm(f, tempContent);
                    }

                    result.add(temp);
                }
            } catch (SQLException e) {
                MainWindow.dialog.showDialog("Adatb\u00E1zis lek\u00E9rdez\u00E9si hiba", "Sikertelen lek\u00E9rdez\u00E9s az"
                        + (onlineFlag ? " online " : " ") + "adatb\u00E1zisb\u00F3l.\n" + e.toString(), DialogType.ERROR);
                connected = false;
            }
        }
        return result;
    }

    public String buildQuery(QuizComponents components) {
        StringBuilder queryBuilder = new StringBuilder();

        // select part
        queryBuilder.append("SELECT Verbo.Infinitivo, ");
        if (components.isParticipioPresentoSelected())
            queryBuilder.append("Verbo.Presento, ");
        if (components.isParticipioPasadoSelected())
            queryBuilder.append("Verbo.Pasado, ");
        for (Form f : components.getSelectedForms())
            queryBuilder.append("`").append(f.toString()).append("`.*, ");
        queryBuilder.append("''\n");

        // from part
        queryBuilder.append("FROM ");
        if (!components.onlyParticipio()) {
            for (int i = 0; i < components.getSelectedForms().size(); i++)
                queryBuilder.append("(");

            queryBuilder.append("Verbo ");
            for (Form f : components.getSelectedForms()) {
                queryBuilder.append("INNER JOIN `").append(f.toString()).append("` on Verbo.ID = ");
                queryBuilder.append("`").append(f.toString()).append("`.VerbID)\n");
            }
        }
        else queryBuilder.append("Verbo\n");
        //remaining parts
        queryBuilder.append("ORDER BY ").append(randomKeyword).append("\nlimit ").append(components.getNumberOfVerbs()).append(";");

        // debug
        System.out.println(queryBuilder.toString());
        return queryBuilder.toString();
    }
}