package model;

public class WordQuizComponents extends QuizComponents {
    private Difficulty difficulty;
    private boolean articlesNeeded;

    public WordQuizComponents() {
        super();
        this.isVerb = false;
        this.difficulty = Difficulty.Easy;
        this.articlesNeeded = false;
    }

    public void printStats() {
        System.out.println("Number of words: " + getWordAmount() + "\n"
                + "Groups: " + getSelectedGroups().toString() + "\n"
                + "Duration min: " + getDurationMin() + ", sec: " + getDurationSec() + "\n"
                + "Difficulty: " + getDifficulty().toString() + "\n"
                + "Articles needed: " + isArticlesNeeded() + "\n");
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isArticlesNeeded() {
        return articlesNeeded;
    }

    public void setArticlesNeeded(boolean articlesNeeded) {
        this.articlesNeeded = articlesNeeded;
    }
}
