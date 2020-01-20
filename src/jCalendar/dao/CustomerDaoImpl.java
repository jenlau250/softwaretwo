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

    public static ObservableList<Customer> addCustomers2() {

        //adding customer notes and active to this might it not work, so make sure it's exactly
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(
                    "SELECT customerId, customerName, customerPhone, customerEmail, pet.petId, petName, petType, petDescription "
                    + "FROM customer, pet "
                    + "WHERE customer.petId = pet.petId");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("customerId");
                String name = rs.getString("customerName");
                String phone = rs.getString("customerPhone");
                String email = rs.getString("customerEmail");
//                String notes = rs.getString("notes");
//                String active = rs.getString("active");
                Pet pet = new Pet(rs.getInt("pet.petId"), rs.getString("pet.petName"), rs.getString("pet.petType"), rs.getString("pet.petDescription"));
//                int petId = rs.getInt("petId");
//                String petName = rs.getString("petName");
//                String petType = rs.getString("petType");
//                String petDescription = rs.getString("petDescription");

                customerList.add(new Customer(id, name, phone, email, pet));
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

//    public static ObservableList<Customer> populateCustomerCombo() {
//
//        ObservableList<Customer> customerList = FXCollections.observableArrayList();
//
//        try {
//            PreparedStatement ps = DBConnection.getConn().prepareStatement(
//                    "SELECT customerId, customerName, customerPhone, customerEmail, pet.petId, petName, petType, petDescription "
//                    + "FROM customer, pet "
//                    + "WHERE customer.petId = pet.petId");
//
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                int id = rs.getInt("customerId");
//                String name = rs.getString("customerName");
//                String phone = rs.getString("customerPhone");
//                String email = rs.getString("customerEmail");
////                Pet pet = new Pet(rs.getInt("pet.petId"), rs.getString("pet.petName"), rs.getString("pet.petType"), rs.getString("pet.petDescription"));
//                int petId = rs.getInt("petId");
////                String petName = rs.getString("petName");
////                String petType = rs.getString("petType");
////                String petDescription = rs.getString("petDescription");
//
//                customerList.add(new Customer(id, name, phone, email, petId));
////                customerList.add(new Customer(id, name, phone, email, petId, petName, petType, petDescription));
//
//            }
//
//        } catch (SQLException sqe) {
//            System.out.println("Check SQL Exception with add customers2");
//            sqe.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("Check Exception");
//        }
//        return customerList;
//
//    }

//    public void listCustomers() {
//
//        try {
//            PreparedStatement ps = DBConnection.getConn().prepareStatement(
//                    "SELECT customerId, customerName, customerPhone, customerEmail, pet.petId, petName, petType, petDescription "
//                    + "FROM customer, pet "
//                    + "WHERE customer.petId = pet.petId");
//
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                int id = rs.getInt("customerId");
//                String name = rs.getString("customerName");
//                String phone = rs.getString("customerPhone");
//                String email = rs.getString("customerEmail");
////                Pet pet = new Pet(rs.getInt("pet.petId"), rs.getString("pet.petName"), rs.getString("pet.petType"), rs.getString("pet.petDescription"));
//                int petId = rs.getInt("petId");
////                String petName = rs.getString("petName");
////                String petType = rs.getString("petType");
////                String petDescription = rs.getString("petDescription");
//
//                customerList.add(new Customer(id, name, phone, email, petId));
////                customerList.add(new Customer(id, name, phone, email, petId, petName, petType, petDescription));
//
//            }
//
//        } catch (SQLException sqe) {
//            System.out.println("Check SQL Exception with add customers2");
//            sqe.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("Check Exception");
//        }
//        return customerList;
//
//    }
//
    public static ObservableList<Customer> getallCustomers() throws SQLException, Exception {
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

                + "FROM customer, pet "
                + "WHERE customer.petId = pet.petId ");

        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();

        while (result.next()) {
            int customerId = result.getInt("customerId");
            String customerNameG = result.getString("customerName");
            String phone = result.getString("customerPhone");
            String email = result.getString("customerEmail");
            String notes = result.getString("notes");
            String active = result.getString("active");
            Pet pet = new Pet(result.getInt("pet.petId"), result.getString("pet.petName"), result.getString("pet.petType"), result.getString("pet.petDescription"));
//            int barberId = result.getInt("barberId");
//	    String zipcode = result.getString("pet.postalCode")

            Customer customerResult = new Customer(customerId, customerNameG, phone, email, notes, active, pet);

//	String sqlStatement = 
//		("SELECT customer.customerId, "
//                + "customer.customerName, "
//                + "customer.customerPhone, "
//                + "customer.customerEmail, "
//                + "pet.postalCode, "
//                + "city.cityId, "
//                + "city.city, "
//                + "calendar.calendar, "
//                + "customer.notes "
//		+ "FROM customer, "
//                + "pet, "
//                + "city, "
//                + "calendar "
//		+ "WHERE customer.petId = customer.customerPhoneId AND pet.cityId = city.cityId AND city.countryId = calendar.countryId "
//		+ "ORDER BY customer.customerId");
//	
//	Query.makeQuery(sqlStatement);
//	ResultSet result = Query.getResult(); 
//
//	while (result.next()) {
//	    int customerId = result.getInt("customerId");
//	    String customerNameG = result.getString("customerName");
//	    String pet = result.getString("customer.customerPhone");
//	    String address2 = result.getString("customer.customerEmail");
//	    Barber city = new Barber(result.getInt("city.cityId"), result.getString("city.city"));
//	    String countryG = result.getString("calendar.calendar");
//	    String zipcode = result.getString("pet.postalCode");
//	    String phone = result.getString("customer.notes");
//
//	    Customer customerResult = new Customer(customerId, customerNameG, pet, address2, city, countryG, zipcode, phone);
//  
            allCustomers.add(customerResult);
        }

//	DBConnection.closeConnection();
        return allCustomers;
    }
