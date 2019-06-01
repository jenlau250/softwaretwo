/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.DAO;

import com.sun.deploy.uitoolkit.impl.fx.ui.FXAboutDialog;
import jCalendar.model.Customer;
import static jCalendar.utilities.TimeFiles.stringToCalendar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author jlau2
 */
public class CustomerDaoImpl {
    
    // Load customer data into table, including create, update and delete methods
    static boolean act;
    public static Customer getCustomer(String customerName) throws SQLException, Exception {
	
	DBConnection.init();
	String SqlStatement ="select * from customer where customerName = '" + customerName + "'";
	Query.makeQuery(SqlStatement);
	
	Customer customerResult;
	ResultSet result=Query.getResult();
	while (result.next()) {
	    int customerId = result.getInt("customerId");
	    String customerNameG = result.getString("customerName");
	    int addressId = result.getInt("addressId");
	    int active = result.getInt("active");
	    if (active == 1) {
		act = true;
	    }
	    String createDate = result.getString("createDate");
	    String createdBy = result.getString("createBy");
	    String lastUpdate = result.getString("lastUpdate");
	    String lastUpdateby = result.getString("lastUpdatedBy");
	    Calendar createDateCalendar = stringToCalendar(createDate);
	    Calendar lastUpdateCalendar = stringToCalendar(lastUpdate);
	    customerResult = new Customer(customerId, customerName, lastUpdate, act, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
	    return customerResult;   
	    
	}
	DBConnection.closeConnection();
	return null;
    }
    
    public static ObservableList<Customer> getallCustomers() throws SQLException, Exception {
	ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
	DBConnection.init();
	String sqlStatement = "select * from customer";
	Query.makeQuery(sqlStatement);
	ResultSet result = Query.getResult();
	while (result.next()) {
	    int customerId = result.getInt("customerId");
	    String customerNameG = result.getString("customerName");
	    int addressId = result.getInt("addressId");
	    int active = result.getInt("active");
	    if (active == 1) {
		act = true;
	    }
	    String createDate = result.getString("createDate");
	    String createdBy = result.getString("createBy");
	    String lastUpdate = result.getString("lastUpdate");
	    String lastUpdateby = result.getString("lastUpdatedBy");
	    Calendar createDateCalendar = stringToCalendar(createDate);
	    Calendar lastUpdateCalendar = stringToCalendar(lastUpdate);
	    Customer customerResult = new Customer(customerId, customerNameG, lastUpdate, act, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
	    allCustomers.add(customerResult);
	}
	DBConnection.closeConnection();
	return allCustomers;
    }

}
