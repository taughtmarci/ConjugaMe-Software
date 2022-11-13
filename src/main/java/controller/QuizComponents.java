package controller;

import model.Form;
import model.Group;
import model.Pronoun;

import java.util.ArrayList;

public class QuizComponents {
    private int numberOfVerbs;
    private ArrayList<Group> selectedGroups = new ArrayList<Group>();
    private ArrayList<Pronoun> selectedPronouns = new ArrayList<Pronoun>();
    private ArrayList<Form> selectedForms = new ArrayList<Form>();

    private boolean participioPresentoSelected;
    private boolean participioPasadoSelected;

    public void printStats() {
        System.out.println("Pronouns: " + getSelectedPronouns().toString() + "\n"
                + "Forms: " + getSelectedForms().toString() + "\n"
                + "Number of verbs: " + getNumberOfVerbs() + "\n"
                + "Has other than participio: " + onlyParticipio());
    }

    public ArrayList<Integer> getPronounIndices() {
        ArrayList<Integer> result = new ArrayList<>();
        for (Pronoun p : selectedPronouns) {
            switch (p) {
                case Yo -> result.add(0);
                case Tu -> result.add(1);
                case Vos -> result.add(2);
                case Usted -> result.add(3);
                case Nosotros -> result.add(4);
                case Vosotros -> result.add(5);
                case Ustedes -> result.add(6);
            }
        }
        return result;
    }

    public boolean onlyParticipio() {
        return (participioPresentoSelected || participioPasadoSelected) && selectedForms.size() == 0;
    }

    public boolean isParticipioPresentoSelected() {
        return participioPresentoSelected;
    }

    public void setParticipioPresentoSelected(boolean participioPresentoSelected) {
        this.participioPresentoSelected = participioPresentoSelected;
    }

    public boolean isParticipioPasadoSelected() {
        return participioPasadoSelected;
    }

    public void setParticipioPasadoSelected(boolean participioPasadoSelected) {
        this.participioPasadoSelected = participioPasadoSelected;
    }

    public ArrayList<Pronoun> getSelectedPronouns() {
        return selectedPronouns;
    }

    public ArrayList<Form> getSelectedForms() {
        return selectedForms;
    }

    public void addPronoun(String elem) {
        selectedPronouns.add(Pronoun.fromString(elem));
    }

    public int getNumberOfVerbs() {
        return numberOfVerbs;
    }

    public void setNumberOfVerbs(int numberOfVerbs) {
        this.numberOfVerbs = numberOfVerbs;
    }

    public void addForm(String elem) {
        selectedForms.add(Form.fromString(elem));
    }
}
