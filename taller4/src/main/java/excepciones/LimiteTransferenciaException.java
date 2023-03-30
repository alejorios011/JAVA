package excepciones;

public class LimiteTransferenciaException extends Exception {
    /**
     * Excepción que se lanza cuando una cuenta corriente no puede hacer más transferencias a una cuenta de ahorro
     */
    public LimiteTransferenciaException(String mensaje) { super(mensaje); }
}
