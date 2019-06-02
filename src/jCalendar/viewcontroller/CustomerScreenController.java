/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import jCalendar.DAO.CustomerDaoImpl;
import jCalendar.DAO.DBConnection;
import jCalendar.DAO.Query;
import jCalendar.jCalendar;
import jCalendar.model.City;
import jCalendar.model.Customer;
import jCalendar.model.User;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class CustomerScreenController {
    
    
 
    @FXML    private Button btnCustomerAdd;
    @FXML    private Button btnCustomerUpdate;
    @FXML    private Button btnCustomerDelete;
    @FXML    private Button btnCustomerSave;
    @FXML    private Button btnCustomerCancelSave;
    @FXML    private TableView<Customer> CustomerTable;
    @FXML    private TableColumn<Customer, String> txtCustomerID1;
    @FXML    private TableColumn<Customer, String> txtCustomerName1;
    @FXML    private TableColumn<Customer, String> txtCustomerCountry1;
    @FXML    private TextField txtCustomerId;
    @FXML    private TextField txtCustomerName;
    @FXML    private TextField txtCustomerCity;
    @FXML    private TextField txtCustomerAddress;
    @FXML    private TextField txtCustomerAddress2;
    @FXML    private TextField txtCustomerCountry;
    @FXML    private TextField txtCustomerZipCode;
    @FXML    private TextField txtCustomerPhone;
    @FXML    private Label customerLabel;
    @FXML    private ComboBox<String> cityCombo;
    @FXML    private ComboBox<String> countryCombo;
//
//    @FXML    private TableColumn<Customer, String> txtCustomerPhone;
//    @FXML    private TableColumn<Customer, String> txtCustomerZipCode;
//    @FXML    private TableColumn<Customer, String> txtCustomerCity;
//    @FXML    private TableColumn<Customer, String> txtCustomerAddress2;
//    @FXML    private TableColumn<Customer, String> txtCustomerCountry;
    
    
    @FXML   ObservableList<Customer> Customers = FXCollections.observableArrayList();
    @FXML   ObservableList<City> Cities = FXCollections.observableArrayList();
    
    private jCalendar mainApp;
    private User currentUser;
    private Stage dialogStage;
    boolean editMode;
    //private User currentUser;

     @FXML
    private void comboCountryAction(ActionEvent event) {
	
	//initializeCity();

	String country = countryCombo.getSelectionModel().getSelectedItem();

	String sql = "SELECT city.city "
		+ "FROM city, country "
		+ "WHERE city.countryId = country.countryId "
		+ "AND country.country = \"" + country + "\"";

	Query.makeQuery(sql);
	ResultSet rs = Query.getResult();
	cityCombo.getItems().clear();

	try {
	    while (rs.next()) {

		cityCombo.getItems().add(rs.getString(1));

	    }
	} catch (SQLException ex) {
	    Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    private void initializeCity() {
//	String country = countryCombo.getValue();
//
//	String sql = "SELECT city.city "
//		+ "FROM city, country "
//		+ "WHERE city.countryId = country.countryId "
//		+ "AND country.country = \"" + country + "\"";
//
//	Query.makeQuery(sql);
//	ResultSet rs = Query.getResult();
//	cityCombo.getItems().clear();
//
//	try {
//	    while (rs.next()) {
//
//		cityCombo.getItems().add(rs.getString(1));
//
//	    }
//	} catch (SQLException ex) {
//	    Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
//	}
    }
    
    @FXML
    private void initializeCountry() {
	
	Query.makeQuery("SELECT country FROM country");
	ResultSet rs = Query.getResult();

	//ResultSet rs = accessDB("SELECT country FROM country");
	try {

	    while (rs.next()) {

		countryCombo.getItems().add(rs.getString(1));

	    }
	} catch (SQLException ex) {
	    Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
	}

    }
    @FXML
    private void showCustomerDetails(Customer selectedCustomer) {

//	String custID = Integer.toString(selectedCustomer.getCustomerId());
//	System.out.println(custID);
//	txtCustomerId.setText(custID);
	
// **CAUSES ERROR FOR SOME REASON TO SET CUSTOMER ID IN TEXT FIELD
//txtCustomerId.setText(String.valueOf(selectedCustomer.getCustomerId()));
	txtCustomerName.setText(selectedCustomer.getCustomerName());
	txtCustomerAddress.setText(selectedCustomer.getAddress());
	txtCustomerAddress2.setText(selectedCustomer.getAddress2());	
	countryCombo.setValue(selectedCustomer.getCountry());
	//initializeCity();
	//txtCustomerCountry.setText(selectedCustomer.getCountry());
	txtCustomerZipCode.setText(selectedCustomer.getZipCode());
	txtCustomerPhone.setText(selectedCustomer.getPhone());
	//System.out.println(selectedCustomer.getPhone());
	

	cityCombo.setValue(selectedCustomer.getCity());
    }
    
    
    @FXML
    void handleAddCustomer(ActionEvent event) {
	customerLabel.setText("Add Customer Details");
	//clear customer details
	clearFields();
	btnCustomerDelete.setDisable(true);
	btnCustomerUpdate.setDisable(true);
	editMode = false;
	enableEdits();

//	    editClicked = false;
//        enableCustomerFields();
//        saveCancelButtonBar.setDisable(false);
//        customerTable.setDisable(true);
//        clearCustomerDetails();
//        customerIdField.setText("Auto-Generated");
//        newEditDeleteButtonBar.setDisable(true);  
    }

    @FXML
    void handleCancelCustomer(ActionEvent event) {;

    }
    
    @FXML
    void handleUpdateCustomer(ActionEvent event) {
	
	customerLabel.setText("Update Customer Details");
	Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();

	if (selectedCustomer != null) {
	    editMode = true;
	    
//	    btnCustomerDelete.setDisable(true);
//	    btnCustomerUpdate.setDisable(true);
	    enableEdits();
	    initializeCountry();
	    
	} else {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle("No Selection");
	    alert.setHeaderText("No Customer selected");
	    alert.setContentText("Please select a Customer in the Table");
	    alert.showAndWait();
	}

    }
    
    public CustomerScreenController() {

    }

    /**
     * Initializes the controller class.
     *
     * @param mainApp
     * @param currentUser
     */
    public void setCustomerScreen(jCalendar mainApp, User currentUser) {

	this.mainApp = mainApp;
	this.currentUser = currentUser;
	
	customerLabel.setText("Customer Details");
	// Bind table propeties 
	txtCustomerID1.setCellValueFactory(new PropertyValueFactory<>("customerId"));
	txtCustomerName1.setCellValueFactory(new PropertyValueFactory<>("customerName"));
	txtCustomerCountry1.setCellValueFactory(new PropertyValueFactory<>("country"));
	//initializeCountry();
//	initializeCity();

//	cityCombo.setConverter(new StringConverter<City>() {
//
//	    @Override
//	    public String toString(City object) {
//		return object.getCity();
//	    }
//
//	    @Override
//	    public City fromString(String string) {
//		return cityCombo.getItems().stream().filter(ap -> ap.getCity().equals(string)).findFirst().orElse(null);
//	    }
//	});
	try {

	    Customers.addAll(CustomerDaoImpl.getallCustomers());
	    // Cities.addAll(CustomerDaoImpl.getallCities());
	} catch (Exception ex) {
	    Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);

	}

	CustomerTable.getItems().setAll(Customers);

	CustomerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	    if (newSelection != null) {
		System.out.println(newSelection);
		showCustomerDetails(newSelection);
	    }
	});


	disableEdits();
	
    }



