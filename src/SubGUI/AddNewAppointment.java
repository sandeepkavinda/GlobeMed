package SubGUI;

import com.raven.datechooser.DateChooser;
import controller.appointment.AddAppointmentCommand;
import controller.appointment.AddPatientCommand;
import controller.appointment.AppointmentInvoker;
import model.IdGenerater;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.CurrencyFormatter;
import model.MySQL;
import model.TimeFormatter;
import panels.AppointmentManagement;

public class AddNewAppointment extends javax.swing.JDialog {

    AppointmentManagement appointmentManagement;
    DateChooser dobDateChooser = new DateChooser();
    DateChooser appoitmentDateChooser = new DateChooser();

    private HashMap<Integer, String> serviceIdMap = new HashMap<>();

    public AddNewAppointment(java.awt.Frame parent, boolean modal, AppointmentManagement appointmentManagement) {
        super(parent, modal);
        this.appointmentManagement = appointmentManagement;
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        initComponents();
        dobDateChooser.setTextRefernce(dobTextField);
        dobDateChooser.setForeground(new java.awt.Color(0, 105, 75));
        dobDateChooser.setDateFormat("yyyy-MM-dd");
        dobDateChooser.setSelectedDate(new Date());
        appoitmentDateChooser.setTextRefernce(appointmentDateTextField);
        appoitmentDateChooser.setForeground(new java.awt.Color(0, 105, 75));
        appoitmentDateChooser.setDateFormat("yyyy-MM-dd");
        appoitmentDateChooser.setSelectedDate(new Date());

        loadGenders();
        loadAppointmentTable();
        loadServices();
        resetFields();
    }

