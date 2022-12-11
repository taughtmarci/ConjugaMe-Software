package model;

import java.util.ArrayList;

public class VerbBasic {
    public int ID;
    public String infinitivo;
    public String presento;
    public String pasado;

    public ArrayList<String> definitions = new ArrayList<>();


    public VerbBasic(int ID, String infinitivo) {
        this.ID = ID;
        this.infinitivo = infinitivo;
    }

    public VerbBasic(String infinitivo, String presento, String pasado) {
        this.infinitivo = infinitivo;
        this.presento = presento;
        this.pasado = pasado;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getInfinitivo() {
        return infinitivo;
    }

    public void setInfinitivo(String infinitivo) {
        this.infinitivo = infinitivo;
    }

    public String getPresento() {
        return presento;
    }

    public void setPresento(String presento) {
        this.presento = presento;
    }

    public String getPasado() {
        return pasado;
    }

    public void setPasado(String pasado) {
        this.pasado = pasado;
    }


    public void addDefinition(String text) {
        definitions.add(text);
    }

    public String getDefinitions() {
        StringBuilder currentDefinitions = new StringBuilder();
        for (String def : definitions)
            if (!def.equals("")) currentDefinitions.append(def).append(", ");
        if (currentDefinitions.length() > 1)
            currentDefinitions = new StringBuilder((currentDefinitions.substring(0, currentDefinitions.length() - 2)));
        return currentDefinitions.toString();
    }

    public String getDefinition(int at) {
        return definitions.get(at);
    }

    public void setDefinitions(ArrayList<String> definitions) {
        this.definitions = definitions;
    }
}
