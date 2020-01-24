/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jlau2
 */
public class Pet {

    private final StringProperty petId = new SimpleStringProperty();
    private final StringProperty petName = new SimpleStringProperty();
    private final StringProperty petType = new SimpleStringProperty();
    private final StringProperty petDescription = new SimpleStringProperty();
    private final StringProperty petActive = new SimpleStringProperty();
    private Customer customer;

//    private String customerId;
    private String image;

    //FOR TABLEVIEW
    public Pet(String id, String name, String type, String desc) {
        this.petId.set(id);
        this.petName.set(name);
        this.petType.set(type);
        this.petDescription.set(desc);
    }

//    public Pet(int id, String name, String type, String desc, String customerId) {
//        this.petId.setValue(id);
//        this.petName2.set(name);
//        this.petType2.set(type);
//        this.petDescription.set(desc);
//        this.customerId = customerId;
//    }
    public Pet(String id, String name, String type, String desc, String active, Customer customer) {
        this.petId.set(id);
        this.petName.set(name);
        this.petType.set(type);
        this.petDescription.set(desc);
        this.petActive.set(active);
        this.customer = customer;
    }

    public StringProperty nameProperty() {
        return petName;
    }

    public StringProperty typeProperty() {
        return petType;
    }

    public StringProperty descProperty() {
        return petDescription;
    }

    public StringProperty petIdProperty() {
        return petId;
    }

    public Pet() {

    }

    public Pet(String petId, String petName) {
        this.petId.set(petId);
        this.petName.set(petName);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Pet ID: " + petId + '\n'
                + "Name: " + petName + '\n'
                + "Type: " + petType + '\n'
                + "Desc: " + petDescription + '\n'
                + "Customer: " + customer + '\n';
    }

}
