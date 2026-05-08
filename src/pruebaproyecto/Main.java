package pruebaproyecto;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Main {

    // =========================
    // MÉTODOS AUXILIARES
    // =========================
    /**
     * Gestiona la persistencia automática de los datos del sistema.
     * Invoca el guardado tanto de la colección de clientes como de sus tickets asociados
     * en archivos de texto externos.
     * @param clientes Mapa que contiene la base de datos de clientes actual.
     */
    public static void autoGuardar(HashMap<Integer, Cliente> clientes) {
    ArchivoUtil.guardarClientes(clientes);
    ArchivoUtil.guardarTickets(clientes);
}
    
    /**
     * Realiza una búsqueda transversal en todas las listas de tickets de los clientes.
     * @param clientes Colección donde se buscará el ticket.
     * @param id Identificador único del ticket a localizar.
     * @return El objeto {@code Ticket} encontrado, o {@code null} si no existe.
     */
    public static Ticket buscarTicket(HashMap<Integer, Cliente> clientes, int id) {
        for (Cliente c : clientes.values()) {
            for (Ticket t : c.getTickets()) {
                if (t.getIdTicket() == id) {
                    return t;
                }
            }
        }
        return null;
    }

    public static boolean editarTicket(HashMap<Integer, Cliente> clientes, int id, String nuevaDesc) {
        Ticket t = buscarTicket(clientes, id);
        if (t != null) {
            t.setDescripcion(nuevaDesc);
            return true;
        }
        return false;
    }

    public static boolean eliminarTicket(HashMap<Integer, Cliente> clientes, int id) {
        for (Cliente c : clientes.values()) {
            for (int i = 0; i < c.getTickets().size(); i++) {
                if (c.getTickets().get(i).getIdTicket() == id) {
                    c.getTickets().remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    // =========================
    // MAIN
    // =========================
    public static void main(String[] args) throws UnsupportedEncodingException {

        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        Scanner leer = new Scanner(System.in);

        HashMap<Integer, Cliente> clientes = ArchivoUtil.cargarClientes();
        ArchivoUtil.cargarTickets(clientes);

        System.out.println("======================================");
        System.out.println("        SELECCIÓN DE MODO");
        System.out.println("======================================");
        System.out.println("1. Consola");
        System.out.println("2. Ventana");
        System.out.println("======================================");
        System.out.print("Seleccione opción: ");

        int modo = leer.nextInt();
        leer.nextLine();

        if (modo == 2) {
            ejecutarModoVentana(clientes);
            return;
        }

        int opcion;

        do {
            System.out.println("\n======================================");
            System.out.println("        SISTEMA DE TICKETS");
            System.out.println("======================================");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Mostrar clientes");
            System.out.println("3. Crear ticket");
            System.out.println("4. Mostrar tickets");
            System.out.println("5. Buscar cliente");
            System.out.println("6. Eliminar cliente");
            System.out.println("7. Editar cliente");
            System.out.println("8. Cerrar ticket");
            System.out.println("9. Calificar ticket");
            System.out.println("10. Buscar ticket");
            System.out.println("11. Editar ticket");
            System.out.println("12. Eliminar ticket");
            System.out.println("13. Promedio satisfacción");
            System.out.println("0. Salir");
            System.out.println("======================================");
            System.out.print("Opción: ");

            opcion = leer.nextInt();
            leer.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("ID: ");
                    int id = leer.nextInt();
                    leer.nextLine();

                    if (clientes.containsKey(id)) {
                        System.out.println("[ERROR] ID ya existe.");
                        break;
                    }

                    System.out.print("Nombre: ");
                    String nombre = leer.nextLine();

                    System.out.print("Correo: ");
                    String correo = leer.nextLine();

                    clientes.put(id, new Cliente(id, nombre, correo));
                    System.out.println("[OK] Cliente agregado.");
                    
                    autoGuardar(clientes);
                    
                    break;

                case 2:
                    System.out.println("\n📋 Lista de clientes:");
                    if (clientes.isEmpty()) {
                        System.out.println("No hay clientes.");
                    } else {
                        for (Cliente c : clientes.values()) {
                            System.out.println(c);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Descripción: ");
                    String desc = leer.nextLine();

                    System.out.print("ID cliente: ");
                    int idCliente = leer.nextInt();
                    leer.nextLine();

                    Cliente cli = clientes.get(idCliente);

                    if (cli != null) {
                        new Ticket(desc, 0, cli);
                        System.out.println("[OK] Ticket creado.");
                        
                        autoGuardar(clientes);
                        
                    } else {
                        System.out.println("[ERROR] Cliente no encontrado.");
                    }
                    break;

                case 4:
                    System.out.println("\n🎫 Lista de tickets:");
                    boolean hay = false;
                    for (Cliente c : clientes.values()) {
                        for (Ticket t : c.getTickets()) {
                            System.out.println(t);
                            hay = true;
                        }
                    }
                    if (!hay) {
                        System.out.println("No hay tickets.");
                    }
                    break;

                case 5:
                    System.out.print("ID del cliente a buscar: ");
                    try {
                        int buscar = Integer.parseInt(leer.nextLine());
                        Cliente encontrado = clientes.get(buscar);
        
                        if (encontrado == null) {
                            // Lanzamos excepción personalizada
                            throw new ClienteNoEncontradoException("No existe un cliente con el ID " + buscar);
                        }
                        encontrado.mostrar();
        
                    } catch (NumberFormatException e) {
                    System.out.println("[ERROR] Debe ingresar un número válido.");
                    } catch (ClienteNoEncontradoException e) {
                        // Atrapamos excepción y mostramos el mensaje
                        System.out.println("[ERROR DE NEGOCIO] " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.print("ID: ");
                    int eliminar = leer.nextInt();
                    leer.nextLine();

                    if (clientes.remove(eliminar) != null) {
                        System.out.println("[OK] Cliente eliminado.");
                    } else {
                        System.out.println("[ERROR] Cliente no encontrado.");
                    }
                    
                    autoGuardar(clientes);
                    
                    break;

                case 7:
                    System.out.print("ID: ");
                    int editar = leer.nextInt();
                    leer.nextLine();

                    Cliente c = clientes.get(editar);

                    if (c != null) {
                        System.out.print("Nuevo nombre: ");
                        c.setNombre(leer.nextLine());

                        System.out.print("Nuevo correo: ");
                        c.setCorreo(leer.nextLine());

                        System.out.println("[OK] Cliente actualizado.");
                        
                        autoGuardar(clientes);
                        
                        
                    } else {
                        System.out.println("[ERROR] Cliente no encontrado.");
                        
                        
                    }
                    break;

                case 8:
                    System.out.print("ID ticket: ");
                    int cerrar = leer.nextInt();
                    leer.nextLine();

                    Ticket tCerrar = buscarTicket(clientes, cerrar);

                    if (tCerrar != null) {
                        tCerrar.cerrarTicket();
                        System.out.println("Ticket cerrado.");
                        
                        autoGuardar(clientes);
                        
                        
                    } else {
                        System.out.println("Ticket no encontrado.");
                    }
                    break;

                case 9:
                    System.out.print("ID ticket: ");
                    int calif = leer.nextInt();
                    leer.nextLine();

                    System.out.print("Nota (1-5): ");
                    int nota = leer.nextInt();
                    leer.nextLine();

                    Ticket tCalif = buscarTicket(clientes, calif);

                    if (tCalif != null && tCalif.calificar(nota)) {
                        System.out.println("Ticket calificado.");
                        
                        autoGuardar(clientes);
                        
                        
                    } else {
                        System.out.println("Error al calificar.");
                    }
                    break;

                case 10:
                    System.out.print("ID del ticket a buscar: ");
                    try {
                        int idT = Integer.parseInt(leer.nextLine());
                        Ticket tEncontrado = buscarTicket(clientes, idT);
        
                        if (tEncontrado == null) {
                            throw new TicketNoEncontradoException("El ticket #" + idT + " no existe en el sistema.");
                        }
                        System.out.println(tEncontrado);
        
                    } catch (NumberFormatException e) {
                        System.out.println("[ERROR] Ingrese un ID numérico.");
                    } catch (TicketNoEncontradoException e) {
                        System.out.println("[ERROR DE NEGOCIO] " + e.getMessage());
                    }
                    break;

                case 11:
                    System.out.print("ID ticket: ");
                    int idEdit = leer.nextInt();
                    leer.nextLine();

                    System.out.print("Nueva descripción: ");
                    String nuevaDesc = leer.nextLine();

                    System.out.println(editarTicket(clientes, idEdit, nuevaDesc)
                            ? "Ticket actualizado."
                            : "Ticket no encontrado.");
                    
                    autoGuardar(clientes);
                    
                    break;

                case 12:
                    System.out.print("ID ticket: ");
                    int idDel = leer.nextInt();
                    leer.nextLine();

                    System.out.println(eliminarTicket(clientes, idDel)
                            ? "Ticket eliminado."
                            : "Ticket no encontrado.");
                    
                    autoGuardar(clientes);
                    
                    break;

                case 13:
                    int suma = 0,
                     count = 0;

                    for (Cliente cl : clientes.values()) {
                        for (Ticket t : cl.getTickets()) {
                            if (t.getSatisfaccion() > 0) {
                                suma += t.getSatisfaccion();
                                count++;
                            }
                        }
                    }

                    System.out.println(count > 0
                            ? "📊 Promedio: " + (suma / (double) count)
                            : "No hay datos.");
                    break;

                case 0:
                    ArchivoUtil.guardarClientes(clientes);
                    ArchivoUtil.guardarTickets(clientes);

                    System.out.println("\n======================================");
                    System.out.println("        SISTEMA CERRADO");
                    System.out.println("======================================");
                    System.out.println("📊 RESUMEN FINAL:");

                    int total = 0,
                     abiertos = 0,
                     cerrados = 0;

                    for (Cliente cl : clientes.values()) {
                        for (Ticket t : cl.getTickets()) {
                            total++;
                            if (t.getEstado().equalsIgnoreCase("Abierto")) {
                                abiertos++;
                            } else {
                                cerrados++;
                            }
                        }
                    }

                    System.out.println("👥 Clientes: " + clientes.size());
                    System.out.println("🎫 Tickets: " + total);
                    System.out.println("🟢 Abiertos: " + abiertos);
                    System.out.println("🔴 Cerrados: " + cerrados);
                    System.out.println("======================================");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);

        leer.close();
    }

    // =========================
    // MODO VENTANA COMPLETO
    // =========================
    public static void ejecutarModoVentana(HashMap<Integer, Cliente> clientes) {

        javax.swing.JFrame frame = new javax.swing.JFrame();
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.toFront();

        JOptionPane.showMessageDialog(frame, "Modo ventana activado");

        while (true) {

            String opcionStr = JOptionPane.showInputDialog(frame,
                    "======================================\n"
                    + "        SISTEMA DE TICKETS\n"
                    + "======================================\n"
                    + "1. Agregar cliente\n"
                    + "2. Mostrar clientes\n"
                    + "3. Crear ticket\n"
                    + "4. Mostrar tickets\n"
                    + "5. Buscar cliente\n"
                    + "6. Eliminar cliente\n"
                    + "7. Editar cliente\n"
                    + "8. Cerrar ticket\n"
                    + "9. Calificar ticket\n"
                    + "10. Buscar ticket\n"
                    + "11. Editar ticket\n"
                    + "12. Eliminar ticket\n"
                    + "13. Promedio satisfacción\n"
                    + "0. Salir\n"
                    + "======================================"
            );

            if (opcionStr == null) {
                frame.dispose();
                return;
            }

            int opcion;
            try {
                opcion = Integer.parseInt(opcionStr);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Ingrese un número válido.");
                continue;
            }

            switch (opcion) {

                case 1:
                    try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "ID:"));
                    String nombre = JOptionPane.showInputDialog(frame, "Nombre:");
                    String correo = JOptionPane.showInputDialog(frame, "Correo:");

                    if (clientes.containsKey(id)) {
                        JOptionPane.showMessageDialog(frame, "ID ya existe.");
                        break;
                    }

                    clientes.put(id, new Cliente(id, nombre, correo));
                    JOptionPane.showMessageDialog(frame, "Cliente agregado.");
                    
                    autoGuardar(clientes);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error en datos.");
                }
                break;

                case 2: {
                    StringBuilder lista = new StringBuilder();
                    for (Cliente c : clientes.values()) {
                        lista.append(c.toString()).append("\n");
                    }

                    JTextArea area = new JTextArea(lista.toString());
                    area.setEditable(false);

                    JScrollPane scroll = new JScrollPane(area);
                    scroll.setPreferredSize(new java.awt.Dimension(600, 400));

                    JOptionPane.showMessageDialog(frame,
                            lista.length() == 0 ? "No hay clientes." : scroll);
                }
                break;

                case 3:
                    try {
                    String desc = JOptionPane.showInputDialog(frame, "Descripción:");
                    int idCliente = Integer.parseInt(JOptionPane.showInputDialog(frame, "ID Cliente:"));

                    Cliente cli = clientes.get(idCliente);

                    if (cli == null) {
                        JOptionPane.showMessageDialog(frame, "Cliente no encontrado.");
                        break;
                    }

                    new Ticket(desc, 0, cli);
                    JOptionPane.showMessageDialog(frame, "Ticket creado.");
                    
                    autoGuardar(clientes);
                    

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error.");
                }
                break;

                case 4: {
                    StringBuilder tickets = new StringBuilder();
                    for (Cliente cli : clientes.values()) {
                        for (Ticket t : cli.getTickets()) {
                            tickets.append(t.toString()).append("\n");
                        }
                    }

                    JTextArea area = new JTextArea(tickets.toString());
                    area.setEditable(false);

                    JScrollPane scroll = new JScrollPane(area);
                    scroll.setPreferredSize(new java.awt.Dimension(600, 400));

                    JOptionPane.showMessageDialog(frame,
                            tickets.length() == 0 ? "No hay tickets." : scroll);
                }
                break;

                case 5:
    try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "ID del cliente:"));
                    Cliente c = clientes.get(id);

                    if (c != null) {
                        JTextArea area = new JTextArea(c.toString());
                        area.setEditable(false);
                        JScrollPane scroll = new JScrollPane(area);
                        scroll.setPreferredSize(new java.awt.Dimension(600, 400));

                        JOptionPane.showMessageDialog(frame, scroll);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Cliente no encontrado.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error.");
                }
                break;

                case 6:
    try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "ID del cliente:"));

                    if (clientes.remove(id) != null) {
                        JOptionPane.showMessageDialog(frame, "Cliente eliminado.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Cliente no encontrado.");
                    }
                    
                    autoGuardar(clientes);
                    
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error.");
                }
                break;

                case 7:
    try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "ID del cliente:"));
                    Cliente c = clientes.get(id);

                    if (c != null) {
                        String nombre = JOptionPane.showInputDialog(frame, "Nuevo nombre:");
                        String correo = JOptionPane.showInputDialog(frame, "Nuevo correo:");

                        c.setNombre(nombre);
                        c.setCorreo(correo);

                        JOptionPane.showMessageDialog(frame, "Cliente actualizado.");
                        
                        autoGuardar(clientes);
                        
                        
                    } else {
                        JOptionPane.showMessageDialog(frame, "Cliente no encontrado.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error.");
                }
                break;

                case 8:
    try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "ID ticket:"));
                    Ticket t = buscarTicket(clientes, id);

                    if (t != null) {
                        t.cerrarTicket();
                        JOptionPane.showMessageDialog(frame, "Ticket cerrado.");
                        
                        autoGuardar(clientes);
                        
                        
                    } else {
                        JOptionPane.showMessageDialog(frame, "Ticket no encontrado.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error.");
                }
                break;

                case 9:
    try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "ID ticket:"));
                    int nota = Integer.parseInt(JOptionPane.showInputDialog(frame, "Nota (1-5):"));

                    Ticket t = buscarTicket(clientes, id);

                    if (t != null && t.calificar(nota)) {
                        JOptionPane.showMessageDialog(frame, "Ticket calificado.");
                        
                        autoGuardar(clientes);
                        
                        
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error al calificar.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error.");
                }
                break;

                case 10:
                    try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "ID ticket:"));
                    Ticket t = buscarTicket(clientes, id);
                    JOptionPane.showMessageDialog(frame,
                            t != null ? t.toString() : "Ticket no encontrado.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error.");
                }
                break;

                case 11:
                    try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "ID ticket:"));
                    String desc = JOptionPane.showInputDialog(frame, "Nueva descripción:");

                    JOptionPane.showMessageDialog(frame,
                            editarTicket(clientes, id, desc)
                            ? "Ticket actualizado."
                            : "No encontrado.");
                    
                    autoGuardar(clientes);
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error.");
                }
                break;

                case 12:
                    try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "ID ticket:"));

                    JOptionPane.showMessageDialog(frame,
                            eliminarTicket(clientes, id)
                            ? "Ticket eliminado."
                            : "No encontrado.");
                    
                    autoGuardar(clientes);
                    
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error.");
                }
                break;

                case 13:
                    int suma = 0,
                     count = 0;

                    for (Cliente cl : clientes.values()) {
                        for (Ticket t : cl.getTickets()) {
                            if (t.getSatisfaccion() > 0) {
                                suma += t.getSatisfaccion();
                                count++;
                            }
                        }
                    }

                    JOptionPane.showMessageDialog(frame,
                            count > 0 ? "📊 Promedio: " + (suma / (double) count)
                                    : "No hay datos.");
                    break;

                case 0:
                    ArchivoUtil.guardarClientes(clientes);
                    ArchivoUtil.guardarTickets(clientes);
                    frame.dispose();
                    return;

                default:
                    JOptionPane.showMessageDialog(frame, "Opción inválida.");
            }
        }
    }
}
