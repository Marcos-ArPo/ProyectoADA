package controladores;

import modelos.Modelo;
import vistas.VisResVip;
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
        // Cargar servicios VIP disponibles
        cargarServicios();
    }
    
    private void cargarServicios() {
        try {
            List<modelos.clases.serviciovip> servicios = modelo.obtenerServiciosVip();
            // Actualizar la vista con los servicios disponibles
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void hacerReserva(java.util.Date fecha, Time horaIni, Time horaFin, List<Integer> serviciosSeleccionados) {
        try {
            int idCliente = modelo.obtenerIdCliente(matricula);
            boolean exito = modelo.hacerReserva(idCliente, fecha, horaIni, horaFin, true, serviciosSeleccionados);
            
            if (exito) {
                // Mostrar mensaje de éxito
                // Volver al índice VIP
            } else {
                // Mostrar mensaje de error
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}