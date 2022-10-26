package controller;

import model.QuizComponents;
import model.Verb;
import view.MainWindow;

import java.util.ArrayList;

public class VerbCollection {
    private MainWindow main;
    private QuizComponents components;
    private ArrayList<Verb> verb = new ArrayList<>();

    public VerbCollection(MainWindow main, QuizComponents components) {
        this.main = main;
        this.components = components;
        loadVerbs();
    }

    public void loadVerbs() {
        ArrayList<String> verbHolder = main.online.singleQueryStatement("Verbos", "verb", true, components.getNumberOfVerbs());
        for (String str : verbHolder)
            verb.add(new Verb(str));
    }

    public MainWindow getMain() {
        return main;
    }

    public QuizComponents getComponents() {
        return components;
    }

    public Verb getVerb(int index) {
        return verb.get(index);
    }

    public ArrayList<Verb> getVerbList() {
        return verb;
    }
}
