package excepciones;

public class CampoVacioException extends Exception {
    /**
     * Excepción que se lanza cuando hay un campo vacio en los datos solicitados
     */
    public CampoVacioException(String mensaje) { super(mensaje); }
}
