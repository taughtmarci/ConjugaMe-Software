package model;

public enum Pronoun {
    Yo ("Yo"),
    Tu ("T\u00FA"),
    Vos ("Vos"),
    Usted ("Ella/\u00E9l"),
    Nosotros ("Nosotros"),
    Vosotros ("Vosotros"),
    Ustedes ("Ellas/ellos");

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

    public static Pronoun fromString(String name) {
        for (Pronoun p : Pronoun.values()) if (p.name.equalsIgnoreCase(name)) return p;
        return null;
    }
}