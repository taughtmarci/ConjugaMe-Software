package model;

import java.util.ArrayList;

public class Verb {
    public VerbBasic basic;
    public ArrayList<String> definition;

    public Verb pronominal;

    public ArrayList<String> indicativoPresente;
    public ArrayList<String> indicativoImperfecto;
    public ArrayList<String> indicativoPreterito;
    public ArrayList<String> indicativoFuturo;
    public ArrayList<String> indicativoCondicional;

    public ArrayList<String> subjuntivoPresente;
    public ArrayList<String> subjuntivoImperfecto;
    public ArrayList<String> subjuntivoFuturo;

    public ArrayList<String> imperativoAfirmativo;
    public ArrayList<String> imperativoNegativo;

    public Verb(VerbBasic basic) {
        this.basic = basic;
    }

    public VerbBasic getBasic() {
        return basic;
    }

    public void setBasic(VerbBasic basic) {
        this.basic = basic;
    }

    public ArrayList<String> getDefinition() {
        return definition;
    }

    public void setDefinition(ArrayList<String> definition) {
        this.definition = definition;
    }

    public Verb getPronominal() {
        return pronominal;
    }

    public void setPronominal(Verb pronominal) {
        this.pronominal = pronominal;
    }

    public ArrayList<String> getIndicativoPresente() {
        return indicativoPresente;
    }

    public void setIndicativoPresente(ArrayList<String> indicativoPresente) {
        this.indicativoPresente = indicativoPresente;
    }

    public ArrayList<String> getIndicativoImperfecto() {
        return indicativoImperfecto;
    }

    public void setIndicativoImperfecto(ArrayList<String> indicativoImperfecto) {
        this.indicativoImperfecto = indicativoImperfecto;
    }

    public ArrayList<String> getIndicativoPreterito() {
        return indicativoPreterito;
    }

    public void setIndicativoPreterito(ArrayList<String> indicativoPreterito) {
        this.indicativoPreterito = indicativoPreterito;
    }

    public ArrayList<String> getIndicativoFuturo() {
        return indicativoFuturo;
    }

    public void setIndicativoFuturo(ArrayList<String> indicativoFuturo) {
        this.indicativoFuturo = indicativoFuturo;
    }

    public ArrayList<String> getIndicativoCondicional() {
        return indicativoCondicional;
    }

    public void setIndicativoCondicional(ArrayList<String> indicativoCondicional) {
        this.indicativoCondicional = indicativoCondicional;
    }

    public ArrayList<String> getSubjuntivoPresente() {
        return subjuntivoPresente;
    }

    public void setSubjuntivoPresente(ArrayList<String> subjuntivoPresente) {
        this.subjuntivoPresente = subjuntivoPresente;
    }

    public ArrayList<String> getSubjuntivoImperfecto() {
        return subjuntivoImperfecto;
    }

    public void setSubjuntivoImperfecto(ArrayList<String> subjuntivoImperfecto) {
        this.subjuntivoImperfecto = subjuntivoImperfecto;
    }

    public ArrayList<String> getSubjuntivoFuturo() {
        return subjuntivoFuturo;
    }

    public void setSubjuntivoFuturo(ArrayList<String> subjuntivoFuturo) {
        this.subjuntivoFuturo = subjuntivoFuturo;
    }

    public ArrayList<String> getImperativoAfirmativo() {
        return imperativoAfirmativo;
    }

    public void setImperativoAfirmativo(ArrayList<String> imperativoAfirmativo) {
        this.imperativoAfirmativo = imperativoAfirmativo;
    }

    public ArrayList<String> getImperativoNegativo() {
        return imperativoNegativo;
    }

    public void setImperativoNegativo(ArrayList<String> imperativoNegativo) {
        this.imperativoNegativo = imperativoNegativo;
    }
}
