package model;

import java.util.ArrayList;

public class IncorrectConjugations {
    private final ArrayList<String> mistakes;
    private final ArrayList<Conjugation> conjugations;
    private final String[] columnNames = {"Hib\u00E1s v\u00E1lasz", "Ige", "Ragozatlan alak", "Igeid\u0151/m\u00F3d", "Szem\u00E9ly"};

    public IncorrectConjugations(ArrayList<String> mistakes, ArrayList<Conjugation> conjugations) {
        this.mistakes = mistakes;
        this.conjugations = conjugations;
    }

    public int size() {
        return conjugations.size();
    }

    public String[][] getData() {
        String[][] result = new String[this.size()][columnNames.length];

        for (int i = 0; i < this.size(); i++) {
            String[] temp = {
                    mistakes.get(i),
                    conjugations.get(i).infinitivo(),
                    conjugations.get(i).conjugation(),
                    conjugations.get(i).form(),
                    conjugations.get(i).pronoun()
            };
            result[i] = temp;
        }

        return result;
    }

    public String[] getColumnNames() {
        return columnNames;
    }
}
