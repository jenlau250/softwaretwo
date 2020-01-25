/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jlau2
 */
public class Customer {

    private final StringProperty customerId = new SimpleStringProperty();
    private final StringProperty customerName = new SimpleStringProperty();
    private final StringProperty customerPhone = new SimpleStringProperty();
    private final StringProperty customerEmail = new SimpleStringProperty();
    private final StringProperty active = new SimpleStringProperty();
    private final StringProperty notes = new SimpleStringProperty();
    private ObjectProperty<Pet> pet = new SimpleObjectProperty<>();
    private int petId;

    public Customer(String id, String name) {

        setCustomerId(id);
        setCustomerName(name);

    }

    public StringProperty customerIdProperty() {
        return this.customerId;
    }

    public StringProperty customerNameProperty() {
        return this.customerName;
    }

    public String getCustomerId() {
        return this.customerId.get();
    }

    public void setCustomerId(String customerId) {
        this.customerId.set(customerId);
    }

    public String getCustomerName() {
        return this.customerName.get();
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }
//    private int customerId2;
//    private String customerName2;
//    private String phone;
////    private String email;
//    private String active;
//    private String notes;
//    private Pet pet;
    private String count;
//    private Barber barber;

    public Customer(String id, String name, String phone, String email, String active, String notes) {
        this.customerId.set(id);
        this.customerName.set(name);
        this.customerPhone.set(phone);
        this.customerEmail.set(email);
        this.active.set(active);
        this.notes.set(notes);

    }

    public Customer(String id, String name, String phone, String email, String active, String notes, Pet pet) {
        this.customerId.set(id);
        this.customerName.set(name);
        this.customerPhone.set(phone);
        this.customerEmail.set(email);
        this.active.set(active);
        this.notes.set(notes);
        this.pet.set(pet);

    }

//    public ObjectProperty<Pet> petProperty() {
//        return pet;
//    }
//    public Pet getPet() {
//        return petProperty().get();
//    }
//
//    public void setPet(Pet pet) {
//        petProperty().set(pet);
//    }
//    public Customer(String id, String name, String phone, String email, String active, String notes, int petId) {
//        this.customerId.set(id);
//        this.customerName.set(name);
//        this.customerPhone.set(phone);
//        this.customerEmail.set(email);
//        this.active.set(active);
//        this.notes.set(notes);
//        this.petId = petId;
//
//    }
    //FOR TABLEVIEW
    public Customer(String id, String name, String phone, String email, Pet pet) {
        this.customerId.set(id);
        this.customerName.set(name);
        this.customerPhone.set(phone);
        this.customerEmail.set(email);
//        this.active.set(active);
//        this.notes.set(notes);
        this.pet.set(pet);

    }

//    public int getPetId() {
//        return petId;
//
//    }
//
//    public void setPetId(int petId) {
//        this.petId = petId;
//    }
    public StringProperty customerPhoneProperty() {
        return this.customerPhone;
    }

    public StringProperty customerEmailProperty() {
        return this.customerEmail;
    }

    public Customer() {

    }

//    public Customer(Pet pet, String count) {
//        this.pet.set(pet);
//        this.count = count;
//    }
//    public Customer(int customerId, String customerNameG, String phone, String email, String notes, String active, Pet pet) {
//        this.customerId2 = customerId;
//        this.customerName2 = customerNameG;
//        this.phone = phone;
//        this.email = email;
//        this.notes = notes;
//        this.active = active;
//        this.pet = pet;
//
//    }
    public String getPhone() {
        return customerPhone.get();
    }

    public void setPhone(String phone) {
        this.customerPhone.set(phone);
    }

    public String getEmail() {
        return customerEmail.get();
    }

    public void setEmail(String email) {
        this.customerEmail.set(email);

    }

    public String getActive() {
        return active.get();
    }

    public void setActive(String active) {
        this.active.set(active);
    }

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

//    public Pet getPet() {
//        return pet;
//
//    }
    @Override
    public String toString() {
        return "Customer id " + customerIdProperty().get()
                + " name " + customerNameProperty()
                + " phone " + customerPhoneProperty()
                + " email " + customerEmailProperty();
    }

//    @Override
//    public String toString() {
//        return String.valueOf(customerName);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Customer that = (Customer) o;
//        return Objects.equals(customerId.get(), that.customerId.get())
//                && Objects.equals(customerName.get(), that.customerName.get());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(customerId.get(), customerName.get());
//    }
}
