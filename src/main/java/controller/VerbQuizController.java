package controller;

import model.Verb;
import model.VerbQuizComponents;
import view.MainWindow;

import java.util.ArrayList;
import java.util.Collections;

public class VerbQuizController {
    private MainWindow main;
    private ArrayList<Verb> verbs;
    private VerbQuizComponents comps;

    private boolean isTimedQuiz;
    public void querySelectedVerbs() {
        verbs = main.online.processQuery(main.online.buildQuery(comps), comps);
    }

    public void randomizeVerbList(ArrayList<Verb> verbs) {
        Collections.shuffle(verbs);
    }
}
