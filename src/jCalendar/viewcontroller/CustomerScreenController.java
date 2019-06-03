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
import jCalendar.model.Country;
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
    @FXML    private TextField txtCustomerID;
    @FXML    private TextField txtCustomerName;
    @FXML    private TextField txtCustomerCity;
    @FXML    private TextField txtCustomerAddress;
    @FXML    private TextField txtCustomerAddress2;
    @FXML    private TextField txtCustomerCountry;
    @FXML    private TextField txtCustomerZipCode;
    @FXML    private TextField txtCustomerPhone;
    @FXML    private Label customerLabel;
    @FXML    private ComboBox<City> cityCombo;
    @FXML    private ComboBox<Country> countryCombo;
//
//    @FXML    private TableColumn<Customer, String> txtCustomerPhone;
//    @FXML    private TableColumn<Customer, String> txtCustomerZipCode;
//    @FXML    private TableColumn<Customer, String> txtCustomerCity;
//    @FXML    private TableColumn<Customer, String> txtCustomerAddress2;
//    @FXML    private TableColumn<Customer, String> txtCustomerCountry;
    
    
    @FXML   ObservableList<Customer> Customers = FXCollections.observableArrayList();
    @FXML   ObservableList<City> Cities = FXCollections.observableArrayList();
    ObservableList<City> selectedCities = FXCollections.observableArrayList();
    ObservableList<Country> Countries = FXCollections.observableArrayList();
   
    private jCalendar mainApp;
    private User currentUser;
    private Stage dialogStage;
    boolean editMode;
    //private User currentUser;

     @FXML
    private void comboCountryAction(ActionEvent event) {
	
	populateCityList();

    }

    private void populateCountriesList() {


	String sql = "SELECT country, countryId FROM country";

	Query.makeQuery(sql);
	ResultSet rs = Query.getResult();
	countryCombo.getItems().clear();

	try {
	    while (rs.next()) {
		Countries.add(new Country(rs.getInt("country.countryId"), rs.getString("country.country")));
//		cityCombo.getItems().add(rs.getString(1));
//		cityCombo.getItems().add(selectedCities.get(0));
		//cityCombo.getItems().setAll(selectedCities);
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
	}
	countryCombo.setItems(Countries);

    }
	
    private void populateCityList() {
	
//	String sqlstmt = "SELECT country, countryId FROM country;";
//	try {
//
//	    Query.makeQuery(sqlstmt);
//	    ResultSet rs = Query.getResult();
//	    while (rs.next()) {
//		Countries.add(new Country(rs.getInt("country.countryId"), rs.getString("country.country")));
//
//	    }
//
//	} catch (SQLException ex) {
//	    Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
//	}
//	
//	countryCombo.setItems(Countries);
	
	String country = countryCombo.getValue().getCountry();
	
	String sql = "SELECT city.city, city.cityId "
		+ "FROM city, country "
		+ "WHERE city.countryId = country.countryId "
		+ "AND country.country = \"" + country + "\"";

	Query.makeQuery(sql);
	ResultSet result = Query.getResult();
	cityCombo.getItems().clear();

	try {
	    while (result.next()) {
		selectedCities.add(new City(result.getInt("city.CityId"), result.getString("city.city")));
//		cityCombo.getItems().add(rs.getString(1));
//		cityCombo.getItems().add(selectedCities.get(0));
		//cityCombo.getItems().setAll(selectedCities);
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
	}
	cityCombo.setItems(selectedCities);
	
    }
    
//    @FXML
//    private void initializeCountry() {
//	
//
//	countryCombo.setItems(CustomerDaoImpl.getCountries());
//	//Customers.getClass().getDeclaredFields().
//	Query.makeQuery("SELECT country, countryId FROM country");
//	ResultSet rs = Query.getResult();
//
//	//ResultSet rs = accessDB("SELECT country FROM country");
//	try {
//
//	    while (rs.next()) {
//		selectedCountries.add(new Country(rs.getInt("country.countryId"), rs.getString("country.country")));
//		//Country country = new Country(result.getInt("country.countryId"), result.getString("country.country"));
//		//countryCombo.getItems().add(rs.getString(1));
//
//	    }
//	} catch (SQLException ex) {
//	    Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
//	}

//    }
    
    
    @FXML
    private void showCustomerDetails(Customer selectedCustomer) {

	
//	String custID = Integer.toString(selectedCustomer.getCustomerId());
	txtCustomerID.setText(String.valueOf(selectedCustomer.getCustomerId()));
	txtCustomerName.setText(selectedCustomer.getCustomerName());
	txtCustomerAddress.setText(selectedCustomer.getAddress());
	txtCustomerAddress2.setText(selectedCustomer.getAddress2());
	
	countryCombo.setValue(selectedCustomer.getCountry());
	cityCombo.setValue(selectedCustomer.getCity());
	txtCustomerZipCode.setText(selectedCustomer.getZipCode());
	txtCustomerPhone.setText(selectedCustomer.getPhone());

    }
    
    
    @FXML
    void handleAddCustomer(ActionEvent event) {
	customerLabel.setText("Add Customer Details");
	//clear customer details
	clearFields();
//	btnCustomerDelete.setDisable(true);
//	btnCustomerUpdate.setDisable(true);
	editMode = false;
	//enableEdits();

//	    editClicked = false;
//        enableCustomerFields();
//        saveCancelButtonBar.setDisable(false);
//        customerTable.setDisable(true);
//        clearCustomerDetails();
//        customerIdField.setText("Auto-Generated");
//        newEditDeleteButtonBar.setDisable(true);  
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

    @FXML
    void handleCancelCustomer(ActionEvent event) {

    }

    @FXML
    void handleSaveCustomer(ActionEvent event) {
	
	Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();
	
	int cusId = selectedCustomer.getCustomerId();
	if (editMode) {
	    updateCustomer();
	} else {
	    saveNewCustomer();
	}
	mainApp.showCustomerScreen(currentUser);
    
    }

	@FXML
    void handleUpdateCustomer(ActionEvent event) {
	
	customerLabel.setText("Update Customer Details");
	
	Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();

	
	if (selectedCustomer != null) {
	    editMode = true;
	    updateCustomer();
//	    btnCustomerDelete.setDisable(true);
//	    btnCustomerUpdate.setDisable(true);
//	    enableEdits();
//	    initializeCountry();
	    
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
	//populateCountriesList();
	//countryCombo.setItems(Countries);
	populateCityList();
	


//	initializeCity();

	cityCombo.setConverter(new StringConverter<City>() {

	    @Override
	    public String toString(City object) {
		return object.getCityName();
	    }

	    @Override
	    public City fromString(String string) {
		return cityCombo.getItems().stream().filter(ap -> ap.getCityName().equals(string)).findFirst().orElse(null);
	    }
	});

//	countryCombo.setConverter(new StringConverter<Country>() {
//
//	    @Override
//	    public String toString(Country object) {
//		return object.getCountry();
//	    }
//
//	    @Override
//	    public Country fromString(String string) {
//		return countryCombo.getItems().stream().filter(ap -> ap.getCountry().equals(string)).findFirst().orElse(null);
//	    }
//	});
	
	
	
	try {

	    Customers.addAll(CustomerDaoImpl.getallCustomers());
	    //Countries.addAll(CustomerDaoImpl.getCountries());
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

	


	//triggers errors disableEdits();
	
    }



   
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
	countryCombo.setValue(null);
	cityCombo.setValue(null);
	txtCustomerZipCode.clear();
	txtCustomerPhone.clear();
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
	  //  cityCombo.getEditor().getText();
	    ps.setString(4, txtCustomerZipCode.getText());
	    ps.setString(5, txtCustomerPhone.getText());
	    ps.setString(6, currentUser.getUserName());
	    ps.setString(7, currentUser.getUserName());
	
	    boolean res = ps.execute();
	    int newAddressId = -1;
	    int newCustomerId = -1;
	    ResultSet rs = ps.getGeneratedKeys();

	    if (rs.next()) {
		newAddressId = rs.getInt(1);
		newCustomerId = rs.getInt(1);
		System.out.println("Generated AddressId: "+ newAddressId);
		System.out.println("Generated CustomerId: "+ newCustomerId);
		
		
	    }

	    PreparedStatement psc = DBConnection.getConn().prepareStatement("INSERT INTO customer "
		    + "(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy, customerId)"
		    + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?, ?)");

	    psc.setString(1, txtCustomerName.getText());
	    psc.setInt(2, newAddressId);
	    psc.setInt(3, 1);
	    //psc.setString(4, LocalDateTime.now().toString());
	    psc.setString(4, currentUser.getUserName());
	    //psc.setString(6, LocalDateTime.now().toString());
	    psc.setString(5, currentUser.getUserName());
	    psc.setInt(6, newCustomerId);
	    
	    int result = psc.executeUpdate();

	} catch (SQLException ex) {
	    ex.printStackTrace();
	}
    }

    /**
     * Updates Customer records
     */
    private void updateCustomer() {
	try {
	    int addId = -1;
	    int newcusId = -1;
	    Customer sCustomer = CustomerTable.getSelectionModel().getSelectedItem();
	    int cusId = sCustomer.getCustomerId();

	    System.out.println(cusId)
		    ;
	    PreparedStatement ps = DBConnection.getConn().prepareStatement("UPDATE address, customer, city, country "
		    + "SET address = ?, address2 = ?, address.cityId = ?, country.country = ?, postalCode = ?, phone = ? , customerName = ?, address.lastUpdate = CURRENT_TIMESTAMP, address.lastUpdateBy = ? , customer.lastUpdate = CURRENT_TIMESTAMP, customer.lastUpdateBy = ?"
		    + "WHERE customer.customerId = " + cusId + " AND customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId");
	    
	    
	    //address.lastUpdate = CURRENT_TIMESTAMP, address.lastUpdateBy = ? , customer.lastUpdate = CURRENT_TIMESTAMP, customer.lastUpdateBy = ?"
	    ps.setString(1, txtCustomerAddress.getText());
	    ps.setString(2, txtCustomerAddress2.getText());
	    ps.setInt(3, cityCombo.getValue().getCityId());
	    System.out.println(countryCombo.getValue().getCountry());
	    ps.setString(4, countryCombo.getValue().getCountry());
	    ps.setString(5, txtCustomerZipCode.getText());
	    ps.setString(6, txtCustomerPhone.getText());
	    //ps.setString(7, txtCustomerID.getText());
	    ps.setString(7, txtCustomerName.getText());
	    ps.setString(8, currentUser.getUserName());
	    ps.setString(9, currentUser.getUserName());

	    int result = ps.executeUpdate();

//	    ResultSet rs = ps.getGeneratedKeys();

//	    if (rs.next()) {
//		addId = rs.getInt(1);
//		newcusId = rs.getInt(1);
//	    }
//	    System.out.println(addId);
//	    System.out.println(newcusId);
	    
//	    PreparedStatement ps2 = DBConnection.getConn().prepareStatement("UPDATE address, customer, city, country "
//			+ "SET customerName = ?, address.lastUpdate = CURRENT_TIMESTAMP, address.lastUpdateBy = ? , customer.lastUpdate = CURRENT_TIMESTAMP, customer.lastUpdateBy = ?"
//		    + "WHERE customer.customerId = " + cusId + " AND customer.addressId = address.addressId)", Statement.RETURN_GENERATED_KEYS); 
//
//	    ps2.setString(1, txtCustomerName.getText());
//	    ps2.setString(2, currentUser.getUserName());
//	    ps2.setString(3, currentUser.getUserName());
//	   // ps2.setString(4, txtCustomerID.getText());
//
//	    int res = ps2.executeUpdate();

//	    PreparedStatement ps = DBConnection.getConn().prepareStatement("UPDATE address, customer, city, country "
//		    + "SET customerName = ?, address = ?, address2 = ?, address.cityId = ?, postalCode = ?, phone = ?, address.lastUpdate = CURRENT_TIMESTAMP, address.lastUpdateBy = ? , customer.lastUpdate = CURRENT_TIMESTAMP, customer.lastUpdateBy = ?"
//		    + "WHERE customer.customerId = ? AND customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId");
//	    
//	    ps.setString(1,txtCustomerName.getText());
//	    ps.setString(2, txtCustomerAddress.getText());
//	    ps.setString(3, txtCustomerAddress2.getText());
//	    ps.setInt(4, cityCombo.getValue().getCityId());
//	    ps.setString(5, txtCustomerZipCode.getText());
//	    ps.setString(6, txtCustomerPhone.getText());
//	    ps.setString(7, currentUser.getUserName());
//	    ps.setString(8, currentUser.getUserName());
//	    ps.setString(8, txtCustomerID.getText());
//
//	    int result = ps.executeUpdate();
	    
	    
//
//	    PreparedStatement psc = DBConnection.getConn().prepareStatement("UPDATE customer, address, city "
//		    + "SET customerName = ?, customer.lastUpdate = CURRENT_TIMESTAMP, customer.lastUpdateBy = ? "
//		    + "WHERE customer.customerId = ? AND customer.addressId = address.addressId AND address.cityId = city.cityId");
//
//	    psc.setString(1, txtCustomerName.getText());
//	    psc.setString(2, currentUser.getUserName());
//	    psc.setString(3, customerIdField.getText());
//	    int results = psc.executeUpdate();

	} catch (SQLException ex) {
	    ex.printStackTrace();
	}
    }

    private void deleteCustomer(Customer customer) {

	try {
	    PreparedStatement pst = DBConnection.getConn().prepareStatement("DELETE customer.*, address.* from customer, address WHERE customer.customerId = ? AND customer.addressId = address.addressId");
	    pst.setInt(1, customer.getCustomerId());
	    pst.executeUpdate();

	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    

}
