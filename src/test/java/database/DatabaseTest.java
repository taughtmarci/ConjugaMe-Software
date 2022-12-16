package database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    public static Local local;
    public static Local wrongLocal;

    @BeforeEach
    void setUp() {
        local = new Local("database/mainDB.db");
        wrongLocal = new Local("fake/path.db");
    }

    @Test
    void buildCountQuery() {
        // throws exception if wrong table/column or query is given
    }

    @Test
    void processVerbCountQuery() {
    }

    @Test
    void processVerbQueries() {
    }

    @Test
    void buildBasicVerbQuery() {
    }

    @Test
    void buildVerbQuery() {
    }

    @Test
    void processWordQueries() {
    }

    @Test
    void buildWordQuery() {
    }

    @Test
    void processVerbRevisionQuery() {
    }

    @Test
    void processWordRevisionQuery() {
    }

    @Test
    void executeUpdateQuery() {
    }

    @Test
    void updateLevels() {
    }

    @Test
    void resetLevels() {
    }

    @Test
    void insertVerbScore() {
    }

    @Test
    void insertNounScore() {
    }

    @Test
    void buildScoreQuery() {
    }

    @Test
    void processScoreQuery() {
    }

    @Test
    void resetScores() {
    }
}