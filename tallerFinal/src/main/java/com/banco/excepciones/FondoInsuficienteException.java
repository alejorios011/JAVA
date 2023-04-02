package com.banco.excepciones;

public class FondoInsuficienteException extends Exception{
    /**
     * Excepción que se lanza cuando no hay fondos suficientes en la cuenta para hacer el retiro
     */
    public FondoInsuficienteException(String mensaje) { super(mensaje); }
}
