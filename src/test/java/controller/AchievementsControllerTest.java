package controller;

import org.junit.jupiter.api.Test;
import view.Achievements;
import view.MainWindow;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AchievementsControllerTest {

    public AchievementsController achievements = new AchievementsController(new Achievements(new MainWindow()));

    AchievementsControllerTest() throws IOException {
    }

    @Test
    void testWordRevisionList() {
        /*
        Tests if the controller gets words to revise from a group
        Also checks if the query is successful
         */


    }

    @Test
    void updateVerbRevisionList() {
    }

    @Test
    void updateScoresList() {
    }

    @Test
    void getBadges() {
    }
}