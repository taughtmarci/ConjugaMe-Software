package controller;

import model.Verb;
import model.VerbBasic;
import view.MainWindow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VerbCollection {
    private final MainWindow main;
    private final QuizComponents components;
    private final ArrayList<Verb> verbs;

    public VerbCollection(MainWindow main, QuizComponents components) {
        this.main = main;
        this.components = components;
        verbs = main.online.processQuery(main.online.buildQuery(components), components);
    }

    public MainWindow getMain() {
        return main;
    }

    public QuizComponents getComponents() {
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
}
