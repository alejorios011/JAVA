package com.banco.repositorio;

import com.banco.entidades.Cuenta;
import com.banco.interfaces.Repositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaRepository implements Repositorio {
    private ConexionBD conexionBD;

    public CuentaRepository() {
        conexionBD = new ConexionBD();
    }

    @Override
    public void crear(Object objeto) {
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            Cuenta nuevaCuenta = (Cuenta) objeto;
            String sql = "INSERT INTO CUENTAS (NUMERO_CUENTA, SALDO, TIPO_CUENTA, ID_USUARIO) " +
                    "VALUES('" + nuevaCuenta.getNumeroCuenta() + "', " +
                    "" + nuevaCuenta.getSaldo() + ", " +
                    "'" + nuevaCuenta.getTipoCuenta() + "', " +
                    "" + nuevaCuenta.getIdUsuario() + ");";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String id) {
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            String sql = "DELETE FROM CUENTAS WHERE ID = " + id + ";";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    // Este no se solicita en el taller pero lo dejare como extra, no se modificara el saldo, solo los demas datos
    @Override
    public void actualizar(Object objeto) {
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            Cuenta cuenta = (Cuenta) objeto;
            String sql = "UPDATE CUENTAS " +
                    "SET NUMERO_CUENTA = '"+ cuenta.getNumeroCuenta() + "', " +
                    "TIPO_CUENTA = '" + cuenta.getTipoCuenta() + "', " +
                    "ID_USUARIO = " + cuenta.getIdUsuario() + " " +
                    "WHERE ID = " + cuenta.getId() + ";";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    @Override
    public List<?> listar() {
        List<Cuenta> cuentas = new ArrayList<Cuenta>();
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            String sql = "SELECT * FROM CUENTAS;";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null){
                while (resultadoConsulta.next()){
                    int id = resultadoConsulta.getInt("ID");
                    String numeroCuenta = resultadoConsulta.getString("NUMERO_CUENTA");
                    double saldo = resultadoConsulta.getDouble("SALDO");
                    String tipoCuenta = resultadoConsulta.getString("TIPO_CUENTA");
                    int idUsuario = resultadoConsulta.getInt("ID_USUARIO");

                    Cuenta cuenta = new Cuenta(id, numeroCuenta, saldo, tipoCuenta, idUsuario);
                    cuentas.add(cuenta);
                }
                return cuentas;
            }
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }

    // Para listar por Id
    public List<?> listarPorId(String idConsulta){
        List<Cuenta> cuentas = new ArrayList<Cuenta>();
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            String sql = "SELECT * FROM CUENTAS WHERE ID_USUARIO = " + idConsulta + ";";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null){
                while (resultadoConsulta.next()){
                    int id = resultadoConsulta.getInt("ID");
                    String numeroCuenta = resultadoConsulta.getString("NUMERO_CUENTA");
                    double saldo = resultadoConsulta.getDouble("SALDO");
                    String tipoCuenta = resultadoConsulta.getString("TIPO_CUENTA");
                    int idUsuario = resultadoConsulta.getInt("ID_USUARIO");

                    Cuenta cuenta = new Cuenta(id, numeroCuenta, saldo, tipoCuenta, idUsuario);
                    cuentas.add(cuenta);
                }
                return cuentas;
            }
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }

    // Este lo usaremos para verificar los datos de una única cuenta
    @Override
    public Object buscar(String idConsulta) {
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            String sql = "SELECT * FROM CUENTAS WHERE ID = " + idConsulta + ";";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null && resultadoConsulta.next()){
                int id = resultadoConsulta.getInt("ID");
                String numeroCuenta = resultadoConsulta.getString("NUMERO_CUENTA");
                double saldo = resultadoConsulta.getDouble("SALDO");
                String tipoCuenta = resultadoConsulta.getString("TIPO_CUENTA");
                int idUsuario = resultadoConsulta.getInt("ID_USUARIO");

                Cuenta cuenta = new Cuenta(id, numeroCuenta, saldo, tipoCuenta, idUsuario);
                return cuenta;
            }
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }
}
