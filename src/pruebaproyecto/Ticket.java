package pruebaproyecto;

public class Ticket
{
    private static int contador = 1;

    private int idTicket;
    private String descripcion;
    private String estado;
    private int tiempoRespuesta;
    private Cliente cliente;
    private long tiempoCreacion;
    private long tiempoCierre;
    private int satisfaccion;
    
    public Ticket(String descripcion, int tiempoRespuesta, Cliente cliente)
    {
        this.idTicket = contador;
        contador++;

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

    public void cerrarTicket()
    {
    if (estado.equals("Abierto"))
    {
        estado = "Cerrado";
        tiempoCierre = System.currentTimeMillis();
        tiempoRespuesta = (int) ((tiempoCierre - tiempoCreacion) / 1000); // en segundos
    } else
    {
        System.out.println("El ticket ya está cerrado.");
    }
    }
    
    public int getSatisfaccion()
    {
        return satisfaccion;
    }
    
    public void setSatisfaccion(int satisfaccion)
    {
        this.satisfaccion = satisfaccion;
    }
    
    public void calificar(int nota)
    {
    if (!estado.equals("Cerrado"))
    {
        System.out.println("Solo se puede calificar tickets cerrados.");
        return;
    }

    if (nota >= 1 && nota <= 5)
    {
        this.satisfaccion = nota;
    } 
    else 
    {
        System.out.println("La nota debe ser entre 1 y 5.");
    }
}
    
    
    
    @Override
    public String toString()
    {
    return "\n------------------------------"
         + "\n Ticket #" + idTicket
         + "\n Cliente  : " + cliente.getNombre()
         + "\n Estado   : " + estado
         + "\n Descripción: " + descripcion
         + "\n Satisfacción: " + satisfaccion
         + "\n------------------------------\n";
    }
}