/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar;

import Cache.AppointmentCache;
import Cache.BarberCache;
import Cache.CustomerCache;
import Cache.PetCache;
import jCalendar.dao.DBConnection;
import jCalendar.model.Appointment;
import jCalendar.model.Barber;
import jCalendar.model.Customer;
import jCalendar.model.User;
import jCalendar.utilities.Loggerutil;
import jCalendar.viewcontroller.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author jlau2
 */
public class jCalendar extends Application {

    private Stage mainStage;
    @FXML
    BorderPane mainScreen;
    private AnchorPane screen;

    private Stage dialogStage;
    private User currUser;
//    private Appointment appt;
    private Customer customer;
    private DBConnection databasebHandler;

    @Override
    public void start(Stage mainStage) {
        this.mainStage = mainStage;
        this.mainStage.setTitle("Posh Paws Appointment Scheduler");
        this.mainStage.getIcons().add(new Image("/jCalendar/images/icon.png"));

        //order is important
        BarberCache.flush();
        PetCache.flush();
        CustomerCache.flush();
        AppointmentCache.flush();

        showMain(currUser);

        Loggerutil.init();
    }

    public void refreshView() {

        BarberCache.flush();
        PetCache.flush();
        CustomerCache.flush();
        AppointmentCache.flush();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        DBConnection.init();

//        connection = DBConnection.getConn();
        launch(args);
//        DBConnection.closeConnection();
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
            screen = (AnchorPane) loader.load();

            LoginScreenController controller = loader.getController();
            controller.setMainController(this);

            Scene scene = new Scene(screen);
            mainStage.setScene(scene);
            mainStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void showCustomerScreen() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/CustomerScreen.fxml"));
            screen = (AnchorPane) loader.load();
            screen.setMinSize(25, 25);
            screen.setPrefSize(25000, 25000);
            mainScreen.setCenter(screen);
            mainScreen.setLeft(null);

            CustomerScreenController controller = loader.getController();
            controller.setMainController(this);
//            controller.setCustomer(customer);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showBarberScreen() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/BarberScreen.fxml"));
            screen = (AnchorPane) loader.load();

            screen.setMinSize(25, 25);
            screen.setPrefSize(25000, 25000);
            mainScreen.setCenter(screen);
            mainScreen.setLeft(null);

            BarberScreenController controller = loader.getController();
            controller.setMainController(this);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showBarberAddScreen() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/BarberAdd.fxml"));
            screen = (AnchorPane) loader.load();

            mainScreen.setLeft(screen);

            BarberAddController controller = loader.getController();
            controller.setMainController(this, currUser);
//            controller.setSelectedAppointment(selectedAppt);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showBarberAddScreen(Barber selected) {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/BarberAdd.fxml"));
            screen = (AnchorPane) loader.load();

            mainScreen.setLeft(screen);

            BarberAddController controller = loader.getController();
            controller.setMainController(this, currUser);
            controller.setSelected(selected);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//
//    public void showAppointmentScreen() {
//
//        try {
//
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/AppointmentScreen.fxml"));
//            screen = (AnchorPane) loader.load();
//
//            mainScreen.setCenter(screen);
//
//            AppointmentScreenController controller = loader.getController();
//            controller.setMainController(this);
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
    public void showReportScreen() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/ReportScreen.fxml"));
            screen = (AnchorPane) loader.load();
            screen.setMinSize(25, 25);
            screen.setPrefSize(25000, 25000);
            mainScreen.setCenter(screen);
            ReportScreenController controller = loader.getController();
            controller.setMainController(this);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showAppointmentListScreen() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/AppointmentList.fxml"));
            screen = (AnchorPane) loader.load();
            screen.setMinSize(25, 25);
            screen.setPrefSize(25000, 25000);
            mainScreen.setCenter(screen);
            mainScreen.setLeft(null);

            AppointmentListController controller = loader.getController();
            controller.setMainController(this);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showAppointmentAddScreen() {

        //TO PASS SELECTED APPT FOR EDITING
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/Appointment_Add.fxml"));
            screen = (AnchorPane) loader.load();

            mainScreen.setLeft(screen);

            Appointment_AddController controller = loader.getController();
            controller.setMainController(this, currUser);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showAppointmentAddScreen(Appointment selectedAppt) {

        //TO PASS SELECTED APPT FOR EDITING
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/Appointment_Add.fxml"));
            screen = (AnchorPane) loader.load();

            mainScreen.setLeft(screen);

            Appointment_AddController controller = loader.getController();
            controller.setMainController(this, currUser);
            controller.setSelectedAppointment(selectedAppt);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//        try {
//
//
//
//  //this was used to load scene in front of main appointment list
//            // Load root layout from fxml file.
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/jCalendar/viewcontroller/Appointment_Add.fxml"));
//            AnchorPane rootPane = (AnchorPane) loader.load();
//            Stage stage = new Stage(StageStyle.UNDECORATED);
//            stage.initModality(Modality.APPLICATION_MODAL);
//
//            // Pass main controller reference to view
//            Appointment_AddController controller = loader.getController();
//            controller.setMainController(this);
//
//            // Show the scene containing the root layout.
//            Scene scene = new Scene(rootPane);
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException ex) {
////            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
//            ex.printStackTrace();
//        }
//}
}
