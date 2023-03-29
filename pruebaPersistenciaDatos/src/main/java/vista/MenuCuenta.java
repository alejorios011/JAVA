package vista;

import controlador.ControladorCuenta;
import modelo.Cuenta;

import java.util.List;
import java.util.Scanner;

public class MenuCuenta {
    private boolean encendido = true;
    private ControladorCuenta controladorCuenta;

    public MenuCuenta() {
        controladorCuenta = new ControladorCuenta();
    }

    // Creamos la función que muestre en pantalla las opciones del menu
    public void iniciarApp() {
        // Pintamos en consola las opciones con un ciclo
        while (encendido == true) {
            System.out.println("Bienvenido, seleccione el número de la opción que desea realizar");
            System.out.println("1. Crear cuenta");
            System.out.println("2. Listar cuentas");
            System.out.println("3. Buscar cuenta");
            System.out.println("4. Modificar cuenta");
            System.out.println("5. Eliminar cuenta");
            System.out.println("6. Salir");
            // Recibimos la opción por consola
            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt();
            // Le pasamos la opcion a la funcion
            opcionMenu(opcion);
        }
    }

    // Creamos que actué de una forma según cada las opciones
    private void opcionMenu(int opcion){
        switch (opcion) {
            case 1:
                crearCuenta();
                break;
            case 2:
                listarCuentas();
                break;
            case 3:
                buscarCuenta();
                break;
            case 4:
                break;
            case 5:
                eliminarCuenta();
                break;
            case 6:
                salir();
                break;
            default:
                System.out.println("Opción no valida");
                break;
        }
    }

    private void crearCuenta(){
        // Solicitamos por consola los datos para abrir la cuenta
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el número de cuenta");
        int numCuenta = scanner.nextInt();
        // Cuando va un NextLine() despues de un nextInt() se generan ciertos conflictos por lo que
        // para solucionarlo agregamos un nextLine() vacio.
        scanner.nextLine();
        System.out.println("Ingrese el nombre del propietario");
        String nomPropietario = scanner.nextLine();
        System.out.println("Si la cuenta tiene saldo, agregue el monto de lo contrario deje un 0");
        double saldo = scanner.nextDouble();
        // Al parecer, el problema es el mismo con el nextDouble()
        scanner.nextLine();
        System.out.println("¿La cuenta es de ahorro o corriente?");
        String tipo = scanner.nextLine();
        // Creamos dos variables más para la cantidad de veces que se han hecho retiros o depositos y
        // las inicializamos en 0, por ser una cuenta nueva
        int cantRetiros = 0;
        int cantDepositos = 0;

        // Creamos un objeto Cuenta y le pasamos en el contructor los datos que recibimos
        Cuenta nuevaCuenta = new Cuenta(numCuenta, nomPropietario, saldo, tipo, cantRetiros, cantDepositos);
        // Pasamos la nueva cuenta al controlador
        try{
            controladorCuenta.crearCuenta(nuevaCuenta);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarCuentas(){
        System.out.println("Listando cuentas...");
        List<Cuenta> cuentasBaseDatos = controladorCuenta.listarCuentas();

        for (Cuenta cuentaBD : cuentasBaseDatos) {
            // Solo mostraremos el número de cuenta, el nombre del propietario y el tipo de cuenta que es.
            System.out.println("Número de cuenta: " + cuentaBD.getNumero_cuenta());
            System.out.println("Propietario: " + cuentaBD.getNombre_propietario());
            System.out.println("Tipo de cuenta: " + cuentaBD.getTipo());
            // Dejaremos unas lineas que sirvan para separar cada registro
            System.out.println("-----------------");
        }
    }

    private void buscarCuenta(){
        System.out.println("Buscar Cuenta");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor ingrese el numero de cuenta:");
        int numCuenta = scanner.nextInt();
        // Le pasamos el numero de cuenta al controlador
        try {
            // Recibimos en un objeto de tipo cuenta los datos encontrados
            Cuenta cuentaEncontrada = controladorCuenta.buscarCuenta(numCuenta);
            // Solo mostraremos el número de cuenta, el nombre del propietario y el tipo de cuenta que es.
            System.out.println("DATOS CUENTA");
            System.out.println("Número de cuenta: " + cuentaEncontrada.getNumero_cuenta());
            System.out.println("Propietario: " + cuentaEncontrada.getNombre_propietario());
            System.out.println("Tipo de cuenta: " + cuentaEncontrada.getTipo());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void eliminarCuenta(){
        System.out.println("Eliminar Cuenta");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor ingrese el numero de cuenta que desea eliminar");
        int numCuenta = scanner.nextInt();
        try{
            // Ahora solo pasamos el numero de cuenta al controlador
            controladorCuenta.eliminarCuenta(numCuenta);
            // Mostramos un mensaje que nos informe el exito del proceso
            System.out.println("Cuenta eliminada");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void salir(){
        System.out.println("Finalizando App...");
        this.encendido = false;
    }
}
