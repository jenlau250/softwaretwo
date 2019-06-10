/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar;

import jCalendar.DAO.DBConnection;
import jCalendar.model.Appointment;
import jCalendar.model.User;
import jCalendar.utilities.Loggerutil;
import jCalendar.viewcontroller.AppointmentScreenController;
import jCalendar.viewcontroller.CustomerScreenController;
import jCalendar.viewcontroller.LoginScreenController;
import jCalendar.viewcontroller.MainScreenController;
import jCalendar.viewcontroller.ReportScreenController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author jlau2
 */
public class jCalendar extends Application {
    
    private Stage mainStage;
    private BorderPane mainScreen;
    private AnchorPane loginScreen;
    private AnchorPane appointmentScreen;
    private AnchorPane customerScreen;
    private AnchorPane reportScreen;
    
    private static Connection connection;
    private Stage dialogStage;
    private User currUser;
    
    @Override
    public void start(Stage mainStage) {
	this.mainStage= mainStage;
	this.mainStage.setTitle("Calendar App");
	showLoginScreen();

	//showCustomerScreen(user);

    }
    
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, Exception {
	DBConnection.init();
	connection = DBConnection.getConn();
	// Uncomment to change language back to English
	//Locale.setDefault(new Locale("jp","JP"));
	//System.out.println(Locale.getDefault());
	//connection = DBConnection.getConn();
        launch(args);
	Loggerutil.init();
	DBConnection.closeConnection();
    }
    
    /**
     *
     * @param currentUser
     */
    public void showMain(User currentUser) {
	try {
	    // Load root layout from fxml file.
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/MainScreen.fxml"));
	    mainScreen = (BorderPane) loader.load();

	    // Show the scene containing the root layout.
	    Scene scene = new Scene(mainScreen);
	    mainStage.setScene(scene);
	    // Give the controller access to the main app.
	    MainScreenController controller = loader.getController();
	    controller.setMenu(this, currentUser);

	    mainStage.show();
	} catch (IOException e) {
	    e.getCause().printStackTrace();
	}
    
    }
   
    
     public void showLoginScreen() {

	try {
	    // Load screen
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/LoginScreen.fxml"));
	    loginScreen = (AnchorPane) loader.load();

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
	    loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/CustomerScreen.fxml"));
	    customerScreen = (AnchorPane) loader.load();

	    
	    //set customer screen in main screen root layout
	    mainScreen.setCenter(customerScreen);
	    
	    // Give controller access
	    CustomerScreenController controller = loader.getController();
	    controller.setCustomerScreen(this, currentUser);

	    //Show scene
//	    Scene scene = new Scene(customerScreen);
//	    mainStage.setScene(scene);
//	    mainStage.show();


	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }

    public void showAppointmentScreen(User currentUser) {

	try {
	    // Load screen
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/AppointmentScreen.fxml"));
	    appointmentScreen = (AnchorPane) loader.load();

	    //set customer screen in main screen root layout
	    mainScreen.setCenter(appointmentScreen);

	    // Give controller access
	    AppointmentScreenController controller = loader.getController();
	    controller.setAppointmentScreen(this, currentUser);

	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }
    
    public void showReportScreen(User currentUser, MenuItem menuReports) throws ParseException {

	try {
	    // Load screen
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/ReportScreen.fxml"));
	    reportScreen = (AnchorPane) loader.load();

	    //set customer screen in main screen root layout
	    mainScreen.setCenter(reportScreen);

	    // Give controller access
	    ReportScreenController controller = loader.getController();
	    controller.setReportScreen(this, currentUser, menuReports);

	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }
    



}