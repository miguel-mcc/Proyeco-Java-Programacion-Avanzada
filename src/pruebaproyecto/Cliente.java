package pruebaproyecto;

import java.util.ArrayList;

public class Cliente extends Persona {

    private int id;
    private ArrayList<Ticket> tickets;

    // Constructor
    public Cliente(int id, String nombre, String correo) {
        super(nombre, correo);
        this.id = id;
        this.tickets = new ArrayList<>();
    }

    // Getter ID
    public int getId() {
        return id;
    }

    // Setter ID
    public void setId(int id) {
        this.id = id;
    }

    // Tickets
    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void agregarTicket(Ticket t) {
        tickets.add(t);
    }
    
    
    
    

    // =========================
    // TO STRING (IMPORTANTE)
    // =========================
    @Override
public String toString() {

    StringBuilder abiertos = new StringBuilder();
    StringBuilder cerrados = new StringBuilder();

    for (Ticket t : tickets) {

        String info = "\n - ID: " + t.getIdTicket()
                    + " | " + t.getDescripcion();

        if ("Abierto".equalsIgnoreCase(t.getEstado())) {
            abiertos.append(info);
        } else {
            cerrados.append(info);
        }
    }

    if (abiertos.length() == 0) {
        abiertos.append("\n - Ninguno");
    }

    if (cerrados.length() == 0) {
        cerrados.append("\n - Ninguno");
    }

    return "\n=============================="
         + "\n        CLIENTE #" + id
         + "\n=============================="
         + "\n Nombre : " + getNombre()
         + "\n Correo : " + getCorreo()
         + "\n\n TICKETS ABIERTOS:" + abiertos
         + "\n\n TICKETS CERRADOS:" + cerrados
         + "\n==============================\n";
}

    // =========================
    // SOBRECARGA (SIA-5)
    // =========================

    // 1. Mostrar básico
    public void mostrar() {
        System.out.println(this.toString());
    }

    // 2. Mostrar con detalle
    public void mostrar(boolean detalle) {
        if (detalle) {
            System.out.println("=== CLIENTE DETALLADO ===");
            System.out.println("ID: " + id);
            System.out.println("Nombre: " + getNombre());
            System.out.println("Correo: " + getCorreo());
            System.out.println("Tickets: " + tickets.size());
        } else {
            mostrar();
        }
    }

    // 3. Mostrar con mensaje personalizado
    public void mostrar(String mensaje) {
        System.out.println(mensaje + ": " + getNombre() + " (" + id + ")");
    }
}