//    
//    @FXML
//    private void initializeCity() {
//
//	String country = countryCombo.getValue();
//
//	String sql = "SELECT city.city "
//		+ "FROM city, country "
//		+ "WHERE city.countryId = country.countryId "
//		+ "AND country.country = \"" + country + "\"";
//
//	Query.makeQuery(sql);
//	ResultSet rs = Query.getResult();
//	cityCombo.getItems().clear();
//
//	try {
//	    while (rs.next()) {
//
//		cityCombo.getItems().add(rs.getString(1));
//
//	    }
//	} catch (SQLException ex) {
//	    Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
//	}
//    }

   
    private void enableEdits() {

	txtCustomerName.setEditable(true);
	txtCustomerAddress.setEditable(true);
	txtCustomerAddress2.setEditable(true);
	txtCustomerCountry.setEditable(true);
	txtCustomerZipCode.setEditable(true);
	txtCustomerPhone.setEditable(true);
    }

    private void disableEdits() {

	txtCustomerName.setEditable(false);
	txtCustomerAddress.setEditable(false);
	txtCustomerAddress2.setEditable(false);
	txtCustomerCountry.setEditable(false);
	txtCustomerZipCode.setEditable(false);
	txtCustomerPhone.setEditable(false);
    }

    private void clearFields() {

	txtCustomerName.clear();
	txtCustomerAddress.clear();
	txtCustomerAddress2.clear();
	txtCustomerCountry.clear();
	txtCustomerZipCode.clear();
	txtCustomerPhone.clear();
    }


    @FXML
    void handleDeleteCustomer(ActionEvent event) {

	Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();

	if (selectedCustomer != null) {
	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle("Confirm Deletion");
	    alert.setHeaderText("Are you sure you want to delete " + selectedCustomer.getCustomerName() + "?");
	    alert.showAndWait()
		    .filter(response -> response == ButtonType.OK)
		    .ifPresent(response -> {
			deleteCustomer(selectedCustomer);
			mainApp.showCustomerScreen(currentUser);
		    }
		    );
	} else {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle("No Selection");
	    alert.setHeaderText("No Customer selected for Deletion");
	    alert.setContentText("Please select a Customer in the Table to delete");
	    alert.showAndWait();
	}
    }

