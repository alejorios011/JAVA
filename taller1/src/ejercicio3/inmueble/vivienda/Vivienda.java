package ejercicio3.inmueble.vivienda;

import ejercicio3.inmueble.Inmueble;

public abstract class Vivienda extends Inmueble {
    protected int numeroHabitaciones;
    protected int numeroBaños;

    public Vivienda(int id, int area, String direccion, int numeroHabitaciones, int numeroBaños) {
        super(id, area, direccion);
        this.numeroHabitaciones = numeroHabitaciones;
        this.numeroBaños = numeroBaños;
    }

    abstract public void consultarPrecio ();
}
