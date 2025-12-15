package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import modelos.Modelo;
import vistas.VisResNor;
import javax.swing.JOptionPane;
import java.sql.Time;

public class ConResNor {

    private VisResNor vista;
    private Modelo modelo;
    private String matricula;

    public ConResNor(String matricula) {
        this.vista = new VisResNor();
        this.modelo = new Modelo();
        this.matricula = matricula;
        inicializarVista();
    }

    private void inicializarVista() {
        vista.lblMatricula.setText(matricula);

        // Configurar listeners
        vista.jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vista.txtFecha.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "Escriba una fecha", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String horIni = vista.txtHoraIni.getText().replace(":", "");
                String horFin = vista.txtHoraFin.getText().replace(":", "");

                if (horIni.length() != 4 || horFin.length() != 4) {
                    JOptionPane.showMessageDialog(vista, "Formato de hora incorrecto (HH:MM)", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    Time horaIni = Time.valueOf(vista.txtHoraIni.getText() + ":00");
                    Time horaFin = Time.valueOf(vista.txtHoraFin.getText() + ":00");
                    Date fecha = Date.valueOf(vista.txtFecha.getText());

                    hacerReserva(fecha, horaIni, horaFin);
                } catch (Exception x) {
                    JOptionPane.showMessageDialog(vista, "Error grave : " + x.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
                    x.printStackTrace();
                }
            }
        });

        vista.jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarReserva();
            }
        });

        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    public void hacerReserva(Date fecha, Time horaIni, Time horaFin) {
        try {
            int idCliente = modelo.obtenerIdCliente(matricula);
            boolean exito = modelo.hacerReserva(idCliente, fecha, horaIni, horaFin, false, null);

            if (exito) {
                JOptionPane.showMessageDialog(vista,
                        "Reserva realizada exitosamente.",
                        "Reserva Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

                // Volver al índice normal
                ConIndexNor indexNor = new ConIndexNor(matricula);
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
        ConIndexNor indexNor = new ConIndexNor(matricula);
        vista.dispose();
    }
}
