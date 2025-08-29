/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */
public class AddressFormatter {

    public static String formatAddress(String address) {
        if (address == null || address.isEmpty()) {
            return "";
        }
        return address.replace(",", ",\n");
    }
}
