package repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBaseDatos {
    private String cadenaConexion;

    // Haremos la conexión a base de datos dentro del constructor para que se cree apenas se instancie
    public ConexionBaseDatos() {
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

    public String getCadenaConexion() {
        return cadenaConexion;
    }

    public void setCadenaConexion(String cadenaConexion) {
        this.cadenaConexion = cadenaConexion;
    }
}
