package controller;

import model.Conjugation;
import model.CorrectConjugations;
import model.IncorrectConjugations;
import model.VerbQuizComponents;
import view.MainWindow;

import java.util.ArrayList;

public class VerbQuizResults {
    private final int score;
    private final int outOf;
    private final VerbQuizController controller;
    private final VerbQuizComponents comps;

    private final CorrectConjugations correctConjugations;
    private final IncorrectConjugations incorrectConjugations;


    public VerbQuizResults(VerbQuizController controller) {
        this.controller = controller;
        this.comps = controller.getComps();

        this.score = controller.getScore();
        this.correctConjugations = new CorrectConjugations(controller.getCorrectConjugations());
        this.incorrectConjugations = new IncorrectConjugations(controller.getMistakes(), controller.getIncorrectConjugations());

        if (comps.isNormal()) this.outOf = comps.getTotalNumberOfVerbs();
        else this.outOf = controller.getOutOf();

        if (controller.getOutOf() > 0) {
            if (score > 0) updateCorrectLevels();
            if (outOf - score > 0) updateIncorrectLevels();
        }
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

    public VerbQuizComponents getComps() {
        return comps;
    }

    public VerbQuizController getController() {
        return controller;
    }

    public CorrectConjugations getCorrectConjugations() {
        return correctConjugations;
    }

    public IncorrectConjugations getIncorrectConjugations() {
        return incorrectConjugations;
    }
}
