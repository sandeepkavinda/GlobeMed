/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.appointment;

import model.MySQL;

/**
 *
 * @author HP
 */
public class AddPatientCommand implements AppointmentCommand {
    private String nic, fullName, dob, gender, address;
    private String currentDateTime;

    public AddPatientCommand(String nic, String fullName, String dob, String gender, String address, String currentDateTime) {
        this.nic = nic;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.currentDateTime = currentDateTime;
    }

    @Override
    public void execute() throws Exception {
        // Insert patient into database
        MySQL.execute("INSERT INTO patient (nic, full_name, date_of_birth, gender_id, address, registered_date) " +
                      "VALUES ('" + nic + "','" + fullName + "','" + dob + "','" + gender + "','" + address + "','" + currentDateTime + "')");
    }
}

