package ejercicio_tres.entidades;

import ejercicio_tres.interfaces.Reproductor;

public class ReproductorMp3 implements Reproductor{
    // Esta parte será un machetazo también... no encuentro un tipo de dato para el tiempo asi que los
    // pondré por aparte como tipo int
    private int duracionMinutos;
    private int duracionSegundos;
    // Peso de la canción
    private String tamaño;
    private String artista;
    private String compositor;
    private String genero;

    // Constructor
    public ReproductorMp3(int duracionMinutos, int duracionSegundos, String tamaño, String artista, String compositor, String genero) {
        this.duracionMinutos = duracionMinutos;
        this.duracionSegundos = duracionSegundos;
        this.tamaño = tamaño;
        this.artista = artista;
        this.compositor = compositor;
        this.genero = genero;
    }

    // Ejemplo de implementación
    @Override
    public void reproducirMusica() {
        System.out.println("Reproduciendo MP3");
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

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getCompositor() {
        return compositor;
    }

    public void setCompositor(String compositor) {
        this.compositor = compositor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
