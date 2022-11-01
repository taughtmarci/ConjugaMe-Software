package controller;

import model.Verb;
import model.VerbBasic;
import view.MainWindow;

import java.util.ArrayList;

public class VerbCollection {
    private MainWindow main;
    private QuizComponents components;
    private ArrayList<Verb> verbs = new ArrayList<>();

    public VerbCollection(MainWindow main, QuizComponents components) {
        this.main = main;
        this.components = components;
        loadVerbs();
    }

    public void loadVerbs() {
        loadEssentials();
    }

    public void loadEssentials() {
        ArrayList<VerbBasic> verbHolder = main.online.essentialQuery("Verbo",
                components.hasGerundio(), components.hasParticipio(), components.getNumberOfVerbs(), true);
        for (VerbBasic verb : verbHolder)
            verbs.add(new Verb(verb));
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
}
