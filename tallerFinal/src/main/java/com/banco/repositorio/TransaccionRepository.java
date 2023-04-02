package com.banco.repositorio;

import com.banco.entidades.Cuenta;
import com.banco.entidades.Transacciones;
import com.banco.excepciones.FondoInsuficienteException;
import com.banco.excepciones.RegistroInexistenteException;
import com.banco.interfaces.Repositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaccionRepository implements Repositorio {
    private ConexionBD conexionBD;
    private CuentaRepository cuentaRepository;

    public TransaccionRepository() {
        conexionBD = new ConexionBD();
        cuentaRepository = new CuentaRepository();
    }

    // Funcion para actualizar el saldo cuando se haga cualquier tipo de transaccion
    public void actualizarSaldo(Object objeto){
        // Haremos un parseo al objeto recibido
        Cuenta cuenta = (Cuenta) objeto;
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            String sql = "UPDATE CUENTAS " +
                    "SET SALDO = " + cuenta.getSaldo() + " " +
                    "WHERE ID = " + cuenta.getId() + ";";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    // Función para consultar la cantidad de transacciones hechas por un mismo tipo de transaccion
    public int consultarCantidadTransacciones(Transacciones transaccion){
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            String sql = "SELECT count('" + transaccion.getTipoTransaccion() + "') AS CANTIDAD_TRANSACCIONES " +
                    "FROM TRANSACCIONES " +
                    "WHERE ID_CUENTA = " + transaccion.getIdCuenta() + ";";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null){
                int cantidadTransacciones = resultadoConsulta.getInt("CANTIDAD_TRANSACCIONES");

                return cantidadTransacciones;
            } else {
                throw new RegistroInexistenteException("No se encontraron transacciones");
            }
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        } catch (RegistroInexistenteException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void depositar(Object objeto) throws RegistroInexistenteException {
        // Hacemos un parseo al objeto recibido
        Transacciones transaccion = (Transacciones) objeto;
        // Como la función buscar() recibe es un String, guardamos en una variable el valor del idCuenta
        String idCuenta = String.valueOf(transaccion.getIdCuenta());
        // Obtenemos los datos de la cuenta a la que va a afectar el saldo del monto recibido
        Cuenta cuentaTransacción = (Cuenta) cuentaRepository.buscar(idCuenta);
        // Validamos que la cuenta si exista
        if(cuentaTransacción != null){
            // Validamos la cantidad de depositos para darle el beneficio
            int cantidadDepositos = consultarCantidadTransacciones(transaccion);
            if (cantidadDepositos < 2){
                // Se adiciona un 0.5% más del valor depositado
                double bonoBienvenida = (transaccion.getMonto()*0.5)/100;
                // Actualizamos el valor del monto
                transaccion.setMonto(transaccion.getMonto() + bonoBienvenida);
                // Actualizamos el atributo saldo con el monto de la transacción
                cuentaTransacción.setSaldo(cuentaTransacción.getSaldo() + transaccion.getMonto());
                // Procedemos a hacer los registros en la base de datos
                crear(transaccion);
                actualizarSaldo(cuentaTransacción);
            } else {
                cuentaTransacción.setSaldo(cuentaTransacción.getSaldo() + transaccion.getMonto());
                crear(transaccion);
                actualizarSaldo(cuentaTransacción);
            }
        } else {
            throw new RegistroInexistenteException(idCuenta + ": Error, esta cuenta no existe");
        }
    }

    public void retirar(Object objeto) throws RegistroInexistenteException, FondoInsuficienteException {
        // Hacemos lo mismo que con la funcion depositar()
        Transacciones transaccion = (Transacciones) objeto;
        String idCuenta = String.valueOf(transaccion.getIdCuenta());
        Cuenta cuentaTransaccion = (Cuenta) cuentaRepository.buscar(idCuenta);
        // Validamos que la cuenta exista
        if (cuentaTransaccion != null){
            // Validamos el número de retiros
            int cantidadRetiros = consultarCantidadTransacciones(transaccion);
            if (cantidadRetiros < 3){
                //Validamos que el monto solicitado se puede retirar
                if (transaccion.getMonto() < cuentaTransaccion.getSaldo()){
                    cuentaTransaccion.setSaldo(cuentaTransaccion.getSaldo() - transaccion.getMonto());
                    crear(transaccion);
                    actualizarSaldo(cuentaTransaccion);
                } else {
                    throw new FondoInsuficienteException("No tiene fondos suficientes para hacer el retiro");
                }
            } else {
                // Como ha realizado más de 3 retiros restamos el 1% del valor que retira
                double cobroAdicional = (transaccion.getMonto()*1)/100;
                // Actualizamos el valor a retirar
                transaccion.setMonto(transaccion.getMonto()+cobroAdicional);

                //Validamos que el monto solicitado se puede retirar
                if (transaccion.getMonto() < cuentaTransaccion.getSaldo()){
                    cuentaTransaccion.setSaldo(cuentaTransaccion.getSaldo() - transaccion.getMonto());
                    crear(transaccion);
                    actualizarSaldo(cuentaTransaccion);
                } else {
                    throw new FondoInsuficienteException("No tiene fondos suficientes para hacer el retiro");
                }
            }
        } else {
            throw new RegistroInexistenteException(idCuenta + ": Error, esta cuenta no existe");
        }
    }

    public void transferir(Object objeto) throws RegistroInexistenteException, FondoInsuficienteException {
        Transacciones transaccion = (Transacciones) objeto;
        String idCuentaTransaccion = String.valueOf(transaccion.getIdCuenta());
        Cuenta cuentaTransaccion = (Cuenta) cuentaRepository.buscar(idCuentaTransaccion);
        if (cuentaTransaccion != null){
            // Se haran unos cobros adicionales dependiendo de los tipos de cuentas
            if (cuentaTransaccion.getTipoCuenta() == "Ahorro" && cuentaTransaccion.getTipoCuenta() != transaccion.getTipoCuentaDestino()){
                // Se hace un cobro adicional del 1.5 %
                double cobroAdicional = (transaccion.getMonto()*1.5)/100;
                // Actualizamos el valor a retirar
                transaccion.setMonto(transaccion.getMonto() + cobroAdicional);
                //Validamos que el monto solicitado se puede retirar
                if (transaccion.getMonto() < cuentaTransaccion.getSaldo()){
                    // Actualizamos el saldo en ambos objetos cuenta
                    cuentaTransaccion.setSaldo(cuentaTransaccion.getSaldo() - transaccion.getMonto());
                    crear(transaccion);
                    // Actualizamos el saldo de las cuentas en la BD
                    actualizarSaldo(cuentaTransaccion);
                } else {
                    throw new FondoInsuficienteException("No tiene fondos suficientes para hacer el retiro");
                }
            } else if (cuentaTransaccion.getTipoCuenta() == "Corriente" && cuentaTransaccion.getTipoCuenta() != transaccion.getTipoCuentaDestino()) {
                // Se hace un cobro adicional del 2 %
                double cobroAdicional = (transaccion.getMonto()*2)/100;
                // Actualizamos el valor a retirar
                transaccion.setMonto(transaccion.getMonto() + cobroAdicional);
                //Validamos que el monto solicitado se puede retirar
                if (transaccion.getMonto() < cuentaTransaccion.getSaldo()){
                    // Actualizamos el saldo en ambos objetos cuenta
                    cuentaTransaccion.setSaldo(cuentaTransaccion.getSaldo() - transaccion.getMonto());
                    crear(transaccion);
                    // Actualizamos el saldo de las cuentas en la BD
                    actualizarSaldo(cuentaTransaccion);
                } else {
                    throw new FondoInsuficienteException("No tiene fondos suficientes para hacer el retiro");
                }
            } else {
                //Validamos que el monto solicitado se puede retirar
                if (transaccion.getMonto() < cuentaTransaccion.getSaldo()){
                    // Actualizamos el saldo en ambos objetos cuenta
                    cuentaTransaccion.setSaldo(cuentaTransaccion.getSaldo() - transaccion.getMonto());
                    crear(transaccion);
                    // Actualizamos el saldo de las cuentas en la BD
                    actualizarSaldo(cuentaTransaccion);
                } else {
                    throw new FondoInsuficienteException("No tiene fondos suficientes para hacer el retiro");
                }
            }
        } else {
            throw new RegistroInexistenteException(idCuentaTransaccion + ": Error, esta cuenta no existe");
        }
    }

    @Override
    public void crear(Object objeto) {
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            Transacciones transaccion = (Transacciones) objeto;
            String sql = "INSERT INTO TRANSACCIONES (FECHA, HORA, TIPO_TRANSACCION, MONTO, ID_CUENTA, TIPO_CUENTA_DESTINO)" +
                    "VALUES('" + transaccion.getFecha() + "', " +
                    "'" + transaccion.getHora() + "', " +
                    "'" + transaccion.getTipoTransaccion() + "', " +
                    "" + transaccion.getMonto() + ", " +
                    "" + transaccion.getIdCuenta() + ", " +
                    "'" + transaccion.getTipoCuentaDestino() + "');";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String id) {
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            String sql = "DELETE FROM TRANSACCIONES WHERE ID = " + id + ";";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    public void eliminarPorCuenta(String idCuenta) {
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            String sql = "DELETE FROM TRANSACCIONES WHERE ID_CUENTA = " + idCuenta + ";";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Object objeto) {
        // No se va a implementar debido a que no se solicita en el ejercicio y tampoco debería permitirse modificar una transacción
    }

    @Override
    public List<?> listar() {
        List<Transacciones> transacciones = new ArrayList<Transacciones>();
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            String sql = "SELECT * FROM TRANSACCIONES";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null){
                while (resultadoConsulta.next()){
                    int id = resultadoConsulta.getInt("ID");
                    String fecha = resultadoConsulta.getString("FECHA");
                    String hora = resultadoConsulta.getString("HORA");
                    String tipoTransaccion = resultadoConsulta.getString("TIPO_TRANSACCION");
                    double monto = resultadoConsulta.getDouble("MONTO");
                    int idCuenta = resultadoConsulta.getInt("ID_CUENTA");
                    String tipoCuentaDestino = resultadoConsulta.getString("TIPO_CUENTA_DESTINO");

                    Transacciones transaccion = new Transacciones(id, fecha, hora, tipoTransaccion, monto, idCuenta, tipoCuentaDestino);
                    transacciones.add(transaccion);
                }
                return transacciones;
            }
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }

    public List<?> listarPorId(String idConsulta){
        List<Transacciones> transacciones = new ArrayList<Transacciones>();
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            String sql = "SELECT * FROM TRANSACCIONES WHERE ID_CUENTA = "+ idConsulta + ";";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null){
                while (resultadoConsulta.next()){
                    int id = resultadoConsulta.getInt("ID");
                    String fecha = resultadoConsulta.getString("FECHA");
                    String hora = resultadoConsulta.getString("HORA");
                    String tipoTransaccion = resultadoConsulta.getString("TIPO_TRANSACCION");
                    double monto = resultadoConsulta.getDouble("MONTO");
                    int idCuenta = resultadoConsulta.getInt("ID_CUENTA");
                    String tipoCuentaDestino = resultadoConsulta.getString("TIPO_CUENTA_DESTINO");

                    Transacciones transaccion = new Transacciones(id, fecha, hora, tipoTransaccion, monto, idCuenta, tipoCuentaDestino);
                    transacciones.add(transaccion);
                }
                return transacciones;
            }
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Object buscar(String idConsulta) {
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            String sql = "SELECT * FROM TRANSACCIONES WHERE ID = "+ idConsulta + ";";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null && resultadoConsulta.next()){
                int id = resultadoConsulta.getInt("ID");
                String fecha = resultadoConsulta.getString("FECHA");
                String hora = resultadoConsulta.getString("HORA");
                String tipoTransaccion = resultadoConsulta.getString("TIPO_TRANSACCION");
                double monto = resultadoConsulta.getDouble("MONTO");
                int idCuenta = resultadoConsulta.getInt("ID_CUENTA");
                String tipoCuentaDestino = resultadoConsulta.getString("TIPO_CUENTA_DESTINO");

                Transacciones transaccion = new Transacciones(id, fecha, hora, tipoTransaccion, monto, idCuenta, tipoCuentaDestino);
                return transaccion;
            }
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }
}
