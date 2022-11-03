package controller;

import model.Form;
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
        // TODO CSINÁLD MEG NORMÁLISAN A PARTICIPIOKAT
        if (components.hasOtherThanParticipio()) {
            verbs = loadComplexVerbs();
            for (Verb verb : verbs) {
                verb.printVerb();
            }
        }
        else {
            verbs = loadEssentials();
        }
    }

    private ArrayList<Verb> loadEssentials() {
        ArrayList<VerbBasic> verbBasicHolder = main.online.essentialQuery(components.hasGerundio(),
                components.hasPasado(), components.getNumberOfVerbs(), true);

        ArrayList<Verb> verbHolder = new ArrayList<>();
        for (VerbBasic verbBasic : verbBasicHolder)
            verbHolder.add(new Verb(verbBasic));

        return verbHolder;
    }

    private ArrayList<Verb> loadComplexVerbs() {
        return main.online.complexQuery(components.hasGerundio(), components.hasPasado(),
                components.getSelectedFormsWithoutParticipio(), components.getNumberOfVerbs(), true);
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
