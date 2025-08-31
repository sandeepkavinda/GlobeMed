/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.billing;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class BillingController {

    private BillingHandler chain;

    public BillingController() {
        setupChain();
    }

    /**
     * Setup the Chain of Responsibility
     */
    private void setupChain() {
        BillingHandler validation = new AppointmentValidationHandler();
        BillingHandler insertion = new BillInsertionHandler();

        // Connect the chain
        validation.setNext(insertion);

        this.chain = validation;
    }

    public void processBilling(String appointmentId, String paymentMethodId) {

        try {
            chain.handle(appointmentId,paymentMethodId);
            JOptionPane.showMessageDialog(null, "Bill Paied Successfully", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/resources/success.png")));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error processing bill: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
