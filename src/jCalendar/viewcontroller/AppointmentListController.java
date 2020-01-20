/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import com.jfoenix.controls.JFXButton;
import jCalendar.dao.AppointmentDaoImpl;
import jCalendar.dao.BarberDaoImpl;
import jCalendar.dao.CustomerDaoImpl;
import jCalendar.dao.PetDaoImpl;
import jCalendar.jCalendar;
import jCalendar.model.Appointment;
import jCalendar.model.Barber;
import jCalendar.model.Customer;
import jCalendar.model.DisplayAppointment;
import jCalendar.model.Pet;
import jCalendar.model.User;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Jen
 */
public class AppointmentListController {

    private jCalendar mainApp;
    private User currentUser;

    @FXML
    private TableView<DisplayAppointment> tableView;

    @FXML
    private TableColumn<DisplayAppointment, String> colTitle;

    @FXML
    private TableColumn<DisplayAppointment, String> colCusName;

    @FXML
    private TableColumn<DisplayAppointment, LocalDateTime> colDate;

    @FXML
    private TableColumn<DisplayAppointment, LocalDateTime> colStart;

    @FXML
    private TableColumn<DisplayAppointment, LocalDateTime> colEnd;

    @FXML
    private TableColumn<DisplayAppointment, String> colBarber;

    @FXML
    private TableColumn<DisplayAppointment, String> colPet;

    @FXML
    private TableColumn<DisplayAppointment, String> colPhone;

    @FXML
    private TableColumn<DisplayAppointment, String> colEmail;

    @FXML
    private JFXButton btnNewAdd;

    private ObservableList<Appointment> appointmentData = FXCollections.observableArrayList();
    private ObservableList<Customer> customerData = FXCollections.observableArrayList();
    private ObservableList<Barber> barberData = FXCollections.observableArrayList();
    private ObservableList<Pet> petData = FXCollections.observableArrayList();
    private ObservableList<DisplayAppointment> displayAppointments = FXCollections.observableArrayList();

    @FXML
    void handleApptAddNew(ActionEvent event) {
        mainApp.showAppointmentAddScreen(currentUser);
    }

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public AppointmentListController() {

    }

    @FXML
    private void initialize() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded. Add observable list data to table
     * view here
     */
    public void setAppointmentListScreen(jCalendar mainApp, User currentUser) {

        this.mainApp = mainApp;
        this.currentUser = currentUser;

        appointmentData.addAll(AppointmentDaoImpl.addAppointments2());
        customerData.addAll(CustomerDaoImpl.addCustomers2());
        barberData.addAll(BarberDaoImpl.addBarbers());
        petData.addAll(PetDaoImpl.addPets());

        for (Appointment appointment
                : appointmentData) {

            Predicate<Customer> customerIdMatch = c -> c.getCustomerId() == appointment.getCustomerId();

            customerData.stream()
                    // Check if the customers list contains a customer with this ID
                    .filter(customerIdMatch)
                    //                    .filter(p -> p.getCustomerId() == appointment.getCustomerId())
                    .findFirst()
                    // Add the new DisplayStudent to the list
                    .ifPresent(c -> {
                        displayAppointments.add(new DisplayAppointment(
                                appointment,
                                c
                        ));
                    });

        }

        //SET TABLEVIEW AFTER MODEL
        colTitle.setCellValueFactory(f -> f.getValue().getAppointment().titleProperty());
        colDate.setCellValueFactory(f -> f.getValue().getAppointment().startProperty());
        colStart.setCellValueFactory(f -> f.getValue().getAppointment().startProperty());
        colEnd.setCellValueFactory(f -> f.getValue().getAppointment().endProperty());

        colCusName.setCellValueFactory(f -> f.getValue().getCustomer().customerNameProperty());
        colPhone.setCellValueFactory(f -> f.getValue().getCustomer().customerPhoneProperty());
        colEmail.setCellValueFactory(f -> f.getValue().getCustomer().customerEmailProperty());

        colPet.setCellValueFactory(f -> f.getValue().getCustomer().getPet().nameProperty());
        colBarber.setCellValueFactory(f -> f.getValue().getAppointment().getBarber().nameProperty());

        tableView.setItems(displayAppointments);

        //SET HOW DATE, START AND END TIME FIELDS ARE DISPLAYED
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

        colDate.setCellFactory(column -> new TableCell<DisplayAppointment, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime datetime, boolean empty) {
                super.updateItem(datetime, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(dateFormatter.format(datetime));
                }
            }
        });

        colStart.setCellFactory(column -> new TableCell<DisplayAppointment, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime datetime, boolean empty) {
                super.updateItem(datetime, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(datetime.atZone(ZoneOffset.UTC)
                            .format(timeFormatter));
                }
            }
        });

        colEnd.setCellFactory(column -> new TableCell<DisplayAppointment, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime datetime, boolean empty) {
                super.updateItem(datetime, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(datetime.atZone(ZoneOffset.UTC)
                            .format(timeFormatter));
                }
            }
        });

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded. initialize the table view with
     * setCellValueFactories here
     */
    public ObservableList<Appointment> getAppointmentData() {
        return appointmentData;
    }

    public ObservableList<Pet> getPetData() {
        return petData;
    }

    public ObservableList<Customer> getCustomerData() {
        return customerData;
    }

    public ObservableList<Barber> getBarberData() {
        return barberData;
    }

}
