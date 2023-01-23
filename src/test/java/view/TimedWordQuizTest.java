package view;

import controller.ConfigIO;
import model.Group;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TimedWordQuizTest {
    MainWindow main;
    SetupPane setupPane;

    @BeforeEach
    void setUp() throws IOException {
        main = new MainWindow();
        MainWindow.wordComps = ConfigIO.getDefaultWordComps();
        setupPane = new SetupPane(main);
    }

    @Test
    void testVerbQuizSetup() throws InterruptedException, IOException {
        main.switchPanels(main.dashboard, setupPane);
        Thread.sleep(3000);
        WordQuizSetup setup = setupPane.getWordQuizSetup();
        setupPane.getTabbedPane().setSelectedComponent(setup);

        Thread.sleep(3000);

        // set difficulty, no groups
        setup.getDifficultyRadioButtons().get(3).setSelected(true);
        ArrayList<Group> groups = new ArrayList<Group>();
        setup.getGroupList().setSelectedRows(groups);

        Thread.sleep(3000);

        // check for valid error handling
        setup.getPrefs().savePrefs(false);
        setup.updateUI();
        assertNotEquals("", setup.getErrorLabel().getText());

        Thread.sleep(3000);

        // set groups properly
        groups.add(new Group(0, "Conejito"));
        groups.add(new Group(4, "Experto"));
        setup.getGroupList().setSelectedRows(groups);

        Thread.sleep(3000);

        // change to timed mode, allow articles
        setup.getTimedModeRadio().setSelected(true);
        setup.getArticlesCheckBox().setEnabled(true);

        Thread.sleep(3000);

        // check for valid error handling
        setup.getMinutesChooser().setValue(32498623);
        setup.getSecondsChooser().setValue(0);
        setup.getPrefs().savePrefs(false);
        setup.updateUI();
        assertNotEquals("", setup.getErrorLabel().getText());

        Thread.sleep(3000);

        // make form valid
        setup.getMinutesChooser().setValue(1);
        setup.getPrefs().savePrefs(false);
        assertEquals("Sikeres ment\u00E9s!", setup.getErrorLabel().getText());

        Thread.sleep(3000);

        // start quiz
        setup.getPrefs().savePrefs(false);
        WordQuiz quiz = new WordQuiz(main);
        main.switchPanels(setupPane, quiz);

        Thread.sleep(3000);

        /* simulate a quiz
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

         */
    }

    @AfterEach
    void tearDown() {
    }
}