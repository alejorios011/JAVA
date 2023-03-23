package ejercicio_tres.entidades;

import ejercicio_tres.interfaces.Reproductor;

public class ReproductorMp4 implements Reproductor {
    // No encuentro un tipo de dato para el tiempo asi que los pondré por aparte como tipo int
    private int duracionMinutos;
    private int duracionSegundos;
    private String tamaño;
    private String creador;
    // Tipo de video
    private String tipo;

    // Constructor
    public ReproductorMp4(int duracionMinutos, int duracionSegundos, String tamaño, String creador, String tipo) {
        this.duracionMinutos = duracionMinutos;
        this.duracionSegundos = duracionSegundos;
        this.tamaño = tamaño;
        this.creador = creador;
        this.tipo = tipo;
    }

    @Override
    public void reproducirMusica() {
        System.out.println("Reproduciendo MP4: solo audio");
    }

    // Getters y Setters
    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public void setDuracionSegundos(int duracionSegundos) {
        this.duracionSegundos = duracionSegundos;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
