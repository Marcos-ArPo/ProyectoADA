package controladores;

import modelos.Modelo;
import vistas.VisIndexVip;
import vistas.VisResVip;
import vistas.VisLogin;
import javax.swing.JOptionPane;
import java.util.List;
import modelos.clases.reserva;
import modelos.clases.serviciovip;

public class ConIndexVip {

    private VisIndexVip vista;
    private Modelo modelo;
    private String matricula;
    private int idCliente;

    public ConIndexVip(VisIndexVip vista, String matricula) {
        this.vista = vista;
        this.matricula = matricula;
        this.modelo = new Modelo();
        inicializar();
    }

    private void inicializar() {
        try {
            this.idCliente = modelo.obtenerIdCliente(matricula);
            cargarReservasActivas();
            verificarEstadoCuota();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al inicializar: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void hacerReservaVip() {
        try {
            // Verificar si hay plazas VIP disponibles
            List<modelos.clases.plaza> plazasVipDisponibles = modelo.obtenerPlazasDisponibles(true);

            if (plazasVipDisponibles.isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "No hay plazas VIP disponibles en este momento.\n"
                        + "Puede intentar realizar una reserva normal o esperar a que se libere una plaza VIP.",
                        "Sin Plazas VIP Disponibles",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            VisResVip resVip = new VisResVip(matricula);
            resVip.setVisible(true);
            vista.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al verificar disponibilidad VIP: " + e.getMessage(),
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
                        "Mis Reservas VIP",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("=== MIS RESERVAS VIP ===\n\n");

            for (reserva r : reservas) {
                sb.append("Reserva ID: ").append(r.getIdReserva()).append("\n");
                sb.append("Fecha: ").append(r.getFechaRes()).append("\n");
                sb.append("Hora: ").append(r.getHoraIni()).append(" - ").append(r.getHoraFin()).append("\n");
                sb.append("Estado: ").append(r.getEstado()).append("\n");

                // Obtener servicios de la reserva
                List<String> servicios = obtenerServiciosReserva(r.getIdReserva());
                if (!servicios.isEmpty()) {
                    sb.append("Servicios: ").append(String.join(", ", servicios)).append("\n");
                }

                sb.append("--------------------\n");
            }

            JOptionPane.showMessageDialog(vista,
                    sb.toString(),
                    "Mis Reservas VIP",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al cargar reservas VIP: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarServiciosVip() {
        try {
            List<serviciovip> servicios = modelo.obtenerServiciosVip();

            StringBuilder sb = new StringBuilder();
            sb.append("=== SERVICIOS VIP DISPONIBLES ===\n\n");

            for (serviciovip servicio : servicios) {
                sb.append("• ").append(servicio.getNombreServ()).append("\n");
                sb.append("  Descripción: ").append(servicio.getDescr()).append("\n");
                sb.append("  Precio: €").append(servicio.getPrecio()).append("\n\n");
            }

            sb.append("Beneficios VIP:\n");
            sb.append("- Planta exclusiva C (50 plazas)\n");
            sb.append("- Servicio de aparcacoches\n");
            sb.append("- Limpieza exterior incluida\n");
            sb.append("- Carga eléctrica para vehículos\n");
            sb.append("- Atención personalizada 24/7\n");

            JOptionPane.showMessageDialog(vista,
                    sb.toString(),
                    "Servicios VIP",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al cargar servicios VIP: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void verEstadoCuota() {
        try {
            boolean cuotaPagada = verificarEstadoCuotaEnBD();

            String mensaje = cuotaPagada
                    ? "Su cuota VIP está al día.\n\n"
                    + "Beneficios activos:\n"
                    + "- Acceso a planta exclusiva\n"
                    + "- Servicios premium incluidos\n"
                    + "- Reservas prioritarias\n"
                    + "- Descuentos especiales"
                    : "Su cuota VIP está pendiente de pago.\n\n"
                    + "Para seguir disfrutando de los beneficios VIP,\n"
                    + "por favor abone la cuota desde su banco.\n\n"
                    + "Sin el pago de la cuota, no podrá realizar\n"
                    + "reservas en la planta VIP.";

            JOptionPane.showMessageDialog(vista,
                    mensaje,
                    "Estado de Cuota VIP",
                    cuotaPagada ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al verificar estado de cuota: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancelarReservaVip() {
        try {
            List<reserva> reservasActivas = obtenerReservasActivas();

            if (reservasActivas.isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "No tiene reservas activas para cancelar.",
                        "Cancelar Reserva VIP",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String[] opciones = new String[reservasActivas.size()];
            for (int i = 0; i < reservasActivas.size(); i++) {
                reserva r = reservasActivas.get(i);
                String serviciosInfo = "";
                try {
                    List<String> servicios = obtenerServiciosReserva(r.getIdReserva());
                    if (!servicios.isEmpty()) {
                        serviciosInfo = " - Servicios: " + String.join(", ", servicios);
                    }
                } catch (Exception e) {
                    serviciosInfo = " - Error al cargar servicios";
                }
                opciones[i] = "Reserva " + r.getIdReserva() + " - " + r.getFechaRes() + " " + r.getHoraIni() + serviciosInfo;
            }

            String seleccion = (String) JOptionPane.showInputDialog(vista,
                    "Seleccione la reserva VIP a cancelar:",
                    "Cancelar Reserva VIP",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (seleccion != null) {
                int idReserva = Integer.parseInt(seleccion.split(" ")[1]);

                int confirmacion = JOptionPane.showConfirmDialog(vista,
                        "¿Está seguro de que desea cancelar esta reserva VIP?\n"
                        + "Los servicios asociados también serán cancelados.",
                        "Confirmar Cancelación VIP",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    boolean exito = cancelarReservaVipEnBD(idReserva);
                    if (exito) {
                        JOptionPane.showMessageDialog(vista,
                                "Reserva VIP cancelada exitosamente.",
                                "Cancelación Exitosa",
                                JOptionPane.INFORMATION_MESSAGE);
                        cargarReservasActivas();
                    }
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al cancelar reserva VIP: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void verInformacionParkingVip() {
        try {
            int plazasVipDisponibles = modelo.obtenerPlazasDisponibles(true).size();
            int plazasNormalesDisponibles = modelo.obtenerPlazasDisponibles(false).size();

            StringBuilder info = new StringBuilder();
            info.append("=== INFORMACIÓN PARKING VIP ===\n\n");
            info.append("Plazas VIP Totales: 50 (Planta C exclusiva)\n");
            info.append("Plazas VIP Disponibles: ").append(plazasVipDisponibles).append("\n");
            info.append("Plazas Normales Disponibles: ").append(plazasNormalesDisponibles).append("\n\n");
            info.append("Tarifas VIP:\n");
            info.append("- Cuota Mensual: €100\n");
            info.append("- Tarifa Hora: €8 (incluye servicios básicos)\n");
            info.append("- Servicios Adicionales:\n");
            info.append("  • Limpieza exterior: €15\n");
            info.append("  • Aparcacoches personal: €20\n");
            info.append("  • Carga eléctrica: €10\n\n");
            info.append("Ventajas VIP:\n");
            info.append("- Reservas garantizadas\n");
            info.append("- Planta cubierta y vigilada\n");
            info.append("- Servicio 24/7\n");
            info.append("- Atención personalizada\n");

            JOptionPane.showMessageDialog(vista,
                    info.toString(),
                    "Información Parking VIP",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al obtener información VIP: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(vista,
                "¿Está seguro de que desea cerrar sesión?",
                "Cerrar Sesión VIP",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            VisLogin login = new VisLogin();
            login.setVisible(true);
            vista.dispose();
        }
    }

    // Métodos auxiliares privados
    private List<reserva> obtenerReservasCliente() throws Exception {
        // En una implementación real, consulta a la BD
        return java.util.Collections.emptyList();
    }

    private List<reserva> obtenerReservasActivas() throws Exception {
        // Filtrar por estado "ACTIVA"
        return obtenerReservasCliente();
    }

    private List<String> obtenerServiciosReserva(int idReserva) throws Exception {
        // En implementación real, consultar servicios de la reserva
        return java.util.Collections.emptyList();
    }

    private boolean verificarEstadoCuotaEnBD() throws Exception {
        // En implementación real, consultar estado de cuota
        return true;
    }

    private boolean cancelarReservaVipEnBD(int idReserva) throws Exception {
        // En implementación real, cancelar reserva y servicios
        return true;
    }

    private void cargarReservasActivas() {
        // Actualizar interfaz con reservas activas
        try {
            List<reserva> reservasActivas = obtenerReservasActivas();
            // Actualizar vista
        } catch (Exception e) {
            // Manejar error silenciosamente
        }
    }

    private void verificarEstadoCuota() {
        try {
            boolean cuotaPagada = verificarEstadoCuotaEnBD();
            if (!cuotaPagada) {
                JOptionPane.showMessageDialog(vista,
                        "Su cuota VIP está pendiente de pago.\n"
                        + "Para acceder a todos los beneficios VIP,\n"
                        + "por favor regularice su situación.",
                        "Cuota Pendiente",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            // Manejar error silenciosamente
        }
    }

    public String getMatricula() {
        return matricula;
    }

    public int getIdCliente() {
        return idCliente;
    }
}
