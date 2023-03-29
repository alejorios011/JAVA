package modelo;

public class Cuenta {
    private int numero_cuenta;
    private String nombre_propietario;
    private double saldo;
    private String tipo;
    private int cantidad_retiro;
    private int cantidad_deposito;

    // Constructor
    public Cuenta(int numero_cuenta, String nombre_propietario, double saldo, String tipo, int cantidad_retiro, int cantidad_deposito) {
        this.numero_cuenta = numero_cuenta;
        this.nombre_propietario = nombre_propietario;
        this.saldo = saldo;
        this.tipo = tipo;
        this.cantidad_retiro = cantidad_retiro;
        this.cantidad_deposito = cantidad_deposito;
    }

    // Getters y Setters
    public int getNumero_cuenta() {
        return numero_cuenta;
    }

    public void setNumero_cuenta(int numero_cuenta) {
        this.numero_cuenta = numero_cuenta;
    }

    public String getNombre_propietario() {
        return nombre_propietario;
    }

    public void setNombre_propietario(String nombre_propietario) {
        this.nombre_propietario = nombre_propietario;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad_retiro() {
        return cantidad_retiro;
    }

    public void setCantidad_retiro(int cantidad_retiro) {
        this.cantidad_retiro = cantidad_retiro;
    }

    public int getCantidad_deposito() {
        return cantidad_deposito;
    }

    public void setCantidad_deposito(int cantidad_deposito) {
        this.cantidad_deposito = cantidad_deposito;
    }
}
