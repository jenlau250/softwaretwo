/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Jen
 */
public class DisplayAppointment {

    private final ObjectProperty<Appointment> appointment = new SimpleObjectProperty<>();
    private final ObjectProperty<Customer> customer = new SimpleObjectProperty<>();
    private final ObjectProperty<Barber> barber = new SimpleObjectProperty<>();
    private final ObjectProperty<Pet> pet = new SimpleObjectProperty<>();

    public DisplayAppointment(Customer customer, Appointment appointment, Barber barber, Pet pet) {
        this.customer.set(customer);
        this.appointment.set(appointment);
        this.barber.set(barber);
        this.pet.set(pet);
    }

    public DisplayAppointment(Appointment appointment, Customer customer) {

        this.appointment.set(appointment);
        this.customer.set(customer);

    }

    public DisplayAppointment(Customer customer, Barber barber) {
        this.customer.set(customer);
        this.barber.set(barber);

    }

    public DisplayAppointment(Pet pet, Customer customer) {
        this.pet.set(pet);
        this.customer.set(customer);

    }

    public Appointment getAppointment() {
        return appointment.get();
    }

    public ObjectProperty<Appointment> appointmentProperty() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment.set(appointment);
    }

    public Customer getCustomer() {
        return customer.get();
    }

    public ObjectProperty<Customer> customerProperty() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer.set(customer);
    }

    public void setBarber(Barber barber) {
        this.barber.set(barber);
    }

    public Barber getBarber() {
        return barber.get();
    }

    public ObjectProperty<Barber> barberProperty() {
        return barber;
    }

    public Pet getPet() {
        return pet.get();
    }

    public ObjectProperty<Pet> petProperty() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet.set(pet);
    }

}
