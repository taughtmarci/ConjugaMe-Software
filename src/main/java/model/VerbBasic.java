package model;

public class VerbBasic {
    public String infitivo;
    public String presente;
    public String pasado;

    boolean hasPresente;
    boolean hasPasado;

    public VerbBasic(String infitivo) {
        this.infitivo = infitivo;
        this.hasPresente = false;
        this.hasPasado = false;
    }

    public VerbBasic(String infinitivo, String second, boolean hasPresente) {
        this.infitivo = infinitivo;
        this.hasPresente = hasPresente;

        if (hasPresente) {
            this.presente = second;
            this.hasPasado = false;
        }
        else {
            this.pasado = second;
            this.hasPasado = true;
        }
    }

    public VerbBasic(String infitivo, String presente, String pasado) {
        this.infitivo = infitivo;
        this.presente = presente;
        this.pasado = pasado;

        this.hasPresente = true;
        this.hasPasado = true;
    }

    public String getInfitivo() {
        return infitivo;
    }

    public void setInfitivo(String infitivo) {
        this.infitivo = infitivo;
    }

    public String getPresente() {
        return presente;
    }

    public void setPresente(String presente) {
        this.presente = presente;
    }

    public String getPasado() {
        return pasado;
    }

    public void setPasado(String pasado) {
        this.pasado = pasado;
    }
}
