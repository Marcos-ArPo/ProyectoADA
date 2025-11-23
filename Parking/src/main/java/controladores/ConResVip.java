package controladores;

import modelos.Modelo;
import vistas.VisResVip;
import vistas.VisIndexVip;
import javax.swing.JOptionPane;
import java.sql.Time;
import java.util.List;

public class ConResVip {
    private VisResVip vista;
    private Modelo modelo;
    private String matricula;
    
    public ConResVip(VisResVip vista, String matricula) {
        this.vista = vista;
        this.modelo = new Modelo();
        this.matricula = matricula;
    }
    
    public void hacerReserva(java.util.Date fecha, Time horaIni, Time horaFin, List<Integer> serviciosSeleccionados) {
        try {
            // Verificar estado de cuota
            int idCliente = modelo.obtenerIdCliente(matricula);
            boolean cuotaPagada = modelo.verificarEstadoCuota(idCliente);
            
            if (!cuotaPagada) {
                JOptionPane.showMessageDialog(vista,
                        "No puede realizar reservas VIP con cuota pendiente.",
                        "Cuota Pendiente",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean exito = modelo.hacerReserva(idCliente, fecha, horaIni, horaFin, true, serviciosSeleccionados);
            
            if (exito) {
                JOptionPane.showMessageDialog(vista,
                        "Reserva VIP realizada exitosamente.",
                        "Reserva VIP Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Volver al índice VIP
                VisIndexVip indexVip = new VisIndexVip(matricula);
                indexVip.setVisible(true);
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista,
                        "No se pudo realizar la reserva VIP. No hay plazas disponibles.",
                        "Error en Reserva VIP",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al realizar la reserva VIP: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
