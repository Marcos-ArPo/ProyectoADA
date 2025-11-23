package controladores;

import modelos.Modelo;
import vistas.VisIndexNor;
import vistas.VisResNor;
import vistas.VisLogin;
import javax.swing.JOptionPane;
import java.util.List;
import modelos.clases.reserva;
import modelos.clases.plaza;

public class ConIndexNor {

    private VisIndexNor vista;
    private Modelo modelo;
    private String matricula;
    private int idCliente;

    public ConIndexNor(VisIndexNor vista, String matricula) {
        this.vista = vista;
        this.matricula = matricula;
        this.modelo = new Modelo();
        inicializar();
    }

    private void inicializar() {
        try {
            this.idCliente = modelo.obtenerIdCliente(matricula);
            cargarReservasActivas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al inicializar: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void hacerReserva() {
        try {
            // Verificar si hay plazas disponibles
            List<plaza> plazasDisponibles = modelo.obtenerPlazasDisponibles(false);

            if (plazasDisponibles.isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "No hay plazas disponibles en este momento para clientes normales.\n"
                        + "Por favor, intente más tarde.",
                        "Sin Plazas Disponibles",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            VisResNor resNor = new VisResNor(matricula);
            resNor.setVisible(true);
            vista.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al verificar disponibilidad: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void verReservas() {
        try {
            List<reserva> reservas = obtenerReservasCliente();

            if (reservas.isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "No tiene reservas activas.",
                        "Mis Reservas",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("=== MIS RESERVAS ===\n\n");

            for (reserva r : reservas) {
                sb.append("Reserva ID: ").append(r.getIdReserva()).append("\n");
                sb.append("Fecha: ").append(r.getFechaRes()).append("\n");
                sb.append("Hora: ").append(r.getHoraIni()).append(" - ").append(r.getHoraFin()).append("\n");
                sb.append("Estado: ").append(r.getEstado()).append("\n");
                sb.append("--------------------\n");
            }

            JOptionPane.showMessageDialog(vista,
                    sb.toString(),
                    "Mis Reservas Activas",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al cargar reservas: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancelarReserva() {
        try {
            List<reserva> reservasActivas = obtenerReservasActivas();

            if (reservasActivas.isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "No tiene reservas activas para cancelar.",
                        "Cancelar Reserva",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Mostrar lista de reservas para cancelar
            String[] opciones = new String[reservasActivas.size()];
            for (int i = 0; i < reservasActivas.size(); i++) {
                reserva r = reservasActivas.get(i);
                opciones[i] = "Reserva " + r.getIdReserva() + " - " + r.getFechaRes() + " " + r.getHoraIni();
            }

            String seleccion = (String) JOptionPane.showInputDialog(vista,
                    "Seleccione la reserva a cancelar:",
                    "Cancelar Reserva",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (seleccion != null) {
                int idReserva = Integer.parseInt(seleccion.split(" ")[1]);

                int confirmacion = JOptionPane.showConfirmDialog(vista,
                        "¿Está seguro de que desea cancelar esta reserva?",
                        "Confirmar Cancelación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    boolean exito = cancelarReservaEnBD(idReserva);
                    if (exito) {
                        JOptionPane.showMessageDialog(vista,
                                "Reserva cancelada exitosamente.",
                                "Cancelación Exitosa",
                                JOptionPane.INFORMATION_MESSAGE);
                        cargarReservasActivas();
                    } else {
                        JOptionPane.showMessageDialog(vista,
                                "Error al cancelar la reserva.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al cancelar reserva: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void verInformacionParking() {
        try {
            int plazasDisponibles = modelo.obtenerPlazasDisponibles(false).size();
            int plazasTotales = 90; // 50 planta baja + 40 planta -1

            StringBuilder info = new StringBuilder();
            info.append("=== INFORMACIÓN DEL PARKING ===\n\n");
            info.append("Plazas Totales Normales: ").append(plazasTotales).append("\n");
            info.append("Plazas Disponibles: ").append(plazasDisponibles).append("\n");
            info.append("Tarifa Base: €5 por hora\n");
            info.append("Horario: 24 horas\n");
            info.append("Plantas: Baja (50 plazas), -1 (40 plazas)\n\n");
            info.append("Servicios incluidos:\n");
            info.append("- Seguridad 24/7\n");
            info.append("- Cámaras de vigilancia\n");
            info.append("- Iluminación LED\n");

            JOptionPane.showMessageDialog(vista,
                    info.toString(),
                    "Información del Parking",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al obtener información: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(vista,
                "¿Está seguro de que desea cerrar sesión?",
                "Cerrar Sesión",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            VisLogin login = new VisLogin();
            login.setVisible(true);
            vista.dispose();
        }
    }

    // Métodos auxiliares privados - IMPLEMENTADOS
    private List<reserva> obtenerReservasCliente() throws Exception {
        return modelo.obtenerReservasCliente(idCliente);
    }

    private List<reserva> obtenerReservasActivas() throws Exception {
        return modelo.obtenerReservasActivas(idCliente);
    }

    private boolean cancelarReservaEnBD(int idReserva) throws Exception {
        return modelo.cancelarReserva(idReserva);
    }

    private void cargarReservasActivas() {
        try {
            List<reserva> reservasActivas = obtenerReservasActivas();

            // Actualizar la vista con la información de reservas
            if (vista != null) {
                actualizarTablaReservasEnVista(reservasActivas);
            }
        } catch (Exception e) {
            // Manejar error silenciosamente
            System.err.println("Error al cargar reservas activas: " + e.getMessage());
        }
    }
    
    private void actualizarTablaReservasEnVista(List<reserva> reservas) {
    try {
        // Buscar JTable en la vista para mostrar reservas
        java.awt.Component[] components = vista.getContentPane().getComponents();
        for (java.awt.Component comp : components) {
            if (comp instanceof javax.swing.JTable) {
                javax.swing.JTable tabla = (javax.swing.JTable) comp;
                if (tabla.getName() != null && tabla.getName().equals("tablaReservas")) {
                    // Crear modelo de tabla con las reservas
                    javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                        new Object[]{"ID", "Fecha", "Hora Inicio", "Hora Fin", "Estado"}, 0
                    );
                    
                    for (reserva r : reservas) {
                        model.addRow(new Object[]{
                            r.getIdReserva(),
                            r.getFechaRes(),
                            r.getHoraIni(),
                            r.getHoraFin(),
                            r.getEstado()
                        });
                    }
                    tabla.setModel(model);
                    break;
                }
            }
        }
    } catch (Exception e) {
        System.err.println("Error al actualizar tabla: " + e.getMessage());
    }
}

    public String getMatricula() {
        return matricula;
    }

    public int getIdCliente() {
        return idCliente;
    }
}
