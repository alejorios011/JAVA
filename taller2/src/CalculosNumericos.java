import java.util.Scanner;

public class CalculosNumericos {
    public static void main(String[] args) {
        // calcularRaizCuadrada(-16);

        // Recibimos las coordenadas por teclado
        Scanner teclado = new Scanner(System.in);
        System.out.println("Coordenada en el eje Y de la primera pendiente: ");
        double y1 = teclado.nextDouble();
        System.out.println("Coordenada en el eje X de la primera pendiente: ");
        double x1 = teclado.nextDouble();
        System.out.println("Coordenada en el eje Y de la segunda pendiente: ");
        double y2 = teclado.nextDouble();
        System.out.println("Coordenada en el eje X de la segunda pendiente: ");
        double x2 = teclado.nextDouble();
        // Probemos las funciones que creamos
        calcularPendienteRecta(y1, x1, y2, x2);
        calcularPuntoMedioRecta(y1, x1, y2, x2);
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
}
