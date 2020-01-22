/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import com.jfoenix.controls.JFXButton;
import jCalendar.jCalendar;
import jCalendar.model.Appointment;
import jCalendar.model.User;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Jen
 */
public class AppointmentListController implements Initializable {

    private jCalendar mainApp;
    private User currentUser;

    @FXML
    private TableView<Appointment> tableView;

    @FXML
    private TableColumn<Appointment, String> colTitle;

    @FXML
    private TableColumn<Appointment, String> colType;

    @FXML
    private TableColumn<Appointment, String> colCusName;

    @FXML
    private TableColumn<Appointment, LocalDateTime> colDate;

    @FXML
    private TableColumn<Appointment, LocalDateTime> colStart;

    @FXML
    private TableColumn<Appointment, LocalDateTime> colEnd;

    @FXML
    private TableColumn<Appointment, String> colBarber;

    @FXML
    private TableColumn<Appointment, String> colPet;

    @FXML
    private TableColumn<Appointment, String> colPhone;

    @FXML
    private TableColumn<Appointment, String> colEmail;

    @FXML
    private JFXButton btnNewAdd;

    //moved to mainApp
//    private ObservableList<Appointment> appointmentData = FXCollections.observableArrayList();
//    private ObservableList<Customer> customerData = FXCollections.observableArrayList();
//    private ObservableList<Barber> barberData = FXCollections.observableArrayList();
//    private ObservableList<Pet> petData = FXCollections.observableArrayList();
//    private ObservableList<DisplayAppointment> displayAppointments = FXCollections.observableArrayList();
    @FXML
    void handleApptAddNew(ActionEvent event) {
        mainApp.showAppointmentAddScreen();
    }

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public AppointmentListController() {

    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        //1 - INITIALIZE THE TABLECOLUMNS
        colTitle.setCellValueFactory(f -> f.getValue().titleProperty());
        colDate.setCellValueFactory(f -> f.getValue().startProperty());
        colStart.setCellValueFactory(f -> f.getValue().startProperty());
        colEnd.setCellValueFactory(f -> f.getValue().endProperty());
        colType.setCellValueFactory(f -> f.getValue().descriptionProperty());

        colBarber.setCellValueFactory(f -> f.getValue().getBarber().nameProperty());
        colPet.setCellValueFactory(f -> f.getValue().getCustomer().getPet().nameProperty());
        colCusName.setCellValueFactory(f -> f.getValue().getCustomer().customerNameProperty());
        colPhone.setCellValueFactory(f -> f.getValue().getCustomer().customerPhoneProperty());
        colEmail.setCellValueFactory(f -> f.getValue().getCustomer().customerEmailProperty());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

//SET HOW DATE, START AND END TIME FIELDS ARE DISPLAYED
        colDate.setCellFactory(column -> new TableCell<Appointment, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime datetime, boolean empty
            ) {
                super.updateItem(datetime, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(dateFormatter.format(datetime));
                }
            }
        });

        colStart.setCellFactory(column -> new TableCell<Appointment, LocalDateTime>() {
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

        colEnd.setCellFactory(column -> new TableCell<Appointment, LocalDateTime>() {
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
     * Initializes the controller class.This method is automatically called
     * after the fxml file has been loaded. Add observable list data to table
     * view here
     *
     * @param mainApp
     */
    public void setMainController(jCalendar mainApp) {

        this.mainApp = mainApp;
//        this.currentUser = currentUser;

//        appointmentData.addAll(AppointmentDaoImpl.addAppointments2());
//        customerData.addAll(CustomerDaoImpl.addCustomers2());
//        barberData.addAll(BarberDaoImpl.addBarbers());
//        petData.addAll(PetDaoImpl.addPets());
//
//        for (Appointment appointment
//                : appointmentData) {
//
//            Predicate<Customer> customerIdMatch = c -> c.getCustomerId() == appointment.getCustomerId();
//
//            customerData.stream()
//                    // Check if the customers list contains a customer with this ID
//                    .filter(customerIdMatch)
//                    //                    .filter(p -> p.getCustomerId() == appointment.getCustomerId())
//                    .findFirst()
//                    // Add the new DisplayStudent to the list
//                    .ifPresent(c -> {
//                        displayAppointments.add(new DisplayAppointment(
//                                appointment,
//                                c
//                        ));
//                    });
//
//        }
//        colCusName.setCellValueFactory(f -> f.getValue().getCustomer().customerNameProperty());
//        colPhone.setCellValueFactory(f -> f.getValue().getCustomer().customerPhoneProperty());
//        colEmail.setCellValueFactory(f -> f.getValue().getCustomer().customerEmailProperty());
//
//        colPet.setCellValueFactory(f -> f.getValue().getCustomer().getPet().nameProperty());
        //CLEAR AND LOAD DETAILS EX: SHOWAPPOINTMENTDETAILS(NULL)
        tableView.getItems().clear();
        tableView.setItems(mainApp.getAppointmentData());

        //LISTEN FOR SELECTION CHANGES AND SHOW THE APPT DETAILS WHEN CHANGED
    }

}
