package controller.permissions;

import gui.Signin;
import java.util.Arrays;

public abstract class AccessHandler {

    public static final String DISPLAY_DASHBOARD = "1";

    public static final String DISPLAY_STAFF = "2";
    public static final String ADD_NEW_STAFF_MEMBER = "3";
    public static final String CHANGE_STAFF_MEMBER_STATUS = "4";

    public static final String DISPLAY_SERVICE = "5";
    public static final String ADD_NEW_SERVICE = "6";

    public static final String DISPLAY_PATIENT = "7";
    public static final String ADD_NEW_PATIENT = "8";
    public static final String VIEW_PATIENT_DETAILS = "9";
    public static final String ADD_MEDICAL_RECORD = "10";

    public static final String DISPLAY_APPOINTMENTS = "11";
    public static final String ADD_NEW_APPOINTMENT = "12";

    public static final String DISPLAY_BILLING = "13";
    public static final String ADD_NEW_BILL = "14";

    public static final String DISPLAY_REPORT = "15";
    public static final String ADD_NEW_DIAGNOSE_REPORT = "16";
    public static final String GENERATE_DIAGNOSE_REPORT = "17";
    public static final String GENERATE_PATIENT_MEDICAL_REPORT = "18";
    public static final String GENERATE_FINANCIAL_REPORT = "19";

    public static final String DISPLAY_HOSPITAL = "20";

    String[] adminPermissions = {DISPLAY_DASHBOARD, DISPLAY_STAFF, ADD_NEW_STAFF_MEMBER, CHANGE_STAFF_MEMBER_STATUS, DISPLAY_SERVICE, ADD_NEW_SERVICE, DISPLAY_PATIENT,ADD_NEW_PATIENT,VIEW_PATIENT_DETAILS,DISPLAY_APPOINTMENTS,ADD_NEW_APPOINTMENT,DISPLAY_BILLING,ADD_NEW_BILL ,DISPLAY_REPORT,GENERATE_DIAGNOSE_REPORT, GENERATE_PATIENT_MEDICAL_REPORT, GENERATE_FINANCIAL_REPORT};
    String[] doctorPermissions = {DISPLAY_PATIENT, VIEW_PATIENT_DETAILS, ADD_MEDICAL_RECORD,ADD_NEW_DIAGNOSE_REPORT, GENERATE_DIAGNOSE_REPORT, GENERATE_PATIENT_MEDICAL_REPORT,DISPLAY_REPORT};
    String[] nursePermissions = {DISPLAY_PATIENT,VIEW_PATIENT_DETAILS, ADD_NEW_DIAGNOSE_REPORT};
    String[] pharmacistPermissions = {DISPLAY_PATIENT, VIEW_PATIENT_DETAILS, ADD_NEW_APPOINTMENT, DISPLAY_BILLING, ADD_NEW_BILL,GENERATE_DIAGNOSE_REPORT, GENERATE_PATIENT_MEDICAL_REPORT};
    String[] receptionistPermissions = {DISPLAY_PATIENT, ADD_NEW_PATIENT, DISPLAY_APPOINTMENTS, ADD_NEW_APPOINTMENT, DISPLAY_BILLING, ADD_NEW_BILL, GENERATE_DIAGNOSE_REPORT, GENERATE_PATIENT_MEDICAL_REPORT};

    protected AccessHandler nextHandler;

    //Actions
    public void setNextHandler(AccessHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract boolean checkAccess(String action);
}




// Admin Permissions
class AdminAccessHandler extends AccessHandler {

    @Override
    public boolean checkAccess(String action) {

        if (Signin.userBean.getStaffRole().equals("ADMIN")) {

            if (Arrays.asList(adminPermissions).contains(action)) {
                return true;
            }
        }
        return nextHandler != null && nextHandler.checkAccess(action);
    }
}





// Doctor Permissions
class DoctorAccessHandler extends AccessHandler {

    @Override
    public boolean checkAccess(String action) {

        if (Signin.userBean.getStaffRole().equals("DOCTOR")) {

            if (Arrays.asList(doctorPermissions).contains(action)) {
                return true;
            }
        }
        return nextHandler != null && nextHandler.checkAccess(action);
    }
}




// Nurse Permissions
class NurseAccessHandler extends AccessHandler {

    @Override
    public boolean checkAccess(String action) {

        if (Signin.userBean.getStaffRole().equals("NURSE")) {
            if (Arrays.asList(nursePermissions).contains(action)) {
                return true;
            }

        }
        return nextHandler != null && nextHandler.checkAccess(action);
    }
}




// Pharmacist Permissions
class PharmacistAccessHandler extends AccessHandler {

    @Override
    public boolean checkAccess(String action) {

        if (Signin.userBean.getStaffRole().equals("PHARMACIST")) {
            if (Arrays.asList(pharmacistPermissions).contains(action)) {
                return true;
            }
        }
        return nextHandler != null && nextHandler.checkAccess(action);
    }
}




// Receptionist Permissions
class ReceptionistAccessHandler extends AccessHandler {

    @Override
    public boolean checkAccess(String action) {

        if (Signin.userBean.getStaffRole().equals("RECEPTIONIST")) {
            if (Arrays.asList(receptionistPermissions).contains(action)) {
                return true;
            }
        }
        return nextHandler != null && nextHandler.checkAccess(action);
    }
}

