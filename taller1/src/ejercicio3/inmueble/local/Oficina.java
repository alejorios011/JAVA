package ejercicio3.inmueble.local;

public class Oficina extends Local{
    protected boolean gobierno;

    public Oficina(int id, int area, String direccion, String localizacion, boolean gobierno) {
        super(id, area, direccion, localizacion);
        this.gobierno = gobierno;
    }

    public void consultarPrecio() {
        // Valor por metro cuadrado
        int ValorM2 = 3500000;
        int precio = this.area*ValorM2;
        System.out.println("El precio de compra es de: " + precio);
    }
}
