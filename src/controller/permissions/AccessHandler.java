package controller.permissions;

import gui.Signin;

public abstract class AccessHandler {

   
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

            if (action.equals(AccessControl.ADD_MEDICAL_RECORD)) {
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

            if (action.equals(AccessControl.ADD_MEDICAL_RECORD)) {
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

        }
        return nextHandler != null && nextHandler.checkAccess(action);
    }
}

// Pharmacist Permissions
class PharmacistAccessHandler extends AccessHandler {

    @Override
    public boolean checkAccess(String action) {

        if (Signin.userBean.getStaffRole().equals("NURSE")) {

        }
        return nextHandler != null && nextHandler.checkAccess(action);
    }
}

// Receptionist Permissions
class ReceptionistAccessHandler extends AccessHandler {

    @Override
    public boolean checkAccess(String action) {

        if (Signin.userBean.getStaffRole().equals("NURSE")) {

        }
        return nextHandler != null && nextHandler.checkAccess(action);
    }
}
