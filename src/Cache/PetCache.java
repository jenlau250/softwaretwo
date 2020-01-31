/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cache;

import jCalendar.dao.PetDaoImpl;
import jCalendar.model.Customer;
import jCalendar.model.Pet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jen
 */
public class PetCache {

    private static ObservableList<Pet> petList = FXCollections.observableArrayList();

    public static ObservableList<Pet> getAllPets() {
        ObservableList<Pet> returnList = FXCollections.observableArrayList();
        returnList.addAll(petList);
        return returnList;
    }

    public static Pet getPet(String petId) {
        for (Pet p : petList) {
            if (p.getPetId().equals(petId)) {
                return p;
            }
        }
        return null;
    }

    //want to add this to Customer's Pets List
    public static Pet getPetsByCustomerId(String customerId) {
        for (Pet p : petList) {
            if (p.getCustomerId().equals(customerId)) {
                return p;
            }
        }
        return null;
    }

    public static void flush() {
        petList.clear();
        petList.addAll(new PetDaoImpl().loadPetData());
    }

    //this doesn't work, i only want to run it once
    public static void getPetsByCustomer(Customer c) {
        for (Pet p : petList) {
            if (p.getCustomerId().equals(c.getCustomerId())) {
                c.getPets().add(p);
            }
        }

    }

    public static ObservableList<Pet> getSelectedPets(Customer c) {

        ObservableList<Pet> returnList = FXCollections.observableArrayList();
        for (Pet p : petList) {
            if (p.getCustomerId().equals(c.getCustomerId())) {
                returnList.addAll(p);
            }

        }
        return returnList;

    }

    public static ObservableList<String> getPetNames(Customer c) {
        ObservableList<String> petNames = FXCollections.observableArrayList();
        for (Pet p : petList) {
            if (p.getCustomerId().equals(c.getCustomerId())) {
                petNames.addAll(p.getPetName());
            }
        }
        return petNames;
    }
}
