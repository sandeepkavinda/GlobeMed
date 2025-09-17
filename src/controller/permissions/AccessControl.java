/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.permissions;

/**
 *
 * @author HP
 */
public class AccessControl {

    private AccessHandler chain;

    public AccessControl() {

        AdminAccessHandler admin = new AdminAccessHandler();
        DoctorAccessHandler doctor = new DoctorAccessHandler();
        NurseAccessHandler nurse = new NurseAccessHandler();
        PharmacistAccessHandler pharmacist = new PharmacistAccessHandler();
        ReceptionistAccessHandler receptionist = new ReceptionistAccessHandler();

        admin.setNextHandler(doctor);
        doctor.setNextHandler(nurse);
        nurse.setNextHandler(pharmacist);
        pharmacist.setNextHandler(receptionist);

        chain = admin;
    }

    public boolean hasAccess(String action) {
        return chain.checkAccess(action);
    }
}
