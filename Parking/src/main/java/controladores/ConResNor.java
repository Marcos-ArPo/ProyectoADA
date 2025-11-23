package controladores;

import modelos.Modelo;
import vistas.VisResNor;
import vistas.VisIndexNor;
import javax.swing.JOptionPane;
import java.sql.Time;

public class ConResNor {
    private VisResNor vista;
    private Modelo modelo;
    private String matricula;
    
    public ConResNor(VisResNor vista, String matricula) {
        this.vista = vista;
        this.modelo = new Modelo();
        this.matricula = matricula;
    }
    
    public void hacerReserva(java.util.Date fecha, Time horaIni, Time horaFin) {
        try {
            int idCliente = modelo.obtenerIdCliente(matricula);
            boolean exito = modelo.hacerReserva(idCliente, fecha, horaIni, horaFin, false, null);
            
            if (exito) {
                JOptionPane.showMessageDialog(vista,
                        "Reserva realizada exitosamente.",
                        "Reserva Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Volver al índice normal
                VisIndexNor indexNor = new VisIndexNor(matricula);
                indexNor.setVisible(true);
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista,
                        "No se pudo realizar la reserva. No hay plazas disponibles.",
                        "Error en Reserva",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al realizar la reserva: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void cancelarReserva() {
        // Volver al índice sin hacer reserva
        VisIndexNor indexNor = new VisIndexNor(matricula);
        indexNor.setVisible(true);
        vista.dispose();
    }
}