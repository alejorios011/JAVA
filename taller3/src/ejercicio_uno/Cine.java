package ejercicio_uno;

import excepciones.SinRecursoException;

public abstract class Cine {
    // Este sería el precio de la boleta por unidad
    protected int valorBoleta;
    // Establecemos una cantidad máxima de boletas que se pueden vender
    protected int disponibles;

    public Cine(int valorBoleta, int disponibles) {
        this.valorBoleta = valorBoleta;
        this.disponibles = disponibles;
    }

    // Creamos un método abstracto para que la clase hija implemente la función... (además de practicar el tema de ayer)
    public abstract void calcularValorBoleta(int cantidad) throws SinRecursoException;
}