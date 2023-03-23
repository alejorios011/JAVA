package excepciones;

public class NoInscritoException extends Exception {
    /**
     * Excepción que se lanza cuando no hay boletas disponibles para venta
     */
    public NoInscritoException(String mensaje) {super(mensaje);}
}
