package controlador;

import modelo.BancoBaseDatos;
import modelo.Cuenta;

import java.util.List;

public class ControladorCuenta {
    // Agregamos como atributo la clase que tiene la conexion a la base de datos
    private BancoBaseDatos bancoBaseDatos;

    public ControladorCuenta() {
        // La inicializamos en el contructor
        bancoBaseDatos = new BancoBaseDatos();
    }

    public void crearCuenta(Cuenta cuentaNueva) throws Exception {
        // Validamos que no lleguen los datos vacios
        if (cuentaNueva != null) {
            bancoBaseDatos.crearCuenta(cuentaNueva);
        } else {
            throw new Exception("Error al crear la cuenta");
        }
    }

    public List<Cuenta> listarCuentas(){
        return (List<Cuenta>) bancoBaseDatos.listarCuentas();
    }

    public Cuenta buscarCuenta(int numCuenta) throws Exception {
        Object cuenta = bancoBaseDatos.buscarCuenta(numCuenta);
        if(cuenta == null){
            throw new Exception("No se encontro la cuenta");
        } else {
            return (Cuenta) cuenta;
        }
    }

    public void eliminarCuenta(int numCuenta){
        bancoBaseDatos.eliminarCuenta(numCuenta);
    }
}
