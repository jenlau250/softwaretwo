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

    private final IntegerProperty barberId = new SimpleIntegerProperty();
    private final StringProperty barberName = new SimpleStringProperty();
    private final StringProperty barberPhone = new SimpleStringProperty();
    private final StringProperty barberEmail = new SimpleStringProperty();
    private final StringProperty notes = new SimpleStringProperty();
    private final StringProperty active = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> hireDate = new SimpleObjectProperty();

    public Barber() {

    }

    //FOR TABLEVIEW
    public Barber(int id, String name, String phone, String email, String active, String notes, LocalDate date) {
        this.barberId.setValue(id);
        this.barberName.set(name);
        this.barberPhone.set(name);
        this.barberEmail.set(name);

        this.active.set(active);
        this.notes.set(notes);
        this.hireDate.set(date);
    }

    public StringProperty nameProperty() {
        return barberName;
    }

    public StringProperty barberPhoneProperty() {
        return barberPhone;
    }

    public StringProperty barberEmailProperty() {
        return barberEmail;
    }

    public StringProperty noteProperty() {
        return notes;
    }

    public StringProperty activeProperty() {
        return active;
    }

    public IntegerProperty barberIdProperty() {
        return barberId;
    }

    public Barber(int barberId, String name) {
        this.barberId.setValue(barberId);
        this.barberName.set(name);
    }

    public String getPhone() {
        return barberPhone.get();
    }

    public void setPhone(String phone) {
        this.barberPhone.set(phone);
    }

    public String getEmail() {
        return barberEmail.get();
    }

    public void setEmail(String email) {
        this.barberEmail.set(email);

    }

    public int getBarberId() {
        return barberId.get();
    }

    public void setBarberId(int barberId) {
        this.barberId.set(barberId);
    }

    public String getBarberName() {
        return barberName.get();
    }

    public void setBarberName(String barberName) {
        this.barberName.set(barberName);
    }

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public String getActive() {
        return active.get();
    }

    public void setActive(String active) {
        this.active.set(active);
    }

    public ObjectProperty hireDateProperty() {
        return hireDate;
    }

    public LocalDate getHireDate() {
        return hireDate.get();
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate.setValue(hireDate);
    }

    @Override
    public String toString() {
        return String.valueOf(barberName);
    }

}
