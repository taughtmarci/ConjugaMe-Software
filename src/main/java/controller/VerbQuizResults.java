package controller;

import model.*;
import view.MainWindow;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class VerbQuizResults {
    private final int score;
    private final int outOf;
    private final float percentage;

    private final VerbQuizController controller;
    private final VerbQuizComponents comps;

    private final CorrectConjugations correctConjugations;
    private final IncorrectConjugations incorrectConjugations;

    private final String percentText;

    public VerbQuizResults(VerbQuizController controller) {
        this.controller = controller;
        this.comps = controller.getComps();

        this.score = controller.getScore();
        this.correctConjugations = new CorrectConjugations(controller.getCorrectConjugations());
        this.incorrectConjugations = new IncorrectConjugations(controller.getMistakes(), controller.getIncorrectConjugations());

        if (comps.isNormal()) this.outOf = comps.getTotalNumberOfVerbs();
        else this.outOf = controller.getOutOf();

        this.percentage = ((float) getScore() / (float) getOutOf()) * 100;
        this.percentText = QuizComponents.df.format(this.percentage) + "%";

        if (correctConjugations.size() > 0) {
            updateCorrectLevels();
            insertScore();
        }
        if (incorrectConjugations.size() > 0) updateIncorrectLevels();

        checkForBadges();
    }

    private void insertScore() {
        Score newScore = new Score(getScore(), comps.isNormal() ? getOutOf() : comps.getDuration(),
                getPercentage(), "", QuizComponents.dtf.format(LocalDateTime.now()));
        MainWindow.local.insertVerbScore(comps.isNormal(), newScore);
    }

    private void updateIncorrectLevels() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Conjugation conjugation : incorrectConjugations.getConjugations()) {
            if (!ids.contains(conjugation.id()))
                ids.add(conjugation.id());
        }

        MainWindow.local.updateLevels(MainWindow.local.VERB_TABLE, true, ids);
    }

    private void updateCorrectLevels() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Conjugation conjugation : correctConjugations.getConjugations()) {
            if (!ids.contains(conjugation.id()))
                ids.add(conjugation.id());
        }

        MainWindow.local.updateLevels(MainWindow.local.VERB_TABLE, false, ids);
    }

    private void checkForBadges() {
        for (Group group: comps.getSelectedGroups()) {
            int totalAmount = MainWindow.local.buildCountQuery("SELECT COUNT(ID) FROM GRUPO_" + group.name(), "COUNT(ID)");
            int currentAmount = MainWindow.local.processVerbCountQuery(group.name());
            validateBadge(group.name(), (float) currentAmount / totalAmount);
        }
    }

    private void validateBadge(String groupName, float percentage) {
        System.out.println(percentage);
        try {
            if (percentage >= 0.5)
                ConfigIO.updateBadgeFile("config/badges.cfg", "bronze_" + groupName);
            if (percentage >= 0.8)
                ConfigIO.updateBadgeFile("config/badges.cfg", "silver_" + groupName);
            if (percentage == 1)
                ConfigIO.updateBadgeFile("config/badges.cfg", "gold_" + groupName);
        } catch (IOException e) {
            MainWindow.dialog.showExceptionDialog("B\u00E1lyeg beolvas\u00E1si hiba", "Az alkalmaz\u00E1s konfigur\u00E1ci\u00F3s f\u00E1jljai megs\u00E9r\u00FClhettek.\n" +
                    "K\u00E9rj\u00FCk, telep\u00EDtsd \u00FAjra az alkalmaz\u00E1st!\nR\u00E9szletek: " + e.toString(), DialogType.ERROR);
            throw new RuntimeException(e);
        }
    }

    public int getScore() {
        return score;
    }

    public int getOutOf() {
        return outOf;
    }

    public float getPercentage() {
        return percentage;
    }

    public String getPercentText() {
        return percentText;
    }

    public CorrectConjugations getCorrectConjugations() {
        return correctConjugations;
    }

    public IncorrectConjugations getIncorrectConjugations() {
        return incorrectConjugations;
    }

    public VerbQuizComponents getComps() {
        return comps;
    }

    public VerbQuizController getController() {
        return controller;
    }

}
