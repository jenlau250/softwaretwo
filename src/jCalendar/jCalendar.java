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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
    private Tab selTab;
    private TabPane selTabPane;
    
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
	Loggerutil.init();
	connection = DBConnection.getConn();

	// Uncomment to change language back to English
//	Locale.setDefault(new Locale("jp", "JP"));

	System.out.println(Locale.getDefault());

	launch(args);
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
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/LoginScreen.fxml"));
	    loginScreen = (AnchorPane) loader.load();

	    LoginScreenController controller = loader.getController();
	    controller.setLogin(this);

	    Scene scene = new Scene(loginScreen);
	    mainStage.setScene(scene);
	    mainStage.show();
	    
	} catch (IOException ex) {
	    ex.printStackTrace();
	}

    }

    public void showCustomerScreen(User currentUser) {

	try {

	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/CustomerScreen.fxml"));
	    customerScreen = (AnchorPane) loader.load();

	    mainScreen.setCenter(customerScreen);

	    CustomerScreenController controller = loader.getController();
	    controller.setCustomerScreen(this, currentUser);


	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }

    public void showAppointmentScreen(User currentUser) {

	try {

	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/AppointmentScreen.fxml"));
	    appointmentScreen = (AnchorPane) loader.load();

	    mainScreen.setCenter(appointmentScreen);
	    
	    AppointmentScreenController controller = loader.getController();
	    controller.setAppointmentScreen(this, currentUser);

	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }
    
    public void showReportScreen(User currentUser) {

	try {
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/ReportScreen.fxml"));
	    reportScreen = (AnchorPane) loader.load();

	    mainScreen.setCenter(reportScreen);
	    ReportScreenController controller = loader.getController();
	    controller.setReportScreen(this, currentUser);

	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }
    
}