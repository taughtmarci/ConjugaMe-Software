package database;

import controller.ConfigIO;
import controller.DialogCommands;
import controller.DialogCommands.OfflineModeCommand;
import controller.DialogCommands.ExitCommand;
import model.VerbQuizComponents;
import model.*;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;
import view.MainWindow;

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

    private final String WORD_REVISION_PATH = "database/wordrevisionquery.sql";
    private final String VERB_REVISION_PATH = "database/verbrevisionquery.sql";

    public final String VERBSCORE_NORMAL_TABLE = "VERBOSCORE_Normal";
    public final String VERBSCORE_TIMED_TABLE = "VERBOSCORE_Timed";
    public final String NOUNSCORE_NORMAL_TABLE = "PALABRASCORE_Normal";
    public final String NOUNSCORE_TIMED_TABLE = "PALABRASCORE_Timed";

    private final String SCORE_QUERY_PATH = "database/scorequery.sql";
    private final String INSERT_NOUNSCORE_PATH = "database/insertnounscore.sql";
    private final String INSERT_VERBSCORE_PATH = "database/insertverbscore.sql";
    private final String RESET_SCORES_PATH = "database/resetscores.sql";

    private final String BADGE_VERB_PATH = "database/badgeverbgroupquery.sql";

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

    public boolean connect() {
        boolean result = false;
        DriverManager.setLoginTimeout(10);

        if (onlineFlag) {
            try {
                connection = DriverManager.getConnection(location, username, password);
                result = true;
            } catch (SQLException e) {
                String errorMessage = """
                        Kapcsol\u00F3d\u00E1s az online adatb\u00E1zishoz sikertelen.
                        Szeretn\u00E9d elind\u00EDtani az alkalmaz\u00E1st offline m\u00F3dban?
                        """;
                String errorTitle = "Kapcsol\u00F3d\u00E1si hiba";
                MainWindow.dialog.showYesNoDialog(errorTitle, errorMessage, DialogType.QUESTION, new OfflineModeCommand(), new ExitCommand());
                result = false;
            }
        } else {
            try {
                Class.forName("org.sqlite.JDBC");
                SQLiteConfig config = new SQLiteConfig();
                config.resetOpenMode(SQLiteOpenMode.CREATE);
                connection = DriverManager.getConnection(location, config.toProperties());
                result = true;
            } catch (SQLException | ClassNotFoundException e) {
                String errorMessage = """
                        Kapcsol\u00F3d\u00E1s a lok\u00E1lis adatb\u00E1zishoz sikertelen.
                        K\u00E9rj\u00FCk telep\u00EDtsd \u00FAjra a programot.
                        R\u00E9szletek:
                        """ + e.toString();
                String errorTitle = "Kapcsol\u00F3d\u00E1si hiba";
                MainWindow.dialog.showExceptionDialog(errorTitle, errorMessage, DialogType.ERROR);
                System.exit(3);
            }
        }
        return result;
    }

    public int buildCountQuery(String query, String columnLabel) {
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                return resultSet.getInt(columnLabel);
            } catch (SQLException e) {
                MainWindow.dialog.showExceptionDialog("Adatb\u00E1zis lek\u00E9rdez\u00E9si hiba", "Sikertelen lek\u00E9rdez\u00E9s az"
                        + (onlineFlag ? " online " : " ") + "adatb\u00E1zisb\u00F3l.\n" + e.toString(), DialogType.ERROR);
                connected = false;
            }
        }
        return -1;
    }

    public int processVerbCountQuery(String tableName) {
        String queryDefault = ConfigIO.readSQL(BADGE_VERB_PATH);
        String query = queryDefault.replace("[GROUP_TABLE]", "GRUPO_" + tableName);
        return buildCountQuery(query, "COUNT(VerbID)");
    }

    public ArrayList<Verb> processVerbQueries(VerbQuizComponents comps) {
        ArrayList<Verb> result = new ArrayList<>();

        String queryDefault;
        if (comps.onlyParticipio()) queryDefault = ConfigIO.readSQL(BASIC_VERB_QUERY_PATH);
        else queryDefault = ConfigIO.readSQL(COMPLEX_VERB_QUERY_PATH);

        int amountPerGroup;
        if (comps.isNormal()) {
            amountPerGroup = comps.getWordAmount() / comps.getSelectedGroups().size();
            amountPerGroup = comps.getWordAmount() % 2 == 0 ? amountPerGroup : amountPerGroup + 1;
        } else amountPerGroup = comps.getDuration();

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
            query = query.replace("[AMOUNT]", Integer.toString(amountPerGroup));

            // make query
            result.addAll(buildVerbQuery(query, comps));
        }

        return result;
    }

    public ArrayList<VerbBasic> buildBasicVerbQuery(String query) {
        ArrayList<VerbBasic> result = new ArrayList<>();
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    VerbBasic tempBasic = new VerbBasic(resultSet.getInt("VerbID"), resultSet.getString("Infinitivo"));
                    for (int i = 0; i < 3; i++)
                        tempBasic.addDefinition(resultSet.getString("Definici\uu00F3n_0" + (i + 1)));
                    result.add(tempBasic);
                }
            } catch (SQLException e) {
                MainWindow.dialog.showExceptionDialog("Adatb\u00E1zis lek\u00E9rdez\u00E9si hiba", "Sikertelen lek\u00E9rdez\u00E9s az"
                        + (onlineFlag ? " online " : " ") + "adatb\u00E1zisb\u00F3l.\n" + e.toString(), DialogType.ERROR);
                connected = false;
            }
        }
        return result;
    }

    public ArrayList<Verb> buildVerbQuery(String query, VerbQuizComponents comps) {
        ArrayList<Verb> result = new ArrayList<>();
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    VerbBasic tempBasic = new VerbBasic(resultSet.getInt("VerbID"), resultSet.getString("Infinitivo"));
                    if (comps.isParticipioPresenteSelected()) tempBasic.setPresento(resultSet.getString("Presento"));
                    if (comps.isParticipioPasadoSelected()) tempBasic.setPasado(resultSet.getString("Pasado"));

                    Verb temp = new Verb(tempBasic);
                    for (int i = 0; i < 3; i++)
                        temp.getBasic().addDefinition(resultSet.getString("Definici\uu00F3n_0" + (i + 1)));

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
                MainWindow.dialog.showExceptionDialog("Adatb\u00E1zis lek\u00E9rdez\u00E9si hiba", "Sikertelen lek\u00E9rdez\u00E9s az"
                        + (onlineFlag ? " online " : " ") + "adatb\u00E1zisb\u00F3l.\n" + e.toString(), DialogType.ERROR);
                connected = false;
            }
        }
        return result;
    }

    public ArrayList<Word> processWordQueries(WordQuizComponents comps) {
        ArrayList<Word> result = new ArrayList<>();

        String queryDefault = ConfigIO.readSQL(WORD_QUERY_PATH);

        int amountPerGroup;
        if (comps.isNormal()) {
            amountPerGroup = comps.getWordAmount() / comps.getSelectedGroups().size();
            amountPerGroup = comps.getWordAmount() % 2 == 0 ? amountPerGroup : amountPerGroup + 1;
        } else amountPerGroup = comps.getDuration();

        for (Group g : comps.getSelectedGroups()) {
            // replace group tables and amount
            String query = queryDefault.replace("[GROUP_TABLE]", "PALABRA_" + g.name());
            query = query.replace("[AMOUNT]", Integer.toString(amountPerGroup));

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
                MainWindow.dialog.showExceptionDialog("Adatb\u00E1zis lek\u00E9rdez\u00E9si hiba", "Sikertelen lek\u00E9rdez\u00E9s az"
                        + (onlineFlag ? " online " : " ") + "adatb\u00E1zisb\u00F3l.\n" + e.toString(), DialogType.ERROR);
                connected = false;
            }
        }
        return result;
    }

    public ArrayList<VerbBasic> processVerbRevisionQuery(Group group) {
        String queryDefault;
        queryDefault = ConfigIO.readSQL(VERB_REVISION_PATH);
        String query = queryDefault.replace("[GROUP_TABLE]", "GRUPO_" + group.name());
        return buildBasicVerbQuery(query);
    }

    public ArrayList<Word> processWordRevisionQuery(Group group) {
        String queryDefault;
        queryDefault = ConfigIO.readSQL(WORD_REVISION_PATH);
        String query = queryDefault.replace("[GROUP_TABLE]", "PALABRA_" + group.name());
        return buildWordQuery(query);
    }

    public void executeUpdateQuery(String query) {
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                int result = statement.executeUpdate(query);
                // debug
                //System.out.println(result);
            } catch (SQLException e) {
                    MainWindow.dialog.showExceptionDialog("Adatb\u00E1zis friss\u00EDt\u00E9si hiba", "Sikertelen t\u00E1blafriss\u00EDt\u00E9s az"
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

    public void resetLevels(String tableName) {
        String queryDefault = ConfigIO.readSQL(RESET_LEVELS_PATH);
        String query = queryDefault.replace("[TABLE_NAME]", tableName);
        executeUpdateQuery(query);
    }

    public void insertVerbScore(boolean isNormal, Score newScore) {
        String tableName = isNormal ? VERBSCORE_NORMAL_TABLE : VERBSCORE_TIMED_TABLE;
        String thirdColumnName = isNormal ? "OutOf" : "Duration";

        String queryDefault = ConfigIO.readSQL(INSERT_VERBSCORE_PATH);
        String query = queryDefault.replace("[TABLE_NAME]", tableName);
        query = query.replace("[THIRD_NAME]", thirdColumnName);
        query = query.replace("[SCORE]", Integer.toString(newScore.score()));
        query = query.replace("[THIRD_VALUE]", Integer.toString(newScore.third()));
        query = query.replace("[PERCENT]", String.valueOf(newScore.percent()));

        // debug
        System.out.println(query);
        executeUpdateQuery(query);
    }

    public void insertNounScore(boolean isNormal, Score newScore) {
        String tableName = isNormal ? NOUNSCORE_NORMAL_TABLE : NOUNSCORE_TIMED_TABLE;
        String thirdColumnName = isNormal ? "OutOf" : "Duration";

        String queryDefault = ConfigIO.readSQL(INSERT_NOUNSCORE_PATH);
        String query = queryDefault.replace("[TABLE_NAME]", tableName);
        query = query.replace("[THIRD_NAME]", thirdColumnName);
        query = query.replace("[SCORE]", Integer.toString(newScore.score()));
        query = query.replace("[THIRD_VALUE]", Integer.toString(newScore.third()));
        query = query.replace("[PERCENT]", String.valueOf(newScore.percent()));
        query = query.replace("[DIFFICULTY]", '\"' + newScore.difficulty() + '\"');

        // debug
        System.out.println(query);
        executeUpdateQuery(query);
    }

    public ArrayList<Score> buildScoreQuery(boolean isVerb, boolean isNormal, String query) {
        ArrayList<Score> result = new ArrayList<>();
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Score temp = new Score(resultSet.getInt("Score"), resultSet.getInt(isNormal ? "OutOf" : "Duration"),
                            resultSet.getFloat("Percent"), isVerb ? "" : resultSet.getString("Difficulty"),
                            resultSet.getString("Timestamp"));

                    // debug
                    //System.out.println(temp.toString());
                    result.add(temp);
                }
            } catch (SQLException e) {
                MainWindow.dialog.showExceptionDialog("Adatb\u00E1zis lek\u00E9rdez\u00E9si hiba", "Sikertelen lek\u00E9rdez\u00E9s az"
                        + (onlineFlag ? " online " : " ") + "adatb\u00E1zisb\u00F3l.\n" + e.toString(), DialogType.ERROR);
                connected = false;
            }
        }
        return result;
    }

    public ArrayList<Score> processScoreQuery(boolean isVerb, boolean isNormal) {
        String tableName;
        if (isVerb) {
            if (isNormal) tableName = VERBSCORE_NORMAL_TABLE;
            else tableName = VERBSCORE_TIMED_TABLE;
        } else {
            if (isNormal) tableName = NOUNSCORE_NORMAL_TABLE;
            else tableName = NOUNSCORE_TIMED_TABLE;
        }

        String queryDefault = ConfigIO.readSQL(SCORE_QUERY_PATH);
        String query = queryDefault.replace("[TABLE_NAME]", tableName);
        return buildScoreQuery(isVerb, isNormal, query);
    }

    public void resetScores() {
        String[] scoresTables = new String[]{VERBSCORE_NORMAL_TABLE, VERBSCORE_TIMED_TABLE, NOUNSCORE_NORMAL_TABLE, NOUNSCORE_TIMED_TABLE};
        for (String tableName : scoresTables) {
            String queryDefault = ConfigIO.readSQL(RESET_SCORES_PATH);
            String query = queryDefault.replace("[TABLE_NAME]", tableName);
            executeUpdateQuery(query);
        }
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

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}