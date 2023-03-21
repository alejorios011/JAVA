package ejercicio3.inmueble.vivienda.casa;

import ejercicio3.inmueble.vivienda.Vivienda;

import java.util.Scanner;

public class CasaUrbana extends Vivienda {
    protected int cantidadPisos;
    // Las casas urbanas pueden estar en un conjunto cerrado o ser independientes,
    // por lo que para no crear mas clases, agregaremos un(os) atributo(s) e
    // inventaremos algunos metodos segun el tipo de casa urbana
    protected String tipo;
    protected double administracion;
    protected boolean piscina;
    protected boolean campoDeportivo;

    public CasaUrbana(int id, int area, String direccion, int numeroHabitaciones, int numeroBaños, int cantidadPisos) {
        super(id, area, direccion, numeroHabitaciones, numeroBaños);
        this.cantidadPisos = cantidadPisos;
    }

    // Primero, creamos una función que nos solicite el tipo de casa Urbana
    public void solicitarTipo() {
        // Creamos la variable que solicitara los datos por consola
        Scanner lectura = new Scanner(System.in);
        System.out.println("¿La vivienda esta en un conjunto cerrado o es independiente?");
        // Actualizamos el atributo tipo segun el dato que nos dieron
        this.tipo = lectura.nextLine();
    }

    // Creamos un método que nos solicite mas datos según el tipo de casa Urbana
    public void tipoCasaUrbana() {
        // si esta en conjunto cerrado
        if (this.tipo.equals("conjunto cerrado")) {
            Scanner lectura = new Scanner(System.in);
            // Primero solicitamos la administracion
            System.out.println("¿Cuanto paga de administración?");
            this.administracion = lectura.nextDouble();
            // Despues preguntamos por las areas comunes
            System.out.println("El conjunto cerrado dispone de piscina: ¿Si/No?");
            this.piscina = lectura.next().equalsIgnoreCase("Si")?true:false;
            System.out.println("El conjunto cerrado dispone de campo deportivo: ¿Si/No?");
            this.campoDeportivo = lectura.next().equalsIgnoreCase("Si")?true:false;
        } else {
            // Si es independiente entonces no pagaremos administracion y tampoco dispondremos de areas comunes
            this.administracion = 0;
            this.piscina = false;
            this.campoDeportivo = false;
        }
    }

    public void consultarPrecio() {
        // El precio cambiara según el tipo de vivienda
        if (this.tipo.equalsIgnoreCase("Conjunto cerrado")) {
            int ValorM2 = 2500000;
            int precio = this.area*ValorM2;
            System.out.println("El precio de compra es de: " + precio);
        } else if (this.tipo.equalsIgnoreCase("Independiente")){
            int ValorM2 = 3000000;
            int precio = this.area*ValorM2;
            System.out.println("El precio de compra es de: " + precio);
        }
    }
}
