/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import jCalendar.model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import jCalendar.jCalendar;
import javafx.scene.control.MenuItem;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class MainScreenController {

    @FXML    private MenuItem menuCustomers;
    @FXML    private MenuItem menuExit;
    @FXML    private MenuItem menuReports;
    @FXML    private MenuItem menuAppointments;

    private jCalendar mainApp;
    private User currentUser;
    
    // initialize mainApp,currentuser, and empty controller constructor
    public MainScreenController() {
	
    }
    
    /**
     * Initializes the controller class.
     */
    /**
     * Initializes Menu
     *
     * @param mainApp
     * @param currentUser
     */
    public void setMenu(jCalendar mainApp, User currentUser) {
	this.mainApp = mainApp;
	this.currentUser = currentUser;

	menuExit.setOnAction((evt) -> {
	    System.exit(0);
	});
	
	menuCustomers.setOnAction((evt) -> {
	    mainApp.showCustomerScreen(currentUser);
	});

	//logoutUser.setText("Logout: " + currentUser.getUsername());
    }


    
    
    
}
