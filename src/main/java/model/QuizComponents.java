package model;

import java.text.DecimalFormat;
import java.util.ArrayList;

public abstract class QuizComponents {
    protected boolean isVerb;
    protected boolean isNormal;
    protected int wordAmount;
    protected int durationMin;
    protected int durationSec;

    protected ArrayList<Group> selectedGroups;
    public static final DecimalFormat df = new DecimalFormat("0.00");

    public QuizComponents() {
        this.isNormal = true;
        this.wordAmount = 0;
        this.selectedGroups = new ArrayList<>();
    }

    public boolean isWorkingCorrectly() {
        if (wordAmount < 1 || wordAmount > 250)
            return false;
        else if (getSelectedGroups().size() == 0)
            return false;
        return durationSec >= 0 && durationSec <= 59 && durationMin >= 1 && durationMin <= 30;
    }

    public void printStats() {
        System.out.println("Number of words: " + getWordAmount() + "\n"
                + "Groups: " + getSelectedGroups().toString() + "\n"
                + "Duration min: " + getDurationMin() + ", sec: " + getDurationSec() + "\n");
    }

    public boolean isVerb() {
        return isVerb;
    }

    public boolean isNormal() {
        return isNormal;
    }

    public void setNormal(boolean normal) {
        isNormal = normal;
    }

    public int getWordAmount() {
        return wordAmount;
    }

    public void setWordAmount(int wordAmount) {
        this.wordAmount = wordAmount;
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

    public int getDuration() {
        return (durationMin * 60) + durationSec;
    }

    public ArrayList<Group> getSelectedGroups() {
        return selectedGroups;
    }

    public void addGroup(Group group) {
        selectedGroups.add(group);
    }

    public void setSelectedGroups(ArrayList<Group> selectedGroups) {
        this.selectedGroups = selectedGroups;
    }
}
