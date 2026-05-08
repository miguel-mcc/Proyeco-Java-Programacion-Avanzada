package pruebaproyecto;

/**
 * Representa la entidad base del sistema utilizando el concepto de Abstracción.
 * Define los atributos y comportamientos comunes para cualquier individuo
 * sirviendo como clase padre para la especialización de roles como Clientes.
 */
public abstract class Persona {

    private String nombre;
    private String correo;

    public Persona(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    // GETTERS
    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    // SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // sobreescritura
    @Override
    public String toString() {
        return "Persona: " + nombre + " - " + correo;
    }
}
