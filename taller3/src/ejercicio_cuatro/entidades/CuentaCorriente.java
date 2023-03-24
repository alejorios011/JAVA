package ejercicio_cuatro.entidades;

import ejercicio_cuatro.abstracta.Cuenta;
import excepciones.FondoInsuficienteException;
import excepciones.LimiteDeRetiroException;

public class CuentaCorriente extends Cuenta {

    public CuentaCorriente(int numeroCuenta, double saldo, String propietario) {
        super(numeroCuenta, saldo, propietario);
    }

    @Override
    public void depositar(double monto) {
        // Se hace el deposito normal
        this.saldo = this.saldo + monto;
        // Se aumenta el número de depositos en la cuenta
        this.cantidadDepositos = this.cantidadDepositos+1;

        // Mostramos la cantidad depositada en pantalla
        System.out.println("¡Transacción exitosa! Ha depositado: " + monto);
        // Imprimimos un recibo con los datos y el saldo tiene la cuenta
        imprimir();
    }

    @Override
    public void retirar(double monto) throws FondoInsuficienteException, LimiteDeRetiroException {
        // Primero validamos la cantidad de retiros
        if (this.cantidadRetiros > 5) {
            throw new LimiteDeRetiroException("Ha alcanzado el limite de retiros de este mes");
        } else {
            // Validamos que sea posible retirar el monto solicitado
            if (monto < saldo) {
                this.saldo = this.saldo - monto;
                // Como se hizo la transacción, aumentamos el número de retiros
                this.cantidadRetiros = this.cantidadRetiros + 1;

                // Mostramos la cantidad retirada en pantalla
                System.out.println("¡Transacción exitosa! Ha retirado: " + monto);
                // Imprimimos un recibo con los datos y el saldo de la cuenta
                imprimir();
            } else {
                throw new FondoInsuficienteException("No tiene fondos suficientes para hacer el retiro");
            }
        }
    }
}
