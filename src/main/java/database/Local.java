package database;

public class Local extends Database {

    public Local(String location) {
        super(false, "jdbc:sqlite:" + location);
        connect();
    }

}
