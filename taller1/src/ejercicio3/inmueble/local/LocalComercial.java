package ejercicio3.inmueble.local;

public class LocalComercial extends Local {
    String centroComercial;

    public LocalComercial(int id, int area, String direccion, String localizacion, String centroComercial) {
        super(id, area, direccion, localizacion);
        this.centroComercial = centroComercial;
    }

    public void consultarPrecio() {
        // Valor por metro cuadrado
        int ValorM2 = 3000000;
        int precio = this.area*ValorM2;
        System.out.println("El precio de compra es de: " + precio);
    }
}
