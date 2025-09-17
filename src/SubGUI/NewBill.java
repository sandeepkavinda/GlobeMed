package SubGUI;

import com.raven.datechooser.DateChooser;
import controller.billing.BillingController;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.CurrencyFormatter;
import model.IdGenerater;
import model.MySQL;
import panels.BillingManagement;
import panels.PatientManagement;

/**
 *
 * @author Sandeep
 */
public class NewBill extends javax.swing.JDialog {

    BillingManagement billingManagement;
    public static HashMap<Integer, String> paymentMethodMap = new HashMap<>();

    public NewBill(java.awt.Frame parent, boolean modal, BillingManagement billingManagement) {
        super(parent, modal);
        this.billingManagement = billingManagement;
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        initComponents();
        loadPaymentMethods();
        loadBillTable();
        resetFields();
    }

    private void loadPaymentMethods() {
        try {
            ResultSet result = MySQL.execute("SELECT * FROM `payment_method`");
            Vector v = new Vector();
            v.add("Select");
            int rowNum = 1;
            while (result.next()) {
                v.add(result.getString("method"));
                paymentMethodMap.put(rowNum, result.getString("id"));
                rowNum++;
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            paymentMethodComboBox.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBillTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) userTable.getModel();
            model.setRowCount(0);

            ResultSet results = MySQL.execute("SELECT * FROM bill b "
                    + "INNER JOIN appointment a ON b.appointment_id = a.id "
                    + "INNER JOIN service s ON a.service_id = s.id "
                    + "INNER JOIN payment_method p ON b.payment_method_id = p.id "
                    + "ORDER BY b.date_time DESC");

            while (results.next()) {

                Vector v = new Vector();
                v.add(results.getString("b.id"));
                v.add(results.getString("b.date_time"));
                v.add(results.getString("a.id"));
                v.add(CurrencyFormatter.formatCurrency(results.getDouble("s.price")));
                v.add(results.getString("p.method"));
                model.addRow(v);
            }
            billingManagement.loadBillingTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAppointmentId() {
        String appoitmentId = appointmentIdTextField.getText();

        if (!appoitmentId.isEmpty()) {
            try {
                ResultSet appointmentResult = MySQL.execute("SELECT * FROM appointment a "
                        + "WHERE id = '" + appoitmentId + "'");

                if (appointmentResult.next()) {
                    ResultSet billResult = MySQL.execute("SELECT * FROM bill b "
                            + "WHERE b.appointment_id = '" + appoitmentId + "'");

                    if (!billResult.next()) {
                        ResultSet selectedAppoitmentResult = MySQL.execute("SELECT * FROM appointment a "
                                + "INNER JOIN service s ON a.service_id = s.id "
                                + "INNER JOIN patient p ON a.patient_nic = p.nic "
                                + "WHERE a.id = '" + appoitmentId + "'");

                        selectedAppoitmentResult.next();
                        patientNicTextField.setText(selectedAppoitmentResult.getString("p.nic"));
                        patientNameTextFieldTextField.setText(selectedAppoitmentResult.getString("p.full_name"));
                        serviceTextField.setText(selectedAppoitmentResult.getString("s.service_name"));
                        jLabel9.setText(CurrencyFormatter.formatCurrency(selectedAppoitmentResult.getDouble("s.price")));

                        appointmentIdTextField.setEnabled(false);
                        checkAppIdButton.setEnabled(false);
                        paymentMethodComboBox.setEnabled(true);
                        payButton.setEnabled(true);

                    } else {
                        JOptionPane.showMessageDialog(this, "Already paid for this Appointment", "Warning", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Appointment Id", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please Enter Appointment Id", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void resetFields() {
        appointmentIdTextField.setEnabled(true);
        appointmentIdTextField.setText("");
        checkAppIdButton.setEnabled(true);
        patientNicTextField.setText("");
        patientNameTextFieldTextField.setText("");
        serviceTextField.setText("");
        paymentMethodComboBox.setEnabled(false);
        paymentMethodComboBox.setSelectedIndex(0);
        appointmentIdTextField.grabFocus();
        jLabel9.setText("LKR 0.00");

        payButton.setEnabled(false);
    }

    private void addNewBill() {
        String appoitmentId = appointmentIdTextField.getText();
        int selectedPaymentIndex = paymentMethodComboBox.getSelectedIndex();

        if (appoitmentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Appointment Number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (selectedPaymentIndex == 0) {
            JOptionPane.showMessageDialog(this, "Please Select Payment Method", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                
                String paymentMethodId = paymentMethodMap.get(selectedPaymentIndex);
                BillingController billingController = new BillingController();
                billingController.processBilling(appoitmentId, paymentMethodId);
                loadBillTable();
                resetFields();
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        appointmentIdTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        paymentMethodComboBox = new javax.swing.JComboBox<>();
        payButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        patientNicTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        patientNameTextFieldTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        serviceTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        checkAppIdButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Bill");
        setMinimumSize(new java.awt.Dimension(700, 500));
        setResizable(false);

        appointmentIdTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        appointmentIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appointmentIdTextFieldActionPerformed(evt);
            }
        });

        jLabel6.setText("Appointment Id");

        jLabel8.setText("Payment Method");

        paymentMethodComboBox.setEnabled(false);
        paymentMethodComboBox.setPreferredSize(new java.awt.Dimension(72, 35));
        paymentMethodComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentMethodComboBoxActionPerformed(evt);
            }
        });

        payButton.setText("Pay");
        payButton.setBorder(null);
        payButton.setEnabled(false);
        payButton.setPreferredSize(new java.awt.Dimension(99, 35));
        payButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payButtonActionPerformed(evt);
            }
        });

        resetButton.setBackground(new java.awt.Color(255, 255, 255));
        resetButton.setForeground(new java.awt.Color(0, 105, 75));
        resetButton.setText("Reset");
        resetButton.setBorder(null);
        resetButton.setPreferredSize(new java.awt.Dimension(99, 35));
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        jLabel10.setText("Patient NIC");

        patientNicTextField.setEnabled(false);
        patientNicTextField.setPreferredSize(new java.awt.Dimension(64, 35));

        jLabel11.setText("Patient Name");

        patientNameTextFieldTextField.setEnabled(false);
        patientNameTextFieldTextField.setPreferredSize(new java.awt.Dimension(64, 35));

        jLabel12.setText("Service");

        serviceTextField.setEnabled(false);
        serviceTextField.setPreferredSize(new java.awt.Dimension(64, 35));

        jLabel9.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("LKR 0.00");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Amount");

        checkAppIdButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/enter.png"))); // NOI18N
        checkAppIdButton.setBorder(null);
        checkAppIdButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkAppIdButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(patientNicTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(patientNameTextFieldTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(payButton, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addComponent(resetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paymentMethodComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(serviceTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12)
                            .addComponent(appointmentIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkAppIdButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkAppIdButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(appointmentIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(patientNicTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(patientNameTextFieldTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serviceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paymentMethodComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(0, 0, 0)
                .addComponent(jLabel9)
                .addGap(13, 13, 13)
                .addComponent(payButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Bill Id", "Date time", "Appointment Id", "Amount", "Payment Method"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(userTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                .addContainerGap())
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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void payButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payButtonActionPerformed
        addNewBill();
    }//GEN-LAST:event_payButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        resetFields();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void appointmentIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appointmentIdTextFieldActionPerformed
        checkAppointmentId();
    }//GEN-LAST:event_appointmentIdTextFieldActionPerformed

    private void paymentMethodComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentMethodComboBoxActionPerformed
//        addressTextArea1.grabFocus();
    }//GEN-LAST:event_paymentMethodComboBoxActionPerformed

    private void checkAppIdButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAppIdButtonActionPerformed
        checkAppointmentId();
    }//GEN-LAST:event_checkAppIdButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ;
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField appointmentIdTextField;
    private javax.swing.JButton checkAppIdButton;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField patientNameTextFieldTextField;
    private javax.swing.JTextField patientNicTextField;
    private javax.swing.JButton payButton;
    private javax.swing.JComboBox<String> paymentMethodComboBox;
    private javax.swing.JButton resetButton;
    private javax.swing.JTextField serviceTextField;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
