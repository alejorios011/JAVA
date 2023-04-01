package com.banco.repositorio;

import com.banco.entidades.Usuario;
import com.banco.interfaces.Repositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository implements Repositorio {
    private ConexionBD conexionBD;

    public UsuarioRepository() {
        conexionBD = new ConexionBD();
    }

    @Override
    public void crear(Object objeto) {
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            // Hacemos un parseo del objeto entrante
            Usuario nuevoUsuario = (Usuario) objeto;
            String sql = "INSERT INTO USUARIOS (NOMBRE, APELLIDO, CEDULA) " +
                    "VALUES('"+ nuevoUsuario.getNombre() +"', " +
                    "'" + nuevoUsuario.getApellido() + "', " +
                    "'" + nuevoUsuario.getCedula() + "');";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String id) {
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            String sql = "DELETE FROM USUARIOS WHERE ID=" + id + ";";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Object objeto) {
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            Usuario usuario = (Usuario) objeto;
            String sql = "UPDATE USUARIOS " +
                    "SET NOMBRE = '" + usuario.getNombre() + "', " +
                    "APELLIDO = '" + usuario.getApellido() + "', " +
                    "CEDULA = '" + usuario.getCedula() + "' " +
                    "WHERE ID = " + usuario.getId() + ";";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

    @Override
    public List<?> listar() {
        List<Usuario> usuarios = new ArrayList<Usuario>();

        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            String sql = "SELECT * FROM USUARIOS;";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null) {
                while (resultadoConsulta.next()){
                    int id = resultadoConsulta.getInt("ID");
                    String nombre = resultadoConsulta.getString("NOMBRE");
                    String apellido = resultadoConsulta.getString("APELLIDO");
                    String cedula = resultadoConsulta.getString("CEDULA");

                    Usuario usuario = new Usuario(id, nombre, apellido, cedula);
                    usuarios.add(usuario);
                }
                return usuarios;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Object buscar(String idConsulta) {
        try(Connection conexion = DriverManager.getConnection(conexionBD.getCadenaConexion())){
            String sql = "SELECT * FROM USUARIOS WHERE ID=" + idConsulta + ";";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null && resultadoConsulta.next()){
                int id = resultadoConsulta.getInt("ID");
                String nombre = resultadoConsulta.getString("NOMBRE");
                String apellido = resultadoConsulta.getString("APELLIDO");
                String cedula = resultadoConsulta.getString("CEDULA");

                Usuario usuario = new Usuario(id, nombre, apellido, cedula);
                return usuario;
            }
        } catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return null;
    }
}
