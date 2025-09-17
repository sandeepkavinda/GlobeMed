/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package SubGUI;

import com.raven.datechooser.DateChooser;
import gui.Signin;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySQL;
import panels.StaffManagement;

/**
 *
 * @author Sandeep
 */
public class AddNewStaffMember extends javax.swing.JDialog {

    StaffManagement staffManagement;
    DateChooser dateChooser = new DateChooser();

    public AddNewStaffMember(java.awt.Frame parent, boolean modal, StaffManagement staffManagement) {
        super(parent, modal);
        this.staffManagement = staffManagement;
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        initComponents();

        loadRoles();
        loadStaffTables();
    }

    private void loadRoles() {
        try {
            ResultSet result = MySQL.execute("SELECT * FROM `staff_role`");
            Vector v = new Vector();
            v.add("Select");
            while (result.next()) {
                v.add(result.getString("role"));
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            roleComboBox.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStaffTables() {
        try {
            DefaultTableModel model = (DefaultTableModel) userTable.getModel();
            model.setRowCount(0);

            ResultSet results = MySQL.execute("SELECT * FROM staff s "
                    + "INNER JOIN staff_role sr ON s.staff_role_id = sr.id "
                    + "INNER JOIN staff_status ss ON s.staff_status_id = ss.id "
                    + "ORDER BY s.added_date_time DESC");

            while (results.next()) {

                Vector v = new Vector();
                v.add(results.getString("nic"));
                v.add(results.getString("first_name"));
                v.add(results.getString("last_name"));
                v.add(results.getString("role"));
                v.add(results.getString("status"));
                model.addRow(v);
            }
            staffManagement.loadStaffTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetFields() {
        nicTextField.setText("");
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        roleComboBox.setSelectedIndex(0);
        passwordPasswordField.setText("");
        retypePasswordPasswordField.setText("");
        nicTextField.grabFocus();
    }

    private void addNewMember() {
        String nic = nicTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String role = roleComboBox.getSelectedItem().toString();
        String password = String.valueOf(passwordPasswordField.getPassword());
        String retypePassword = String.valueOf(retypePasswordPasswordField.getPassword());

        if (nic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter NIC Number", "Warning", JOptionPane.WARNING_MESSAGE);
            nicTextField.grabFocus();
        } else if (nic.length() > 12) {
            JOptionPane.showMessageDialog(this, "Invalid NIC Number", "Warning", JOptionPane.WARNING_MESSAGE);
            nicTextField.grabFocus();
            nicTextField.selectAll();
        } else if (firstName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter First Name", "Warning", JOptionPane.WARNING_MESSAGE);
            firstNameTextField.grabFocus();
        } else if (firstName.length() > 45) {
            JOptionPane.showMessageDialog(this, "The First Name must contain fewer than 45 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
            firstNameTextField.grabFocus();
            firstNameTextField.selectAll();
        } else if (lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Last Name", "Warning", JOptionPane.WARNING_MESSAGE);
            lastNameTextField.grabFocus();
        } else if (lastName.length() > 45) {
            JOptionPane.showMessageDialog(this, "The Last Name must contain fewer than 45 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
            lastNameTextField.grabFocus();
            lastNameTextField.selectAll();
        }else if (role.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Member Role", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Password", "Warning", JOptionPane.WARNING_MESSAGE);
            passwordPasswordField.grabFocus();
            passwordPasswordField.selectAll();
        } else if (retypePassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Retype Password", "Warning", JOptionPane.WARNING_MESSAGE);
            retypePasswordPasswordField.grabFocus();
            retypePasswordPasswordField.selectAll();
        } else if (!retypePassword.equals(password)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Warning", JOptionPane.WARNING_MESSAGE);
        }else {

            try {
                ResultSet resultset = MySQL.execute("SELECT * FROM `staff` WHERE `nic`='" + nic + "'");
                if (resultset.next()) {
                    JOptionPane.showMessageDialog(this, '"' + "NIC Already Registered", "Warning", JOptionPane.WARNING_MESSAGE);
                    nicTextField.grabFocus();
                    nicTextField.selectAll();
                } else {
                    Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateTime = format.format(date);

                    ResultSet roleResultSet = MySQL.execute("SELECT * FROM `staff_role` WHERE `role`='" + role + "'");

                    if (roleResultSet.next()) {
                        String roleId = roleResultSet.getString("id");
                        String hospitaId = Signin.userBean.getHospitalId();

                        MySQL.execute("INSERT INTO `staff` (`nic`,`first_name`,`last_name`,`password`,`hospital_id`,`staff_status_id`,`staff_role_id`,`added_date_time`) "
                                + "VALUES ('" + nic + "','" + firstName + "','" + lastName + "','" + password + "','" + hospitaId + "','1','"+roleId+"','" + currentDateTime + "')");
                        JOptionPane.showMessageDialog(this, "Staff Member Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/resources/success.png")));
                        loadStaffTables();
                        resetFields();
                    } else {
                        JOptionPane.showMessageDialog(this, "Something Went Wrong. Try Again", "Warning", JOptionPane.WARNING_MESSAGE);
                    }

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        nicTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        roleComboBox = new javax.swing.JComboBox<>();
        addMemberButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        firstNameTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        lastNameTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        passwordPasswordField = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        retypePasswordPasswordField = new javax.swing.JPasswordField();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Patient");
        setMinimumSize(new java.awt.Dimension(700, 500));
        setResizable(false);

        nicTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        nicTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nicTextFieldActionPerformed(evt);
            }
        });

        jLabel6.setText("NIC Number");

        jLabel8.setText("Role");

        roleComboBox.setPreferredSize(new java.awt.Dimension(72, 35));
        roleComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roleComboBoxActionPerformed(evt);
            }
        });

        addMemberButton.setText("Add Member");
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

        jLabel10.setText("Full Name");

        firstNameTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        firstNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameTextFieldActionPerformed(evt);
            }
        });

        jLabel11.setText("Last Name");

        lastNameTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        lastNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameTextFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("Password");

        passwordPasswordField.setPreferredSize(new java.awt.Dimension(233, 35));

        jLabel4.setText("ReType Password");

        retypePasswordPasswordField.setPreferredSize(new java.awt.Dimension(233, 35));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nicTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addMemberButton, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addComponent(resetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roleComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(retypePasswordPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nicTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(retypePasswordPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addMemberButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                "NIC Number", "First Name", "Last Name", "Role", "Status"
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
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

    private void nicTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nicTextFieldActionPerformed
        firstNameTextField.grabFocus();
    }//GEN-LAST:event_nicTextFieldActionPerformed

    private void roleComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roleComboBoxActionPerformed
        passwordPasswordField.grabFocus();
    }//GEN-LAST:event_roleComboBoxActionPerformed

    private void firstNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameTextFieldActionPerformed
        lastNameTextField.grabFocus();
    }//GEN-LAST:event_firstNameTextFieldActionPerformed

    private void lastNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameTextFieldActionPerformed
        roleComboBox.grabFocus();
    }//GEN-LAST:event_lastNameTextFieldActionPerformed

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
            java.util.logging.Logger.getLogger(AddNewStaffMember.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddNewStaffMember.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddNewStaffMember.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddNewStaffMember.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton addMemberButton;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JTextField nicTextField;
    private javax.swing.JPasswordField passwordPasswordField;
    private javax.swing.JButton resetButton;
    private javax.swing.JPasswordField retypePasswordPasswordField;
    private javax.swing.JComboBox<String> roleComboBox;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
