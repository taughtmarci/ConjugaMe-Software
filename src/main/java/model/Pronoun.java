package model;

public enum Pronoun {
    YO ("Yo"),
    TU ("Tú"),
    VOS ("Vos"),
    USTED ("Usted"),
    NOSOTROS ("Nosotros"),
    VOSOTROS ("Vosotros"),
    USTEDES ("Ustedes");

    private final String name;

    private Pronoun(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}