package com.banco.entidades;

public class Cuenta {
    private int id;
    private String numeroCuenta;
    private double saldo;
    private String tipoCuenta;
    private int idUsuario;

    // Constructor para insertar datos
    public Cuenta(String numeroCuenta, double saldo, String tipoCuenta, int idUsuario) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.tipoCuenta = tipoCuenta;
        this.idUsuario = idUsuario;
    }

    // Constructor para listar datos
    public Cuenta(int id, String numeroCuenta, double saldo, String tipoCuenta, int idUsuario) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.tipoCuenta = tipoCuenta;
        this.idUsuario = idUsuario;
    }

    // Constructor para actualizar los datos sin afectar el saldo de la cuenta
    public Cuenta(int id, String numeroCuenta, String tipoCuenta, int idUsuario) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
