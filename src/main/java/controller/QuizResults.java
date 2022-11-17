package controller;

import model.Verb;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class QuizResults {
    private final int score;
    private final ArrayList<Verb> incorrectVerbs;
    public static final DecimalFormat df = new DecimalFormat("0.00");

    public QuizResults(int score, ArrayList<Verb> incorrectVerbs) {
        this.score = score;
        this.incorrectVerbs = incorrectVerbs;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Verb> getIncorrectVerbs() {
        return incorrectVerbs;
    }

}
