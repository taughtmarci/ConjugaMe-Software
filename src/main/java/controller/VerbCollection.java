package controller;

import model.VerbQuizComponents;
import model.Verb;
import view.MainWindow;

import java.util.ArrayList;
import java.util.Collections;

public class VerbCollection {
    private final MainWindow main;
    private final VerbQuizComponents components;
    private final ArrayList<Verb> verbs;

    public VerbCollection(MainWindow main, VerbQuizComponents components) {
        this.main = main;
        this.components = components;
        verbs = main.online.processQuery(main.online.buildQuery(components), components);
    }

    public MainWindow getMain() {
        return main;
    }

    public VerbQuizComponents getComponents() {
        return components;
    }

    public Verb getVerb(int index) {
        return verbs.get(index);
    }

    public ArrayList<Verb> getVerbList() {
        return verbs;
    }

    public void printVerbList() {
        for (Verb v : verbs)
            v.printVerb();
    }

    public void randomizeVerbList() {
        Collections.shuffle(verbs);
    }
}
