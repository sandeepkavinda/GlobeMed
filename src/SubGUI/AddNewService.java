/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package SubGUI;

import gui.Signin;
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
import panels.ServiceManagement;

/**
 *
 * @author Sandeep
 */
public class AddNewService extends javax.swing.JDialog {

    private HashMap<Integer, String> doctorNicMap = new HashMap<>();
    private HashMap<Integer, String> serviceTypeMap = new HashMap<>();

    ServiceManagement serviceManagement;

    public AddNewService(java.awt.Frame parent, boolean modal, ServiceManagement serviceManagement) {
        super(parent, modal);
        this.serviceManagement = serviceManagement;
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        initComponents();

        loadServiceTypes();
        loadDoctors();
        loadStaffTables();
    }

    private void loadServiceTypes() {
        try {
            ResultSet result = MySQL.execute("SELECT * FROM `service_type`");
            Vector v = new Vector();
            v.add("Select");
            int index = 1;
            while (result.next()) {
                serviceTypeMap.put(index, result.getString("id"));
                v.add(result.getString("type"));
                index++;
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            serviceTypeComboBox.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDoctors() {
        try {
            ResultSet result = MySQL.execute("SELECT *,CONCAT(first_name, ' ',last_name) AS doctor_name "
                    + "FROM staff s "
                    + "INNER JOIN staff_role sr ON s.staff_role_id = sr.id "
                    + "WHERE sr.role = 'DOCTOR' AND hospital_id= '"+Signin.userBean.getHospitalId()+"' ");

            Vector v = new Vector();
            v.add("Select");

            int index = 1;
            while (result.next()) {
                doctorNicMap.put(index, result.getString("nic"));
                v.add(result.getString("doctor_name") + " (" + result.getString("nic") + ")");
                index++;
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            doctorComboBox.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStaffTables() {
        try {
            DefaultTableModel model = (DefaultTableModel) serviceTable.getModel();
            model.setRowCount(0);

            ResultSet results = MySQL.execute("SELECT *,CONCAT(stf.first_name, ' ', stf.last_name) AS doctor_name "
                    + "FROM service ser "
                    + "INNER JOIN staff stf ON ser.doctor_nic = stf.nic "
                    + "INNER JOIN staff_role stfr ON stf.staff_role_id = stfr.id "
                    + "INNER JOIN service_type sert ON ser.service_type_id = sert.id "
                    + "WHERE stf.hospital_id = '"+Signin.userBean.getHospitalId()+"'"
                    + "ORDER BY ser.id DESC");

            while (results.next()) {

                Vector v = new Vector();
                v.add(results.getString("id"));
                v.add(results.getString("service_name"));
                v.add(results.getString("doctor_name"));
                v.add(results.getString("description"));
                v.add(CurrencyFormatter.formatCurrency(results.getDouble("price")));
                v.add(results.getString("time"));
                v.add(results.getString("type"));
                model.addRow(v);
            }
            serviceManagement.loadServiceTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetFields() {
        serviceNameTextField.setText("");
        descriptionTextField.setText("");
        serviceTypeComboBox.setSelectedIndex(0);
        doctorComboBox.setSelectedIndex(0);
        priceTextField.setText("");
        serviceNameTextField.grabFocus();
    }

    private void addNewMember() {
        String serviceName = serviceNameTextField.getText();
        String description = descriptionTextField.getText();
        int serviceTypeIndex = serviceTypeComboBox.getSelectedIndex();
        String staringTime = startingTimeComboBox.getSelectedItem().toString();
        int doctorIndex = doctorComboBox.getSelectedIndex();
        String price = priceTextField.getText();

        if (serviceName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter the Service Name", "Warning", JOptionPane.WARNING_MESSAGE);
            serviceNameTextField.grabFocus();
        } else if (serviceTypeIndex == 0) {
            JOptionPane.showMessageDialog(this, "Please Select the Service Type", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (staringTime.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select the Service Starting Time", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (doctorIndex == 0) {
            JOptionPane.showMessageDialog(this, "Please Select the Doctor", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter the Price", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                String appointmentId = IdGenerater.generateId("service", "id", "SER");
                double doublePrice = Double.parseDouble(price);
                String doctorNic = doctorNicMap.get(doctorIndex);
                String serviceTypeId = serviceTypeMap.get(serviceTypeIndex);

                SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a");
                Date date = sdf12.parse(staringTime);
                SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm:ss");
                String formattedStaringTime = sdf24.format(date);

                try {

                    MySQL.execute("INSERT INTO `service` (`id`,`service_name`,`description`,`price`,`doctor_nic`,`service_type_id`,`time`) "
                            + "VALUES ('" + appointmentId + "','" + serviceName + "','" + description + "','" + doublePrice + "','" + doctorNic + "','" + serviceTypeId + "','" + formattedStaringTime + "')");
                    JOptionPane.showMessageDialog(this, "Staff Member Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/resources/success.png")));
                    loadStaffTables();
                    resetFields();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid Price", "Warning", JOptionPane.WARNING_MESSAGE);

            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        serviceNameTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        addMemberButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        serviceTypeComboBox = new javax.swing.JComboBox<>();
        doctorComboBox = new javax.swing.JComboBox<>();
        priceTextField = new javax.swing.JTextField();
        descriptionTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        startingTimeComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        serviceTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Patient");
        setMinimumSize(new java.awt.Dimension(700, 500));
        setResizable(false);

        serviceNameTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        serviceNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceNameTextFieldActionPerformed(evt);
            }
        });

        jLabel6.setText("Service Name");

        jLabel8.setText("Price");

        addMemberButton.setText("Add Service");
        addMemberButton.setBorder(null);
        addMemberButton.setPreferredSize(new java.awt.Dimension(99, 35));
        addMemberButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMemberButtonActionPerformed(evt);
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

        jLabel10.setText("Service Type");

        jLabel11.setText("Doctor");

        jLabel3.setText("Description");

        serviceTypeComboBox.setPreferredSize(new java.awt.Dimension(72, 35));
        serviceTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceTypeComboBoxActionPerformed(evt);
            }
        });

        doctorComboBox.setPreferredSize(new java.awt.Dimension(72, 35));
        doctorComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doctorComboBoxActionPerformed(evt);
            }
        });

        priceTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        priceTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceTextFieldActionPerformed(evt);
            }
        });

        descriptionTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        descriptionTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descriptionTextFieldActionPerformed(evt);
            }
        });

        jLabel9.setText("Starting Time");

        startingTimeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "12:00 AM", "12:30 AM", "01:00 AM", "01:30 AM", "02:00 AM", "02:30 AM", "03:00 AM", "03:30 AM", "04:00 AM", "04:30 AM", "05:00 AM", "05:30 AM", "06:00 AM", "06:30 AM", "07:00 AM", "07:30 AM", "08:00 AM", "08:30 AM", "09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "01:00 PM", "01:30 PM", "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM", "04:00 PM", "04:30 PM", "05:00 PM", "05:30 PM", "06:00 PM", "06:30 PM", "07:00 PM", "07:30 PM", "08:00 PM", "08:30 PM", "09:00 PM", "09:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        startingTimeComboBox.setPreferredSize(new java.awt.Dimension(72, 35));
        startingTimeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startingTimeComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(serviceNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addMemberButton, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addComponent(resetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(descriptionTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(serviceTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(doctorComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(priceTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(startingTimeComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serviceNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serviceTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startingTimeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(doctorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(priceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addMemberButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        serviceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Service Id", "Service Name", "Doctor", "Description", "Price", "Time", "Service Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(serviceTable);

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
                .addComponent(jScrollPane1)
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

    private void addMemberButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMemberButtonActionPerformed
        addNewMember();
    }//GEN-LAST:event_addMemberButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        resetFields();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void serviceNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceNameTextFieldActionPerformed
        descriptionTextField.grabFocus();
    }//GEN-LAST:event_serviceNameTextFieldActionPerformed

    private void serviceTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceTypeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serviceTypeComboBoxActionPerformed

    private void doctorComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doctorComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_doctorComboBoxActionPerformed

    private void priceTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceTextFieldActionPerformed

    private void descriptionTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descriptionTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_descriptionTextFieldActionPerformed

    private void startingTimeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startingTimeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startingTimeComboBoxActionPerformed

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
            java.util.logging.Logger.getLogger(AddNewService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddNewService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddNewService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddNewService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
    private javax.swing.JButton addMemberButton;
    private javax.swing.JTextField descriptionTextField;
    private javax.swing.JComboBox<String> doctorComboBox;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField priceTextField;
    private javax.swing.JButton resetButton;
    private javax.swing.JTextField serviceNameTextField;
    private javax.swing.JTable serviceTable;
    private javax.swing.JComboBox<String> serviceTypeComboBox;
    private javax.swing.JComboBox<String> startingTimeComboBox;
    // End of variables declaration//GEN-END:variables
}
