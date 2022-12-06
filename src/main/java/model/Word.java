package model;

import java.util.ArrayList;

public class Word {
    public int ID;
    public String femenino;
    public String masculino;

    public ArrayList<String> definitions = new ArrayList<>();

    public Word(int ID, boolean isFemenino, String word) {
        this.ID = ID;
        if (isFemenino) this.femenino = word;
        else this.masculino = word;
    }

    public Word(int ID, String femenino, String masculino) {
        this.ID = ID;
        this.femenino = femenino;
        this.masculino = masculino;
    }

    public void printWord() {
        StringBuilder currentDefinitions = new StringBuilder();
        for (String def : definitions)
            if (!def.equals("")) currentDefinitions.append(def).append(", ");
        currentDefinitions = new StringBuilder((currentDefinitions.substring(0, currentDefinitions.length() - 2)));

        System.out.println("ID: " + getID() + "\n" +
                "La: " + femenino + "\n" +
                "El: " + masculino + "\n" +
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

    public void setFemenino(String femenino) {
        this.femenino = femenino;
    }

    public String getMasculino() {
        return masculino;
    }

    public void setMasculino(String masculino) {
        this.masculino = masculino;
    }
}
