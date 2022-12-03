package controller;

import model.Difficulty;
import model.WordQuizComponents;
import view.MainWindow;
import view.VerbQuiz;
import view.WordQuiz;
import view.WordQuizSetup;

import javax.swing.*;
import java.io.IOException;

public class WordQuizPreferences extends QuizPreferences {
    private final String FILE_PATH = "config/wordpreferences.cfg";
    private WordQuizComponents comps;
    private final WordQuizSetup setup;
    private WordQuiz current;

    public WordQuizPreferences(WordQuizSetup setup) throws IOException {
        super();
        this.setup = setup;
        this.comps = new WordQuizComponents();
    }

    public void setupComps() {
        comps = new WordQuizComponents();

        // difficulty
        for (JRadioButton difficultyRadioButton : setup.getDifficultyRadioButtons()) {
            if (difficultyRadioButton.isSelected()) comps.setDifficulty(Difficulty.fromString(difficultyRadioButton.getText()));
        }

        // groups
        comps.setSelectedGroups(selector.getSelectedRows());

        // normal or timed
        comps.setNormal(setup.getNormalModeRadio().isSelected());

        // number of words
        comps.setWordAmount((int) setup.getWordNumberChooser().getValue());

        // duration min and sec
        comps.setDurationMin((int) setup.getMinutesChooser().getValue());
        comps.setDurationSec((int) setup.getSecondsChooser().getValue());
    }

    public String validateForm() {
        String error = "";

        if (comps.getSelectedGroups().size() == 0)
            error = "Legal\u00E1bb egy csoport kiv\u00E1laszt\u00E1sa sz\u00FCks\u00E9ges!";
        else if (comps.getWordAmount() < 0 || comps.getWordAmount() > 250)
            error = "Az szavak sz\u00E1ma 0 \u00E9s 250 k\u00F6z\u00F6tt kell, hogy legyen!";
        else if (comps.getDurationMin() < 1 || comps.getDurationMin() > 30)
            error = "A megadott perc 1 \u00E9s 30 k\u00F6z\u00F6tt kell, hogy legyen!";
        else if (comps.getDurationMin() < 0 || comps.getDurationMin() > 59)
            error = "A megadott m\u00E1sodperc 1 \u00E9s 30 k\u00F6z\u00F6tt kell, hogy legyen!";

        return error;
    }

    public void savePrefs(boolean startFlag) {
        setupComps();
        String error = validateForm();
        if (error.equals("")) {
            try {
                getConfig().writeWordComponents("config/wordpreferences.cfg", getComps());
                MainWindow.wordComps = getComps();
                error = "Sikeres ment\u00E9s!";
            } catch (IOException e) {
                error = "Nem sikerült menteni a preferenciákat.";
            }
        }
        if (startFlag && error.equals("Sikeres ment\u00E9s!")) {
            try {
                current = new WordQuiz(setup.getSetupPane().getMain());
            } catch (IOException e) {
                // todo dialogize
                throw new RuntimeException(e);
            }
            setup.setVisible(false);
            setup.getSetupPane().getMain().switchPanels(setup.getSetupPane(), current);
        } else {
            setup.writeOutErrors(error);
        }
    }

    public WordQuizComponents getComps() {
        return comps;
    }

    public WordQuizSetup getSetup() {
        return setup;
    }
}
