/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.DAO;

import jCalendar.model.City;
import jCalendar.model.Country;
import jCalendar.model.Customer;
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
		("SELECT customer.customerId, customer.customerName, address.address, address.address2, address.postalCode, city.cityId, city.city, country.country, address.phone "
		+ "FROM customer, address, city, country "
		+ "WHERE customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId "
		+ "ORDER BY customer.customerId");
	
	Query.makeQuery(sqlStatement);
	ResultSet result = Query.getResult(); 

	while (result.next()) {
	    int customerId = result.getInt("customerId");
	    String customerNameG = result.getString("customerName");
	    String address = result.getString("address.address");
	    String address2 = result.getString("address.address2");
	    City city = new City(result.getInt("city.cityId"), result.getString("city.city"));
	    String countryG = result.getString("country.country");
	    String zipcode = result.getString("address.postalCode");
	    String phone = result.getString("address.phone");

	    Customer customerResult = new Customer(customerId, customerNameG, address, address2, city, countryG, zipcode, phone);
  
	    allCustomers.add(customerResult);
	}
	
	DBConnection.closeConnection();
	return allCustomers;
    }

    public static ObservableList<Country> getCountries() throws SQLException, Exception {
	
	DBConnection.init();
	ObservableList<Country> countries = FXCollections.observableArrayList();
	String sql = "SELECT country FROM country;";
	try {

	    Query.makeQuery(sql);
	    ResultSet rs = Query.getResult();
	    while (rs.next()) {
		countries.add(new Country(rs.getInt("country.countryId"), rs.getString("country.country")));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	DBConnection.closeConnection();
	return countries;
	
    }

        public ObservableList<Customer> populateCustomerList() {

	int tCustomerId;
	String tCustomerName;

	ObservableList<Customer> customerList = FXCollections.observableArrayList();
	try (
		PreparedStatement statement = DBConnection.getConn().prepareStatement(
			"SELECT customer.customerId, customer.customerName "
			+ "FROM customer, address, city, country "
			+ "WHERE customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId "
			+ "ORDER BY customer.customerId");
		
		ResultSet rs = statement.executeQuery();) {

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

