package controller;

import model.Verb;
import model.VerbQuizComponents;
import model.Word;
import model.WordQuizComponents;

import java.text.DecimalFormat;
import java.util.ArrayList;

// TODO EZEKKEL SÜRGŐSEN KEZDENI VALAMIT
public class WordQuizResults {
    private final int score;
    private final WordQuizController controller;
    private final WordQuizComponents comps;
    private final ArrayList<Word> incorrectWords;
    public static final DecimalFormat df = new DecimalFormat("0.00");

    public WordQuizResults(int score, WordQuizController controller, ArrayList<Word> incorrectWords) {
        this.score = score;
        this.controller = controller;
        this.comps = controller.getComps();
        this.incorrectWords = incorrectWords;
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
