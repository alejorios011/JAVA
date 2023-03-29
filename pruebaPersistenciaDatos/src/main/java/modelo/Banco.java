package modelo;

import java.util.List;

public interface Banco {
    public void crearCuenta(Object objeto);

    public void actualizarCuenta(int numeroCuenta, String nombreProp, String tipo);

    public void eliminarCuenta(int numeroCuenta);

    public List<?> listarCuentas();

    public Object buscarCuenta(int numeroCuenta);

}
