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

    private int appointmentId;
    private int customerId;
    private int barberId;
    private LocalDate startDate;

    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> start2 = new SimpleObjectProperty();
    private final ObjectProperty<LocalDateTime> end2 = new SimpleObjectProperty();

    private String sTitle;
    private String type;
    private String location;
    private String month;
    private String count;
    private String start;
    private String end;

    private Customer customer;
    private Pet pet;
    private String user;
    private Barber barber;
    private String calendar;

//    public void setStartDate(ObjectProperty<LocalDateTime> start2) {
//        this.start2.set(dateFormatter.format(start2);
//    }
    //FOR TABLEVIEW
//    public Appointment(int appointmentId, int customerId, String title, String description, LocalDateTime start, LocalDateTime end, Barber barber) {
//        this.appointmentId = appointmentId;
////        this.barberId = barberId;
//        this.customerId = customerId;
//        this.title.set(title);
//        this.description.set(description);
//        this.start2.set(start);
//        this.end2.set(end);
//        this.barber = barber;
//    }
    public Appointment(int customerId, String title) {
        this.customerId = customerId;
        this.title.set(title);

    }

//current tableview
    public Appointment(int appointmentId, String title, LocalDateTime start, LocalDateTime end, String desc, String type, Barber barber, Customer customer) {

        this.appointmentId = appointmentId;
//        this.customerId = customerId;
        this.title.set(title);
        this.start2.set(start);
        this.end2.set(end);
        this.description.set(desc);
        this.type = type;
        this.barber = barber;
        this.customer = customer;

    }

    //for saving
    public Appointment(String title, LocalDateTime start, LocalDateTime end, String desc, String type, Barber barber, Customer customer) {

//        this.appointmentId = appointmentId;
//        this.customerId = customerId;
        this.title.set(title);
        this.start2.set(start);
        this.end2.set(end);
        this.description.set(desc);
        this.type = type;
        this.barber = barber;
        this.customer = customer;

    }

    public int getCustomerId() {
        return customerId;

    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBarberId() {
        return barberId;

    }

    public void setBarberId(int barberId) {
        this.barberId = barberId;
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
        return start2;
    }

    public ObjectProperty endProperty() {
        return end2;
    }

    public LocalDateTime getStart2() {
        return start2.get();
    }

    public LocalDate getStartDate() {
        return start2.get().toLocalDate();
    }

    private static final ObservableList<String> startTimes = FXCollections.observableArrayList();
    private static final ObservableList<String> endTimes = FXCollections.observableArrayList();

    //Default Appt Types
    private static ObservableList<String> apptTypes = FXCollections.observableArrayList("Standard", "Extended");

    //Default Appt Start Times
    public Appointment() {
    }

    public Appointment(String month, String type, String count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    public Appointment(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public Appointment(int appointmentId, String start, String end, String sTitle, String type, String location, Customer customer, String user) {
        this.appointmentId = appointmentId;
        this.start = start;
        this.end = end;
        this.sTitle = sTitle;
        this.type = type;
        this.location = location;
        this.customer = customer;
        this.user = user;
    }

    public Appointment(int sAppointmentId, String format, String format0, String sTitle, String sType, String sLocation, Customer sCustomer, String sUser, Barber sBarber, Calendar sCalendar) {
        this.appointmentId = appointmentId;
        this.start = start;
        this.end = end;
        this.sTitle = sTitle;
        this.type = type;
        this.location = location;
        this.customer = customer;
        this.user = user;
        this.barber = barber;
        this.calendar = calendar;
    }

    public Appointment(int appointmentId, String title, String type, String location, String month, String start, String end, Customer customer, Pet pet) {
        this.appointmentId = appointmentId;
        this.sTitle = title;
        this.type = type;
        this.location = location;
        this.month = month;
        this.start = start;
        this.end = end;
        this.customer = customer;
        this.pet = pet;
    }
//
//    public int getAppointmentId() {
//        return appointmentId;
//    }
//    public void setAppointmentId(int appointmentId) {
//        this.appointmentId = appointmentId;
//    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Barber getBarber() {
        return barber;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public void setBarber(Barber barber) {
        this.barber = barber;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

//    public Pet getPet() {
//        return pet;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
}
