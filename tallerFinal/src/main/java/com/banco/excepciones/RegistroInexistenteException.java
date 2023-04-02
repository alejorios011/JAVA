package com.banco.excepciones;

public class RegistroInexistenteException extends Exception {
    /**
     * Excepción que se lanza cuando no existe el registro en la base de datos
     */
    public RegistroInexistenteException(String mensaje) {
        super(mensaje);
    }
}
