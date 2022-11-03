package database;

import model.*;
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

    private final String mainTable = "Verbo";

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

    public ArrayList<VerbBasic> essentialQuery(boolean gerundioFlag, boolean participioFlag, int limit, boolean randomFlag)  {
        ArrayList<VerbBasic> result = new ArrayList<>();
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                query = "select Infinitivo, " + (gerundioFlag ? "Gerundio, " : "'', ") + (participioFlag ? "Participio " : "'' ");
                query += " from " + mainTable + (randomFlag ? " order by rand()" : "")
                        + (limit > 0 ? " limit " + limit : "") + ";";

                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    VerbBasic temp = new VerbBasic(resultSet.getString("Infinitivo"));
                    if (gerundioFlag) temp.setPresente(resultSet.getString("Gerundio"));
                    if (participioFlag) temp.setPasado(resultSet.getString("Participio"));
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

    public ArrayList<Verb> complexQuery(boolean gerundioFlag, boolean participioFlag,
                                        ArrayList<Form> forms, int limit, boolean randomFlag)  {
        ArrayList<Verb> result = new ArrayList<>();
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("select Verbo.Infinitivo, ").append(gerundioFlag ? "Verbo.Gerundio, " : "'', ")
                        .append(participioFlag ? "Verbo.Participio, " : "',' ");

                StringBuilder formBuilder = new StringBuilder();
                for (Form f : forms) {
                    queryBuilder.append("`").append(f.toString()).append("`").append(".*, ");
                    formBuilder.append(" inner join `").append(f.toString()).append("`").append(" on Verbo.ID = ")
                            .append("`").append(f.toString()).append("`").append(".VerbID)");
                }
                queryBuilder.append("'' from ");
                for (int i = 0; i < forms.size(); i++) queryBuilder.append("(");
                queryBuilder.append(mainTable).append(formBuilder);
                queryBuilder.append(randomFlag ? " order by rand()" : "").append(limit > 0 ? " limit " + limit : "");

                query = queryBuilder.toString() + ";";
                System.out.println(query);
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    VerbBasic tempBasic = new VerbBasic(resultSet.getString("Verbo.Infinitivo"));
                    if (gerundioFlag) tempBasic.setPresente(resultSet.getString("Gerundio"));
                    if (participioFlag) tempBasic.setPasado(resultSet.getString("Participio"));

                    Verb temp = new Verb(tempBasic);
                    ArrayList<String> tempContent = new ArrayList<>();
                    for (Form f : forms) {
                        int index = resultSet.findColumn("ID " + f.toString()) + 2;
                        System.out.println(index);
                        for (int i = 0; i < 7; i++) tempContent.add(resultSet.getString(index + i));
                        temp.setForm(f, tempContent);
                    }

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
