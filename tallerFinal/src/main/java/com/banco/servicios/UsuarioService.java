package com.banco.servicios;

import com.banco.entidades.Cuenta;
import com.banco.entidades.Usuario;
import com.banco.interfaces.Repositorio;
import com.banco.interfaces.Servicio;
import com.banco.repositorio.CuentaRepository;
import com.banco.repositorio.TransaccionRepository;
import com.banco.repositorio.UsuarioRepository;

import java.util.List;
import java.util.Map;

public class UsuarioService implements Servicio {
    private UsuarioRepository usuarioRepository;

    public UsuarioService() {
        usuarioRepository = new UsuarioRepository();
    }

    @Override
    public void crear(Map datos) {
        String nombre = (String) datos.get("nombre");
        String apellido = (String) datos.get("apellido");
        String cedula = (String) datos.get("cedula");

        Usuario nuevoUsuario = new Usuario(nombre, apellido, cedula);
        usuarioRepository.crear(nuevoUsuario);
    }

    @Override
    public void eliminar(String id) {
        // Primero debemos eliminar los registros de cuentas y transacciones
        CuentaRepository cuentaRepository = new CuentaRepository();
        TransaccionRepository transaccionRepository = new TransaccionRepository();
        // El objeto cuenta nos puede servir para eliminar tanto las transacciones como las cuentas de un determinado usuario
        Cuenta cuenta = (Cuenta) cuentaRepository.buscarPorCuenta(id);
        transaccionRepository.eliminarPorCuenta(String.valueOf(cuenta.getId()));
        cuentaRepository.eliminarPorUsuario(String.valueOf(cuenta.getIdUsuario()));
        // Por ultimo, eliminamos al usuario
        usuarioRepository.eliminar(id);
    }

    @Override
    public void actualizar(Map datos) {
        int id = (int) datos.get("id");
        String nombre = (String) datos.get("nombre");
        String apellido = (String) datos.get("apellido");
        String cedula = (String) datos.get("cedula");

        Usuario actualizarUsuario = new Usuario(id, nombre, apellido, cedula);
        usuarioRepository.actualizar(actualizarUsuario);
    }

    @Override
    public List<Usuario> listar() {
        return (List<Usuario>) usuarioRepository.listar();
    }

    @Override
    public Usuario buscar(String id) throws Exception {
        Object usuario = usuarioRepository.buscar(id);
        if (usuario == null){
            throw new Exception("No se encontró el usuario");
        } else {
            return (Usuario) usuario;
        }
    }
}