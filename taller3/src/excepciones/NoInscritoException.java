package excepciones;

public class NoInscritoException extends Exception {
    /**
     * Excepción que se lanza cuando el estudiante no esta inscrito en ninguna materia
     */
    public NoInscritoException(String mensaje) {super(mensaje);}
}
