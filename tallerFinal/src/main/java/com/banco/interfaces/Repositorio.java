package com.banco.interfaces;

import java.util.List;

public interface Repositorio {
    public void crear(Object objeto);
    public void eliminar(String id);
    public void actualizar(Object objeto);
    public List<?> listar();
    public Object buscar(String id);
}
