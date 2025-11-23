package controladores;

import modelos.Modelo;
import vistas.VisLogin;
import vistas.VisReg;
import vistas.VisIndexNor;
import vistas.VisIndexVip;
import javax.swing.JOptionPane;

public class ConLogin {
    private VisLogin vista;
    private Modelo modelo;
    
    public ConLogin(VisLogin vista) {
        this.vista = vista;
        this.modelo = new Modelo();
    }
    
    public void verificarMatricula(String matricula) {
        try {
            String resultado = modelo.verificarLogin(matricula);
            
            switch (resultado) {
                case "NORMAL":
                    VisIndexNor indexNor = new VisIndexNor(matricula);
                    indexNor.setVisible(true);
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
                        VisReg reg = new VisReg();
                        reg.setVisible(true);
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