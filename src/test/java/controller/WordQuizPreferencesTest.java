package controller;

import model.Group;
import org.junit.jupiter.api.Test;
import view.MainWindow;
import view.SetupPane;
import view.WordQuizSetup;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WordQuizPreferencesTest {
    public WordQuizSetup setup = new WordQuizSetup(new SetupPane(new MainWindow()));
    public WordQuizPreferences prefs = new WordQuizPreferences(setup);

    public WordQuizPreferencesTest() throws IOException {
    }

    @Test
    public void testValidateForm() {
        Group g = new Group(0, "Conejito");
        prefs.getComps().getSelectedGroups().add(new Group(0, "Conejito"));
        prefs.getComps().setWordAmount(100);
        prefs.getComps().setDurationMin(3);
        prefs.getComps().setDurationSec(30);

        prefs.getComps().printStats();
        assertEquals(prefs.validateForm(), "");
        assertTrue(prefs.getComps().isWorkingCorrectly());

        prefs.getComps().getSelectedGroups().remove(0);
        assertNotEquals(prefs.validateForm(), "");

        prefs.getComps().getSelectedGroups().add(g);
        prefs.getComps().setDurationSec(60);
        assertNotEquals(prefs.validateForm(), "");
    }
}