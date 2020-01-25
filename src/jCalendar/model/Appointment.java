/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.model;

import jCalendar.utilities.DateTimeUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author jlau2
 */
public class Appointment {

    private final StringProperty appointmentId = new SimpleStringProperty();
//    private final StringProperty startDate = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty();
    private final ObjectProperty<LocalDateTime> start = new SimpleObjectProperty();
    private final ObjectProperty<LocalDateTime> end = new SimpleObjectProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty(); //type
    private Customer customer;
    private Pet pet;
    private String user;
    private Barber barber;

    private String month;
    private String count;

    private static final ObservableList<String> startTimes = FXCollections.observableArrayList();
    private static final ObservableList<String> endTimes = FXCollections.observableArrayList();

    //Default Appt Types
    private static ObservableList<String> apptTypes = FXCollections.observableArrayList("Standard", "Extended");

    public Appointment() {
    }

    public Appointment(String appointmentId, String title, LocalDate startDate, LocalDateTime start, LocalDateTime end, String desc, String type, Barber barber, Customer customer, Pet pet) {

        this.appointmentId.set(appointmentId);
        this.title.set(title);
        this.startDate.set(startDate);
        this.start.set(start);
        this.end.set(end);
        this.description.set(desc);
        this.type.set(type);
        this.barber = barber;
        this.customer = customer;
        this.pet = pet;

    }

    public String getAppointmentId() {
        return appointmentId.get();
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId.set(appointmentId);
    }

    public StringProperty appointmentIdProperty() {
        return appointmentId;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public ObjectProperty startProperty() {
        return start;
    }

    public LocalDateTime getStart() {
        return start.get();
    }

    public ObjectProperty endProperty() {
        return end;
    }

    public LocalDateTime getEnd() {
        return end.get();
    }

    public ObjectProperty startDateProperty() {
        return startDate;
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public Appointment(String month, String type, String count) {
        this.month = month;
        this.type.set(type);
        this.count = count;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Barber getBarber() {
        return barber;
    }

    public void setBarber(Barber barber) {
        this.barber = barber;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public static ObservableList<String> getApptTypes() {
        return apptTypes;
    }

    public static ObservableList<String> getDefaultStartTimes() {
        //Set time from 8AM to 5PM in 15 minute increments
        LocalTime appointmentStartTime = LocalTime.of(8, 0);
        while (!appointmentStartTime.equals(LocalTime.of(17, 0))) {
            String startTime = DateTimeUtil.parseTimeToStringFormat(appointmentStartTime);
            startTimes.add(startTime);
            appointmentStartTime = appointmentStartTime.plusMinutes(15);

        }
        return startTimes;
    }

    public static ObservableList<String> getDefaultEndTimes() {
        //Set time from 8AM to 5PM in 15 minute increments
        LocalTime appointmentEndTime = LocalTime.of(8, 0);
        while (!appointmentEndTime.equals(LocalTime.of(17, 15))) {
            String endTime = DateTimeUtil.parseTimeToStringFormat(appointmentEndTime);
            endTimes.add(endTime);
            appointmentEndTime = appointmentEndTime.plusMinutes(15);

        }
        return endTimes;
    }

    @Override
    //public Appointment(String appointmentId, String title, String startDate, LocalDateTime start, LocalDateTime end, String desc, String type, Barber barber, Customer customer, Pet pet) {

    public String toString() {
        return "ID: " + getAppointmentId() + '\n'
                + "Title: " + getTitle() + '\n'
                + "Start Date: " + getStartDate() + '\n'
                + "Time: " + getStart() + " to " + getEnd() + '\n'
                + "Description: " + getDescription() + '\n'
                + "Type: " + getType() + '\n'
                + "Barber: " + getBarber().getBarberId() + " - " + getBarber().getBarberName() + '\n'
                + "Customer: " + getCustomer().getCustomerId() + " - " + getCustomer().getCustomerName() + '\n'
                + "Pet: " + getPet().getPetId() + " - " + getPet().getPetName() + '\n';
    }

}
