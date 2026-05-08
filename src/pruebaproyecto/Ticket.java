package pruebaproyecto;

public class Ticket {

    private static int contador = 1;

    private int idTicket;
    private String descripcion;
    private String estado;
    private int tiempoRespuesta;
    private Cliente cliente;
    private long tiempoCreacion;
    private long tiempoCierre;
    private int satisfaccion;

    public Ticket(String descripcion, int tiempoRespuesta, Cliente cliente) {

        this.idTicket = contador++;
        this.descripcion = descripcion;
        this.estado = "Abierto";
        this.tiempoCreacion = System.currentTimeMillis();
        this.tiempoCierre = 0;
        this.tiempoRespuesta = 0;
        this.cliente = cliente;
        this.satisfaccion = 0;

        cliente.agregarTicket(this);
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTiempoRespuesta() {
        return tiempoRespuesta;
    }

    public void setTiempoRespuesta(int tiempoRespuesta) {
        this.tiempoRespuesta = tiempoRespuesta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getSatisfaccion() {
        return satisfaccion;
    }

    public void setSatisfaccion(int satisfaccion) {
        this.satisfaccion = satisfaccion;
    }
    /**
     * Finaliza el ciclo de vida de un ticket.
     * Cambia el estado a 'Cerrado' y calcula automáticamente el tiempo de respuesta
     * basado en la diferencia entre la creación y el cierre.
     */
    public void cerrarTicket() {
        if (estado.equals("Abierto")) {
            estado = "Cerrado";
            tiempoCierre = System.currentTimeMillis();
            tiempoRespuesta = (int) ((tiempoCierre - tiempoCreacion) / 1000);
        } else {
            System.out.println("El ticket ya está cerrado.");
        }
    }
    /**
     * Asigna una nota de satisfacción al servicio prestado.
     * @param nota Valor numérico entre 1 y 5.
     * @return {@code true} si la calificación fue exitosa, {@code false} si el ticket 
     * no está cerrado o la nota está fuera de rango.
     */
    public boolean calificar(int nota) {

        if (!estado.equals("Cerrado")) {
            System.out.println("Solo se puede calificar tickets cerrados.");
            return false;
        }

        if (nota >= 1 && nota <= 5) {
            this.satisfaccion = nota;
            return true;
        }

        System.out.println("La nota debe ser entre 1 y 5.");
        return false;
    }

    // =========================
    // TO STRING (MEJORADO)
    // =========================

    @Override
    public String toString() {

        return "\n------------------------------"
             + "\n 🎫 Ticket #" + idTicket
             + "\n 👤 Cliente  : " + cliente.getNombre()
             + "\n Estado   : " + (estado.equalsIgnoreCase("Abierto") ? "🟢 Abierto" : "🔴 Cerrado")
             + "\n 📝 Descripción: " + descripcion
             + "\n ⭐ Satisfacción: " + satisfaccion
             + "\n------------------------------\n";
    }
}
