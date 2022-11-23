package model;

public record Group(int id, String name) {
    public boolean validate() {
        return false;
    }
}
