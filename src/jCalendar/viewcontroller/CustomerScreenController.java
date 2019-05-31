/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import jCalendar.DAO.CustomerDaoImpl;
import jCalendar.jCalendar;
import jCalendar.model.Customer;
import jCalendar.model.User;
import java.net.URL;
import java.util.Date;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class CustomerScreenController {

    @FXML    private Button btnCustomerAdd;
    @FXML    private Button btnCustomerUpdate;
    @FXML    private Button btnCustomerDelete;
    @FXML    private TableView<Customer> CustomerTable;
    @FXML    private TableColumn<Customer, Integer> txtCustomerID;
    @FXML    private TableColumn<Customer, String> txtCustomerName;
    @FXML    private TableColumn<Customer, String> txtCustomerAddress;
    @FXML    private TableColumn<Customer, ?> txtCustomerActive;
    @FXML    private TableColumn<Customer, String> txtCustomerCreateBy;
    @FXML    private TableColumn<?, ?> txtCustomerCreateDate;
    @FXML    private TableColumn<Customer, String> txtCustomerLastUpdateBy;
    @FXML    private TableColumn<?, ?> txtCustomerLastUpdateDate;
    
    ObservableList<Customer> Customers = FXCollections.observableArrayList();
    

    public User currentUser;
    public Stage dialogStage;
    public jCalendar mainApp;

    CustomerScreenController() {
	
    }
    
    /**
     * Initializes the controller class.
     * @param mainApp
     * @param user
     */
    public void setCustomerScreen(jCalendar mainApp, User currentUser) {
	this.mainApp = mainApp;
	this.currentUser = currentUser;

	try {
	    Customers.addAll(CustomerDaoImpl.getallCustomers());
	} catch (Exception ex) {
	    Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);

	}
	CustomerTable.setItems(Customers);

    }

//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//	// TODO
	
	// Bind table propeties 
//	txtCustomerID.setCellFactory(new PropertyValueFactory<>("customerId"));
//	txtCustomerName.setCellFactory(new PropertyValueFactory<>("customerName"));
//	txtCustomerAddress.setCellFactory(new PropertyValueFactory<>("address"));
//	txtCustomerActive.setCellFactory(new PropertyValueFactory<>("active"));
//	txtCustomerCreateBy.setCellFactory(new PropertyValueFactory<>("createdBy"));
//	txtCustomerCreateDate.setCellFactory(new PropertyValueFactory<>("createDate"));
//	txtCustomerLastUpdateBy.setCellFactory(new PropertyValueFactory<>("lastUpdateBy"));
//	txtCustomerLastUpdateDate.setCellFactory(new PropertyValueFactory<>("lastUpdate"));
	

	
	
      
    
}
