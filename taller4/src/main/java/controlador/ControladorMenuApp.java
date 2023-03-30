package controlador;

import servicios.ServiciosCuenta;
import excepciones.CampoVacioException;
import entidades.Cuenta;

import java.util.List;
import java.util.Scanner;

public class ControladorMenuApp {
    private boolean encendido = true;
    private ServiciosCuenta serviciosCuenta;

    public ControladorMenuApp() {
        serviciosCuenta = new ServiciosCuenta();
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
                actualizarCuenta();
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
        System.out.println("CREAR CUENTA");
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

        // Creamos un objeto Movimientos y le pasamos en el contructor los datos que recibimos
        Cuenta nuevaCuenta = new Cuenta(numCuenta, nomPropietario, saldo, tipo, cantRetiros, cantDepositos);
        // Pasamos la nueva cuenta al controlador
        try{
            serviciosCuenta.crearCuenta(nuevaCuenta);
            // Enviemos un mensaje por consola que nos confime que se creo la cuenta exitosamente
            System.out.println("La cuenta ha sido creada con exito!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarCuentas(){
        System.out.println("LISTAR CUENTAS...");
        List<Cuenta> cuentasBaseDatos = serviciosCuenta.listarCuentas();

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
        System.out.println("BUSCAR CUENTA");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor ingrese el numero de cuenta:");
        int numCuenta = scanner.nextInt();
        // Le pasamos el numero de cuenta al controlador
        try {
            // Recibimos en un objeto de tipo cuenta los datos encontrados
            Cuenta cuentaEncontrada = serviciosCuenta.buscarCuenta(numCuenta);

            /**
             * Haremos una modificación al código según lo solicitado en el ejercicio
             */

            /* Comentemos el código anterior
            // Solo mostraremos el número de cuenta, el nombre del propietario y el tipo de cuenta que es.
            System.out.println("DATOS CUENTA");
            System.out.println("Número de cuenta: " + cuentaEncontrada.getNumero_cuenta());
            System.out.println("Propietario: " + cuentaEncontrada.getNombre_propietario());
            System.out.println("Tipo de cuenta: " + cuentaEncontrada.getTipo());
            System.out.println("Saldo cuenta: " + cuentaEncontrada.getSaldo());
            // Dejaremos unas lineas que sirvan para separar estos datos del resto del menu
            System.out.println("-----------------");
            */

            // Pasamos el objeto "cuentaEncontrada" a la clase que maneja el proximo Menu de opciones y un true para iniciarlo
            ControladorMenuCuenta controladorMenuCuenta = new ControladorMenuCuenta(cuentaEncontrada, true);
            controladorMenuCuenta.interfazCuenta();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void actualizarCuenta(){
        System.out.println("ACTUALIZAR CUENTA");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor ingrese el numero de la cuenta");
        int numCuenta = scanner.nextInt();
        // Mandamos el nextline solo para evitar el error que se presento en el crearCuenta()
        scanner.nextLine();
        System.out.println("Ingrese el nuevo nombre del propietario");
        String nombreProp = scanner.nextLine();
        System.out.println("Ingrese el nuevo tipo de cuenta");
        String tipo = scanner.nextLine();

        try {
            serviciosCuenta.actualizarCuenta(numCuenta, nombreProp, tipo);
            // Mostramos un mensaje que nos informe el exito del proceso
            System.out.println("Datos actualizados");
        } catch (CampoVacioException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void eliminarCuenta(){
        System.out.println("ELIMINAR CUENTA");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor ingrese el numero de cuenta que desea eliminar");
        int numCuenta = scanner.nextInt();
        try{
            // Ahora solo pasamos el numero de cuenta al controlador
            serviciosCuenta.eliminarCuenta(numCuenta);
            // Mostramos un mensaje que nos informe el exito del proceso
            System.out.println("Movimientos eliminada");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void salir(){
        System.out.println("Finalizando App...");
        this.encendido = false;
    }
}
