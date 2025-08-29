/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author HP
 */
public class TimeFormatter {

    public static String formatTimeTo12h(String time24h) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mma");

            Date date = inputFormat.parse(time24h);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return time24h; // fallback
        }
    }
}
