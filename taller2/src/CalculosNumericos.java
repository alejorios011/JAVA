import java.util.Scanner;

public class CalculosNumericos {
    public static void main(String[] args) {
        // calcularRaizCuadrada(-16);

        // Creamos unn objeto Scanner para recibir datos por teclado
        Scanner teclado = new Scanner(System.in);

        /* System.out.println("Coordenada en el eje Y de la primera pendiente: ");
        // Recibimos las coordenadas por teclado
        double y1 = teclado.nextDouble();
        System.out.println("Coordenada en el eje X de la primera pendiente: ");
        double x1 = teclado.nextDouble();
        System.out.println("Coordenada en el eje Y de la segunda pendiente: ");
        double y2 = teclado.nextDouble();
        System.out.println("Coordenada en el eje X de la segunda pendiente: ");
        double x2 = teclado.nextDouble();
        // Probemos las funciones que creamos
        calcularPendienteRecta(y1, x1, y2, x2);
        calcularPuntoMedioRecta(y1, x1, y2, x2); */

        // Necesitamos otros datos para calcular la ecuación cuadratica
        /* System.out.println("Ingrese el valor de a: ");
        int a = teclado.nextInt();
        System.out.println("Ingrese el valor de b: ");
        int b = teclado.nextInt();
        System.out.println("Ingrese el valor de c: ");
        int c = teclado.nextInt();
        // Pasamos los datos a la función
        calcularEcuacionCuadratica(a, b, c); */

        // Para pasar un número de base 10 a base b solicitamos algunos datos
        System.out.println("Ingrese el primer numero en base a 10");
        int numero = teclado.nextInt();
        // Llamamos la función y le pasamos el número que deseamos convertir
        convertirBase(numero);
    }

    public static void calcularRaizCuadrada(double numero) {
        try {
            if (numero < 0) {
                throw new ArithmeticException("No existen raíces cuadradas de número negativos");
            } else {
                double resultado = Math.sqrt(numero);
                System.out.println("La raiz cuadrada de: " + numero + " es: " + resultado);
            }
        } catch (ArithmeticException e){
            System.out.println("No existen raíces cuadradas de número negativos" + e);
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número para calcular la raiz cuadrada");
        }
    }

    public static void calcularPendienteRecta(double y1, double x1, double y2, double x2){
        try {
            double pendiente = (y2 - y1)/(x2 - x1);
            System.out.println("La pendiente de esta recta es: " + pendiente);
        } catch (NumberFormatException e){
            System.out.println("Debe ingresar un número para calcular la pendiente");
        }
    }

    public static void calcularPuntoMedioRecta(double y1, double x1, double y2, double x2){
        try {
            // La formula para calcular el punto medio es: m = (x1+x2/2, y1+y2/2) por lo que calcularemos
            // aparte el resultado de los ejes X y Y
            double resultadoX = (x1 + x2)/2;
            double resultadoY = (y1 + y2)/2;

            // Imprimimos el resultado
            System.out.println("Las coordenadas del punto medio son: " + "M(" + resultadoX + ", " + resultadoY + ")");
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar números para realizar los calculos correspondientes");
        }
    }

    public static void calcularEcuacionCuadratica(int a, int b, int c){
        try {
            // Calcular el discriminante
            double discriminante = Math.pow(b,2) - (4*a*c);

            if (discriminante > 0) {
                // Calcular las dos raices
                double raiz1 = ((b*(-1)) + Math.sqrt(discriminante)) / (2*a);
                double raiz2 = ((b*(-1)) - Math.sqrt(discriminante))  / (2*a);
                // Procedemos a imprimir los resultados
                System.out.println("Las dos raíces reales son: " + raiz1 + " y " + raiz2);
            } else {
                throw new ArithmeticException("El discriminante es negativo y no se puede completar la operación");
            }
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar números para realizar los cálculos correspondientes");
        }
    }

    // Convertir un número en base 10 a base b
    public static void convertirBase(int numero){
        try {
            String numeroBaseB = "";
            System.out.println(numero);
            while (numero != 0) {
                int resto = numero % 2;
                numero /= 2;
                numeroBaseB = resto + numeroBaseB;
            }

            System.out.println("El resultado es : " + numeroBaseB);
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar números para realizar los cálculos correspondientes");
        }
    }
}