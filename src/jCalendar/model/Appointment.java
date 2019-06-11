/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.model;

import java.time.Month;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jlau2
 */
public class Appointment {

    private String appointmentId;
    private Customer customer;
    private String title;
    private String type;
    private String location;
    private String month;
    private String count;

    private String start;
    private String end;
    private String user;

    public Appointment() {
    }
    
    public Appointment(String month, String type, String count) {
	this.month = month;
	this.type = type;
	this.count = count;
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
    public Appointment(String appointmentId) {
	this.appointmentId = appointmentId;
    }

    public Appointment(String appointmentId, String start, String end, String title, String type, String location, Customer customer, String user) {
	this.appointmentId = appointmentId;
	this.start = start;
	this.end = end;
	this.title = title;
	this.type = type;
	this.location = location;
	this.customer = customer;
	this.user = user;
    }
   

//    public Appointment(String start, String end, String user) {
//	this.start = start;
//	this.end = end;
//	this.user = user;
//    }

    public String getAppointmentId() {
	return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
	this.appointmentId = appointmentId;
    }

    
    public String getLocation() {
	return location;
    }

    public void setLocation(String location) {
	this.location = location;
    }
    
    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

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

    @Override
    public String toString() {
	return "ID: " + this.appointmentId
		+ " Start: " + this.start
		+ " End: " + this.end
		+ " Title: " + this.title
		+ " Type: " + this.type
		+ " Customer: " + this.customer.getCustomerName()
		+ " Consultant: " + this.user + ".\n";
    }

    public Customer getCustomer() {
	return customer;
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

    
}
