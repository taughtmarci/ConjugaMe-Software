package model;

import java.util.ArrayList;

public class VerbQuizComponents extends QuizComponents {

    private boolean participioPresenteSelected;
    private boolean participioPasadoSelected;

    private ArrayList<Pronoun> selectedPronouns;

    private ArrayList<Form> selectedForms;

    public VerbQuizComponents() {
        super();
        this.isVerb = true;
        this.selectedPronouns = new ArrayList<>();
        this.selectedForms = new ArrayList<>();
    }

    @Override
    public boolean isWorkingCorrectly() {
        if (onlyParticipio() && (!participioPresenteSelected && !participioPasadoSelected))
            return false;
        else if (!onlyParticipio() && (selectedPronouns.size() == 0 || selectedForms.size() == 0))
            return false;
        else if (selectedGroups.size() == 0)
            return false;
        else if (wordAmount < 1 || wordAmount > 250)
            return false;
        return durationSec >= 0 && durationSec <= 59 && durationMin >= 1 && durationMin <= 30;
    }

    @Override
    public void printStats() {
        System.out.println("Pronouns: " + getSelectedPronouns().toString() + "\n"
                + "Forms: " + getSelectedForms().toString() + "\n"
                + "P.Presento: " + participioPresenteSelected + ", P.Pasado: " + participioPasadoSelected + "\n"
                + "Groups: " + getSelectedGroups().toString() + "\n"
                + "Number of verbs: " + getWordAmount() + "\n"
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
        return (participioPresenteSelected || participioPasadoSelected) && selectedForms.size() == 0;
    }

    public boolean isParticipioPresenteSelected() {
        return participioPresenteSelected;
    }

    public void setParticipioPresenteSelected(boolean participioPresenteSelected) {
        this.participioPresenteSelected = participioPresenteSelected;
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
            result = wordAmount * getSelectedPronouns().size();

        if (participioPresenteSelected) result += wordAmount;
        if (participioPasadoSelected) result += wordAmount;

        return result;
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

    public void setSelectedPronouns(ArrayList<Pronoun> selectedPronouns) {
        this.selectedPronouns = selectedPronouns;
    }

    public void setSelectedForms(ArrayList<Form> selectedForms) {
        this.selectedForms = selectedForms;
    }
}
