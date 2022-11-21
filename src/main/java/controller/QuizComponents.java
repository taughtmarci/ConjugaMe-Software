package controller;

import model.Form;
import model.Pronoun;

import java.util.ArrayList;

public class QuizComponents {
    private int numberOfVerbs;

    private boolean feedbackEnabled;

    private int durationMin;
    private int durationSec;

    private ArrayList<String> selectedGroups = new ArrayList<String>();
    private ArrayList<Pronoun> selectedPronouns = new ArrayList<Pronoun>();
    private ArrayList<Form> selectedForms = new ArrayList<Form>();

    private boolean participioPresentoSelected;
    private boolean participioPasadoSelected;

    public void printStats() {
        System.out.println("Pronouns: " + getSelectedPronouns().toString() + "\n"
                + "Forms: " + getSelectedForms().toString() + "\n"
                + "P.Presento: " + participioPresentoSelected + ", P.Pasado: " + participioPasadoSelected + "\n"
                + "Groups: " + getSelectedGroups().toString() + "\n"
                + "Number of verbs: " + getNumberOfVerbs() + "\n"
                + "Feedback enabled: " + isFeedbackEnabled() + "\n"
                + "Duration min: " + getDurationMin() + ", sec: " + getDurationSec() + "\n");
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

    public int getTotalNumberOfVerbs() {
        int result = 0;

        if (!onlyParticipio())
            result = numberOfVerbs * getSelectedPronouns().size();

        if (participioPresentoSelected) result += numberOfVerbs;
        if (participioPasadoSelected) result += numberOfVerbs;

        return result;
    }

    public ArrayList<Pronoun> getSelectedPronouns() {
        return selectedPronouns;
    }

    public ArrayList<Form> getSelectedForms() {
        return selectedForms;
    }

    public ArrayList<String> getSelectedGroups() {
        return selectedGroups;
    }

    public void addGroup(String group) {
        selectedGroups.add(group);
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

    public boolean isFeedbackEnabled() {
        return feedbackEnabled;
    }

    public void setFeedbackEnabled(boolean feedbackEnabled) {
        this.feedbackEnabled = feedbackEnabled;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }

    public int getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(int durationSec) {
        this.durationSec = durationSec;
    }
}
