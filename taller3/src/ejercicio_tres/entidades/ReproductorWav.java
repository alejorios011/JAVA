package ejercicio_tres.entidades;

import ejercicio_tres.interfaces.Reproductor;

public class ReproductorWav implements Reproductor {
    // No encuentro un tipo de dato para el tiempo asi que los pondré por aparte como tipo int
    private int duracionMinutos;
    private int duracionSegundos;
    private String tamaño;
    private String artista;
    private String compositor;
    private String genero;

    public ReproductorWav(int duracionMinutos, int duracionSegundos, String tamaño, String artista, String compositor, String genero) {
        this.duracionMinutos = duracionMinutos;
        this.duracionSegundos = duracionSegundos;
        this.tamaño = tamaño;
        this.artista = artista;
        this.compositor = compositor;
        this.genero = genero;
    }

    @Override
    public void reproducirMusica() {
        System.out.println("Reproduciendo Wav");
    }

    // Getter y Setter

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
