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

    public abstract boolean handle(String username, String password, String role);
}

class UsernameHandler extends AuthHandler {

    @Override
    public boolean handle(String username, String password, String role) {

        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM user WHERE username = '" + username + "'");

            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Invalid Username", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return next == null || next.handle(username, password, role);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}

class PasswordHandler extends AuthHandler {

    @Override
    public boolean handle(String username, String password, String role) {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM user WHERE username = '" + username + "' AND password='" + password + "'  ");

            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Invalid Password!", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return next == null || next.handle(username, password, role);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}

class RoleHandler extends AuthHandler {

    @Override
    public boolean handle(String username, String password, String role) {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM user "
                    + "INNER JOIN user_role ON user.user_role_id = user_role.id"
                    + " WHERE username = '" + username + "' AND role='" + role + "'  ");

            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Invalid Role!", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            return next == null || next.handle(username, password, role);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}

class StatusHandler extends AuthHandler {

    @Override
    public boolean handle(String username, String password, String role) {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM user "
                    + "INNER JOIN user_status ON user.user_status_id = user_status.id"
                    + " WHERE username = '" + username + "' AND status='Active'");

            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Deactivated Account", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            System.out.println("Authentication Successful! Welcome");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
