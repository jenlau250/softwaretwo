/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cache;

import jCalendar.dao.BarberDaoImpl;
import jCalendar.model.Barber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jen
 */
public class BarberCache {

    private static ObservableList<Barber> barberList = FXCollections.observableArrayList();

    public static ObservableList<Barber> getAllBarbers() {
        ObservableList<Barber> returnList = FXCollections.observableArrayList();
        returnList.addAll(barberList);
        return returnList;
    }

    public static ObservableList<Barber> getAllActiveBarbers() {
        ObservableList<Barber> returnList = FXCollections.observableArrayList();
        for (Barber b : barberList) {
            if (b.getActive()) {
                returnList.add(b);
            }
        }
        return returnList;

    }

    public static ObservableList<Barber> getAllInactiveBarbers() {
        ObservableList<Barber> returnList = FXCollections.observableArrayList();
        for (Barber b : barberList) {
            if (!b.getActive()) {
                returnList.add(b);
            }
        }
        return returnList;

    }

    public static Barber getBarber(int barberId) {
        for (Barber b : barberList) {
            if (b.getBarberId() == barberId) {
                return b;
            }
        }
        return null;
    }

    public static void flush() {
        barberList.clear();
        barberList.addAll(new BarberDaoImpl().loadBarberData());
    }

}
