package com.banco.controladores;

import com.banco.entidades.Cuenta;
import com.banco.entidades.Usuario;
import com.banco.servicios.CuentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CuentaController extends HttpServlet {
    private CuentaService cuentaService;
    private ObjectMapper mapper;

    public CuentaController() {
        cuentaService = new CuentaService();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null){
            // Esta función no la solicitaban en el ejercicio pero me parecio
            // importante agregarla también para fines prácticos
            List<Cuenta> cuentas = (List<Cuenta>) cuentaService.listar();
            String json = mapper.writeValueAsString(cuentas);
            resp.setContentType("application/json");
            resp.getWriter().println(json);
        } else {
            switch (path){
                // Esta es la función solicitada en el taller, lista las cuentas de un usuario en especifico por Id
                case "/listar":
                    try {
                        String id = req.getParameter("id");
                        List<Cuenta> cuentas = (List<Cuenta>) cuentaService.listarPorUsuario(id);
                        String json = mapper.writeValueAsString(cuentas);
                        resp.setContentType("application/json");
                        resp.getWriter().println(json);
                    } catch (Exception e){
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        Map<String, String> error = new HashMap<>();
                        error.put("error", e.getMessage());
                        String json = mapper.writeValueAsString(error);
                        resp.setContentType("application/json");
                        resp.getWriter().println(json);
                    }
                    break;
                // Sirve para buscar una única cuenta en especifico
                case "/buscar":
                    try {
                        String id = req.getParameter("id");
                        Cuenta cuenta = (Cuenta) cuentaService.buscar(id);
                        String json = mapper.writeValueAsString(cuenta);
                        resp.setContentType("application/json");
                        resp.getWriter().println(json);
                    } catch (Exception e){
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        Map<String, String> error = new HashMap<>();
                        error.put("Error", e.getMessage());
                        String json = mapper.writeValueAsString(error);
                        resp.setContentType("application/json");
                        resp.getWriter().println(json);
                    }
                    break;
                default:
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "No se encontro la cuenta");
                    String json = mapper.writeValueAsString(error);
                    resp.setContentType("application/json");
                    resp.getWriter().println(json);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = req.getContentType();
        if (content != null && content.equals("application/json")){
            Map<String, Object> cuentaMap = mapper.readValue(req.getInputStream(), HashMap.class);
            try {
                cuentaService.crear(cuentaMap);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                Map<String, String> respuesta = new HashMap<>();
                respuesta.put("mensaje", "Nueva cuenta creada con exito");
                String json = mapper.writeValueAsString(respuesta);
                resp.setContentType("application/json");
                resp.getWriter().println(json);
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                String json = mapper.writeValueAsString(error);
                resp.setContentType("application/json");
                resp.getWriter().println(json);
            }

        } else {
            resp.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            Map<String, String> error = new HashMap<>();
            error.put("error", "El contenido debe ser JSON");
            String json = mapper.writeValueAsString(error);
            resp.setContentType("application/json");
            resp.getWriter().println(json);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = req.getContentType();
        if (content.equals("application/json")){
            Map<String, Object> cuentaMap = mapper.readValue(req.getInputStream(), HashMap.class);

            try {
                cuentaService.actualizar(cuentaMap);
                resp.setStatus(HttpServletResponse.SC_OK);
                Map<String, String> respuesta = new HashMap<>();
                respuesta.put("mensaje", "Cuenta modificada con exito");
                String json = mapper.writeValueAsString(respuesta);
                resp.setContentType("application/json");
                resp.getWriter().println(json);
            } catch (Exception e){
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                String json = mapper.writeValueAsString(error);
                resp.setContentType("application/json");
                resp.getWriter().println(json);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            Map<String, String> error = new HashMap<>();
            error.put("error", "El contenido debe ser Json");
            String json = mapper.writeValueAsString(error);
            resp.setContentType("application/json");
            resp.getWriter().println(json);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try{
            cuentaService.eliminar(id);

            resp.setStatus(HttpServletResponse.SC_OK);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Cuenta eliminada con exito");
            String json = mapper.writeValueAsString(respuesta);
            resp.setContentType("application/json");
            resp.getWriter().println(json);
        } catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            String json = mapper.writeValueAsString(error);
            resp.setContentType("application/json");
            resp.getWriter().println(json);
        }
    }
}
