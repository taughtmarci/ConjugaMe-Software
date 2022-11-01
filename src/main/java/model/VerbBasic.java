package model;

public class VerbBasic {
    public String infitivo;
    public String gerundio;
    public String participio;

    boolean hasGerundio;
    boolean hasParticipio;

    public VerbBasic(String infitivo) {
        this.infitivo = infitivo;
        this.hasGerundio = false;
        this.hasParticipio = false;
    }

    public VerbBasic(String infinitivo, String second, boolean hasGerundio) {
        this.infitivo = infinitivo;
        this.hasGerundio = hasGerundio;

        if (hasGerundio) {
            this.gerundio = second;
            this.hasParticipio = false;
        }
        else {
            this.participio = second;
            this.hasParticipio = true;
        }
    }

    public VerbBasic(String infitivo, String gerundio, String participio) {
        this.infitivo = infitivo;
        this.gerundio = gerundio;
        this.participio = participio;

        this.hasGerundio = true;
        this.hasParticipio = true;
    }

    public String getInfitivo() {
        return infitivo;
    }

    public void setInfitivo(String infitivo) {
        this.infitivo = infitivo;
    }

    public String getGerundio() {
        return gerundio;
    }

    public void setGerundio(String gerundio) {
        this.gerundio = gerundio;
    }

    public String getParticipio() {
        return participio;
    }

    public void setParticipio(String participio) {
        this.participio = participio;
    }
}
