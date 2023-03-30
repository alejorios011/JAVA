package repositorio;

import entidades.Cuenta;
import interfaces.Banco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BancoBaseDatos implements Banco {

    private ConexionBaseDatos db;

    // Haremos la conexión a base de datos dentro del constructor para que se cree apenas se instancie
    public BancoBaseDatos() {
        db = new ConexionBaseDatos();
    }

    @Override
    public void crearCuenta(Object objeto) {
        try(Connection conexion = DriverManager.getConnection(db.getCadenaConexion())){
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
        try(Connection conexion = DriverManager.getConnection(db.getCadenaConexion())){
            // Unicamente permitiremos que modifique el nombre del propietario y el tipo de cuenta
            String sql = "UPDATE cuentas " +
                    "SET nombre_propietario= '" + nombreProp + "', tipo= '" + tipo + "' " +
                    "WHERE numero_cuenta=" + numeroCuenta + ";";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
            if (sentencia.execute(sql) == true){
                System.out.println("Datos modificados correctamente");
            } else {
                throw new Exception("El numero de cuenta no existe en la base de datos");
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void eliminarCuenta(int numeroCuenta) {
        try(Connection conexion = DriverManager.getConnection(db.getCadenaConexion())){
            String sql = "DELETE FROM cuentas WHERE numero_cuenta = " + numeroCuenta + ";";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sql);
            if (sentencia.execute(sql) == true){
                System.out.println("Datos modificados correctamente");
            } else {
                throw new Exception("El numero de cuenta no existe en la base de datos");
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<?> listarCuentas() {
        List<Cuenta> cuentas = new ArrayList<Cuenta>();
        // Probamos la conexion
        try(Connection conexion = DriverManager.getConnection(db.getCadenaConexion())){
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
        try(Connection conexion =  DriverManager.getConnection(db.getCadenaConexion())){
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
}
