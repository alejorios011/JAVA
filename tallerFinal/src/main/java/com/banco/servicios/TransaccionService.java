package com.banco.servicios;

import com.banco.entidades.Cuenta;
import com.banco.entidades.Transacciones;
import com.banco.interfaces.Servicio;
import com.banco.repositorio.TransaccionRepository;

import java.util.List;
import java.util.Map;

public class TransaccionService implements Servicio {
    private TransaccionRepository transaccionRepository;

    public TransaccionService() {
        transaccionRepository = new TransaccionRepository();
    }

    @Override
    public void crear(Map datos) throws Exception {
        String fecha = (String) datos.get("fecha");
        String hora = (String) datos.get("hora");
        String tipoTransaccion = (String) datos.get("tipoTransaccion");
        double monto = (double) datos.get("monto");
        int idCuenta = (int) datos.get("idCuenta");
        String tipoCuentaDestino = "";

        // Como el tipo de cuenta destino puede ser nulo debemos validar su valor
        if (datos.get("tipoCuentaDestino") == null){
            tipoCuentaDestino = "";
        } else {
            tipoCuentaDestino = (String) datos.get("tipoCuentaDestino");
        }

        Transacciones nuevaTransaccion = new Transacciones(fecha, hora, tipoTransaccion, monto, idCuenta, tipoCuentaDestino);

        // Hacemos unas ultimas validaciones para saber que tipo de transacción se desea realizar
        switch (tipoTransaccion){
            case "Depositar":
                try{
                    transaccionRepository.depositar(nuevaTransaccion);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case "Retirar":
                try {
                    transaccionRepository.retirar(nuevaTransaccion);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case "Transferir":
                try{
                    transaccionRepository.transferir(nuevaTransaccion);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            default:
                throw new Exception("Tipo de transaccion no valida");
        }
    }

    @Override
    public void eliminar(String id) {
        transaccionRepository.eliminar(id);
    }

    @Override
    public void actualizar(Map datos) {

    }

    @Override
    public List<Transacciones> listar() {
        return (List<Transacciones>) transaccionRepository.listar();
    }

    public List<Transacciones> listarPorId(String id){
        return (List<Transacciones>) transaccionRepository.listarPorId(id);
    }

    @Override
    public Object buscar(String id) throws Exception {
        Object transaccion = transaccionRepository.buscar(id);
        if (transaccion != null){
            return (Cuenta) transaccion;
        } else {
            throw new Exception("No se encontro la transacción");
        }
    }
}
