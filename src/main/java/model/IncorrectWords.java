package model;

import java.util.ArrayList;

public class IncorrectWords {
    private final ArrayList<String> mistakes;
    private final ArrayList<Word> words;
    private final String[] columnNames = {"Hib\u00E1s v\u00E1lasz", "N\u0151nem", "H\u00EDmnem", "Defin\u00EDci\u00F3(k)"};

    public IncorrectWords(ArrayList<String> mistakes, ArrayList<Word> words) {
        this.mistakes = mistakes;
        this.words = words;
    }

    public int size() {
        return words.size();
    }

    public String[][] getData() {
        String[][] result = new String[this.size()][columnNames.length];

        for (int i = 0; i < this.size(); i++) {
            String[] temp = {
                    mistakes.get(i),
                    ((words.get(i).isNoun() && !words.get(i).getFemenino().equals("")) ? "la " : "") + words.get(i).getFemenino(),
                    ((words.get(i).isNoun() && !words.get(i).getMasculino().equals("")) ? "el " : "") + words.get(i).getMasculino(),
                    words.get(i).getDefinitions()
            };
            result[i] = temp;
        }

        return result;
    }

    public String[] getColumnNames() {
        return columnNames;
    }
}
