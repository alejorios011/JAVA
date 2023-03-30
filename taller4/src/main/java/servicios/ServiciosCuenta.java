package servicios;

import excepciones.CampoVacioException;
import repositorio.BancoBaseDatos;
import entidades.Cuenta;
import repositorio.MovimientosBaseDatos;

import java.util.List;

public class ServiciosCuenta {
    // Agregamos como atributo la clase que tiene la conexion a la base de datos
    private BancoBaseDatos bancoBaseDatos;
    private MovimientosBaseDatos cuentaBaseDatos;

    public ServiciosCuenta() {
        // La inicializamos en el contructor
        bancoBaseDatos = new BancoBaseDatos();
        cuentaBaseDatos = new MovimientosBaseDatos();
    }

    public void crearCuenta(Cuenta cuentaNueva) throws Exception {
        // Validamos que no lleguen los datos vacios
        if (cuentaNueva != null) {
            bancoBaseDatos.crearCuenta(cuentaNueva);
        } else {
            throw new Exception("Error al crear la cuenta");
        }
    }

    public void actualizarCuenta(int numCuenta, String nombreProp, String tipo) throws CampoVacioException {
        // Validemos que el nombre y el tipo no lleguen vacios
        if (nombreProp != null && tipo != null){
            bancoBaseDatos.actualizarCuenta(numCuenta, nombreProp, tipo);
        } else {
            throw new CampoVacioException("Datos faltantes, por favor llene todos los campos solicitados");
        }
    }

    public void eliminarCuenta(int numCuenta){
        bancoBaseDatos.eliminarCuenta(numCuenta);
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

    public void depositar(double monto, Object objeto) throws Exception {
        // Solicitemos que el valor del deposito sea mayor a 10000
        if (monto > 10000) {
            cuentaBaseDatos.depositar(monto, objeto);
        } else {
            throw new Exception("El valor minimo del deposito es de 10000");
        }
    }

    public void retirar(double monto, Object objeto) throws Exception {
        // Solicitemos que el valor del retiro sea mayor a 10000
        if (monto > 10000){
            cuentaBaseDatos.retirar(monto, objeto);
        } else {
            throw new Exception("El valor minimo de retiro es de 10000");
        }
    }
}
