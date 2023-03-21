package ejercicio3.inmueble.vivienda.casa;

import ejercicio3.inmueble.vivienda.Vivienda;

public class CasaRural extends Vivienda {
    protected int cantidadPisos;
    // Distancia a la Cabecera Municipal
    protected int distancia;
    // Altitud sobre el nivel del mar
    protected int altitud;

    public CasaRural(int id, int area, String direccion, int numeroHabitaciones, int numeroBaños, int cantidadPisos, int distancia, int altitud) {
        super(id, area, direccion, numeroHabitaciones, numeroBaños);
        this.cantidadPisos = cantidadPisos;
        this.distancia = distancia;
        this.altitud = altitud;
    }

    @Override
    public void consultarPrecio() {
        // Valor por metro cuadrado
        int ValorM2 = 1500000;
        int precio = this.area*ValorM2;
        System.out.println("El precio de compra es de: " + precio);
    }
}
