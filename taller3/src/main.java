import ejercicio_dos.abstractas.Materias;
import ejercicio_dos.entidades.Estudiantes;
import ejercicio_uno.Boleta;
import ejercicio_uno.Cine;
import excepciones.NoInscritoException;
import excepciones.SinRecursoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        // Probando el ejercicio 1
        // primerEjercicio();
        // Probando segundo ejercicio
        segundoEjercicio();
    }

    // Para que quede más organizado crearé una función para cada ejercicio
    public static void primerEjercicio() {
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

    public static void segundoEjercicio() {
        // Creamos los estudiantes para el ejercicio
        Materias estudiante1 = new Estudiantes("Javascript", 1053843791, "Alejandro Rios");
        Materias estudiante2 = new Estudiantes("Javascript", 1154756925, "Valentina Granada");
        Materias estudiante3 = new Estudiantes("Ingles", 75104673, "Cristian David Lopez");

        // Creamos una lista de materias para agregar los estudiantes
        List<Materias> materias = new ArrayList<Materias>();
        materias.add(estudiante1);
        materias.add(estudiante2);
        materias.add(estudiante3);
        
        for (Materias materia : materias) {
            try {
                // Le pasamos el ID
                materia.consultarMaterias(75104673);
            } catch (NoInscritoException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
