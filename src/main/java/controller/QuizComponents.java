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

    public boolean gerundioFlag;
    public boolean participioFlag;

    public QuizComponents() {
        gerundioFlag = false;
        participioFlag = false;
    }

    public void printStats() {
        System.out.println("Gerundio: " + hasGerundio()
                + ", participio: " + hasParticipio() + "\n"
                + "Pronouns: " + getSelectedPronouns().toString() + "\n"
                + "Forms: " + getSelectedForms().toString() + "\n"
                + "Number of verbs: " + getNumberOfVerbs());
    }

    public boolean hasGerundio() {
        return gerundioFlag;
    }

    public void setGerundioFlag(boolean gerundioFlag) {
        this.gerundioFlag = gerundioFlag;
    }

    public boolean hasParticipio() {
        return participioFlag;
    }

    public void setParticipioFlag(boolean participioFlag) {
        this.participioFlag = participioFlag;
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
