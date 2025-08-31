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
public class AddAppointmentCommand implements AppointmentCommand {
    private String appointmentId, patientNic, serviceId, appointmentDate, currentDateTime;
    private int queueNumber;

    public AddAppointmentCommand(String appointmentId, String patientNic, String serviceId, String appointmentDate, int queueNumber, String currentDateTime) {
        this.appointmentId = appointmentId;
        this.patientNic = patientNic;
        this.serviceId = serviceId;
        this.appointmentDate = appointmentDate;
        this.queueNumber = queueNumber;
        this.currentDateTime = currentDateTime;
    }

    @Override
    public void execute() throws Exception {
        MySQL.execute("INSERT INTO appointment (id, appointment_date, patient_nic, service_id, added_date_time, queue_no) " +
                      "VALUES ('" + appointmentId + "','" + appointmentDate + "','" + patientNic + "','" + serviceId + "','" + currentDateTime + "','" + queueNumber + "')");
    }
}

