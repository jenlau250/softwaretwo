/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cache;

import jCalendar.dao.CustomerDaoImpl;
import jCalendar.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jen
 */
public class CustomerCache {

    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> returnList = FXCollections.observableArrayList();
        returnList.addAll(customerList);
        return returnList;
    }

    public static Customer getCustomer(String customerId) {
        for (Customer c : customerList) {
            if (c.getCustomerId().equals(customerId)) {
                return c;
            }
        }
        return null;
    }

    //        PetDaoImpl petDb = new PetDaoImpl();
//        for (Customer c : customerData) {
//            c.getPets().addAll(petDb.getPetsByCustomer(c.getCustomerId()));
//
    public static void flush() {
        customerList.clear();

        customerList.addAll(new CustomerDaoImpl().loadCustomerData());

    }

    public static void loadPets(String customerId) {

        for (Customer c : customerList) {
            if (c.getCustomerId().equals(customerId)) {
                c.getPets().add(PetCache.getPetsByCustomerId(customerId));
            }

            System.out.println("Testing loadPets() " + c.getPets());
        }
    }
}
