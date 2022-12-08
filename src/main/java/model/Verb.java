package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Verb {
    public int ID;
    public VerbBasic basic;

    public ArrayList<String> definitions = new ArrayList<>();
    public Verb pronominal;

    public HashMap<Form, HashMap<Pronoun, String>> forms;

    public Verb(int ID, VerbBasic basic) {
        this.ID = ID;
        this.basic = basic;
        this.forms = new HashMap<>();
    }

    public void printVerb() {
        System.out.println("ID: " + getID() + "\n" +
                getBasic().getInfinitivo() + ":\n" +
                "Participio presento: " + (getBasic().getPresento() != null ? getBasic().getPresento() : "-") + "\n" +
                "Participio pasado: " + (getBasic().getPasado() != null ? getBasic().getPasado() : "-") + "\n");

        for (Form f : forms.keySet()) {
            System.out.println(f.toString() + ":");
            for (Pronoun p : forms.get(f).keySet())
                System.out.println(p.toString() + ": " + forms.get(f).get(p));
            System.out.println("");
        }
    }

    public void appendVerbForm(Form form, HashMap<Pronoun, String> content) {
        forms.put(form, content);
    }

    public String getSolution(Form form, Pronoun pronoun) {
        if (forms.containsKey(form))
            return forms.get(form).get(pronoun);
        else return null;
    }

    public int getID() {
        return ID;
    }

    public VerbBasic getBasic() {
        return basic;
    }

    public void setBasic(VerbBasic basic) {
        this.basic = basic;
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

    public void setDefinitions(ArrayList<String> definitions) {
        this.definitions = definitions;
    }

    public Verb getPronominal() {
        return pronominal;
    }

    public void setPronominal(Verb pronominal) {
        this.pronominal = pronominal;
    }

}
