package controller;

import model.*;
import view.MainWindow;

import java.util.ArrayList;

public class WordQuizResults {
    private final int score;
    private final int outOf;
    private final WordQuizController controller;
    private final WordQuizComponents comps;

    private final CorrectWords correctWords;
    private final IncorrectWords incorrectWords;

    public WordQuizResults(WordQuizController controller) {
        this.controller = controller;
        this.comps = controller.getComps();

        this.score = controller.getScore();
        this.correctWords = new CorrectWords(controller.getCorrectWords());
        this.incorrectWords = new IncorrectWords(controller.getMistakes(), controller.getIncorrectWords());

        if (comps.isNormal()) this.outOf = comps.getWordAmount();
        else this.outOf = controller.getOutOf();

        if (correctWords.size() > 0) updateCorrectLevels();
        if (incorrectWords.size() > 0) updateIncorrectLevels();
    }

    private void updateIncorrectLevels() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Word word : incorrectWords.getWords()) {
            if (!ids.contains(word.getID()))
                ids.add(word.getID());
        }

        MainWindow.local.updateLevels(MainWindow.local.WORD_TABLE, true, ids);
    }

    private void updateCorrectLevels() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Word word : correctWords.getWords()) {
            if (!ids.contains(word.getID()))
                ids.add(word.getID());
        }

        MainWindow.local.updateLevels(MainWindow.local.WORD_TABLE, false, ids);
    }

    public int getScore() {
        return score;
    }

    public int getOutOf() {
        return outOf;
    }

    public CorrectWords getCorrectWords() {
        return correctWords;
    }

    public IncorrectWords getIncorrectWords() {
        return incorrectWords;
    }

    public WordQuizComponents getComps() {
        return comps;
    }

    public WordQuizController getController() {
        return controller;
    }
}
