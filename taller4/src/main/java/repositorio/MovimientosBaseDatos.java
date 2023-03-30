package repositorio;

import excepciones.FondoInsuficienteException;
import excepciones.LimiteDeRetiroException;
import interfaces.Movimientos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MovimientosBaseDatos implements Movimientos {
    private ConexionBaseDatos db;

    public MovimientosBaseDatos() {
        db = new ConexionBaseDatos();
    }

    // Update especifico para actualizar el saldo
    public void actualizarSaldo(Object objeto){
        try(Connection conexion = DriverManager.getConnection(db.getCadenaConexion())){
            // Haremos un parseo al objeto recibido
            entidades.Cuenta cuentaUsuario = (entidades.Cuenta) objeto;
            String sql = "UPDATE cuentas " + "SET saldo = " + cuentaUsuario.getSaldo() + ", " +
                    "cantidad_retiro =  " + cuentaUsuario.getCantidad_retiro() + ", " +
                    "cantidad_deposito = " + cuentaUsuario.getCantidad_deposito() + " " +
                    "WHERE numero_cuenta = " + cuentaUsuario.getNumero_cuenta() + ";";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e);
        }
    }

    @Override
    public void depositar(double monto, Object objeto) {
        entidades.Cuenta cuentaUsuario = (entidades.Cuenta) objeto;
        // Validamos la cantidad de depositos para darle el beneficio
        if (cuentaUsuario.getCantidad_deposito() < 2) {
            // Se adiciona un 0.5% más del valor depositado
            double porcentaje = (monto*0.5)/100;
            // Actualizamos el valor del monto
            monto = monto+porcentaje;
            // Y se hace el deposito
            cuentaUsuario.setSaldo(cuentaUsuario.getSaldo() + monto);
            // Se aumenta el número de depositos en la cuenta
            cuentaUsuario.setCantidad_deposito(cuentaUsuario.getCantidad_deposito()+1);

            // Con los datos ya actualizados de la cuentaUsuario le pasamos el objeto a la funcion de actualizarSaldo()
            actualizarSaldo(cuentaUsuario);
            // Mostramos la cantidad depositada en pantalla
            System.out.println("¡Transacción exitosa! Ha depositado: " + monto);
        } else {
            // Se hace el deposito normal
            cuentaUsuario.setSaldo(cuentaUsuario.getSaldo() + monto);
            // Se aumenta el número de depositos en la cuenta
            cuentaUsuario.setCantidad_deposito(cuentaUsuario.getCantidad_deposito()+1);

            // Mostramos la cantidad depositada en pantalla
            System.out.println("¡Transacción exitosa! Ha depositado: " + monto);
        }
    }

    @Override
    public void retirar(double monto, Object objeto) throws Exception {
        entidades.Cuenta cuentaUsuario = (entidades.Cuenta) objeto;
        // Validamos el tipo de cuenta y llamamos el método según corresponda
        if (cuentaUsuario.getTipo().equals("Ahorro")){
            retiroCuentaAhorro(monto, cuentaUsuario);
        } else if (cuentaUsuario.getTipo().equals("Corriente")) {
            retiroCuentaCorriente(monto, cuentaUsuario);
        } else {
            throw new Exception("Tipo de cuenta no valido");
        }
    }

    // Craremos dos funciones de retiro segun el tipo de cuenta
    public void retiroCuentaAhorro(double monto, entidades.Cuenta cuentaAhorro) throws FondoInsuficienteException {
        // Validamos primero el número de retiros
        if (cuentaAhorro.getCantidad_retiro() < 3) {
            // Debemos validar también que sea posible retirar el monto solicitado
            if (monto < cuentaAhorro.getSaldo()) {
                cuentaAhorro.setSaldo(cuentaAhorro.getSaldo() - monto);
                // Como se hizo la transacción, aumentamos el número de retiros
                cuentaAhorro.setCantidad_retiro(cuentaAhorro.getCantidad_retiro()+1);

                actualizarSaldo(cuentaAhorro);
                // Mostramos la cantidad retirada en pantalla
                System.out.println("¡Transacción exitosa! Ha retirado: " + monto);
            } else {
                throw new FondoInsuficienteException("No tiene fondos suficientes para hacer el retiro");
            }
        } else {
            // Como ha realizado más de 3 retiros restamos el 1% del valor que retira
            double porcentaje = (monto*1)/100;
            // Actualizamos el valor a retirar
            monto = monto+porcentaje;

            // Validamos que sea posible retirar el monto solicitado
            if (monto < cuentaAhorro.getSaldo()) {
                cuentaAhorro.setSaldo(cuentaAhorro.getSaldo() - monto);
                cuentaAhorro.setCantidad_retiro(cuentaAhorro.getCantidad_retiro()+1);

                actualizarSaldo(cuentaAhorro);
                // Mostramos la cantidad retirada en pantalla
                System.out.println("¡Transacción exitosa! Ha retirado: " + monto);
            } else {
                throw new FondoInsuficienteException("No tiene fondos suficientes para hacer el retiro");
            }
        }
    }

    public void retiroCuentaCorriente(double monto, entidades.Cuenta cuentaCorriente) throws FondoInsuficienteException, LimiteDeRetiroException {
        // Primero validamos la cantidad de retiros
        if (cuentaCorriente.getCantidad_retiro() > 5) {
            throw new LimiteDeRetiroException("Ha alcanzado el limite de retiros de este mes");
        } else {
            // Validamos que sea posible retirar el monto solicitado
            if (monto < cuentaCorriente.getSaldo()) {
                cuentaCorriente.setSaldo(cuentaCorriente.getSaldo() - monto);
                // Como se hizo la transacción, aumentamos el número de retiros
                cuentaCorriente.setCantidad_retiro(cuentaCorriente.getCantidad_retiro()+1);

                actualizarSaldo(cuentaCorriente);
                // Mostramos la cantidad retirada en pantalla
                System.out.println("¡Transacción exitosa! Ha retirado: " + monto);
            } else {
                throw new FondoInsuficienteException("No tiene fondos suficientes para hacer el retiro");
            }
        }
    }

    @Override
    public void transaccion(double monto, Object objeto) {

    }
}
