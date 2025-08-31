/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.billing;

import model.MySQL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.IdGenerater;

public abstract class BillingHandler {
    protected BillingHandler next;

    public void setNext(BillingHandler next) {
        this.next = next;
    }

    public abstract void handle(String appointmentId, String paymentMethodId) throws Exception;
}

class AppointmentValidationHandler extends BillingHandler {
    @Override
    public void handle(String appointmentId, String paymentMethodId) throws Exception {
        ResultSet rs = MySQL.execute("SELECT * FROM appointment WHERE id='" + appointmentId + "'");
        if (rs.next()) {
            System.out.println("Appointment valid.");
            if (next != null) next.handle(appointmentId, paymentMethodId);
        } else {
            throw new Exception("Invalid appointment ID");
        }
    }
}

class BillInsertionHandler extends BillingHandler {
    @Override
    public void handle(String appointmentId, String paymentMethodId) throws Exception {
        String billId = IdGenerater.generateId("bill", "id", "BLL");
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String currentDateTime = format.format(date);

        MySQL.execute("INSERT INTO bill (`id`,`date_time`,`appointment_id`,`payment_method_id`) "
                + "VALUES ('" + billId + "','" + currentDateTime + "','" + appointmentId + "','" + paymentMethodId + "')");

        System.out.println("Bill inserted successfully: " + billId);
    }
}


