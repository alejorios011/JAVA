package ejercicio3.inmueble.vivienda.apartamento;

import ejercicio3.inmueble.vivienda.Vivienda;

public class Apartaestudio extends Vivienda {

    public Apartaestudio(int id, int area, String direccion, int numeroHabitaciones, int numeroBaños) {
        super(id, area, direccion, numeroHabitaciones, numeroBaños);
        // Los aparta estudios tienen una sola habitación, por lo que reasignamos el valor a una sola habitacion
        this.numeroHabitaciones = 1;
    }

    public void consultarPrecio() {
        // Valor por metro cuadrado
        int ValorM2 = 1500000;
        int precio = this.area*ValorM2;
        System.out.println("El precio de compra es de: " + precio);
    }
}
