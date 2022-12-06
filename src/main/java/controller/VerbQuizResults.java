package controller;

import model.Verb;
import model.VerbQuizComponents;

import java.util.ArrayList;

public class VerbQuizResults {
    private final int score;
    private final int outOf;
    private final VerbQuizController controller;
    private final VerbQuizComponents comps;
    private final ArrayList<Verb> incorrectVerbs;


    public VerbQuizResults(VerbQuizController controller) {
        this.controller = controller;
        this.comps = controller.getComps();

        this.score = controller.getScore();
        this.incorrectVerbs = controller.getIncorrectVerbs();

        if (comps.isNormal()) this.outOf = comps.getTotalNumberOfVerbs();
        else this.outOf = controller.getOutOf();
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Verb> getIncorrectVerbs() {
        return incorrectVerbs;
    }

    public VerbQuizComponents getComps() {
        return comps;
    }

    public VerbQuizController getController() {
        return controller;
    }

    public int getOutOf() {
        return outOf;
    }
}
