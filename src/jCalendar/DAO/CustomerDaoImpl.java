/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.DAO;

import com.sun.deploy.uitoolkit.impl.fx.ui.FXAboutDialog;
import jCalendar.model.City;
import jCalendar.model.Customer;
import static jCalendar.utilities.TimeFiles.stringToCalendar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author jlau2
 */
public class CustomerDaoImpl {

//    private static Customer createCustomer(ResultSet rs) {
//	Customer p = new Customer();
//	try {
//	    p.setCustomerId(rs.getInt("customerId"));
//	    p.setCustomerName(rs.getString("customerName"));
//	    p.setAddress(rs.getString("address"));
//	    p.setAddress2(rs.getString("address2"));
//	    p.setCity(new City(rs.getInt("city.cityId"), rs.getString("city.city")));
//	    p.setCountry(rs.getString("country"));
//	    p.setPhone(rs.getString("phone"));
//	    p.setZipCode(rs.getString("postalCode"));
//
//	} catch (SQLException ex ) {
//	}
//	return p;
//   }

//    public static ObservableList<Customer> getCustomers() throws SQLException, Exception {
//	DBConnection.init();
//	String sql = "Select * from customer order by customerName";
//	Query.makeQuery(sql);
//	
//	ObservableList<Customer> customersList = FXCollections.observableArrayList();
//
//	ResultSet rs = Query.getResult();
//	while (rs.next()) {
//	    Customer p = createCustomer(rs);
//	    customersList.add(p);
//	    return customersList;
//	}
//	DBConnection.closeConnection();
//	return null;
//	
//    }

    // Load customer data into table, including create, update and delete methods
//    public static Customer getCustomer(String customerName) throws SQLException, Exception {
//	
//	DBConnection.init();
//	String SqlStatement ="select * from customer where customerName = '" + customerName + "'";
//	Query.makeQuery(SqlStatement);
//	
//	Customer customerResult;
//	ResultSet result=Query.getResult();
//	while (result.next()) {
//	    int customerId = result.getInt("customerId");
//	    String customerNameG = result.getString("customerName");
//	    String address = result.getString("address.address");
//	    String address2 = result.getString("address.address2");
//	    //int address2 = result.getString("city");
//	    String country = result.getString("country.country");
//	    String zipcode = result.getString("address.postalCode");
//	    String phone = result.getString("address.phone");
//	  
//	    customerResult = new Customer(customerId, customerNameG, address, address2, country, zipcode, phone);
//	    return customerResult;   
//	    
//	}
//	DBConnection.closeConnection();
//	return null;
//    }

    public static ObservableList<Customer> getallCustomers() throws SQLException, Exception {
	
	ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
	DBConnection.init();
	String sqlStatement = 
		("SELECT customer.customerId, customer.customerName, address.address, address.address2, address.postalCode, city.cityId, city.city, country.country, address.phone "
		+ "FROM customer, address, city, country "
		+ "WHERE customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId ");
		//+ "ORDER BY customer.customerName");
	
	Query.makeQuery(sqlStatement);
	ResultSet result = Query.getResult(); 

//	    public Customer(int customerId, String customerName, String address, String address2, String city, String country, String zipCode, String phone) {

	while (result.next()) {
	    int customerId = result.getInt("customerId");
	    String customerNameG = result.getString("customerName");
	    String address = result.getString("address.address");
	    String address2 = result.getString("address.address2");
	    //int cityId = result.getInt("city.cityId");
	    String cityG = result.getString("city.city");
	    String countryG = result.getString("country.country");
//City city = new City(result.getInt("city.cityId"), result.getString("city.city"));
	    String zipcode = result.getString("address.postalCode");
	    String phone = result.getString("address.phone");

	    Customer customerResult = new Customer(customerId, customerNameG, address, address2, cityG, countryG, zipcode, phone);
  
	    allCustomers.add(customerResult);
	}
	DBConnection.closeConnection();
	return allCustomers;
    }
//    
//    public static ObservableList<City> getallCities() throws SQLException, Exception {
//	ObservableList<City> cities = FXCollections.observableArrayList();
//	DBConnection.init();
//	String sql = ("SELECT cityId, city FROM city");
//	
//	Query.makeQuery(sql);
//	ResultSet result = Query.getResult();
//	while (result.next()) {
//	    cities.add(new City(result.getInt("city.cityId"), result.getString("city.city")));
//	    
//	    
//	}
//	DBConnection.closeConnection();
//	return cities;	
	
//    }
    
    
    

}
