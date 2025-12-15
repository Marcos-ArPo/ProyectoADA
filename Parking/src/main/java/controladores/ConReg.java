package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelos.Modelo;
import vistas.VisReg;
import javax.swing.JOptionPane;

public class ConReg {
    private VisReg vista;
    private Modelo modelo;
    
    public ConReg() {
        this.vista = new VisReg();
        this.modelo = new Modelo();
        configurarListeners();
    }
    
    private void configurarListeners() {
        vista.btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = vista.txtNombre.getText().trim();
                String apellidos = vista.txtApellidos.getText().trim();
                String matricula = vista.txtMatricula.getText().trim().toUpperCase();
                String tipo = (String) vista.comboCliente.getSelectedItem();
                
                if (nombre.isEmpty() || apellidos.isEmpty() || matricula.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, 
                        "Por favor, completa todos los campos.", 
                        "Error", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                registrarCliente(nombre, apellidos, matricula, tipo);
            }
        });
        
        vista.btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConLogin();
                vista.dispose();
            }
        });
        
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
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
                ConLogin login = new ConLogin();
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