//    public static ObservableList<Calendar> getCountries() throws SQLException, Exception {
//	
//	DBConnection.init();
//	ObservableList<Calendar> countries = FXCollections.observableArrayList();
//	String sql = "SELECT calendar FROM calendar;";
//	try {
//
//	    Query.makeQuery(sql);
//	    ResultSet rs = Query.getResult();
//	    while (rs.next()) {
//		countries.add(new Calendar(rs.getInt("calendar.countryId"), rs.getString("calendar.calendar")));
//	    }
//	} catch (SQLException e) {
//	    e.printStackTrace();
//	}
//	DBConnection.closeConnection();
//	return countries;
//	
//    }

    public ObservableList<Customer> populateCustomerList() {

        int tCustomerId;
        String tCustomerName;

        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try (
                 PreparedStatement statement = DBConnection.getConn().prepareStatement(
                        "SELECT customer.customerId, customer.customerName "
                        + "FROM customer, pet "
                        + "WHERE customer.petId = pet.petId "
                        + "ORDER BY customer.customerId");  ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                tCustomerId = rs.getInt("customer.customerId");
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

//    public static ObservableList<Customer> getListofCustomers() throws SQLException {
//
//        //initialize new list
//        ObservableList<Customer> listOfCustomers = FXCollections.observableArrayList();
//
//        //SQL query for all Appointment data from Appointments table
//        String queryListOfCustomers = "SELECT customerId, customerName FROM customer";
//        PreparedStatement ps = DBConnection.getConn().prepareStatement(queryListOfCustomers);
//        //store results of query
//        ResultSet rs = ps.executeQuery();
//
//        try {
//
//            while (rs.next()) {
//
//                int custId = rs.getInt("customerId");
//                String customerName = rs.getString("customerName");
//                listOfCustomers.add(new Customer(custId, customerName));
//
//            }
//        } catch (SQLException ex) {
//
//            System.out.println("error with getting getListofCustomers() in DBConnection");
//        }
//        return listOfCustomers;
//    }

    public static ObservableList<Pet> populatePets() {

        int petId;
        String petName;

        ObservableList<Pet> petList = FXCollections.observableArrayList();
        try (
                 PreparedStatement ps = DBConnection.getConn().prepareStatement(
                        "SELECT pet.petId, pet.petName "
                        + "FROM pet, customer "
                        + "WHERE pet.petId = customer.petId"
                );  ResultSet rs = ps.executeQuery();) {

                    while (rs.next()) {
                        petId = rs.getInt("pet.petId");
                        petName = rs.getString("pet.petName");
                        petList.add(new Pet(petId, petName));
                    }
                } catch (SQLException sqe) {
                    System.out.println("Check SQL Exception");
                    sqe.printStackTrace();
                } catch (Exception e) {
                    System.out.println("Check Exception");
                }
                return petList;

    }

}
