package controller;

import model.Verb;
import model.VerbQuizComponents;
import view.MainWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class VerbQuizController {
    private final MainWindow main;
    private ArrayList<Verb> verbs;
    private final VerbQuizComponents comps;

    public VerbQuizController(MainWindow main, VerbQuizComponents comps) throws IOException {
        this.main = main;
        this.comps = comps;
        this.verbs = main.local.processQueries(comps);
        randomizeVerbList();
        printVerbs();
    }

    public void randomizeVerbList() {
        Collections.shuffle(this.verbs);
    }

    public ArrayList<Verb> getVerbs() {
        return verbs;
    }

    public void printVerbs() {
        for (Verb v : verbs) {
            v.printVerb();
            System.out.println("\n");
        }
    }

    public VerbQuizComponents getComps() {
        return comps;
    }

    public MainWindow getMain() {
        return main;
    }
}
