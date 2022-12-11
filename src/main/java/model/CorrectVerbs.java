package model;

import java.util.ArrayList;

public class CorrectVerbs {
    private final ArrayList<VerbBasic> verbs;
    private final String[] columnNames = {"Ige", "Defin\u00EDci\u00F3(k)"};

    public CorrectVerbs(ArrayList<VerbBasic> verbs) {
        this.verbs = verbs;
    }

    public int size() {
        return verbs.size();
    }

    public String[][] getData() {
        String[][] result = new String[this.size()][columnNames.length];

        for (int i = 0; i < this.size(); i++) {
            String[] temp = {
                    verbs.get(i).getInfinitivo(),
                    verbs.get(i).getDefinitions()
            };
            result[i] = temp;
        }

        return result;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public ArrayList<VerbBasic> getVerbs() {
        return verbs;
    }
}
