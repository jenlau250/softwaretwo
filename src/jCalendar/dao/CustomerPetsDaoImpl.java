/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.dao;

import jCalendar.model.Customer;
import jCalendar.model.CustomerPets;
import jCalendar.model.Pet;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jen
 */
public class CustomerPetsDaoImpl {

    public static ObservableList<CustomerPets> getallCustomerPets() throws SQLException, Exception {
        //can delete DB init and close
        ObservableList<CustomerPets> allCustomerPets = FXCollections.observableArrayList();

//	DBConnection.init();
        String sqlStatement
                = ("SELECT customerpets.customerpetsId, "
                + "customer.customerId, "
                + "customer.customerName, "
                + "customer.customerPhone, "
                + "customer.customerEmail, "
                + "customer.notes, "
                + "customer.active, "
                + "pet.petId, "
                + "pet.petName, "
                + "pet.petType, "
                + "pet.petDescription "
                //                + "barber.barberId "

                + "FROM customer, customerpets, pet "
                + "WHERE customer.customerId = customerpets.customerId "
                + "AND customerpets.petId = pet.petId");

        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();

        while (result.next()) {
            int customerId = result.getInt("customer.customerId");
            String customerNameG = result.getString("customerName");
            String phone = result.getString("customerPhone");
            String email = result.getString("customerEmail");

            String active = result.getString("active");
            String notes = result.getString("notes");

            Customer customer = new Customer(
                    result.getInt("customer.customerId"),
                    result.getString("customerName"),
                    result.getString("customerPhone"),
                    result.getString("customerEmail"),
                    result.getString("active"),
                    result.getString("notes")
            );

//            Customer customerResult = new Customer(customerId, customerNameG, phone, email, active, notes);
            Pet petResult = new Pet(result.getInt("pet.petId"), result.getString("pet.petName"), result.getString("pet.petType"), result.getString("pet.petDescription"));
//            int barberId = result.getInt("barberId");
//	    String zipcode = result.getString("pet.postalCode")

//
            allCustomerPets.add(customer, petResult);
        }

//	DBConnection.closeConnection();
        return allCustomerPets;
    }
}
