package com.banco.servicios;

import com.banco.entidades.Usuario;
import com.banco.interfaces.Repositorio;
import com.banco.interfaces.Servicio;
import com.banco.repositorio.UsuarioRepository;

import java.util.List;
import java.util.Map;

public class UsuarioService implements Servicio {
    private Repositorio repositorioUsuario;

    public UsuarioService() {
        repositorioUsuario = new UsuarioRepository();
    }

    @Override
    public void crear(Map datos) {
        String nombre = (String) datos.get("nombre");
        String apellido = (String) datos.get("apellido");
        String cedula = (String) datos.get("cedula");

        Usuario nuevoUsuario = new Usuario(nombre, apellido, cedula);
        repositorioUsuario.crear(nuevoUsuario);
    }

    @Override
    public void eliminar(String id) {
        repositorioUsuario.eliminar(id);
    }

    @Override
    public void actualizar(Map datos) {
        int id = (int) datos.get("id");
        String nombre = (String) datos.get("nombre");
        String apellido = (String) datos.get("apellido");
        String cedula = (String) datos.get("cedula");

        Usuario actualizarUsuario = new Usuario(id, nombre, apellido, cedula);
        repositorioUsuario.actualizar(actualizarUsuario);
    }

    @Override
    public List<Usuario> listar() {
        return (List<Usuario>) repositorioUsuario.listar();
    }

    @Override
    public Usuario buscar(String id) throws Exception {
        Object usuario = repositorioUsuario.buscar(id);
        if (usuario == null){
            throw new Exception("No se encontró el usuario");
        } else {
            return (Usuario) usuario;
        }
    }
}