package model;

import java.util.ArrayList;

public class ScoresList {
    private final ArrayList<Score> scores;
    private final String[] columnNames;

    private final boolean isNormal;
    private final boolean isVerb;

    public ScoresList(boolean isNormal, boolean isVerb, ArrayList<Score> scores) {
        this.scores = scores;
        this.isNormal = isNormal;
        this.isVerb = isVerb;

        if (isVerb) {
            if (isNormal) columnNames = new String[]{"Pontsz\u00E1m", "Ennyib\u0151l", "Sz\u00E1zal\u00E9k", "Id\u0151pont"};
            else columnNames = new String[]{"Pontsz\u00E1m", "Id\u0151tartam", "Sz\u00E1zal\u00E9k", "Id\u0151pont"};
        } else {
            if (isNormal) columnNames = new String[]{"Pontsz\u00E1m", "Ennyib\u0151l", "Sz\u00E1zal\u00E9k", "Neh\u00E9zs\u00E9g", "Id\u0151pont"};
            else columnNames = new String[]{"Pontsz\u00E1m", "Id\u0151tartam", "Sz\u00E1zal\u00E9k", "Neh\u00E9zs\u00E9g", "Id\u0151pont"};
        }
    }

    public int size() {
        return scores.size();
    }

    public Object[][] getData() {
        Object[][] result = new Object[this.size()][columnNames.length];

        for (int i = 0; i < this.size(); i++) {
            if (columnNames.length == 4) {
                Object[] temp = {
                        scores.get(i).score(),
                        isNormal ? scores.get(i).third() :
                                ((scores.get(i).third() / 60) + ":" + (scores.get(i).third() % 60)),
                        QuizComponents.df.format(scores.get(i).percent()) + "%",
                        scores.get(i).timestamp()
                };
                result[i] = temp;
            } else {
                Object[] temp = {
                        scores.get(i).score(),
                        isNormal ? scores.get(i).third() :
                                ((scores.get(i).third() / 60) + ":" + (scores.get(i).third() % 60)),
                        QuizComponents.df.format(scores.get(i).percent()) + "%",
                        scores.get(i).difficulty(),
                        scores.get(i).timestamp()
                };
                result[i] = temp;
            }
        }

        return result;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

}
