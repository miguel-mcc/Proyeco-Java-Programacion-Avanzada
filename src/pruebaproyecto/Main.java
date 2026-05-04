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

    public static Ticket buscarTicket(HashMap<Integer, Cliente> clientes, int id) {
        for (Cliente c : clientes.values()) {
            for (Ticket t : c.getTickets()) {
                if (t.getIdTicket() == id) return t;
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
<<<<<<< HEAD
                        mostrarMensaje("ID ya existe.", true);
=======
                        System.out.println("[ERROR] ID ya existe.");
>>>>>>> f68af45 (Version final: CRUD completo tickets + mejoras visuales + correcciones retroalimentacion)
                        break;
                    }

                    System.out.print("Nombre: ");
                    String nombre = leer.nextLine();

                    System.out.print("Correo: ");
                    String correo = leer.nextLine();

<<<<<<< HEAD
                    Cliente nuevo = new Cliente(id, nombre, correo);
                    clientes.put(id, nuevo);

                    mostrarMensaje("Cliente agregado.", false);
=======
                    clientes.put(id, new Cliente(id, nombre, correo));
                    System.out.println("[OK] Cliente agregado.");
>>>>>>> f68af45 (Version final: CRUD completo tickets + mejoras visuales + correcciones retroalimentacion)
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
                    if (!hay) System.out.println("No hay tickets.");
                    break;

                case 5:
                    System.out.print("ID: ");
                    int buscar = leer.nextInt();
                    leer.nextLine();

                    Cliente encontrado = clientes.get(buscar);

                    if (encontrado != null) {
                        System.out.println("Encontrado:");
                        System.out.println(encontrado);
                    } else {
                        System.out.println("Cliente no encontrado.");
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
                    } else {
                        System.out.println("Error al calificar.");
                    }
                    break;

                case 10:
                    System.out.print("ID ticket: ");
                    int idBuscarT = leer.nextInt();
                    leer.nextLine();

                    Ticket tBuscado = buscarTicket(clientes, idBuscarT);

                    System.out.println(tBuscado != null ? tBuscado : "Ticket no encontrado.");
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
                    break;

                case 12:
                    System.out.print("ID ticket: ");
                    int idDel = leer.nextInt();
                    leer.nextLine();

                    System.out.println(eliminarTicket(clientes, idDel)
                            ? "Ticket eliminado."
                            : "Ticket no encontrado.");
                    break;

                case 13:
                    int suma = 0, count = 0;

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

                    int total = 0, abiertos = 0, cerrados = 0;

                    for (Cliente cl : clientes.values()) {
                        for (Ticket t : cl.getTickets()) {
                            total++;
                            if (t.getEstado().equalsIgnoreCase("Abierto")) abiertos++;
                            else cerrados++;
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
                } break;

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
                } break;

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
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(frame, "Error.");
                    }
                    break;

                case 13:
                    int suma = 0, count = 0;

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
<<<<<<< HEAD
}
}
    
    
=======
}
>>>>>>> f68af45 (Version final: CRUD completo tickets + mejoras visuales + correcciones retroalimentacion)
