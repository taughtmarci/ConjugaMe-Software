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
    public final String VERB_TABLE = "Verbo";
    public final String WORD_TABLE = "Palabra";
    private final String BASIC_VERB_QUERY_PATH = "database/basicverbquery.sql";
    private final String COMPLEX_VERB_QUERY_PATH = "database/complexverbquery.sql";
    private final String WORD_QUERY_PATH = "database/wordquery.sql";

    private final String UPDATE_LEVELS_PATH = "database/updatelevels.sql";
    private final String RESET_LEVELS_PATH = "database/resetlevels.sql";

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

    public ArrayList<Verb> processVerbQueries(VerbQuizComponents comps) {
        ArrayList<Verb> result = new ArrayList<>();

        String queryDefault;
        if (comps.onlyParticipio()) queryDefault = ConfigIO.readSQL(BASIC_VERB_QUERY_PATH);
        else queryDefault = ConfigIO.readSQL(COMPLEX_VERB_QUERY_PATH);

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
            query = query.replace("[AMOUNT]", Integer.toString(comps.getWordAmount()));

            // make query
            result.addAll(buildVerbQuery(query, comps));
        }

        return result;
    }

    public ArrayList<Verb> buildVerbQuery(String query, VerbQuizComponents comps) {
        ArrayList<Verb> result = new ArrayList<>();
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    VerbBasic tempBasic = new VerbBasic(resultSet.getString("Infinitivo"));
                    if (comps.isParticipioPresentoSelected()) tempBasic.setPresento(resultSet.getString("Presento"));
                    if (comps.isParticipioPasadoSelected()) tempBasic.setPasado(resultSet.getString("Pasado"));

                    Verb temp = new Verb(resultSet.getInt("VerbID"), tempBasic);
                    for (int i = 0; i < 3; i++)
                        temp.addDefinition(resultSet.getString("Definici\uu00F3n_0" + (i + 1)));

                    ResultSetMetaData metaData = resultSet.getMetaData();

                    for (Form f : comps.getSelectedForms()) {
                        HashMap<Pronoun, String> tempContent = new HashMap<>();
                        int index = resultSet.findColumn("ID " + f.toString()) + 1;
                        for (int i = 0; i < comps.getSelectedPronouns().size(); i++) {
                            Pronoun columnName = Pronoun.fromString(metaData.getColumnName(index + i));
                            tempContent.put(columnName, resultSet.getString(index + i));
                        }
                        temp.appendVerbForm(f, tempContent);
                    }

                    // debug
                    temp.printVerb();
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

    public ArrayList<Word> processWordQueries(WordQuizComponents comps) {
        ArrayList<Word> result = new ArrayList<>();

        String queryDefault = ConfigIO.readSQL(WORD_QUERY_PATH);

        for (Group g : comps.getSelectedGroups()) {
            // replace group tables and amount
            String query = queryDefault.replace("[GROUP_TABLE]", "PALABRA_" + g.name());
            query = query.replace("[AMOUNT]", Integer.toString(comps.getWordAmount()));

            // make query
            result.addAll(buildWordQuery(query));
        }

        return result;
    }

    public ArrayList<Word> buildWordQuery(String query) {
        ArrayList<Word> result = new ArrayList<>();
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Word temp = new Word(resultSet.getInt("WordID"), resultSet.getString("Nombre_F"),
                            resultSet.getString("Nombre_M"), resultSet.getBoolean("IsNoun"));
                    for (int i = 0; i < 3; i++)
                        temp.addDefinition(resultSet.getString("Definici\uu00F3n_0" + (i + 1)));

                    // debug
                    //temp.printWord();
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

    public void executeUpdateQuery(String query) {
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                int result = statement.executeUpdate(query);
                // debug
                //System.out.println(result);
            } catch (SQLException e) {
                    MainWindow.dialog.showDialog("Adatb\u00E1zis friss\u00EDt\u00E9si hiba", "Sikertelen t\u00E1blafriss\u00EDt\u00E9s az"
                            + (onlineFlag ? " online " : " ") + "adatb\u00E1zisban.\n" + e.toString(), DialogType.ERROR);
                    connected = false;
                }
        }
    }

    public void updateLevels(String tablename, boolean isIncrement, ArrayList<Integer> ids) {
        String queryDefault = ConfigIO.readSQL(UPDATE_LEVELS_PATH);
        String query = queryDefault.replace("[TABLE_NAME]", tablename);

        int amount = isIncrement ? 3 : -1;
        query = query.replace("[AMOUNT]", Integer.toString(amount));

        StringBuilder conditions = new StringBuilder();
        final String supply = isIncrement ? "ID = " : "(Level > 0 AND ID = ";

        conditions.append(supply).append(ids.get(0));
        if (!isIncrement) conditions.append(")");
        ids.remove(0);

        for (int id : ids) {
            conditions.append(" OR ").append(supply).append(id);
            if (!isIncrement) conditions.append(")");
        }

        query = query.replace("[CONDITIONS]", conditions.toString());
        // debug
        //System.out.println(query);
        executeUpdateQuery(query);
    }
}