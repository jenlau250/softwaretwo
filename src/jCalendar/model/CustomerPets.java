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
public class CustomerPets {

    int customerpetId;
    int petId;
    int customerId;

    private final ObjectProperty<Customer> customer = new SimpleObjectProperty<>();
    private final ObjectProperty<Pet> pet = new SimpleObjectProperty<>();

    //empty constructor
    public CustomerPets() {
        this.customer.set(null);
        this.pet.set(null);
    }

    public CustomerPets(Customer customer, Pet pet) {
        this.customer.set(customer);
        this.pet.set(pet);
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
