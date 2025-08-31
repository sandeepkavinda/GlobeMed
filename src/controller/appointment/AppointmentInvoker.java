/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentInvoker {
    private List<AppointmentCommand> commandQueue = new ArrayList<>();

    public void addCommand(AppointmentCommand command) {
        commandQueue.add(command);
    }

    public void executeCommands() {
        try {
            for (AppointmentCommand command : commandQueue) {
                command.execute();
            }
            commandQueue.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
