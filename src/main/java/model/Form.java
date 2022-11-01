package model;

public enum Form {
    Gerundio ("Gerundio"),
    Participio ("Participio"),
    IndicativoPresento ("Indicativo Presento"),
    IndicativoPreterito ("Indicativo Pret\u00E9rito"),
    IndicativoImperfecto ("Indicativo Imperfecto"),
    IndicativoCondicional ("Indicativo Condicional"),
    IndicativoFuturo ("Indicativo Futuro"),
    SubjuntivoPresento ("Subjuntivo Presento"),
    SubjuntivoImperfecto ("Subjuntivo Imperfecto"),
    SubjuntivoFuturo ("Subjuntivo Futuro"),
    ImperativoAffirmativo ("Imperativo Affirmativo"),
    ImperativoNegativo ("Imperativo Negativo");

    private final String name;
    private Form(String s) {
        name = s;
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
