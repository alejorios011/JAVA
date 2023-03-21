package ejercicio3.inmueble.vivienda.apartamento;

import ejercicio3.inmueble.vivienda.Vivienda;

public class ApartamentoFamiliar extends Vivienda {
    protected double administracion;

    public ApartamentoFamiliar(int id, int area, String direccion, int numeroHabitaciones, int numeroBaños, double administracion) {
        super(id, area, direccion, numeroHabitaciones, numeroBaños);
        this.administracion = administracion;
    }

    public void consultarPrecio() {
        // Valor por metro cuadrado
        int ValorM2 = 2000000;
        int precio = this.area*ValorM2;
        System.out.println("El precio de compra es de: " + precio);
    }
}
