package ejercicio_cuatro.abstracta;

import excepciones.FondoInsuficienteException;
import excepciones.LimiteDeRetiroException;

public abstract class Cuenta {
    protected int numeroCuenta;
    protected double saldo;
    // Se solicitará el nombre del propietario para hacerlo más visible
    protected String propietario;
    // Crearemos también un atributo para que contabilice las veces que se hacen retiros y depositos
    protected int cantidadRetiros;
    protected int cantidadDepositos;

    // Constructor
    public Cuenta(int numeroCuenta, double saldo, String propietario) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.propietario = propietario;
        // Se inicializa en 0
        this.cantidadRetiros = 0;
        this.cantidadDepositos = 0;
    }

    public abstract void depositar(double monto);
    public abstract void retirar(double monto) throws FondoInsuficienteException, LimiteDeRetiroException;

    // Crearemos también un método global para imprimir la factura en pantalla
    public void imprimir() {
        System.out.println("DATOS DE LA CUENTA");
        System.out.println("Numero de cuenta: " + this.numeroCuenta);
        System.out.println("Saldo: " + this.saldo);
        // Solo para este ejemplo mostremos también el nombre del propietario
        System.out.println("Propietario: " + this.propietario);
        // Para que quede un espacio
        System.out.println("------------------");
    }
}
