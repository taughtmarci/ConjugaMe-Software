package model;

public enum Difficulty {
    Student ("Tanul\u00F3"),
    Easy ("K\u00F6nny\u0171"),
    Medium ("K\u00F6zepes"),
    Hard ("Neh\u00E9z"),
    WithoutHint ("Nincs seg\u00EDts\u00E9g");

    private final String name;

    private Difficulty(String n) {
        this.name = n;
    }
    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }
    public String toString() {
        return this.name;
    }

    public static Difficulty fromString(String name) {
        for (Difficulty d : Difficulty.values()) if (d.name.equalsIgnoreCase(name)) return d;
        return null;
    }
}
