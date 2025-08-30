package gui;

import java.sql.ResultSet;
import javax.swing.JOptionPane;
import model.MySQL;

// Abstract Handler
abstract class AuthHandler {

    protected AuthHandler next;

    public void setNext(AuthHandler next) {
        this.next = next;
    }

    public abstract boolean handle(String nic, String password, String role);
}

class FieldValidationHandler extends AuthHandler {

    @Override
    public boolean handle(String nic, String password, String role) {

        if (role.equals("Select")) {
            JOptionPane.showMessageDialog(null, "Please Select your role", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (nic.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter your NIC", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter your Password", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            return next == null || next.handle(nic, password, role);
        }

    }
}

class NicHandler extends AuthHandler {

    @Override
    public boolean handle(String nic, String password, String role) {

        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM staff WHERE nic = '" + nic + "'");

            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Invalid NIC", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return next == null || next.handle(nic, password, role);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}

class PasswordHandler extends AuthHandler {

    @Override
    public boolean handle(String nic, String password, String role) {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM staff WHERE nic = '" + nic + "' AND password='" + password + "'  ");

            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Invalid Password!", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return next == null || next.handle(nic, password, role);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}

class RoleHandler extends AuthHandler {

    @Override
    public boolean handle(String nic, String password, String role) {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM staff "
                    + "INNER JOIN staff_role ON staff.staff_role_id = staff_role.id"
                    + " WHERE nic = '" + nic + "' AND role='" + role + "'  ");

            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Invalid Role!", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            Signin.userBean.setStaffRole(resultSet.getString("role"));
            return next == null || next.handle(nic, password, role);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}

class StatusHandler extends AuthHandler {

    @Override
    public boolean handle(String nic, String password, String role) {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM staff "
                    + "INNER JOIN staff_status ON staff.staff_status_id = staff_status.id"
                    + " WHERE nic = '" + nic + "' AND status='Active'");

            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Deactivated Account", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            System.out.println("Authentication Successful! Welcome");
            Signin.userBean.setNic(resultSet.getString("nic"));
            Signin.userBean.setFirstName(resultSet.getString("first_name"));
            Signin.userBean.setLastName(resultSet.getString("last_name"));
            Signin.userBean.setHospitalId(resultSet.getString("last_name"));
            Signin.userBean.setHospitalId(resultSet.getString("hospital_id"));
            

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
