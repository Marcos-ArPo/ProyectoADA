package controladores;

import modelos.Modelo;
import vistas.VisReg;
import javax.swing.JOptionPane;

public class ConReg {
    private VisReg vista;
    private Modelo modelo;
    
    public ConReg(VisReg vista) {
        this.vista = vista;
        this.modelo = new Modelo();
        // Conectar eventos de la vista
    }
    
    public void registrarCliente(String nombre, String apellidos, String matricula, String tipoCliente) {
        try {
            boolean exito = modelo.registrarCliente(nombre, apellidos, matricula, tipoCliente);
            if (exito) {
                JOptionPane.showMessageDialog(vista,
                    "Registro exitoso. Ahora puede iniciar sesión.",
                    "Registro Completado",
                    JOptionPane.INFORMATION_MESSAGE);
                // Volver al login
                vistas.VisLogin login = new vistas.VisLogin();
                login.setVisible(true);
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista,
                    "Error en el registro",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                "Error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}