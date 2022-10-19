package model;

import java.util.ArrayList;

public class QuizComponents {
    private int numberOfVerbs;
    private ArrayList<Group> selectedGroups = new ArrayList<Group>();
    private ArrayList<Pronoun> selectedPronouns = new ArrayList<Pronoun>();
    private ArrayList<Form> selectedForms = new ArrayList<Form>();

    public ArrayList<Pronoun> getSelectedPronouns() {
        return selectedPronouns;
    }

    public ArrayList<Form> getSelectedForms() {
        return selectedForms;
    }

    public void addPronoun(String elem) {
        selectedPronouns.add(Pronoun.fromString(elem));
    }

    public void addForm(String elem) {
        selectedForms.add(Form.fromString(elem));
    }

    public int getNumberOfVerbs() {
        return numberOfVerbs;
    }

    public void setNumberOfVerbs(int numberOfVerbs) {
        this.numberOfVerbs = numberOfVerbs;
    }
}
