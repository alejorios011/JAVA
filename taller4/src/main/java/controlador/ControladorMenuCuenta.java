package controlador;

import servicios.ServiciosCuenta;
import entidades.Cuenta;

import java.util.Scanner;

public class ControladorMenuCuenta {
    private boolean iniciar = false;
    // Crearemos un atributo de tipo Movimientos, para guardar los datos que nos envien ahí
    Cuenta cuentaUsuario;
    private ServiciosCuenta serviciosCuenta;
    // private ControladorMenuApp menuApp;


    public ControladorMenuCuenta(Cuenta cuentaUsuario, boolean iniciar) {
        serviciosCuenta = new ServiciosCuenta();
        this.cuentaUsuario = cuentaUsuario;
        this.iniciar = iniciar;
    }

    // Recibiremos los datos de la cuenta en un objeto
    public Object interfazCuenta(){
        while (iniciar == true){
            System.out.println("Bienvenido " + cuentaUsuario.getNombre_propietario() + ", que desea?");
            System.out.println("1. Retirar saldo");
            System.out.println("2. Depositar saldo");
            System.out.println("3. Transferir saldo");
            System.out.println("4. Ver saldo y otros datos");
            System.out.println("5. Salir");

            // Recibimos la opcion por consola
            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt();
            opcionInterfaz(opcion);
        }
        return null;
    }

    public void opcionInterfaz(int opcion){
        switch (opcion) {
            case 1:
                retirar();
                break;
            case 2:
                depositar();
                break;
            case 3:
                break;
            case 4:
                verDatos();
                break;
            case 5:
                salir();
                break;
        }
    }

    public void retirar(){
        System.out.println("RETIRAR SALDO");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el valor a retirar");
        double retiro = scanner.nextDouble();
        try {
            serviciosCuenta.retirar(retiro, cuentaUsuario);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void depositar(){
        System.out.println("DEPOSITAR SALDO");
        // Solicitamos el valor del deposito por teclado
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el monto del deposito");
        double deposito = scanner.nextDouble();
        // Llamamos el método para hacer el deposito
        try {
            serviciosCuenta.depositar(deposito, cuentaUsuario);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void salir(){
        System.out.println("Saliendo de la App...");
        this.iniciar = false;
    }

    private void verDatos(){
        // Solo mostraremos el número de cuenta, el nombre del propietario, el tipo de cuenta que es y el saldo.
        System.out.println("DATOS DE LA CUENTA");
        System.out.println("Número de cuenta: " + cuentaUsuario.getNumero_cuenta());
        System.out.println("Propietario: " + cuentaUsuario.getNombre_propietario());
        System.out.println("Tipo de cuenta: " + cuentaUsuario.getTipo());
        System.out.println("Saldo cuenta: " + cuentaUsuario.getSaldo());
        // Dejaremos unas líneas que sirvan para separar estos datos del resto del menu
        System.out.println("------------------");
    }
}
