package model;

public enum IconVariation {
    NORMAL (""),
    HOVER ("_hover"),
    CLICKED ("_clicked"),
    GREY("_grey");

    private final String name;

    private IconVariation(String n) {
        this.name = n;
    }
    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }
    public String toString() {
        return this.name;
    }
}
