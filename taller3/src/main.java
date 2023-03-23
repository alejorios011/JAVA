import ejercicio_uno.Boleta;
import ejercicio_uno.Cine;
import excepciones.SinRecursoException;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        // Probando el ejercicio 1
        Cine boletas = new Boleta(10000, 50);
        // Vamos a solicitar por consola la cantidad de boletas que desea comprar
        Scanner scanner = new Scanner(System.in);
        // Además de crear una variable que será util más adelante
        boolean salir = false;

        while (!salir) {
            System.out.println("¿Cuantas boletas desea comprar?");
            int cantidad = scanner.nextInt();

            // El bloque try-catch dentro del ciclo es para que pueda continuar normalmente aún
            // si se presenta la excepción.
            try {
                boletas.calcularValorBoleta(cantidad);
                // Cambiamos el valor de salir para terminar el ciclo
                salir = true;
            } catch (SinRecursoException e) {
                System.out.println(e.getMessage());
            }
        }
        // Leí por ahí que era buena práctica cerrar el Scanner cuando se termine de usar
        scanner.close();
    }
}
