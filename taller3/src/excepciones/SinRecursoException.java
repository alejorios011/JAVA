package excepciones;

public class SinRecursoException extends Exception {
    /**
     * Excepción que se lanza cuando no hay boletas disponibles para venta
     */
    public SinRecursoException(String mensaje) {super(mensaje);}
}
