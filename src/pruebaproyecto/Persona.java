package pruebaproyecto;

public abstract class Persona {

    // 🔴 CAMBIO CLAVE: protected → private
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

    // 🔹 OPCIONAL (pero suma puntos por sobreescritura)
    @Override
    public String toString() {
        return "Persona: " + nombre + " - " + correo;
    }
}
