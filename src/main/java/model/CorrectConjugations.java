package model;

import java.util.ArrayList;

public class CorrectConjugations {
    private final ArrayList<Conjugation> conjugations;
    private final String[] columnNames = {"Ige", "Ragozatlan alak", "Igeid\u0151/m\u00F3d", "Szem\u00E9ly"};

    public CorrectConjugations(ArrayList<Conjugation> conjugations) {
        this.conjugations = conjugations;
    }

    public int size() {
        return conjugations.size();
    }

    public String[][] getData() {
        String[][] result = new String[this.size()][columnNames.length];

        for (int i = 0; i < this.size(); i++) {
            String[] temp = {
                    conjugations.get(i).conjugation(),
                    conjugations.get(i).infinitivo(),
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

    public ArrayList<Conjugation> getConjugations() {
        return conjugations;
    }
}
