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
    private final StringProperty active = new SimpleStringProperty();
    private final StringProperty customerId = new SimpleStringProperty();
    private String image;

    public Pet() {

    }

    //FOR TABLEVIEW
    public Pet(String id, String name, String type, String desc, String active, String customerId) {
        this.petId.set(id);
        this.petName.set(name);
        this.petType.set(type);
        this.petDescription.set(desc);
        this.active.set(active);
        this.customerId.set(customerId);
    }

    //used for getPetsByCustomer(customerId)
    public Pet(String id, String name, String type, String desc) {
        this.petId.set(id);
        this.petName.set(name);
        this.petType.set(type);
        this.petDescription.set(desc);

    }

//    public Pet(String id, String name, String type, String desc, String active, Customer customer) {
//        this.petId.set(id);
//        this.petName.set(name);
//        this.petType.set(type);
//        this.petDescription.set(desc);
//        this.petActive.set(active);
//        this.customer = customer;
//    }
    public Pet(String petId, String petName) {
        this.petId.set(petId);
        this.petName.set(petName);
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

    public StringProperty customerIdProperty() {
        return customerId;
    }

    public StringProperty activeProperty() {
        return active;
    }

    public String getPetId() {
        return petId.get();
    }

    public String getCustomerId() {
        return customerId.get();
    }

    public String getPetName() {
        return petName.get();
    }

    public String getPetType() {
        return petType.get();
    }

    public String getPetDesc() {
        return petDescription.get();
    }

    public String getActive() {
        return active.get();
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Pet ID: " + petId + '\n'
                + "Name: " + petName + '\n'
                + "Type: " + petType + '\n'
                + "Desc: " + petDescription + '\n';
    }

}
