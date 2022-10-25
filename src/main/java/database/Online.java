package database;

public class Online extends Database {

    public Online(String ip, String port, String schema) {
        super(true, "jdbc:mysql://" + ip + ":" + port + "/" + schema);
        setCredentials("admin", "ManchaT3!");
        connect();
        if (connected) testDatabase("Test", "sample");
    }

}
