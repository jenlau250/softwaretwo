/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.dao;

import jCalendar.model.Customer;
import jCalendar.model.Pet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author jlau2
 */
public class CustomerDaoImpl {

    public ObservableList<Customer> loadCustomerData() {

        //adding customer notes and active to this might it not work, so make sure it's exactly
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(
                    //                    "SELECT * FROM customer");
                    "SELECT * FROM customer");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("customerId");
                String name = rs.getString("customerName");
                String phone = rs.getString("customerPhone");
                String email = rs.getString("customerEmail");
                String active = rs.getString("active");
                String notes = rs.getString("notes");

                customerList.add(new Customer(id, name, phone, email, active, notes));
//                customerList.add(new Customer(id, name, phone, email, petId, petName, petType, petDescription));

            }

        } catch (SQLException sqe) {
            System.out.println("Check SQL Exception with add customers2");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Check Exception");
        }
        return customerList;

    }

    public ObservableList<Customer> getallCustomers() throws SQLException, Exception {
        //can delete DB init and close
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

//	DBConnection.init();
        String sqlStatement
                = ("SELECT customer.customerId, "
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

                + "FROM pet, customer "
                + "WHERE pet.customerId = customer.customerId ");

        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();

        while (result.next()) {
            String customerId = result.getString("customerId");
            String customerNameG = result.getString("customerName");
            String phone = result.getString("customerPhone");
            String email = result.getString("customerEmail");

            String active = result.getString("active");
            String notes = result.getString("notes");
            Pet pet = new Pet(result.getString("pet.petId"), result.getString("pet.petName"), result.getString("pet.petType"), result.getString("pet.petDescription"));
//            int barberId = result.getInt("barberId");
//	    String zipcode = result.getString("pet.postalCode")

            Customer customerResult = new Customer(customerId, customerNameG, phone, email, active, notes, pet);

//
            allCustomers.add(customerResult);
        }

//	DBConnection.closeConnection();
        return allCustomers;
    }

    public ObservableList<Customer> populateCustomerList() {

        String tCustomerId;
        String tCustomerName;

        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try (
                PreparedStatement statement = DBConnection.getConn().prepareStatement(
                        "SELECT customer.customerId, customer.customerName "
                        + "FROM customer, pet "
                        + "WHERE customer.petId = pet.petId "
                        + "ORDER BY customer.customerId"); ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                tCustomerId = rs.getString("customer.customerId");
                tCustomerName = rs.getString("customer.customerName");
                customerList.add(new Customer(tCustomerId, tCustomerName));
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return customerList;

    }

}
