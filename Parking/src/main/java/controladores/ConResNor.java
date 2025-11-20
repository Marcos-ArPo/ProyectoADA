package controladores;

import modelos.Modelo;
import vistas.VisResNor;
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
                // Mostrar mensaje de éxito
                // Volver al índice normal
            } else {
                // Mostrar mensaje de error
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}