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
    
    

    public static ObservableList<Customer> getallCustomers() throws SQLException, Exception {
	
	ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
	
	DBConnection.init();
        String sqlStatement = 
		("SELECT customer.customerId, "
                + "customer.customerName, "
                + "customer.phone, "
                + "customer.email, "
                + "customer.notes "
                + "customer.active "
                + "pet.petId, "
                + "pet.petName, "
                
		+ "FROM customer, "
                + "customer, "
                + "pet, "
		+ "WHERE customer.petId = pet.petId"
		+ "ORDER BY customer.customerId");
	
	Query.makeQuery(sqlStatement);
	ResultSet result = Query.getResult(); 

	while (result.next()) {
	    int customerId = result.getInt("customerId");
	    String customerNameG = result.getString("customerName");
	    String phone = result.getString("customer.phone");
	    String email = result.getString("customer.email");
	    String notes = result.getString("customer.notes");
	    String active = result.getString("customer.active");
	    Pet pet = new Pet(result.getInt("pet.petId"), result.getString("pet.petName"));
//	    String zipcode = result.getString("pet.postalCode")

	    Customer customerResult = new Customer(customerId, customerNameG, phone, email, notes, active, pet);
  
//	String sqlStatement = 
//		("SELECT customer.customerId, "
//                + "customer.customerName, "
//                + "customer.phone, "
//                + "customer.email, "
//                + "pet.postalCode, "
//                + "city.cityId, "
//                + "city.city, "
//                + "calendar.calendar, "
//                + "customer.notes "
//		+ "FROM customer, "
//                + "pet, "
//                + "city, "
//                + "calendar "
//		+ "WHERE customer.petId = customer.phoneId AND pet.cityId = city.cityId AND city.countryId = calendar.countryId "
//		+ "ORDER BY customer.customerId");
//	
//	Query.makeQuery(sqlStatement);
//	ResultSet result = Query.getResult(); 
//
//	while (result.next()) {
//	    int customerId = result.getInt("customerId");
//	    String customerNameG = result.getString("customerName");
//	    String pet = result.getString("customer.phone");
//	    String address2 = result.getString("customer.email");
//	    Barber city = new Barber(result.getInt("city.cityId"), result.getString("city.city"));
//	    String countryG = result.getString("calendar.calendar");
//	    String zipcode = result.getString("pet.postalCode");
//	    String phone = result.getString("customer.notes");
//
//	    Customer customerResult = new Customer(customerId, customerNameG, pet, address2, city, countryG, zipcode, phone);
//  
	    allCustomers.add(customerResult);
	}
	
	DBConnection.closeConnection();
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
			+ "ORDER BY customer.customerId");
		
		ResultSet rs = statement.executeQuery()) {

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
        
        

    public static ObservableList<Customer> getListofCustomers() throws SQLException {

        //initialize new list
        
        ObservableList<Customer> listOfCustomers = FXCollections.observableArrayList();

        //SQL query for all Appointment data from Appointments table
        String queryListOfCustomers = "SELECT customerId, customerName FROM customer";
        PreparedStatement ps = DBConnection.getConn().prepareStatement(queryListOfCustomers);
        //store results of query
        ResultSet rs = ps.executeQuery();

        try {

            while (rs.next()) {

                int custId = rs.getInt("customerId");
                String customerName = rs.getString("customerName");
                listOfCustomers.add(new Customer(custId, customerName));

            }
        } catch (SQLException ex) {

            System.out.println("error with getting getListofCustomers() in DBConnection");
        }
        return listOfCustomers;
    }

    
}

