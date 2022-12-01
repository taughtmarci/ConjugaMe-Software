package controller;

import model.Verb;
import model.VerbQuizComponents;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class VerbQuizResults {
    private final int score;
    private final VerbQuizController controller;
    private final VerbQuizComponents comps;
    private final ArrayList<Verb> incorrectVerbs;
    public static final DecimalFormat df = new DecimalFormat("0.00");

    public VerbQuizResults(int score, VerbQuizController controller, ArrayList<Verb> incorrectVerbs) {
        this.score = score;
        this.controller = controller;
        this.comps = controller.getComps();
        this.incorrectVerbs = incorrectVerbs;
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
}
