package pruebaproyecto;

import java.io.*;
import java.util.HashMap;

public class ArchivoUtil {

    private static final String ARCHIVO_CLIENTES = "clientes.txt";
    private static final String ARCHIVO_TICKETS = "tickets.txt";

    // =========================
    // GUARDAR CLIENTES
    // =========================
    public static void guardarClientes(HashMap<Integer, Cliente> clientes) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_CLIENTES))) {

            for (Cliente c : clientes.values()) {
                bw.write(c.getId() + "," + c.getNombre() + "," + c.getCorreo());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar clientes: " + e.getMessage());
        }
    }

    // =========================
    // CARGAR CLIENTES
    // =========================
    public static HashMap<Integer, Cliente> cargarClientes() {

        HashMap<Integer, Cliente> clientes = new HashMap<>();
        File file = new File(ARCHIVO_CLIENTES);

        if (!file.exists()) return clientes;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) continue;

                String[] datos = linea.split(",");

                if (datos.length != 3) continue;

                int id = Integer.parseInt(datos[0].trim());
                String nombre = datos[1].trim();
                String correo = datos[2].trim();

                Cliente c = new Cliente(id, nombre, correo);
                clientes.put(id, c);
            }

        } catch (Exception e) {
            System.out.println("Error al cargar clientes: " + e.getMessage());
        }

        return clientes;
    }

    // =========================
    // GUARDAR TICKETS
    // =========================
    public static void guardarTickets(HashMap<Integer, Cliente> clientes) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_TICKETS))) {

            for (Cliente c : clientes.values()) {
                for (Ticket t : c.getTickets()) {

                    bw.write(
                            t.getIdTicket() + "," +
                            t.getDescripcion() + "," +
                            t.getEstado() + "," +
                            t.getTiempoRespuesta() + "," +
                            c.getId() + "," +
                            t.getSatisfaccion()
                    );

                    bw.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error al guardar tickets: " + e.getMessage());
        }
    }

    // =========================
    // CARGAR TICKETS
    // =========================
    public static void cargarTickets(HashMap<Integer, Cliente> clientes) {

        File file = new File(ARCHIVO_TICKETS);

        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) continue;

                String[] d = linea.split(",");

                if (d.length != 6) continue;

                int idTicket = Integer.parseInt(d[0]);
                String desc = d[1];
                String estado = d[2];
                int tiempo = Integer.parseInt(d[3]);
                int idCliente = Integer.parseInt(d[4]);
                int sat = Integer.parseInt(d[5]);

                Cliente c = clientes.get(idCliente);

                if (c != null) {
                    Ticket t = new Ticket(desc, 0, c);

                    t.setIdTicket(idTicket);
                    t.setEstado(estado);
                    t.setTiempoRespuesta(tiempo);
                    t.setSatisfaccion(sat);
                }
            }

        } catch (Exception e) {
            System.out.println("Error al cargar tickets: " + e.getMessage());
        }
    }
}