//    @FXML
//    void handleSaveCustomer(ActionEvent event) {
//	//if (validateCustomer()) {
////	    saveCancelButtonBar.setDisable(true);
////	    customerTable.setDisable(false);
//	    if (editMode == true) {
//		//edits Customer record
//		updateCustomer();
//	    } else if (editMode == false) {
//		//inserts new customer record
//		saveCustomer();
//	    }
//	    mainApp.showCustomerScreen(currentUser);
//	}
    /**
     * Inserts new customer record
     */
    private void saveNewCustomer() {

	try {

	    PreparedStatement ps = DBConnection.getConn().prepareStatement("INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) "
		    + "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)", Statement.RETURN_GENERATED_KEYS);

	    ps.setString(1, txtCustomerAddress.getText());
	    ps.setString(2, txtCustomerAddress2.getText());
	    ps.setInt(3, cityCombo.getValue().getCityId());
	    ps.setString(4, txtCustomerZipCode.getText());
	    ps.setString(5, txtCustomerPhone.getText());
	    ps.setString(6, currentUser.getUserName());
	    ps.setString(7, currentUser.getUserName());
	    boolean res = ps.execute();
	    int newAddressId = -1;
	    ResultSet rs = ps.getGeneratedKeys();

	    if (rs.next()) {
		newAddressId = rs.getInt(1);
		//System.out.println("Generated AddressId: "+ newAddressId);
	    }

	    PreparedStatement psc = DBConnection.getConn().prepareStatement("INSERT INTO customer "
		    + "(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)"
		    + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)");

	    psc.setString(1, nameField.getText());
	    psc.setInt(2, newAddressId);
	    psc.setInt(3, 1);
	    //psc.setString(4, LocalDateTime.now().toString());
	    psc.setString(4, currentUser.getUsername());
	    //psc.setString(6, LocalDateTime.now().toString());
	    psc.setString(5, currentUser.getUsername());
	    int result = psc.executeUpdate();

	} catch (SQLException ex) {
	    ex.printStackTrace();
	}
    }
    

}
