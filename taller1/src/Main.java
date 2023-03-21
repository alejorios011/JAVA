import ejercicio1.Factura;
import ejercicio2.CuentaAhorros;
import ejercicio3.inmueble.vivienda.casa.CasaUrbana;

public class Main {
    public static void main(String[] args) {
        /*
        // Probando imprimir factura del primer ejercicio
        Factura factura = new Factura("Francisco","Bancolombia",3230000);
        factura.imprimirFactura();
        */

        /*
        // Probar el segundo ejercicio: Realizar un método main que implemente un objeto Cuenta de ahorros y
        // llame a los métodos correspondientes.
        CuentaAhorros cuentaAhorros = new CuentaAhorros(50000f,0.15f);
        // Primero consignemos mas dinero a la cuenta
        cuentaAhorros.consignarSaldo(100000f);
        // Para probar los cobros adicionales retiraremos dinero 5 veces, deberiamos tener 51 mil menos.
        for (int i=0; i < 6; i++) {
            cuentaAhorros.retirarSaldo(10000f);
        }
        // Generemos un extracto mensual
        cuentaAhorros.extractoMensual();
        // Por último mostremos en pantalla el estado de nuestra cuenta
        cuentaAhorros.imprimir();
        */

        // Probemos un poco el tercer ejercicio
        CasaUrbana casaUrbana = new CasaUrbana(1001, 50, "CRA 37 # 95 - 51", 4, 1, 2);
        // No esta muy pulido el programa pero la idea es que cuando uno cree el objeto le solicite si la casa es
        // de conjunto cerrado o independiente pidiendo algunos datos segun la respuesta...
        // aqui lo hare manual llamando un metodo que cree para eso
        casaUrbana.solicitarTipo();
        casaUrbana.tipoCasaUrbana();
        // Por ultimo solicito el precio del inmueble
        casaUrbana.consultarPrecio();
    }
}