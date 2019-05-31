/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar;

import jCalendar.DAO.DBConnection;
import jCalendar.model.User;
import jCalendar.viewcontroller.CustomerScreenController;
import jCalendar.viewcontroller.LoginScreenController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author jlau2
 */
public class jCalendar extends Application {
    
    static Stage mainStage;
    //changed from private to public
    public AnchorPane loginScreen;
    public BorderPane CustomerScreen;
    public static Connection connection;
    
    @Override
    public void start(Stage mainStage) {
        
	this.mainStage= mainStage;
	this.mainStage.setTitle("Calendar App");
	showLoginScreen();

    }

    
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, Exception {
	DBConnection.makeConnection();
	
	// Uncomment to change language back to English
	//Locale.setDefault(new Locale("jp","JP"));
	//System.out.println(Locale.getDefault());
	//connection = DBConnection.getConn();
        launch(args);
	DBConnection.closeConnection();
    }
    
   
     public void showLoginScreen() {

	try {
	    // Load screen
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(jCalendar.class.getResource("viewcontroller/LoginScreen.fxml"));
	    AnchorPane loginScreen = (AnchorPane) loader.load();

	    // Give controller access
	    LoginScreenController controller = loader.getController();
	    controller.setLogin(this);

	    //Show scene
	    Scene scene = new Scene(loginScreen);
	    mainStage.setScene(scene);
	    mainStage.show();

	} catch (IOException ex) {
	    ex.printStackTrace();
	}

    }

    public void showCustomerScreen(User currentUser) {

	try {
	    // Load screen
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(jCalendar.class.getResource("viewcontroller/CustomerScreen.fxml"));
	    BorderPane CustomerScreen = (BorderPane) loader.load();

	    // Give controller access
	    CustomerScreenController controller = loader.getController();
	    controller.setCustomerScreen(this, currentUser);

	    //Show scene
	    Scene scene = new Scene(CustomerScreen);
	    mainStage.setScene(scene);
	    mainStage.show();

	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }


}