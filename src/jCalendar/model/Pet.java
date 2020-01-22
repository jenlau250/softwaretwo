/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jlau2
 */
public class Pet {

    private final IntegerProperty petId2 = new SimpleIntegerProperty();
    private final StringProperty petName2 = new SimpleStringProperty();
    private final StringProperty petType2 = new SimpleStringProperty();
    private final StringProperty petDescription2 = new SimpleStringProperty();
    private CustomerPets customerPet;

//    private int petId;
//    private String petName;
//    private String petType;
//    private String petDescription;
    private String image;

    //FOR TABLEVIEW
    public Pet(int id, String name, String type, String desc) {
        this.petId2.setValue(id);
        this.petName2.set(name);
        this.petType2.set(type);
        this.petDescription2.set(desc);
    }

    public StringProperty nameProperty() {
        return petName2;
    }

    public StringProperty typeProperty() {
        return petType2;
    }

    public StringProperty descProperty() {
        return petDescription2;
    }

    public IntegerProperty petIdProperty() {
        return petId2;
    }

    public Pet() {

    }

    public Pet(int petId, String petName) {
        this.petId2.set(petId);
        this.petName2.set(petName);
    }

//    public Pet(int petId, String petName, String petType, String petDescription) {
//        this.petId = petId;
//        this.petName = petName;
//        this.petType = petType;
//        this.petDescription = petDescription;
//    }
//    public Pet(int petId, String petName, String petType, String petDescription, String image) {
//        this.petId = petId;
//        this.petName = petName;
//        this.petType = petType;
//        this.petDescription = petDescription;
//        this.image = image;
//    }
//
//    public int getPetId() {
//        return petId;
//    }
//
//    public void setPetId(int petId) {
//        this.petId = petId;
//    }
//
//    public String getPetName() {
//        return petName;
//    }
//
//    public void setPetName(String petName) {
//        this.petName = petName;
//    }
//
//    public String getPetType() {
//        return petType;
//    }
//
//    public void setPetType(String petType) {
//        this.petType = petType;
//    }
//
//    public String getPetDescription() {
//        return petDescription;
//    }
//
//    public void setPetDescription(String petDescription) {
//        this.petDescription = petDescription;
//    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
