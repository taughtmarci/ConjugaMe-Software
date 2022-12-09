package model;

import java.util.ArrayList;

public class CorrectWords {
    private final ArrayList<Word> words;
    private final String[] columnNames = {"N\u0151nem", "H\u00EDmnem", "Defin\u00EDci\u00F3(k)"};

    public CorrectWords(ArrayList<Word> words) {
        this.words = words;
    }

    public int size() {
        return words.size();
    }

    public String[][] getData() {
        String[][] result = new String[this.size()][columnNames.length];

        for (int i = 0; i < this.size(); i++) {
            String[] temp = {
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
