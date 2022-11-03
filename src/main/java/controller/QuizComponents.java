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

    public void printStats() {
        System.out.println("Pronouns: " + getSelectedPronouns().toString() + "\n"
                + "Forms: " + getSelectedForms().toString() + "\n"
                + "Number of verbs: " + getNumberOfVerbs() + "\n"
                + "Has other than participio: " + hasOtherThanParticipio());
    }

    public boolean hasGerundio() {
        return getSelectedForms().contains(Form.ParticipioPresente);
    }

    public boolean hasPasado() {
        return getSelectedForms().contains(Form.ParticipioPasado);
    }

    public boolean hasOtherThanParticipio() {
        boolean result = false;

        if (this.hasGerundio() && this.hasPasado()) {
            if (this.getSelectedForms().size() > 2) result = true;
        }
        else if (this.hasGerundio() || this.hasPasado()) {
            if (this.getSelectedForms().size() > 1) result = true;
        }
        else if (this.getSelectedForms().size() > 0) {
            result = true;
        }

        return result;
    }

    public ArrayList<Pronoun> getSelectedPronouns() {
        return selectedPronouns;
    }

    public ArrayList<Form> getSelectedForms() {
        return selectedForms;
    }

    public ArrayList<Form> getSelectedFormsWithoutParticipio() {
        ArrayList<Form> selectedForms = this.getSelectedForms();
        if (this.hasGerundio()) selectedForms.remove(Form.ParticipioPresente);
        if (this.hasPasado()) selectedForms.remove(Form.ParticipioPasado);
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
