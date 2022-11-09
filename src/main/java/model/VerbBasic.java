package model;

public class VerbBasic {
    public String infinitivo;
    public String presento;
    public String pasado;


    public VerbBasic(String infinitivo) {
        this.infinitivo = infinitivo;
    }

    public VerbBasic(String infinitivo, String presento, String pasado) {
        this.infinitivo = infinitivo;
        this.presento = presento;
        this.pasado = pasado;
    }

    public String getInfinitivo() {
        return infinitivo;
    }

    public void setInfinitivo(String infinitivo) {
        this.infinitivo = infinitivo;
    }

    public String getPresento() {
        return presento;
    }

    public void setPresento(String presento) {
        this.presento = presento;
    }

    public String getPasado() {
        return pasado;
    }

    public void setPasado(String pasado) {
        this.pasado = pasado;
    }
}
