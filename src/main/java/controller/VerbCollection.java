package controller;

import model.Verb;
import model.VerbBasic;
import view.MainWindow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VerbCollection {
    private MainWindow main;
    private QuizComponents components;
    private ArrayList<Verb> verbs = new ArrayList<>();

    public VerbCollection(MainWindow main, QuizComponents components) {
        this.main = main;
        this.components = components;
        verbs = main.online.processQuery(main.online.buildQuery(components), components);
        printVerbList();
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
