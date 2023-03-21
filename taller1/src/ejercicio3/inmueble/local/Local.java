package ejercicio3.inmueble.local;

import ejercicio3.inmueble.Inmueble;

public abstract class Local extends Inmueble {
    protected String localizacion;

    public Local(int id, int area, String direccion, String localizacion) {
        super(id, area, direccion);
        // Especificamos si es interno o da a la calle
        this.localizacion = localizacion;
    }

    abstract public void consultarPrecio ();
}
