package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Verb {
    public VerbBasic basic;
    public Verb pronominal;

    public HashMap<Form, HashMap<Pronoun, String>> forms;

    public Verb(VerbBasic basic) {
        this.basic = basic;
        this.forms = new HashMap<>();
    }

    public void printVerb() {
        System.out.println("ID: " + basic.getID() + "\n" +
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

    public int getID() {
        return getBasic().getID();
    }

    public void setID(int ID) {
        getBasic().setID(ID);
    }

    public void appendVerbForm(Form form, HashMap<Pronoun, String> content) {
        forms.put(form, content);
    }

    public String getVerbForm(Form form, Pronoun pronoun) {
        if (forms.containsKey(form)) {
            return forms.get(form).get(pronoun);
        } else return "undefined";
    }

    public String getSolution(Form form, Pronoun pronoun) {
        if (forms.containsKey(form))
            return forms.get(form).get(pronoun);
        else return null;
    }

    public VerbBasic getBasic() {
        return basic;
    }

    public void setBasic(VerbBasic basic) {
        this.basic = basic;
    }

    public Verb getPronominal() {
        return pronominal;
    }

    public void setPronominal(Verb pronominal) {
        this.pronominal = pronominal;
    }

}
