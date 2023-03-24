package excepciones;

public class LimiteDeRetiroException extends Exception {
    /**
     * Excepción que se lanza cuando una cuenta corriente tiene más de 5 retiros
     */
    public LimiteDeRetiroException(String mensaje) { super(mensaje); }
}
