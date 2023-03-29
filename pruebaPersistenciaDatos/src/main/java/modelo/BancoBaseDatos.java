package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BancoBaseDatos implements Banco {

    private String cadenaConexion;

    // Haremos la conexión a base de datos dentro del constructor para que se cree apenas se instancie
    public BancoBaseDatos() {
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            cadenaConexion = "jdbc:sqlite:pruebas.db";
            crearTabla();
        } catch (SQLException e) {
            System.err.println("Error de conexión con la base de datos: " + e);
        }
    }

    // Creamos un método para crear una tabla en la bd y hacer pruebas con esta
    private void crearTabla() {
        // Asociamos la conexión dentro del bloque try para que Java se encargue de cerrarlo
        // automáticamente cuando termine el bloque de código
        try(Connection conexion = DriverManager.getConnection(cadenaConexion)){
            // Creamos un String con el SQL para la tabla
            String sql = "CREATE TABLE IF NOT EXISTS cuentas (\n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                    "numero_cuenta INTEGER NOT NULL UNIQUE, \n" +
                    "nombre_propietario TEXT NOT NULL, \n" +
                    "saldo REAL, \n" +
                    "tipo TEXT NOT NULL, \n" +
                    "cantidad_retiro INTEGER NOT NULL, \n" +
                    "cantidad_deposito INTEGER NOT NULL\n" +
                    ");";
            // Se crea la sentencia para ejecutar el SQL
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e);
        } catch (NullPointerException e) {
            System.out.println("No hay registros en la tabla: " + e);
        }
    }

    @Override
    public void crearCuenta(Object objeto) {
        try(Connection conexion = DriverManager.getConnection(cadenaConexion)){
            Cuenta cuenta = (Cuenta) objeto;
            // Creamos un String de la sentencia SQL para insertar los datos en la tabla
            String sql = "INSERT INTO cuentas (numero_cuenta, nombre_propietario, saldo, tipo, cantidad_retiro, cantidad_deposito) " +
                    "VALUES(" + cuenta.getNumero_cuenta() + ", " +
                    "'" + cuenta.getNombre_propietario() + "', " +
                    "" + cuenta.getSaldo() + ", " +
                    "'" + cuenta.getTipo() + "', " +
                    "" + cuenta.getCantidad_retiro() + ", " +
                    "" + cuenta.getCantidad_deposito() + ");";
            // Preparamos el Statement para poder ejecutar la sentencia SQL
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e);
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }

    @Override
    public void actualizarCuenta(int numeroCuenta, String nombreProp, String tipo) {
        try(Connection conexion = DriverManager.getConnection(cadenaConexion)){
            // Unicamente permitiremos que modifique el nombre del propietario y el tipo de cuenta
            String sql = "UPDATE cuentas " +
                    "SET nombre_propietario= '" + nombreProp + "', tipo= '" + tipo + "' " +
                    "WHERE numero_cuenta=" + numeroCuenta + ";";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e);
        }
    }

    @Override
    public void eliminarCuenta(int numeroCuenta) {
        try(Connection conexion = DriverManager.getConnection(cadenaConexion)){
            String sql = "DELETE FROM cuentas WHERE numero_cuenta = " + numeroCuenta + ";";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e);
        }
    }

    @Override
    public List<?> listarCuentas() {
        List<Cuenta> cuentas = new ArrayList<Cuenta>();
        // Probamos la conexion
        try(Connection conexion = DriverManager.getConnection(cadenaConexion)){
            // Creamos la sentencia SQL para hacer la consulta a la BD
            String sql = "SELECT * FROM cuentas;";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            // Guardamos el resultado de la consulta
            ResultSet resultadoConsulta = sentencia.executeQuery();

            // Validamos que el resultado no este vacio
            if (resultadoConsulta != null){
                while (resultadoConsulta.next()){
                    Cuenta cuenta = null;
                    // Recibimos en variables los datos de los campos de la tabla
                    int numCuenta = resultadoConsulta.getInt("numero_cuenta");
                    String nomPropietario = resultadoConsulta.getString("nombre_propietario");
                    double saldo = resultadoConsulta.getDouble("saldo");
                    String tipo = resultadoConsulta.getString("tipo");
                    int cantRetiros = resultadoConsulta.getInt("cantidad_retiro");
                    int cantDepositos = resultadoConsulta.getInt("cantidad_deposito");

                    // Luego, pasamos los datos del registro en el objeto que creamos de cuenta
                    cuenta = new Cuenta(numCuenta, nomPropietario, saldo, tipo, cantRetiros, cantDepositos);
                    // Agregamos el registro a la lista
                    cuentas.add(cuenta);
                }
                // Cuando termine el ciclo, retornamos la nueva lista con todos los datos de la consulta
                return cuentas;
            } else {
                throw new RuntimeException("No se encontraron registros");
            }
        } catch (SQLException e){
            System.out.println("Error de conexión: " + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object buscarCuenta(int numeroCuenta) {
        try(Connection conexion =  DriverManager.getConnection(cadenaConexion)){
            String sql = "SELECT * FROM cuentas WHERE numero_cuenta = " + numeroCuenta + ";";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            // Guardamos el resultado de la consulta en un resulset
            ResultSet resultadoConsulta = sentencia.executeQuery();

            // Validamos que el resultado de la consulta no venga nulo
            if (resultadoConsulta != null && resultadoConsulta.next()){
                Cuenta cuenta = null;
                // Recibimos en variables los datos de los campos de la tabla
                int numCuenta = resultadoConsulta.getInt("numero_cuenta");
                String nomPropietario = resultadoConsulta.getString("nombre_propietario");
                double saldo = resultadoConsulta.getDouble("saldo");
                String tipo = resultadoConsulta.getString("tipo");
                int cantRetiros = resultadoConsulta.getInt("cantidad_retiro");
                int cantDepositos = resultadoConsulta.getInt("cantidad_deposito");

                // Luego, pasamos los datos del registro en el objeto que creamos de cuenta
                cuenta = new Cuenta(numCuenta, nomPropietario, saldo, tipo, cantRetiros, cantDepositos);
                // Retornamos el objeto
                return cuenta;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e);
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese el número de cuenta con números: " + e.getMessage());
        }
        return null;
    }

    // Update especifico para actualizar el saldo
    public void actualizarSaldo(Object objeto){
        try(Connection conexion = DriverManager.getConnection(cadenaConexion)){
            // Haremos un parseo al objeto recibido
            Cuenta cuentaUsuario = (Cuenta) objeto;
            String sql = "UPDATE cuentas " + "SET saldo = " + cuentaUsuario.getSaldo() + ", " +
                    "cantidad_retiro =  " + cuentaUsuario.getCantidad_retiro() + ", " +
                    "cantidad_deposito = " + cuentaUsuario.getCantidad_deposito() + " " +
                    "WHERE numero_cuenta = " + cuentaUsuario.getNumero_cuenta() + ";";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e);
        }
    }

    @Override
    public void depositar(double monto, Object objeto) {
        Cuenta cuentaUsuario = (Cuenta) objeto;
        // Validamos la cantidad de depositos para darle el beneficio
        if (cuentaUsuario.getCantidad_deposito() < 2) {
            // Se adiciona un 0.5% más del valor depositado
            double porcentaje = (monto*0.5)/100;
            // Actualizamos el valor del monto
            monto = monto+porcentaje;
            // Y se hace el deposito
            cuentaUsuario.setSaldo(cuentaUsuario.getSaldo() + monto);
            // Se aumenta el número de depositos en la cuenta
            cuentaUsuario.setCantidad_deposito(cuentaUsuario.getCantidad_deposito()+1);

            // Con los datos ya actualizados de la cuentaUsuario le pasamos el objeto a la funcion de actualizarSaldo()
            actualizarSaldo(cuentaUsuario);
            // Mostramos la cantidad depositada en pantalla
            System.out.println("¡Transacción exitosa! Ha depositado: " + monto);
        } else {
            // Se hace el deposito normal
            cuentaUsuario.setSaldo(cuentaUsuario.getSaldo() + monto);
            // Se aumenta el número de depositos en la cuenta
            cuentaUsuario.setCantidad_deposito(cuentaUsuario.getCantidad_deposito()+1);

            // Mostramos la cantidad depositada en pantalla
            System.out.println("¡Transacción exitosa! Ha depositado: " + monto);
        }
    }

    @Override
    public void retirar(double monto) {

    }

    @Override
    public void transaccion(double monto) {

    }
}
