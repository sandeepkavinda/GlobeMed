/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.DecimalFormat;

/**
 *
 * @author HP
 */
public class CurrencyFormatter {
    public static String formatCurrency(double value) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "LKR " +df.format(value);
    }
}
