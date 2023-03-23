package ejercicio_dos.abstractas;

import excepciones.NoInscritoException;

public abstract class Materias {
    private String nombreMateria;
    private int idEstudiante;
    private String nombreEstudiante;

    // Constructor
    public Materias(String nombreMateria, int idEstudiante, String nombreEstudiante) {
        this.nombreMateria = nombreMateria;
        this.idEstudiante = idEstudiante;
        this.nombreEstudiante = nombreEstudiante;
    }

    // Creamos la función que deben implementar las clases hijas
    public abstract void consultarMaterias(int id) throws NoInscritoException;

    // Getters y Setters
    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }
}
