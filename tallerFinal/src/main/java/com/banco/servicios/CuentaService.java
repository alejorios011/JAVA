package com.banco.servicios;

import com.banco.entidades.Cuenta;
import com.banco.interfaces.Servicio;
import com.banco.repositorio.CuentaRepository;

import java.util.List;
import java.util.Map;

public class CuentaService implements Servicio {
    private CuentaRepository cuentaRepository;

    public CuentaService() {
        cuentaRepository = new CuentaRepository();
    }

    @Override
    public void crear(Map datos) {
        String numeroCuenta = (String) datos.get("numeroCuenta");
        double saldo = (double) datos.get("saldo");
        String tipoCuenta = (String) datos.get("tipoCuenta");
        int idUsuario = (int) datos.get("idUsuario");

        Cuenta nuevaCuenta = new Cuenta(numeroCuenta, saldo, tipoCuenta, idUsuario);
        cuentaRepository.crear(nuevaCuenta);
    }

    @Override
    public void eliminar(String id) {
        cuentaRepository.eliminar(id);
    }

    @Override
    public void actualizar(Map datos) {
        int id = (int) datos.get("id");
        String numeroCuenta = (String) datos.get("numeroCuenta");
        String tipoCuenta = (String) datos.get("tipoCuenta");
        int idUsuario = (int) datos.get("idUsuario");

        Cuenta cuenta = new Cuenta(id, numeroCuenta, tipoCuenta, idUsuario);
        cuentaRepository.actualizar(cuenta);
    }

    @Override
    public List<?> listar() {
        return (List<Cuenta>) cuentaRepository.listar();
    }

    public List<?> listarPorUsuario(String id){
        return (List<Cuenta>) cuentaRepository.listarPorUsuario(id);
    }

    public Object buscar(String id) throws Exception {
        Object cuenta = cuentaRepository.buscar(id);
        if (cuenta != null){
            return (Cuenta) cuenta;
        } else {
            throw new Exception("No se encontro la cuenta");
        }
    }
}
