package controller;

import model.CorrectWords;
import model.IncorrectWords;
import model.WordQuizComponents;

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
