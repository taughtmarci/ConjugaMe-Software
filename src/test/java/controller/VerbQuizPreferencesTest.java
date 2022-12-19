package controller;

import model.Form;
import model.Group;
import org.junit.jupiter.api.Test;
import view.MainWindow;
import view.SetupPane;
import view.VerbQuizSetup;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class VerbQuizPreferencesTest {
    public VerbQuizSetup setup = new VerbQuizSetup(new SetupPane(new MainWindow()));
    public VerbQuizPreferences prefs = new VerbQuizPreferences(setup);


    VerbQuizPreferencesTest() throws IOException {
    }

    @Test
    public void testValidateForm() {
        Group g = new Group(0, "Conejito");
        prefs.getComps().getSelectedGroups().add(new Group(0, "Conejito"));
        prefs.getComps().setWordAmount(100);
        prefs.getComps().setDurationMin(3);
        prefs.getComps().setDurationSec(30);
        prefs.getComps().setParticipioPasadoSelected(true);

        prefs.getComps().printStats();
        assertEquals(prefs.validateForm(), "");
        assertTrue(prefs.getComps().isWorkingCorrectly());

        prefs.getComps().getSelectedGroups().remove(0);
        assertNotEquals(prefs.validateForm(), "");

        prefs.getComps().getSelectedGroups().add(g);
        prefs.getComps().setDurationSec(60);
        assertNotEquals(prefs.validateForm(), "");

        // no pronoun
        prefs.getComps().getSelectedForms().add(Form.SubjuntivoPresente);
        assertNotEquals(prefs.validateForm(), "");
        assertFalse(prefs.getComps().isWorkingCorrectly());
    }
}