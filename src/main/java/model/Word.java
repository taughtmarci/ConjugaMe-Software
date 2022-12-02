package model;

import java.util.ArrayList;

public class Word {
    public String femenino;
    public String masculino;

    public ArrayList<String> definition;

    public Word(boolean isFemenino, String word) {
        if (isFemenino) this.femenino = word;
        else this.masculino = word;
    }

    public Word(String femenino, String masculino) {
        this.femenino = femenino;
        this.masculino = masculino;
    }

    public void printWord() {
        System.out.println("La: " + (!femenino.equals("") ? femenino : "-") + "\n" +
                "El: " + (!masculino.equals("") ? masculino : "-") + "\n");
    }
}
