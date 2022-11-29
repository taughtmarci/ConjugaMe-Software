package model;

import java.util.ArrayList;

public class VerbQuizComponents {
    private boolean isNormal;
    private int numberOfVerbs;
    private int durationMin;
    private int durationSec;

    private boolean participioPresentoSelected;
    private boolean participioPasadoSelected;

    private ArrayList<Group> selectedGroups;
    private ArrayList<Pronoun> selectedPronouns;

    private ArrayList<Form> selectedForms;

    public VerbQuizComponents() {
        this.isNormal = true;
        this.numberOfVerbs = 0;
        this.selectedGroups = new ArrayList<>();
        this.selectedPronouns = new ArrayList<>();
        this.selectedForms = new ArrayList<>();
    }

    public boolean isWorkingCorrectly() {
        if (onlyParticipio() && (!isParticipioPasadoSelected() && !isParticipioPresentoSelected()))
            return false;
        else if (selectedForms.size() < 0 || selectedPronouns.size() < 0 || selectedGroups.size() < 0)
            return false;
        else if (numberOfVerbs < 5 || numberOfVerbs > 500)
            return false;
        else if (durationMin < 1 || durationMin > 180)
            return false;
        else return durationSec >= 0 && durationSec <= 59;
    }

    public void printStats() {
        System.out.println("Pronouns: " + getSelectedPronouns().toString() + "\n"
                + "Forms: " + getSelectedForms().toString() + "\n"
                + "P.Presento: " + participioPresentoSelected + ", P.Pasado: " + participioPasadoSelected + "\n"
                + "Groups: " + getSelectedGroups().toString() + "\n"
                + "Number of verbs: " + getNumberOfVerbs() + "\n"
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

    public ArrayList<Group> getSelectedGroups() {
        return selectedGroups;
    }

    public void addGroup(Group group) {
        selectedGroups.add(group);
    }

    public void addPronoun(String elem) {
        selectedPronouns.add(Pronoun.fromString(elem));
    }

    public void addForm(String elem) {
        selectedForms.add(Form.fromString(elem));
    }

    public void setSelectedGroups(ArrayList<Group> selectedGroups) {
        this.selectedGroups = selectedGroups;
    }

    public void setSelectedPronouns(ArrayList<Pronoun> selectedPronouns) {
        this.selectedPronouns = selectedPronouns;
    }

    public void setSelectedForms(ArrayList<Form> selectedForms) {
        this.selectedForms = selectedForms;
    }

    public int getNumberOfVerbs() {
        return numberOfVerbs;
    }

    public void setNumberOfVerbs(int numberOfVerbs) {
        this.numberOfVerbs = numberOfVerbs;
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

    public boolean isNormal() {
        return isNormal;
    }

    public void setNormal(boolean normal) {
        isNormal = normal;
    }
}
