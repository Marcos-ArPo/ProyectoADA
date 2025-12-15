package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import modelos.Modelo;
import vistas.VisResVip;
import javax.swing.JOptionPane;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ConResVip {

    private VisResVip vista;
    private Modelo modelo;
    private String matricula;

    public ConResVip(String matricula) {
        this.vista = new VisResVip(matricula);
        this.modelo = new Modelo();
        this.matricula = matricula;
        inicializarVista();
    }

    private void inicializarVista() {
        vista.lblMatricula.setText(matricula);

        // Configurar listeners
        vista.btnConfirmar.addActionListener(new ActionListener() {
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

                    // Obtener servicios seleccionados
                    List<Integer> servicios = new ArrayList<>();
                    if (vista.chkLimpieza.isSelected()) {
                        servicios.add(1);
                    }
                    if (vista.chkAparca.isSelected()) {
                        servicios.add(3);
                    }
                    if (vista.chkCarga.isSelected()) {
                        servicios.add(2);
                    }

                    hacerReserva(fecha, horaIni, horaFin, servicios);
                } catch (Exception x) {
                    JOptionPane.showMessageDialog(vista, "Error grave : " + x.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
                    x.printStackTrace();
                }
            }
        });

        vista.btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarReserva();
            }
        });

        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
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
                ConIndexVip indexVip = new ConIndexVip(matricula);
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

    public void cancelarReserva() {
        // Volver al índice sin hacer reserva
        ConIndexVip indexNor = new ConIndexVip(matricula);
        vista.dispose();
    }
}
