package pruebaproyecto;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        
        
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));

        Scanner leer = new Scanner(System.in);

        HashMap<Integer, Cliente> clientes = ArchivoUtil.cargarClientes();
        ArchivoUtil.cargarTickets(clientes);
        
        
        System.out.println("Seleccione modo:");
        System.out.println("1. Consola");
        System.out.println("2. Ventana");

        int modo = leer.nextInt();
        leer.nextLine();
        
        
        System.out.println("Modo seleccionado: " + modo);
        if (modo == 2)
        {
            System.out.println("Modo seleccionado: " + modo);
            ejecutarModoVentana(clientes);
            return;
        }
        

        int opcion;

        do {
            System.out.println("\n======================================");
            System.out.println("        SISTEMA DE TICKETS");
            System.out.println("\n======================================");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Mostrar clientes");
            System.out.println("3. Crear ticket");
            System.out.println("4. Mostrar tickets");
            System.out.println("5. Buscar cliente por ID");
            System.out.println("6. Eliminar cliente");
            System.out.println("7. Editar cliente");
            System.out.println("8. Cerrar ticket");
            System.out.println("9. Calificar ticket");
            System.out.println("0. Salir");
            System.out.println("==================================");
            System.out.print("Seleccione opción: ");

            opcion = leer.nextInt();
            leer.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("ID: ");
                    int id = leer.nextInt();
                    leer.nextLine();

                    if (clientes.containsKey(id)) {
                        System.out.println("ID ya existe.");
                        break;
                    }

                    System.out.print("Nombre: ");
                    String nombre = leer.nextLine();

                    System.out.print("Correo: ");
                    String correo = leer.nextLine();

                    Cliente nuevo = new Cliente(id, nombre, correo);
                    clientes.put(id, nuevo);

                    System.out.println("Cliente agregado.");
                    break;

                case 2:
                    System.out.println("\nLista de clientes:");

                    if (clientes.isEmpty()) {
                        System.out.println("No hay clientes.");
                    } else {
                        for (Cliente c : clientes.values()) {
                            System.out.println(c);
                        }
                    }
                    break;

                // =========================
                // SIA-12: EXCEPCIONES
                // =========================

                case 3:
                    System.out.print("Descripcion: ");
                    String desc = leer.nextLine();

                    System.out.print("ID Cliente: ");
                    int idCliente = leer.nextInt();
                    leer.nextLine();

                    try {
                        Cliente clienteEncontrado = clientes.get(idCliente);

                        if (clienteEncontrado == null) {
                            throw new ClienteNoEncontradoException(
                                    "Cliente no encontrado con ID: " + idCliente
                            );
                        }

                        new Ticket(desc, 0, clienteEncontrado);
                        System.out.println("Ticket creado.");

                    } catch (ClienteNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 4:
                    System.out.println("\nLista de tickets:");

                    if (clientes.isEmpty()) {
                        System.out.println("No hay clientes.");
                    } else {
                        for (Cliente c : clientes.values()) {
                            for (Ticket t : c.getTickets()) {
                                System.out.println(t);
                            }
                        }
                    }
                    break;

                case 5:
                    System.out.print("Ingrese ID a buscar: ");
                    int buscarId = leer.nextInt();
                    leer.nextLine();

                    Cliente encontrado = clientes.get(buscarId);

                    if (encontrado != null) {
                        System.out.println("Encontrado: " + encontrado);
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 6:
                    System.out.print("Ingrese ID de cliente a eliminar: ");
                    int idEliminar = leer.nextInt();
                    leer.nextLine();

                    if (clientes.remove(idEliminar) != null) {
                        System.out.println("Cliente eliminado.");
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 7:
                    System.out.print("Ingrese ID del cliente a editar: ");
                    int idEditar = leer.nextInt();
                    leer.nextLine();

                    Cliente c = clientes.get(idEditar);

                    if (c != null) {
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = leer.nextLine();

                        System.out.print("Nuevo correo: ");
                        String nuevoCorreo = leer.nextLine();

                        c.setNombre(nuevoNombre);
                        c.setCorreo(nuevoCorreo);

                        System.out.println("Cliente actualizado.");
                    } else {
                        System.out.println("[ERROR] No se encontró el cliente con ese ID.");
                    }
                    break;

                case 8:
                    System.out.print("Ingrese ID del ticket a cerrar: ");
                    int idTicketCerrar = leer.nextInt();
                    leer.nextLine();

                    try {
                        boolean encontradoTicket = false;

                        for (Cliente cli : clientes.values()) {
                            for (Ticket t : cli.getTickets()) {

                                if (t.getIdTicket() == idTicketCerrar) {
                                    t.cerrarTicket();
                                    System.out.println("Ticket cerrado.");
                                    encontradoTicket = true;
                                    break;
                                }
                            }
                            if (encontradoTicket) break;
                        }

                        if (!encontradoTicket) {
                            throw new TicketNoEncontradoException(
                                    "Ticket no encontrado con ID: " + idTicketCerrar
                            );
                        }

                    } catch (TicketNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 9:
                    System.out.print("ID del ticket a calificar: ");
                    int idCalificar = leer.nextInt();
                    leer.nextLine();

                    int nota;
                    while (true)
                    {
                        System.out.print("Nota (1-5): ");
                        nota = leer.nextInt();
                        leer.nextLine();

                        if (nota >= 1 && nota <= 5)
                        {
                        break;
                        }
                        System.out.println("[ERROR] Nota inválida. Vuelva a intentarlo.");
                        }

                    try {
                        boolean encontradoCalificacion = false;

                        for (Cliente cli : clientes.values()) {
                            for (Ticket t : cli.getTickets()) {

                                if (t.getIdTicket() == idCalificar) {
                                    t.calificar(nota);
                                    System.out.println("[OK] Ticket calificado correctamente (" + nota + "★)");
                                    encontradoCalificacion = true;
                                    break;
                                }
                            }
                            if (encontradoCalificacion) break;
                        }

                        if (!encontradoCalificacion) {
                            throw new TicketNoEncontradoException(
                                    "Ticket no encontrado con ID: " + idCalificar
                            );
                        }

                    } catch (TicketNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 0: {

    ArchivoUtil.guardarClientes(clientes);
    ArchivoUtil.guardarTickets(clientes);

    System.out.println("\n======================================");
    System.out.println("        SISTEMA CERRADO");
    System.out.println("======================================");

    System.out.println("📊 RESUMEN FINAL:");

    System.out.println("👥 Clientes registrados: " + clientes.size());

    int totalTickets = 0;
    int ticketsAbiertos = 0;
    int ticketsCerrados = 0;

    for (Cliente cliente : clientes.values()) {
        for (Ticket t : cliente.getTickets()) {
            totalTickets++;

            if (t.getEstado().equalsIgnoreCase("Abierto")) {
                ticketsAbiertos++;
            } else {
                ticketsCerrados++;
            }
        }
    }

    System.out.println("🎫 Total tickets: " + totalTickets);
    System.out.println("🟢 Abiertos: " + ticketsAbiertos);
    System.out.println("🔴 Cerrados: " + ticketsCerrados);

    System.out.println("======================================");
    System.out.println("   Datos guardados correctamente");
    System.out.println("   Gracias por usar el sistema");
    System.out.println("======================================");

    break;
}

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);

        leer.close();
    }
    
    
    
    
    // MODO VENTANA
    
    public static void ejecutarModoVentana(HashMap<Integer, Cliente> clientes)
{
    JOptionPane.showMessageDialog(null, "Modo ventana activado");
    
    javax.swing.JFrame frame = new javax.swing.JFrame();
    frame.setAlwaysOnTop(true);
    frame.setSize(200, 200);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.toFront();

    while (true)
    {
        System.out.println("🔥 ENTRE AL METODO VENTANA 🔥");
        String opcionStr = JOptionPane.showInputDialog(frame,
        "SISTEMA DE TICKETS\n\n"
        + "1. Agregar cliente\n"
        + "2. Mostrar clientes\n"
        + "0. Salir"
        );

        if (opcionStr == null)
        {
            frame.dispose();
            return;
        }

        int opcion;
        try {
            opcion = Integer.parseInt(opcionStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ingrese un número válido.");
            continue;
        }

        switch (opcion)
        {
            case 1:
                try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("ID:"));
                    String nombre = JOptionPane.showInputDialog("Nombre:");
                    String correo = JOptionPane.showInputDialog("Correo:");

                    if (clientes.containsKey(id))
                    {
                        JOptionPane.showMessageDialog(frame, "ID ya existe.");
                        break;
                    }

                    clientes.put(id, new Cliente(id, nombre, correo));
                    JOptionPane.showMessageDialog(frame, "Cliente agregado.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error en datos.");
                }
                break;

            case 2:
                StringBuilder lista = new StringBuilder();

                for (Cliente c : clientes.values())
                {
                    lista.append(c.toString()).append("\n");
                }

                JOptionPane.showMessageDialog(frame,
                    lista.length() == 0 ? "No hay clientes." : lista.toString());
                break;

            case 0:
                ArchivoUtil.guardarClientes(clientes);
                ArchivoUtil.guardarTickets(clientes);
                JOptionPane.showMessageDialog(frame, "Datos guardados. Saliendo...");
                frame.dispose();
                return;

            default:
                JOptionPane.showMessageDialog(frame, "Opción inválida.");
        }
    }
}
}
    
    