    private void loadGenders() {
        try {
            ResultSet result = MySQL.execute("SELECT * FROM `gender`");
            Vector v = new Vector();
            v.add("Select");
            while (result.next()) {
                v.add(result.getString("gender"));
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            genderComboBox.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadServices() {
        try {
            ResultSet result = MySQL.execute("SELECT * FROM service ser "
                    + "INNER JOIN staff stf ON ser.doctor_nic = stf.nic ");
            Vector v = new Vector();
            v.add("Select");
            int rowNum = 1;
            while (result.next()) {
                v.add(result.getString("ser.service_name") + " - " + result.getString("stf.first_name"));
                serviceIdMap.put(rowNum, result.getString("id"));
                rowNum++;
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            serviceComboBox.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSelectedServiceData() {

        if (serviceComboBox.getSelectedIndex() != 0) {
            String selectedServiceId = serviceIdMap.get(serviceComboBox.getSelectedIndex());

            try {
                ResultSet result = MySQL.execute("SELECT * FROM service ser "
                        + "INNER JOIN staff stf ON ser.doctor_nic = stf.nic "
                        + "WHERE ser.id = '" + selectedServiceId + "'");

                if (result.next()) {
                    serviceDesTextField.setText(result.getString("ser.description"));
                    doctorNameTextField.setText(result.getString("stf.first_name") + " " + result.getString("stf.last_name"));
                    timeTextField.setText(TimeFormatter.formatTimeTo12h(result.getString("ser.time")));
                    priceTextField.setText(CurrencyFormatter.formatCurrency(result.getDouble("ser.price")));

                } else {
                    JOptionPane.showMessageDialog(this, "Something Went Wrong. Try Again", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void loadAppointmentTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) userTable.getModel();
            model.setRowCount(0);

            ResultSet results = MySQL.execute("SELECT * FROM appointment a "
                    + "INNER JOIN service s ON a.service_id = s.id "
                    + "INNER JOIN staff stf ON s.doctor_nic = stf.nic "
                    + "ORDER BY a.added_date_time DESC");

            while (results.next()) {

                Vector v = new Vector();
                v.add(results.getString("a.id"));
                v.add(results.getString("a.patient_nic"));
                v.add(results.getString("a.appointment_date"));
                v.add(results.getString("s.service_name"));
                v.add(results.getString("stf.first_name") + " " + results.getString("stf.last_name"));
                v.add(results.getString("a.queue_no"));

                model.addRow(v);
            }
            appointmentManagement.loadAppointmentTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkNicNumber() {
        String enteredNic = nicTextField.getText();

        if (!enteredNic.isEmpty()) {
            try {
                ResultSet result = MySQL.execute("SELECT * FROM patient p "
                        + "INNER JOIN gender g ON p.gender_id = g.id  "
                        + "WHERE p.nic = '" + enteredNic + "'");

                if (result.next()) {
                    fullNameTextField.setText(result.getString("p.full_name"));
                    dobTextField.setText(result.getString("p.date_of_birth"));
                    genderComboBox.setSelectedItem(result.getString("g.gender"));
                    addressTextArea.setText(result.getString("p.address"));

                    nicTextField.setEnabled(false);
                    checkNicButton.setEnabled(false);
                    fullNameTextField.setEnabled(false);
                    dobTextField.setEnabled(false);
                    openDobCalButton.setEnabled(false);
                    genderComboBox.setEnabled(false);
                    addressTextArea.setEnabled(false);

                } else {
                    nicTextField.setEnabled(false);
                    checkNicButton.setEnabled(false);
                    fullNameTextField.setEnabled(true);
                    dobTextField.setEnabled(true);
                    openDobCalButton.setEnabled(true);
                    genderComboBox.setEnabled(true);
                    addressTextArea.setEnabled(true);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please Enter Patient's NIC Number", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void resetFields() {
        nicTextField.setText("");
        fullNameTextField.setText("");
        dobTextField.setText("");
        genderComboBox.setSelectedIndex(0);
        addressTextArea.setText("");
        serviceComboBox.setSelectedIndex(0);
        serviceDesTextField.setText("");
        doctorNameTextField.setText("");
        timeTextField.setText("");
        dobTextField.setText("");
        priceTextField.setText("");

        nicTextField.setEnabled(true);
        checkNicButton.setEnabled(true);
        fullNameTextField.setEnabled(false);
        dobTextField.setEnabled(false);
        openDobCalButton.setEnabled(false);
        genderComboBox.setEnabled(false);
        addressTextArea.setEnabled(false);

        nicTextField.grabFocus();
    }

    private void addNewAppointment() {
        String nic = nicTextField.getText();
        int selectedService = serviceComboBox.getSelectedIndex();
        String selectedAppotmentDate = appointmentDateTextField.getText();
        AppointmentInvoker invoker = new AppointmentInvoker();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formattedSelectedAppotmentDate = LocalDate.parse(appointmentDateTextField.getText(), formatter);
        LocalDate today = LocalDate.now();

        if (nic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Patient's NIC Number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (selectedService == 0) {
            JOptionPane.showMessageDialog(this, "Please Select a service for appointment", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (formattedSelectedAppotmentDate.isBefore(today)) {
            JOptionPane.showMessageDialog(this, "Cannot select past day to appointment", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String appointmentId = IdGenerater.generateId("appointment", "id", "APP");
            String serviceId = serviceIdMap.get(selectedService);
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            String currentDateTime = format.format(date);

            try {
                ResultSet patientResultSet = MySQL.execute("SELECT * FROM patient p "
                        + "INNER JOIN gender g ON p.gender_id = g.id  "
                        + "WHERE p.nic = '" + nic + "'");

                ResultSet appointmentResultSet = MySQL.execute("SELECT COUNT(*) AS appointment_count FROM appointment a "
                        + "WHERE a.patient_nic = '" + nic + "' AND a.service_id= '" + serviceId + "' AND a.appointment_date = '" + selectedAppotmentDate + "' ");
                appointmentResultSet.next();
                int queueNumber = appointmentResultSet.getInt("appointment_count") + 1;

                if (patientResultSet.next()) {
                    // Patient Registered
                    String patientNic = patientResultSet.getString("p.nic");

                    invoker.addCommand(new AddAppointmentCommand(appointmentId, patientNic, serviceId, selectedAppotmentDate, queueNumber, currentDateTime));
                    invoker.executeCommands();

//                    MySQL.execute("INSERT INTO appointment (`id`,`appointment_date`,`patient_nic`,`service_id`,added_date_time,`queue_no`) "
//                            + "VALUES ('" + appointmentId + "','" + selectedAppotmentDate + "','" + patientNic + "','" + serviceId + "','" + currentDateTime + "','" + queueNumber + "')");

                    JOptionPane.showMessageDialog(this, "Appointment Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/resources/success.png")));

                    loadAppointmentTable();
                    appointmentManagement.loadAppointmentTable();
                    resetFields();

                } else {
                    // Patient Not Registered

                    String fullName = fullNameTextField.getText();
                    String dateOfBirth = dobTextField.getText();
                    String gender = genderComboBox.getSelectedItem().toString();
                    String address = addressTextArea.getText();

                    if (nic.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please Enter Patient's NIC Number", "Warning", JOptionPane.WARNING_MESSAGE);
                        nicTextField.grabFocus();
                    } else if (nic.length() > 12) {
                        JOptionPane.showMessageDialog(this, "Invalid NIC Number", "Warning", JOptionPane.WARNING_MESSAGE);
                        nicTextField.grabFocus();
                        nicTextField.selectAll();
                    } else if (fullName.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please Enter Full Name", "Warning", JOptionPane.WARNING_MESSAGE);
                        fullNameTextField.grabFocus();
                    } else if (fullName.length() > 150) {
                        JOptionPane.showMessageDialog(this, "The Full Name must contain fewer than 150 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                        fullNameTextField.grabFocus();
                        fullNameTextField.selectAll();
                    } else if (dateOfBirth.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please Select Patient's Date of Birth", "Warning", JOptionPane.WARNING_MESSAGE);
                        dobTextField.grabFocus();
                    } else if (gender.equals("Select")) {
                        JOptionPane.showMessageDialog(this, "Please Select Patient's Gender", "Warning", JOptionPane.WARNING_MESSAGE);
                        dobTextField.grabFocus();
                        dobTextField.selectAll();
                    } else if (address.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please Enter Patient's Address", "Warning", JOptionPane.WARNING_MESSAGE);
                        genderComboBox.grabFocus();
                    } else {

                        try {
                            ResultSet resultset = MySQL.execute("SELECT * FROM `patient` WHERE `nic`='" + nic + "'");
                            if (resultset.next()) {
                                JOptionPane.showMessageDialog(this, '"' + "NIC Already Registered", "Warning", JOptionPane.WARNING_MESSAGE);
                                nicTextField.grabFocus();
                                nicTextField.selectAll();
                            } else {
                                ResultSet genderResultSet = MySQL.execute("SELECT * FROM `gender` WHERE `gender`='" + gender + "'");

                                if (genderResultSet.next()) {
                                    String genderId = genderResultSet.getString("id");

                                    invoker.addCommand(new AddPatientCommand(nic, fullName, dateOfBirth, genderId, address, currentDateTime));
                                    invoker.addCommand(new AddAppointmentCommand(appointmentId, nic, serviceId, selectedAppotmentDate, queueNumber, currentDateTime));
                                    invoker.executeCommands();

//                                    MySQL.execute("INSERT INTO `patient` (`nic`,`full_name`,`date_of_birth`,`gender_id`,address,`registered_date`) "
//                                            + "VALUES ('" + nic + "','" + fullName + "','" + dateOfBirth + "','" + genderId + "','" + address + "','" + currentDateTime + "')");
//
//                                    MySQL.execute("INSERT INTO appointment (`id`,`appointment_date`,`patient_nic`,`service_id`,added_date_time,`queue_no`) "
//                                            + "VALUES ('" + appointmentId + "','" + selectedAppotmentDate + "','" + nic + "','" + serviceId + "','" + currentDateTime + "','" + queueNumber + "')");
                                    JOptionPane.showMessageDialog(this, "Appointment and Patient Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/resources/success.png")));

                                    loadAppointmentTable();
                                    appointmentManagement.loadAppointmentTable();
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

            } catch (Exception e) {
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
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        genderComboBox = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        fullNameTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        dobTextField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        addressTextArea = new javax.swing.JTextArea();
        checkNicButton = new javax.swing.JButton();
        openDobCalButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        timeTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        doctorNameTextField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        serviceComboBox = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        appointmentDateTextField = new javax.swing.JTextField();
        addPatientButton = new javax.swing.JButton();
        serviceDesTextField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        priceTextField = new javax.swing.JTextField();
        resetButton = new javax.swing.JButton();
        openDobCalButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Appointment");
        setMinimumSize(new java.awt.Dimension(700, 500));
        setResizable(false);

        jPanel2.setEnabled(false);

        nicTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        nicTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nicTextFieldActionPerformed(evt);
            }
        });

        jLabel6.setText("NIC Number");

        jLabel7.setText("Address");

        jLabel8.setText("Gender");

        genderComboBox.setEnabled(false);
        genderComboBox.setPreferredSize(new java.awt.Dimension(72, 35));
        genderComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genderComboBoxActionPerformed(evt);
            }
        });

        jLabel10.setText("Full Name");

        fullNameTextField.setEnabled(false);
        fullNameTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        fullNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullNameTextFieldActionPerformed(evt);
            }
        });

        jLabel11.setText("Date Of Birth");

        dobTextField.setEnabled(false);
        dobTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        dobTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dobTextFieldActionPerformed(evt);
            }
        });

        addressTextArea.setColumns(20);
        addressTextArea.setRows(5);
        addressTextArea.setEnabled(false);
        jScrollPane2.setViewportView(addressTextArea);

        checkNicButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/enter.png"))); // NOI18N
        checkNicButton.setBorder(null);
        checkNicButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkNicButtonActionPerformed(evt);
            }
        });

        openDobCalButton.setBackground(new java.awt.Color(102, 102, 102));
        openDobCalButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/calander.png"))); // NOI18N
        openDobCalButton.setBorder(null);
        openDobCalButton.setEnabled(false);
        openDobCalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDobCalButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fullNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(nicTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkNicButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(dobTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openDobCalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(genderComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addGap(41, 41, 41)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkNicButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nicTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fullNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(openDobCalButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dobTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Appoitment Id", "Patient NIC", "Appointment Date", "Service", "Doctor", "Queue Number"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(userTable);

        jLabel12.setText("Service");

        timeTextField.setEnabled(false);
        timeTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        timeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeTextFieldActionPerformed(evt);
            }
        });

        jLabel13.setText("Doctor's Name");

        doctorNameTextField.setEnabled(false);
        doctorNameTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        doctorNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doctorNameTextFieldActionPerformed(evt);
            }
        });

        jLabel14.setText("Time");

        serviceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        serviceComboBox.setPreferredSize(new java.awt.Dimension(72, 35));
        serviceComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                serviceComboBoxItemStateChanged(evt);
            }
        });
        serviceComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceComboBoxActionPerformed(evt);
            }
        });

        jLabel15.setText("Service Description");

        jLabel16.setText("Appoitment Date");

        appointmentDateTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        appointmentDateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appointmentDateTextFieldActionPerformed(evt);
            }
        });

        addPatientButton.setText("Add Appointment");
        addPatientButton.setBorder(null);
        addPatientButton.setPreferredSize(new java.awt.Dimension(99, 35));
        addPatientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPatientButtonActionPerformed(evt);
            }
        });

        serviceDesTextField.setEnabled(false);
        serviceDesTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        serviceDesTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceDesTextFieldActionPerformed(evt);
            }
        });

        jLabel17.setText("Price");

        priceTextField.setEnabled(false);
        priceTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        priceTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceTextFieldActionPerformed(evt);
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

        openDobCalButton1.setBackground(new java.awt.Color(102, 102, 102));
        openDobCalButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/calander.png"))); // NOI18N
        openDobCalButton1.setBorder(null);
        openDobCalButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDobCalButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(resetButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addPatientButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(priceTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(timeTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(doctorNameTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(serviceDesTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(serviceComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(appointmentDateTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(openDobCalButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serviceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serviceDesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(doctorNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(priceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(openDobCalButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(appointmentDateTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addComponent(addPatientButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void addPatientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPatientButtonActionPerformed
        addNewAppointment();
    }//GEN-LAST:event_addPatientButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        resetFields();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void nicTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nicTextFieldActionPerformed
        checkNicNumber();
    }//GEN-LAST:event_nicTextFieldActionPerformed

    private void genderComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genderComboBoxActionPerformed
        addressTextArea.grabFocus();
    }//GEN-LAST:event_genderComboBoxActionPerformed

    private void fullNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullNameTextFieldActionPerformed
        dobTextField.grabFocus();
    }//GEN-LAST:event_fullNameTextFieldActionPerformed

    private void dobTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dobTextFieldActionPerformed
        genderComboBox.grabFocus();
    }//GEN-LAST:event_dobTextFieldActionPerformed

    private void timeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timeTextFieldActionPerformed

    private void doctorNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doctorNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_doctorNameTextFieldActionPerformed

    private void serviceComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serviceComboBoxActionPerformed

    private void checkNicButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkNicButtonActionPerformed
        checkNicNumber();
    }//GEN-LAST:event_checkNicButtonActionPerformed

    private void appointmentDateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appointmentDateTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_appointmentDateTextFieldActionPerformed

    private void serviceComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_serviceComboBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            loadSelectedServiceData();
        }
    }//GEN-LAST:event_serviceComboBoxItemStateChanged

    private void serviceDesTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceDesTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serviceDesTextFieldActionPerformed

    private void priceTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceTextFieldActionPerformed

    private void openDobCalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDobCalButtonActionPerformed
        dobDateChooser.showPopup();
    }//GEN-LAST:event_openDobCalButtonActionPerformed

    private void openDobCalButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDobCalButton1ActionPerformed
        appoitmentDateChooser.showPopup();
    }//GEN-LAST:event_openDobCalButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(AddNewAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddNewAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddNewAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddNewAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton addPatientButton;
    private javax.swing.JTextArea addressTextArea;
    private javax.swing.JTextField appointmentDateTextField;
    private javax.swing.JButton checkNicButton;
    private javax.swing.JTextField dobTextField;
    private javax.swing.JTextField doctorNameTextField;
    private javax.swing.JTextField fullNameTextField;
    private javax.swing.JComboBox<String> genderComboBox;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nicTextField;
    private javax.swing.JButton openDobCalButton;
    private javax.swing.JButton openDobCalButton1;
    private javax.swing.JTextField priceTextField;
    private javax.swing.JButton resetButton;
    private javax.swing.JComboBox<String> serviceComboBox;
    private javax.swing.JTextField serviceDesTextField;
    private javax.swing.JTextField timeTextField;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
