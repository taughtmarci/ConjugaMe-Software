package model;

import java.util.ArrayList;

public class Word {
    public final int ID;
    public final String femenino;
    public final String masculino;
    public final boolean isNoun;

    public ArrayList<String> definitions = new ArrayList<>();

    public Word(int ID, String femenino, String masculino, boolean isNoun) {
        this.ID = ID;
        this.femenino = femenino;
        this.masculino = masculino;
        this.isNoun = isNoun;
    }

    public void printWord() {
        StringBuilder currentDefinitions = new StringBuilder();
        for (String def : definitions)
            if (!def.equals("")) currentDefinitions.append(def).append(", ");
        currentDefinitions = new StringBuilder((currentDefinitions.substring(0, currentDefinitions.length() - 2)));

        System.out.println("ID: " + getID() + "\n" +
                "La: " + femenino + "\n" +
                "El: " + masculino + "\n" +
                "Noun: " + isNoun() + "\n" +
                currentDefinitions + "\n");
    }

    public void addDefinition(String text) {
        definitions.add(text);
    }

    public int getID() {
        return ID;
    }

    public String getFemenino() {
        return femenino;
    }

    public String getMasculino() {
        return masculino;
    }

    public boolean isNoun() {
        return isNoun;
    }
}
