package controller;

import model.Word;
import model.WordQuizComponents;

import java.text.DecimalFormat;
import java.util.ArrayList;

// TODO EZEKKEL AZONNAL KEZDENI VALAMIT
public class WordQuizResults {
    private final int score;
    private final WordQuizController controller;
    private final WordQuizComponents comps;
    private final ArrayList<Word> incorrectWords;
    public static final DecimalFormat df = new DecimalFormat("0.00");

    public WordQuizResults(WordQuizController controller) {
        this.controller = controller;
        this.comps = controller.getComps();

        this.score = controller.getScore();
        this.incorrectWords = controller.getIncorrectWords();
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Word> getIncorrectWords() {
        return incorrectWords;
    }

    public WordQuizComponents getComps() {
        return comps;
    }

    public WordQuizController getController() {
        return controller;
    }
}
