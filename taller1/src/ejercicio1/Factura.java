package ejercicio1;

public class Factura extends Precio {
    String cliente;
    String emisor;

    public Factura(String cliente, String emisor, int valor) {
        this.cliente = cliente;
        this.emisor = emisor;
        this.valor = valor;
    }


    public void imprimirFactura() {
        System.out.println("Estimado cliente: "+cliente);
        System.out.println("Debe pagar a: "+ emisor);
        System.out.println("Un monto de: "+ valor);
    }
}
