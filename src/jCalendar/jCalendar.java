/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar;

import jCalendar.dao.AppointmentDaoImpl;
import jCalendar.dao.BarberDaoImpl;
import jCalendar.dao.CustomerDaoImpl;
import jCalendar.dao.PetDaoImpl;
import jCalendar.model.Appointment;
import jCalendar.model.Barber;
import jCalendar.model.Customer;
import jCalendar.model.DisplayAppointment;
import jCalendar.model.Pet;
import jCalendar.model.User;
import jCalendar.utilities.Loggerutil;
import jCalendar.viewcontroller.*;
import java.io.IOException;
import java.sql.Connection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author jlau2
 */
public class jCalendar extends Application {

    private Stage mainStage;
    private BorderPane mainScreen;
    private AnchorPane loginScreen;
    private AnchorPane loginScreen2;
    private AnchorPane appointmentScreen;
    private AnchorPane appointmentAddScreen;
    private AnchorPane customerScreen;
    private AnchorPane barberScreen;
    private AnchorPane reportScreen;
    private AnchorPane appointmentListScreen;

    private Tab selTab;
    private TabPane selTabPane;

    private static Connection connection;
    private Stage dialogStage;
    private User currUser;
    private Customer customer;

    private ObservableList<Appointment> appointmentData = FXCollections.observableArrayList();
    private ObservableList<Customer> customerData = FXCollections.observableArrayList();
    private ObservableList<Pet> customerPetData = FXCollections.observableArrayList();
    private ObservableList<Barber> barberData = FXCollections.observableArrayList();
    private ObservableList<Pet> petData = FXCollections.observableArrayList();
    private ObservableList<DisplayAppointment> displayAppointments = FXCollections.observableArrayList();

    public ObservableList<Appointment> getAppointmentData() {
        return appointmentData;
    }

    public ObservableList<Pet> getPetData() {
        return petData;
    }

    public ObservableList<Pet> getCustomerPetData() {
        return customerPetData;
    }

    public ObservableList<Customer> getCustomerData() {
        return customerData;
    }

    public ObservableList<Barber> getBarberData() {
        return barberData;
    }

    public ObservableList<DisplayAppointment> getDisplay() {
        return displayAppointments;
    }

    @Override
    public void start(Stage mainStage) {
        this.mainStage = mainStage;
        this.mainStage.setTitle("Calendar App");

        //load from database
        appointmentData.addAll(new AppointmentDaoImpl().loadApptData());
        barberData.addAll(new BarberDaoImpl().loadBarberData());
        customerData.addAll(new CustomerDaoImpl().loadCustomerData());

        customerPetData.addAll(new PetDaoImpl().loadCustomerPetData());
//        petData.addAll(new PetDaoImpl().loadPetsData());

        for (Pet p : customerPetData) {
            System.out.println("printing" + p.toString());
        }

//        showLoginScreen2();
        showMain(currUser);
//        showBarberScreen();
//        showAppointmentListScreen(currUser);
//        showCustomerScreen(currUser);

        Loggerutil.init();
    }

    public void refreshView() {
        //load from database
        appointmentData.clear();
        barberData.clear();
        customerData.clear();

        appointmentData.addAll(new AppointmentDaoImpl().loadApptData());
        barberData.addAll(new BarberDaoImpl().loadBarberData());
        customerData.addAll(new CustomerDaoImpl().loadCustomerData());
        customerPetData.addAll(new PetDaoImpl().loadCustomerPetData());

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
            loginScreen = (AnchorPane) loader.load();

            LoginScreenController controller;
            controller = loader.getController();
            controller.setLogin(this);

            Scene scene = new Scene(loginScreen);
            mainStage.setScene(scene);
            mainStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void showLoginScreen2() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/LoginScreen2.fxml"));
            loginScreen2 = (AnchorPane) loader.load();

            LoginScreenController2 controller = loader.getController();
            controller.setMainController(this);

            Scene scene = new Scene(loginScreen2);
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
            customerScreen = (AnchorPane) loader.load();

            mainScreen.setCenter(customerScreen);
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
            barberScreen = (AnchorPane) loader.load();

            mainScreen.setCenter(barberScreen);
            mainScreen.setLeft(null);

            BarberScreenController controller = loader.getController();
            controller.setMainController(this, currUser);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showAppointmentScreen() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/AppointmentScreen.fxml"));
            appointmentScreen = (AnchorPane) loader.load();

            mainScreen.setCenter(appointmentScreen);

            AppointmentScreenController controller = loader.getController();
            controller.setMainController(this);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showReportScreen() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/ReportScreen.fxml"));
            reportScreen = (AnchorPane) loader.load();

            mainScreen.setCenter(reportScreen);
            ReportScreenController controller = loader.getController();
            controller.setMainController(this);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showAppointmentListScreen(User currentUser) {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/AppointmentList.fxml"));
            appointmentListScreen = (AnchorPane) loader.load();

            mainScreen.setCenter(appointmentListScreen);
            mainScreen.setLeft(null);

            AppointmentListController controller = loader.getController();
            controller.setMainController(this, currentUser);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showAppointmentAddScreen(User currentUser) {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/Appointment_Add.fxml"));
            appointmentAddScreen = (AnchorPane) loader.load();

            mainScreen.setLeft(appointmentAddScreen);

            Appointment_AddController controller = loader.getController();
            controller.setMainController(this, currentUser);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showAppointmentAddScreen(User currentUser, Appointment appt) {

        //TO PASS SELECTED APPT FOR EDITING
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(jCalendar.class.getResource("/jCalendar/viewcontroller/Appointment_Add.fxml"));
            appointmentAddScreen = (AnchorPane) loader.load();

            mainScreen.setLeft(appointmentAddScreen);

            Appointment_AddController controller = loader.getController();
            controller.setMainController(this, currentUser);

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
