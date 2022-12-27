package view;

import controller.ConfigIO;
import controller.Section;
import model.Group;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NormalVerbQuizTest {
    MainWindow main;
    SetupPane setupPane;

    @BeforeEach
    void setUp() throws IOException {
        main = new MainWindow();
        MainWindow.verbComps = ConfigIO.getDefaultVerbComps();
        setupPane = new SetupPane(main);
    }

     @Test
     void testVerbQuizSetup() throws InterruptedException, IOException, AWTException {
        main.switchPanels(main.dashboard, setupPane);
        VerbQuizSetup setup = setupPane.getVerbQuizSetup();

        // fill out pronoun checkboxes
        setup.getPronounCheckBoxes().get(0).setSelected(true);
        setup.getPronounCheckBoxes().get(2).setSelected(true);
        setup.getPronounCheckBoxes().get(5).setSelected(true);

        Thread.sleep(3000);

        // fill out form checkboxes
        setup.getFormCheckBoxes().get(1).setSelected(false);
        setup.getFormCheckBoxes().get(4).setSelected(true);
        setup.getFormCheckBoxes().get(7).setSelected(true);

        Thread.sleep(3000);

        // check for valid error handling
        setup.getVerbNumberChooser().setValue(32498623);
        setup.getPrefs().savePrefs(false);
        assertNotEquals("", setup.getErrorLabel().getText());

        Thread.sleep(3000);

        // make form valid
        ArrayList<Group> groups = new ArrayList<Group>();
        groups.add(new Group(2, "Intermedio"));
        setup.getGroupList().setSelectedRows(groups);
        setup.getVerbNumberChooser().setValue(3);
        setup.getPrefs().savePrefs(false);
        assertEquals("Sikeres ment\u00E9s!", setup.getErrorLabel().getText());

        Thread.sleep(3000);

        // start quiz
        setup.getPrefs().savePrefs(false);
        VerbQuiz quiz = new VerbQuiz(main);
        main.switchPanels(setupPane, quiz);

        Thread.sleep(3000);

        // simulate a quiz
        // good answers only
        quiz.getPresentoSection().getInput().setText(quiz.getPresentoSection().getSolution());
        for (Section section : quiz.getSections()) {
            section.getInput().setText(section.getSolution());
        }
        Thread.sleep(3000);

        quiz.getSendButton().doClick();
        assertEquals(4, quiz.getController().getScore());
        Thread.sleep(3000);

        // bad answers only
        quiz.getSendButton().doClick();
        assertEquals(4, quiz.getController().getScore());
        Thread.sleep(3000);

        // good and bad answers
        quiz.getPresentoSection().getInput().setText(quiz.getPresentoSection().getSolution());
        for (Section section : quiz.getSections()) {
            section.getInput().setText("blablabla");
        }
        Thread.sleep(3000);

        quiz.getSendButton().doClick();
        assertEquals(5, quiz.getController().getScore());
        Thread.sleep(3000);

     }

    @AfterEach
    void tearDown() {
    }
}