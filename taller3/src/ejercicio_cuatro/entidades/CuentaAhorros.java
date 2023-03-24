package ejercicio_cuatro.entidades;

import ejercicio_cuatro.abstracta.Cuenta;
import excepciones.FondoInsuficienteException;
import excepciones.LimiteDeRetiroException;

public class CuentaAhorros extends Cuenta {
    public CuentaAhorros(int numeroCuenta, double saldo, String propietario) {
        super(numeroCuenta, saldo, propietario);
    }

    @Override
    public void depositar(double monto) {
        // Validamos la cantidad de depositos para darle el beneficio
        if (this.cantidadDepositos < 2) {
            // Se adiciona un 0.5% más del valor depositado
            double porcentaje = (monto*0.5)/100;
            // Actualizamos el valor del monto
            monto = monto+porcentaje;
            // Y se hace el deposito
            this.saldo = this.saldo + monto;
            // Se aumenta el número de depositos en la cuenta
            this.cantidadDepositos = this.cantidadDepositos+1;

            // Mostramos la cantidad depositada en pantalla
            System.out.println("¡Transacción exitosa! Ha depositado: " + monto);
            // Imprimimos un recibo con los datos y el saldo tiene la cuenta
            imprimir();
        } else {
            // Se hace el deposito normal
            this.saldo = this.saldo + monto;
            // Se aumenta el número de depositos en la cuenta
            this.cantidadDepositos = this.cantidadDepositos+1;

            // Mostramos la cantidad depositada en pantalla
            System.out.println("¡Transacción exitosa! Ha depositado: " + monto);
            // Imprimimos un recibo con los datos y el saldo tiene la cuenta
            imprimir();
        }
    }

    @Override
    public void retirar(double monto) throws FondoInsuficienteException, LimiteDeRetiroException {
        // Validamos primero el número de retiros
        if (this.cantidadRetiros < 3) {
            // Debemos validar también que sea posible retirar el monto solicitado
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
        } else {
            // Como ha realizado más de 3 retiros restamos el 1% del valor que retira
            double porcentaje = (monto*1)/100;
            // Actualizamos el valor a retirar
            monto = monto+porcentaje;

            // Debemos validar que sea posible retirar el monto solicitado
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
