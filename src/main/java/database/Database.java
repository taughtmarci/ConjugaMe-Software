package database;

import controller.ConfigIO;
import controller.DialogCommands.DoNothingCommand;
import controller.DialogCommands.ExitCommand;
import model.VerbQuizComponents;
import model.*;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;
import view.MainWindow;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

abstract class Database {
    private final String MAINTABLE = "Verbo";
    private final String BASICVERBQUERYPATH = "config/basicverbquery.sql";
    private final String COMPLEXVERBQUERYPATH = "config/complexverbquery.sql";

    public boolean onlineFlag;
    protected String username;
    protected String password;

    protected String location;
    protected Connection connection;
    public boolean connected = false;

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
        if (onlineFlag) {
            try {
                connection = DriverManager.getConnection(location, username, password);
            } catch (SQLException e) {
                String errorMessage = """
                        Kapcsol\u00F3d\u00E1s az online adatb\u00E1zishoz sikertelen.
                        Szeretn\u00E9d elind\u00EDtani az alkalmaz\u00E1st offline m\u00F3dban?
                        """;
                String errorTitle = "Kapcsol\u00F3d\u00E1si hiba";
                MainWindow.dialog.showYesNoDialog(errorTitle, errorMessage, DialogType.QUESTION, new DoNothingCommand(), new ExitCommand());
                connected = false;
            }
        } else {
            try{
                SQLiteConfig config = new SQLiteConfig();
                config.resetOpenMode(SQLiteOpenMode.CREATE);
                connection = DriverManager.getConnection(location, config.toProperties());
            } catch (SQLException e) {
                String errorMessage = """
                        Kapcsol\u00F3d\u00E1s a lok\u00E1lis adatb\u00E1zishoz sikertelen.
                        K\u00E9rj\u00FCk telep\u00EDtsd \u00FAjra a programot.
                        """;
                String errorTitle = "Kapcsol\u00F3d\u00E1si hiba";
                MainWindow.dialog.showDialog(errorTitle, errorMessage, DialogType.ERROR);
                System.exit(0);
            }
        }
        connected = true;
    }

    public ArrayList<Verb> buildQuery(String query, VerbQuizComponents comps) {
        ArrayList<Verb> result = new ArrayList<>();
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    VerbBasic tempBasic = new VerbBasic(resultSet.getString("Infinitivo"));
                    if (comps.isParticipioPresentoSelected()) tempBasic.setPresento(resultSet.getString("Presento"));
                    if (comps.isParticipioPasadoSelected()) tempBasic.setPasado(resultSet.getString("Pasado"));

                    Verb temp = new Verb(tempBasic);
                    HashMap<Pronoun, String> tempContent = new HashMap<>();
                    ResultSetMetaData metaData = resultSet.getMetaData();

                    for (Form f : comps.getSelectedForms()) {
                        int index = resultSet.findColumn("ID " + f.toString()) + 2;
                        for (int i = 0; i < comps.getSelectedPronouns().size(); i++) {
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

    public ArrayList<Verb> processQueries(VerbQuizComponents comps) throws IOException {
        ArrayList<Verb> result = new ArrayList<>();

        ConfigIO config = new ConfigIO();
        String queryDefault;
        if (comps.onlyParticipio()) queryDefault = config.readSQL(BASICVERBQUERYPATH);
        else queryDefault = config.readSQL(COMPLEXVERBQUERYPATH);

        for (Group g : comps.getSelectedGroups()) {
            // replace group tables
            String query = queryDefault.replace("[GROUP_TABLE]", "GRUPO_" + g.name());

            if (!comps.onlyParticipio()) {
                // replace form and pronoun selects
                StringBuilder formSelects = new StringBuilder();
                for (Form f : comps.getSelectedForms()) {
                    StringBuilder temp = new StringBuilder();
                    String formTemp = "\"" + f.toString() + "\".\"ID " + f.toString() + "\",\n";
                    temp.append(formTemp);

                    for (Pronoun p : comps.getSelectedPronouns()) {
                        String pronounTemp = "\"" + f.toString() + "\".\"" + p.toString() + "\",\n";
                        temp.append(pronounTemp);
                    }
                    formSelects.append(temp);
                }
                query = query.replace("[FORMS_AND_PRONOUNS]", formSelects.toString());

                // replace form inner joins
                StringBuilder formJoins = new StringBuilder();
                for (Form f : comps.getSelectedForms()) {
                    String temp = "INNER JOIN \"" + f.toString() + "\" ON \"" + f.toString() + "\".\"VerbID\" = \"Verbo\".\"ID\"\n";
                    formJoins.append(temp);
                }
                query = query.replace("[FORM_INNER_JOINS]", formJoins.toString());
            }

            // replace amount
            query = query.replace("[AMOUNT]", Integer.toString(comps.getNumberOfVerbs()));

            // debug
            System.out.println("QUERY:\n" + query);

            // make query
            result.addAll(buildQuery(query, comps));
        }

        return result;
    }
}