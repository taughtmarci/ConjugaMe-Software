package model;

public class WordQuizComponents extends QuizComponents {
    private Difficulty difficulty;

    public WordQuizComponents() {
        super();
        this.isVerb = false;
        difficulty = Difficulty.Easy;
    }

    public void printStats() {
        System.out.println("Number of words: " + getWordAmount() + "\n"
                + "Groups: " + getSelectedGroups().toString() + "\n"
                + "Duration min: " + getDurationMin() + ", sec: " + getDurationSec() + "\n"
                + "Difficulty: " + getDifficulty().toString() + "\n");
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
