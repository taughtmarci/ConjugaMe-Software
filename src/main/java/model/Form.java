package model;

public enum Form {
    IndicativoPresente("Indicativo Presente"),
    IndicativoPreterito ("Indicativo Pret\u00E9rito"),
    IndicativoImperfecto ("Indicativo Imperfecto"),
    IndicativoCondicional ("Indicativo Condicional"),
    IndicativoFuturo ("Indicativo Futuro"),
    ImperativoAffirmativo ("Imperativo Affirmativo"),
    ImperativoNegativo ("Imperativo Negativo"),
    SubjuntivoPresente("Subjuntivo Presente"),
    SubjuntivoImperfecto ("Subjuntivo Imperfecto"),
    SubjuntivoFuturo ("Subjuntivo Futuro");

    private final String name;

    private Form(String n) {
        this.name = n;
    }
    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }
    public String toString() {
        return this.name;
    }

    public static Form fromString(String name) {
        for (Form f : Form.values()) if (f.name.equalsIgnoreCase(name)) return f;
        return null;
    }

}
