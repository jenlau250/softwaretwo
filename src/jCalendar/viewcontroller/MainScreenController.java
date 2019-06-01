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

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class MainScreenController {

    @FXML
    private Button loginButton;
    @FXML
    private Button customerButton;
    private jCalendar mainApp;
    private User currentUser;

    /**
     * Initializes the controller class.
     */
    /**
     * Initializes Menu
     * @param mainApp
     * @param currentUser 
     */
    public void setMenu(jCalendar mainApp) {
	this.mainApp = mainApp;
        //this.currentUser = currentUser;
        
        //logoutUser.setText("Logout: " + currentUser.getUsername());
    }   
    
}
