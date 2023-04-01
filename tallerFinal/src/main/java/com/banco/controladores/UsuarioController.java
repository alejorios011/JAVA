package com.banco.controladores;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.banco.entidades.Usuario;
import com.banco.servicios.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UsuarioController extends HttpServlet {
    private UsuarioService usuarioService;
    private ObjectMapper mapper;

    public UsuarioController() {
        usuarioService = new UsuarioService();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();
        if (path == null){
            List<Usuario> usuarios = usuarioService.listar();
            String json = mapper.writeValueAsString(usuarios);
            response.setContentType("application/json");
            response.getWriter().println(json);
        } else {
            switch (path){
                case "/buscar":
                    String id = request.getParameter("id");
                    try {
                        Usuario usuario = usuarioService.buscar(id);
                        String json = mapper.writeValueAsString(usuario);
                        response.setContentType("application/json");
                        response.getWriter().println(json);
                    } catch (Exception e){
                        response.setStatus(404);
                        Map<String, String> error = new HashMap<>();
                        error.put("Error", e.getMessage());
                        String json = mapper.writeValueAsString(error);
                        response.setContentType("application/json");
                        response.getWriter().println(json);
                    }
                    break;
                default:
                    response.setStatus(404);
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "No se encontro el usuario");
                    String json = mapper.writeValueAsString(error);
                    response.setContentType("application/json");
                    response.getWriter().println(json);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  ServletException, IOException {
        // Obtenemos el contenido
        String content = request.getContentType();
        // Validamos que no venga vacio y que sea de tipo json
        if (content != null && content.equals("application/json")){
            Map<String, Object> usuarioMap = mapper.readValue(request.getInputStream(), HashMap.class);
            try {
                usuarioService.crear(usuarioMap);
                response.setStatus(HttpServletResponse.SC_CREATED);
                Map<String, String> respuesta = new HashMap<>();
                respuesta.put("mensaje", "Usuario creado con exito");
                String json = mapper.writeValueAsString(respuesta);
                response.setContentType("application/json");
                response.getWriter().println(json);
            } catch (Exception e){
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                String json = mapper.writeValueAsString(error);
                response.setContentType("application/json");
                response.getWriter().println(json);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            Map<String, String> error = new HashMap<>();
            error.put("error", "El contenido debe ser JSON");
            String json = mapper.writeValueAsString(error);
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String content = request.getContentType();
        if (content != null && content.equals("application/json")){
            Map<String, Object> usuarioMap = mapper.readValue(request.getInputStream(), HashMap.class);

            try{
                usuarioService.actualizar(usuarioMap);
                response.setStatus(HttpServletResponse.SC_OK);
                Map<String, String> respuesta = new HashMap<>();
                respuesta.put("mensaje", "Usuario actualizado con exito");
                String json = mapper.writeValueAsString(respuesta);
                response.setContentType("application/json");
                response.getWriter().println(json);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                String json = mapper.writeValueAsString(error);
                response.setContentType("application/json");
                response.getWriter().println(json);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            Map<String, String> error = new HashMap<>();
            error.put("error", "El contenido debe ser Json");
            String json = mapper.writeValueAsString(error);
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        try {
            usuarioService.eliminar(id);

            response.setStatus(HttpServletResponse.SC_OK);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Usuario eliminado con exito");
            String json = mapper.writeValueAsString(respuesta);
            response.setContentType("application/json");
            response.getWriter().println(json);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            String json = mapper.writeValueAsString(error);
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
    }
}