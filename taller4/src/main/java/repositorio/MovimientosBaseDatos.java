package repositorio;

import entidades.Cuenta;
import excepciones.FondoInsuficienteException;
import excepciones.LimiteDeRetiroException;
import excepciones.LimiteTransferenciaException;
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
                    "cantidad_deposito = " + cuentaUsuario.getCantidad_deposito() + ", " +
                    "cantidad_transferencia = " + cuentaUsuario.getCantidad_transferencia() + " " +
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
            System.out.println("Debido a politicas de la empresa obtuvo un beneficio extra del 2% del deposito: " + porcentaje);
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
    public void retiroCuentaAhorro(double monto, Cuenta cuentaAhorro) throws FondoInsuficienteException {
        // Validamos primero el número de retiros
        if (cuentaAhorro.getCantidad_retiro() < 3) {
            // Debemos validar también que sea posible retirar el monto solicitado
            if (monto < cuentaAhorro.getSaldo()) {
                cuentaAhorro.setSaldo(cuentaAhorro.getSaldo() - monto);
                // Como se hizo la transacción, aumentamos el número de retiros
                cuentaAhorro.setCantidad_retiro(cuentaAhorro.getCantidad_retiro() + 1);

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
                cuentaAhorro.setCantidad_retiro(cuentaAhorro.getCantidad_retiro() + 1);

                actualizarSaldo(cuentaAhorro);
                // Mostramos la cantidad retirada en pantalla
                System.out.println("¡Transacción exitosa! Ha retirado: " + monto);
                System.out.println("Como ha superado el máximo de retiros gratis por mes, se le cobrara un 1% por cada retiro extra: " + porcentaje);
            } else {
                throw new FondoInsuficienteException("No tiene fondos suficientes para hacer el retiro");
            }
        }
    }

    public void retiroCuentaCorriente(double monto, Cuenta cuentaCorriente) throws FondoInsuficienteException, LimiteDeRetiroException {
        // Primero validamos la cantidad de retiros
        if (cuentaCorriente.getCantidad_retiro() > 5) {
            throw new LimiteDeRetiroException("Ha alcanzado el limite de retiros de este mes");
        } else {
            // Validamos que sea posible retirar el monto solicitado
            if (monto < cuentaCorriente.getSaldo()) {
                cuentaCorriente.setSaldo(cuentaCorriente.getSaldo() - monto);
                // Como se hizo la transacción, aumentamos el número de retiros
                cuentaCorriente.setCantidad_retiro(cuentaCorriente.getCantidad_retiro() + 1);

                actualizarSaldo(cuentaCorriente);
                // Mostramos la cantidad retirada en pantalla
                System.out.println("¡Transacción exitosa! Ha retirado: " + monto);
            } else {
                throw new FondoInsuficienteException("No tiene fondos suficientes para hacer el retiro");
            }
        }
    }

    @Override
    public void transferir(int numTransaccion, double monto, Object objeto) throws Exception {
        // Con el numero de cuenta que recibimos verificamos la información de la cuenta a la que
        // le vamos a hacer la transferencia
        BancoBaseDatos bancoBaseDatos = new BancoBaseDatos();
        Cuenta cuentaTransferencia = (Cuenta) bancoBaseDatos.buscarCuenta(numTransaccion);
        // Hacemos un parseo a la cuenta del usuario
        Cuenta cuentaUsuario = (Cuenta) objeto;

        // Validamos el tipo de cuenta a la que vamos a realizar la transferencia
        if (cuentaTransferencia.getTipo().equals("Ahorro")){
            transferirCuentaAhorro(cuentaUsuario, cuentaTransferencia, monto);
        } else if (cuentaTransferencia.getTipo().equals("Corriente")) {
            transferirCuentaCorriente(cuentaUsuario, cuentaTransferencia, monto);
        } else {
            throw new Exception("Tipo de cuenta no valido");
        }
    }

    public void transferirCuentaAhorro(Cuenta cuentaUsuario, Cuenta cuentaTransferencia, double monto) throws Exception {
        // Como se realizan cobros adicionales, debemos validar el tipo de cuenta de la que estamos haciendo la transferencia
        if (cuentaUsuario.getTipo().equals("Ahorro")){
            // Validamos que sea posible hacer la transferencia
            if (monto <= cuentaUsuario.getSaldo()){
                // Descontamos el monto a la cuenta de la que salio el dinero
                cuentaUsuario.setSaldo(cuentaUsuario.getSaldo() - monto);
                // Sumamos ese mismo monto al saldo a la que se le hace la transferencia
                cuentaTransferencia.setSaldo(cuentaTransferencia.getSaldo() + monto);

                // Actualizamos los saldos de las cuentas
                actualizarSaldo(cuentaUsuario);
                actualizarSaldo(cuentaTransferencia);

                System.out.println("¡Transacción exitosa! " +
                        "Ha transferido a la cuenta " + cuentaTransferencia.getNumero_cuenta() +
                        " un monto de " + monto);
            } else {
                throw new FondoInsuficienteException("No tiene fondos suficientes para hacer la transferencia");
            }
        } else if (cuentaUsuario.getTipo().equals("Corriente")) {
            // Se hace un cobro adicional del 2%
            double cobroAdicional = (monto*2)/100;
            // Actualizamos el monto
            monto = monto+cobroAdicional;
            // Validamos que sea posible hacer la transferencia
            if (monto <= cuentaUsuario.getSaldo()){
                // Solo se puede hacer dos transferencias a cuenta de ahorro desde una corriente
                if (cuentaUsuario.getCantidad_transferencia() < 2){
                    // Descontamos el monto a la cuenta de la que salio el dinero
                    cuentaUsuario.setSaldo(cuentaUsuario.getSaldo() - monto);
                    // Sumamos ese mismo monto al saldo a la que se le hace la transferencia
                    cuentaTransferencia.setSaldo(cuentaTransferencia.getSaldo()+monto);
                    // Aumentamos el numero de transferencias realizadas del usuario
                    // Nota este campo de la BD solo se aplicará para este caso
                    cuentaUsuario.setCantidad_transferencia(cuentaUsuario.getCantidad_transferencia() + 1);

                    // Actualizamos los saldos de las cuentas
                    actualizarSaldo(cuentaUsuario);
                    actualizarSaldo(cuentaTransferencia);
                    System.out.println("¡Transacción exitosa! " +
                            "Ha transferido a la cuenta " + cuentaTransferencia.getNumero_cuenta() +
                            ", un monto de " + monto +
                            ", debido a las politicas se hizo un cobro adicional de: " + cobroAdicional);
                } else {
                    throw new LimiteTransferenciaException("No puede hacer más transferencias a cuentas de ahorros durante este mes");
                }
            } else {
                throw new FondoInsuficienteException("No tiene fondos suficientes para hacer la transferencia");
            }
        } else {
            throw new Exception("Tipo de cuenta no valido");
        }
    }

    public void transferirCuentaCorriente(Cuenta cuentaUsuario, Cuenta cuentaTransferencia, double monto) throws Exception {
        // Validamos el tipo de cuenta de la que estamos haciendo la transferencia
        if (cuentaUsuario.getTipo().equals("Ahorro")){
            // Se hace un cobro adicional del 1.5 %
            double cobroAdicional = (monto*1.5)/100;
            // Actualizamos el monto
            monto = monto+cobroAdicional;

            if (monto <= cuentaUsuario.getSaldo()){
                cuentaUsuario.setSaldo(cuentaUsuario.getSaldo() - monto);
                cuentaTransferencia.setSaldo(cuentaTransferencia.getSaldo() + monto);

                actualizarSaldo(cuentaUsuario);
                actualizarSaldo(cuentaTransferencia);

                System.out.println("¡Transacción exitosa! " +
                        "Ha transferido a la cuenta " + cuentaTransferencia.getNumero_cuenta() +
                        ", un monto de " + monto +
                        ", debido a las politicas se hizo un cobro adicional de: " + cobroAdicional);
            } else {
                throw new FondoInsuficienteException("No tiene fondos suficientes para hacer la transferencia");
            }
        } else if (cuentaUsuario.getTipo().equals("Corriente")) {
            // Se hace un cobro adicional del 2 %
            double cobroAdicional = (monto*2)/100;
            // Actualizamos el monto
            monto = monto+cobroAdicional;

            if (monto <= cuentaUsuario.getSaldo()){
                cuentaUsuario.setSaldo(cuentaUsuario.getSaldo() - monto);
                cuentaTransferencia.setSaldo(cuentaTransferencia.getSaldo() + monto);

                actualizarSaldo(cuentaUsuario);
                actualizarSaldo(cuentaTransferencia);

                System.out.println("¡Transacción exitosa! " +
                        "Ha transferido a la cuenta " + cuentaTransferencia.getNumero_cuenta() +
                        ", un monto de " + monto +
                        ", debido a las politicas se hizo un cobro adicional de: " + cobroAdicional);
            } else {
                throw new FondoInsuficienteException("No tiene fondos suficientes para hacer la transferencia");
            }
        } else {
            throw new Exception("Tipo de cuenta no valido");
        }
    }

    public boolean validarSaldo(double saldoRetiro, double saldoDisponible){
        if (saldoRetiro > saldoDisponible){
            // No se puede hacer la transaccion
            return false;
        } else {
            // Si se puede hacer la transacción
            return true;
        }
    }
}
