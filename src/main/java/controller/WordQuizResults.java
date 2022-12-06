package controller;

import model.Word;
import model.WordQuizComponents;

import java.util.ArrayList;

public class WordQuizResults {
    private final int score;
    private final int outOf;
    private final WordQuizController controller;
    private final WordQuizComponents comps;
    private final ArrayList<Word> incorrectWords;

    public WordQuizResults(WordQuizController controller) {
        this.controller = controller;
        this.comps = controller.getComps();

        this.score = controller.getScore();
        this.incorrectWords = controller.getIncorrectWords();

        if (comps.isNormal()) this.outOf = comps.getWordAmount();
        else this.outOf = controller.getOutOf();
    }

    public int getScore() {
        return score;
    }

    public int getOutOf() {
        return outOf;
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
