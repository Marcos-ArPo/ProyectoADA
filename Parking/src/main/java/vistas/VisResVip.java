package vistas;

import controladores.ConResVip;
import javax.swing.JOptionPane;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class VisResVip extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VisResVip.class.getName());
    private ConResVip controlador;
    private String matricula;

    public VisResVip(String matricula) {
        initComponents();
        this.matricula = matricula;
        controlador = new ConResVip(this, matricula);
        setLocationRelativeTo(null);
        
        // Establecer fecha mínima como hoy
        dateFecha.setMinSelectableDate(new java.util.Date());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dateFecha = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        txtHoraIni = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        txtHoraFin = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        lblMatricula = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        chkLimpieza = new javax.swing.JCheckBox();
        chkAparcacoches = new javax.swing.JCheckBox();
        chkCargaElectrica = new javax.swing.JCheckBox();
        btnReservar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reserva - Cliente VIP");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Nueva Reserva VIP"));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("RESERVA DE PLAZA - VIP");

        jLabel2.setText("Fecha:");

        jLabel3.setText("Hora Inicio:");

        try {
            txtHoraIni.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHoraIni.setText("0800");

        jLabel4.setText("Hora Fin:");

        try {
            txtHoraFin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHoraFin.setText("1800");

        jLabel5.setText("Matrícula:");

        lblMatricula.setText(matricula);

        jLabel6.setText("Servicios VIP:");

        chkLimpieza.setText("Limpieza Exterior (€15)");

        chkAparcacoches.setText("Aparcacoches Personal (€20)");

        chkCargaElectrica.setText("Carga Eléctrica (€10)");

        btnReservar.setText("Confirmar Reserva VIP");
        btnReservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarActionPerformed(evt);
            }
        });

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHoraIni, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMatricula)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(chkLimpieza)
                                            .addComponent(chkAparcacoches)
                                            .addComponent(chkCargaElectrica))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnReservar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblMatricula))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(dateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtHoraIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(chkLimpieza)
                        .addGap(5, 5, 5)
                        .addComponent(chkAparcacoches)
                        .addGap(5, 5, 5)
                        .addComponent(chkCargaElectrica))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btnReservar)
                        .addGap(18, 18, 18)
                        .addComponent(btnVolver)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarActionPerformed
        if (dateFecha.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String horaIniStr = txtHoraIni.getText().replace(":", "");
        String horaFinStr = txtHoraFin.getText().replace(":", "");
        
        if (horaIniStr.length() != 4 || horaFinStr.length() != 4) {
            JOptionPane.showMessageDialog(this, "Formato de hora incorrecto (HH:MM)", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Time horaIni = Time.valueOf(horaIniStr.substring(0, 2) + ":" + horaIniStr.substring(2, 4) + ":00");
            Time horaFin = Time.valueOf(horaFinStr.substring(0, 2) + ":" + horaFinStr.substring(2, 4) + ":00");
            
            // Obtener servicios seleccionados
            List<Integer> servicios = new ArrayList<>();
            if (chkLimpieza.isSelected()) servicios.add(1);
            if (chkAparcacoches.isSelected()) servicios.add(2);
            if (chkCargaElectrica.isSelected()) servicios.add(3);
            
            controlador.hacerReserva(dateFecha.getDate(), horaIni, horaFin, servicios);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de hora: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnReservarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        VisIndexVip index = new VisIndexVip(matricula);
        index.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReservar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JCheckBox chkAparcacoches;
    private javax.swing.JCheckBox chkCargaElectrica;
    private javax.swing.JCheckBox chkLimpieza;
    private com.toedter.calendar.JDateChooser dateFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblMatricula;
    private javax.swing.JFormattedTextField txtHoraFin;
    private javax.swing.JFormattedTextField txtHoraIni;
    // End of variables declaration//GEN-END:variables
}
