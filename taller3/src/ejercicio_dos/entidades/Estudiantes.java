package ejercicio_dos.entidades;

import ejercicio_dos.abstractas.Materias;
import excepciones.NoInscritoException;

public class Estudiantes extends Materias {

    public Estudiantes(String nombreMateria, int idEstudiante, String nombreEstudiante) {
        super(nombreMateria, idEstudiante, nombreEstudiante);
    }

    // Respondiendo a la pregunta, al igual que la anterior, por lo que leí en el tema de excepciones que nos paso,
    // siempre es mejor lanzar excepciones para evitar que el programa termine abruptamente.
    // Y para este tipo de situación hipotetica, creo que existe la posibilidad de que el estudiante no tenga materias inscritas..
    @Override
    public void consultarMaterias(int id) throws NoInscritoException {
        if (id == getIdEstudiante()){
            System.out.println("El alumno esta inscrito en: " + getNombreMateria());
            System.out.println("Id del alumno: " + getIdEstudiante());
            System.out.println("Nombre del alumno: " + getNombreEstudiante());
        } else {
            throw new NoInscritoException("El alumno no esta inscrito");
        }
    }
}
