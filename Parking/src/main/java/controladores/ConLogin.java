package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelos.Modelo;
import vistas.VisLogin;
import vistas.VisIndexNor;
import vistas.VisIndexVip;
import javax.swing.JOptionPane;

public class ConLogin {
    private VisLogin vista;
    private Modelo modelo;
    
    public ConLogin() {
        this.vista = new VisLogin();
        this.modelo = new Modelo();
        configurarListeners();
    }
    
    private void configurarListeners() {
        vista.btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mat = vista.txtMatricula.getText().trim().toUpperCase();
                if (mat.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, 
                        "Por favor, introduzca una matrícula.", 
                        "Error", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                verificarMatricula(mat);
            }
        });
        
        vista.btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }
    
    public void verificarMatricula(String matricula) {
        try {
            String resultado = modelo.verificarLogin(matricula);
            
            switch (resultado) {
                case "NORMAL":
                    ConIndexNor indexNor = new ConIndexNor(matricula);
                    vista.dispose();
                    break;
                case "VIP":
                    VisIndexVip indexVip = new VisIndexVip(matricula);
                    indexVip.setVisible(true);
                    vista.dispose();
                    break;
                case "CUOTA_PENDIENTE":
                    JOptionPane.showMessageDialog(vista, 
                        "Debe abonar la cuota correspondiente desde su banco", 
                        "Cuota Pendiente", 
                        JOptionPane.WARNING_MESSAGE);
                    break;
                case "NO_EXISTE":
                    int respuesta = JOptionPane.showConfirmDialog(vista,
                        "Matrícula no encontrada. ¿Desea registrarse?",
                        "Registro",
                        JOptionPane.YES_NO_OPTION);
                    
                    if (respuesta == JOptionPane.YES_OPTION) {
                        ConReg reg = new ConReg();
                        vista.dispose();
                    }
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, 
                "Error al verificar matrícula: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}