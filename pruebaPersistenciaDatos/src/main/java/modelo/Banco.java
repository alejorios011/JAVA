package modelo;

import java.util.List;

public interface Banco {
    public void crearCuenta(Object objeto);

    public List<?> listarCuentas();

    public Object buscarCuenta(int numeroCuenta);

    public void actualizarCuenta(int numeroCuenta);

    public void eliminarCuenta(int numeroCuenta);
}
