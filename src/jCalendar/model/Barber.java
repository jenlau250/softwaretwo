/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.model;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jlau2
 */
public class Barber {

    private final IntegerProperty barberId2 = new SimpleIntegerProperty();
    private final StringProperty barberName2 = new SimpleStringProperty();
    private final StringProperty notes2 = new SimpleStringProperty();
    private final StringProperty active2 = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> hireDate2 = new SimpleObjectProperty();
//
//    private int barberId;
//    private String barberName;
//    private String notes;
//    private String active;
//    private Date hireDate;

    public Barber() {

    }

    //FOR TABLEVIEW
    public Barber(int id, String name, String notes, String active, LocalDate date) {
        this.barberId2.setValue(id);
        this.barberName2.set(name);
        this.notes2.set(notes);
        this.active2.set(active);
        this.hireDate2.set(date);
    }

    public StringProperty nameProperty() {
        return barberName2;
    }

    public StringProperty noteProperty() {
        return notes2;
    }

    public StringProperty activeProperty() {
        return active2;
    }

    public IntegerProperty barberIdProperty() {
        return barberId2;
    }

    public Barber(int barberId, String name) {
        this.barberId2.setValue(barberId);
        this.barberName2.set(name);
    }

    public int getBarberId() {
        return barberId2.get();
    }

    public void setBarberId(int barberId) {
        this.barberId2.set(barberId);
    }

    public String getBarberName() {
        return barberName2.get();
    }

    public void setBarberName(String barberName) {
        this.barberName2.set(barberName);
    }

    public String getNotes() {
        return notes2.get();
    }

    public void setNotes(String notes) {
        this.notes2.set(notes);
    }

    public String getActive() {
        return active2.get();
    }

    public void setActive(String active) {
        this.active2.set(active);
    }

    public ObjectProperty hireDateProperty() {
        return hireDate2;
    }

    public LocalDate getHireDate() {
        return hireDate2.get();
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate2.setValue(hireDate);
    }

    @Override
    public String toString() {
        return String.valueOf(barberName2);
    }

}
