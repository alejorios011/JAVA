package ejercicio_uno;

import excepciones.SinRecursoException;

public class Boleta extends Cine {

    public Boleta(int valorBoleta, int disponibles) {
        super(valorBoleta, disponibles);
    }

    @Override
    public void calcularValorBoleta(int cantidad) throws SinRecursoException {
        // Respondiendo a la pregunta, por lo que leí en el tema de excepciones que nos paso, siempre es mejor
        // lanzar excepciones para evitar que el programa termine abruptamente.
        // Y para este tipo de situación hipotetica, siempre existe la posibilidad de que se agoten las boletas disponibles.

        // Validamos que la cantidad solicitada no sea mayor a la disponible
        if (cantidad > this.disponibles) {
            // Si no hay reservas, lanzamos la exception personalizada
            throw new SinRecursoException("Solo quedan " + this.disponibles + " boletas a la venta");
        } else {
            // Creamos una variable para almacenar el precio a pagar
            int precio = this.valorBoleta * cantidad;
            // Lo mostramos en pantalla
            System.out.println("Por sus " + cantidad + " boletas debera pagar: " + precio);
        }
    }
}
