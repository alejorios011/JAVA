import ejercicio_dos.abstractas.Materias;
import ejercicio_dos.entidades.Estudiantes;
import ejercicio_tres.entidades.ReproductorMp3;
import ejercicio_tres.entidades.ReproductorMp4;
import ejercicio_tres.entidades.ReproductorWav;
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
        // segundoEjercicio();
        // Probando tercer ejercicio
        tercerEjercicio();
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

    // Falta optimización, pues está imprimiendo más de los datos solicitados debido al ciclo y
    // me faltan conocimientos sobre las listas...
    public static void segundoEjercicio() {
        // Creamos los estudiantes para el ejercicio
        Materias estudiante1 = new Estudiantes("Javascript", 1053843791, "Alejandro Rios");
        Materias estudiante2 = new Estudiantes("Javascript", 1154756925, "Valentina Granada");
        Materias estudiante3 = new Estudiantes("Ingles", 75104673, "Cristian David Lopez");
        Materias estudiante4 = new Estudiantes("Ingles", 1154756925, "Valentina Granada");

        // Creamos una lista de materias para agregar los estudiantes
        List<Materias> materias = new ArrayList<Materias>();
        materias.add(estudiante1);
        materias.add(estudiante2);
        materias.add(estudiante3);
        materias.add(estudiante4);

        for (Materias materia : materias) {
            try {
                // Le pasamos el ID
                materia.consultarMaterias(1154756925);
            } catch (NoInscritoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void tercerEjercicio(){
        // Probaremos cada reproductor, las respuestas de porque lo hice como lo hice están en
        // la carpeta de interfaces/Reproductor.Java
        ReproductorMp3 mp3 = new ReproductorMp3(3,16,"3,15Mb", "PUBLIC", "Matthew Alvarado / Ben Lapps / John Vaughn", "Pop/Rock");
        ReproductorWav wav = new ReproductorWav(3,32,"3,32Mb", "Radiohead", "George Fenton", "\n" + "Classical/Vocal Music");
        ReproductorMp4 mp4 = new ReproductorMp4(4,23,"15Mb", "Ed Sheeran", "MP4");

        mp3.reproducirMusica();
        wav.reproducirMusica();
        mp4.reproducirMusica();
    }
}
