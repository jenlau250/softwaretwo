/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cache;

import jCalendar.dao.AppointmentDaoImpl;
import jCalendar.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jen
 */
public class AppointmentCache {

    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> returnList = FXCollections.observableArrayList();
        returnList.addAll(appointmentList);
        return returnList;
    }

    public static Appointment getAppointment(String appointmentId) {
        for (Appointment a : appointmentList) {
            if (a.getAppointmentId().equals(appointmentId)) {
                return a;
            }
        }
        return null;
    }

    public static void flush() {
        appointmentList.clear();
        appointmentList.addAll(new AppointmentDaoImpl().loadApptData());

    }
}
