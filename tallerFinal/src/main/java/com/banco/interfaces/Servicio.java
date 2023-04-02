package com.banco.interfaces;

import java.util.List;
import java.util.Map;

public interface Servicio {
    public void crear(Map datos) throws Exception;
    public void eliminar(String id);
    public void actualizar(Map datos);
    public List<?> listar();
    public Object buscar(String id) throws Exception;
}
