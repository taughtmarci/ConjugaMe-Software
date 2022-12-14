package controller;

import model.*;
import view.MainWindow;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class VerbQuizResults {
    private final int score;
    private final int outOf;
    private final float percentage;

    private final VerbQuizController controller;
    private final VerbQuizComponents comps;

    private final CorrectConjugations correctConjugations;
    private final IncorrectConjugations incorrectConjugations;

    private final String percentText;

    public VerbQuizResults(VerbQuizController controller) {
        this.controller = controller;
        this.comps = controller.getComps();

        this.score = controller.getScore();
        this.correctConjugations = new CorrectConjugations(controller.getCorrectConjugations());
        this.incorrectConjugations = new IncorrectConjugations(controller.getMistakes(), controller.getIncorrectConjugations());

        if (comps.isNormal()) this.outOf = comps.getTotalNumberOfVerbs();
        else this.outOf = controller.getOutOf();

        this.percentage = ((float) getScore() / (float) getOutOf()) * 100;
        this.percentText = QuizComponents.df.format(this.percentage) + "%";

        if (correctConjugations.size() > 0) {
            updateCorrectLevels();
            insertScore();
        }
        if (incorrectConjugations.size() > 0) updateIncorrectLevels();
    }

    private void insertScore() {
        Score newScore = new Score(getScore(), comps.isNormal() ? getOutOf() : comps.getDuration(),
                getPercentage(), "", QuizComponents.dtf.format(LocalDateTime.now()));
        MainWindow.local.insertVerbScore(comps.isNormal(), newScore);
    }

    private void updateIncorrectLevels() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Conjugation conjugation : incorrectConjugations.getConjugations()) {
            if (!ids.contains(conjugation.id()))
                ids.add(conjugation.id());
        }

        MainWindow.local.updateLevels(MainWindow.local.VERB_TABLE, true, ids);
    }

    private void updateCorrectLevels() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Conjugation conjugation : correctConjugations.getConjugations()) {
            if (!ids.contains(conjugation.id()))
                ids.add(conjugation.id());
        }

        MainWindow.local.updateLevels(MainWindow.local.VERB_TABLE, false, ids);
    }

    public int getScore() {
        return score;
    }

    public int getOutOf() {
        return outOf;
    }

    public float getPercentage() {
        return percentage;
    }

    public String getPercentText() {
        return percentText;
    }

    public CorrectConjugations getCorrectConjugations() {
        return correctConjugations;
    }

    public IncorrectConjugations getIncorrectConjugations() {
        return incorrectConjugations;
    }

    public VerbQuizComponents getComps() {
        return comps;
    }

    public VerbQuizController getController() {
        return controller;
    }

}
