/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import jCalendar.jCalendar;
import jCalendar.model.Appointment;
import jCalendar.model.User;
import jCalendar.utilities.Loggerutil;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    private LocalDate currDate;
    Appointment selectedAppt;
//    private Appointment selectedAppt;
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

    @FXML
    private Label labelStartBound;
    @FXML
    private Label labelEndBound;

    @FXML
    private JFXComboBox comboWeekMonth;
    @FXML
    private JFXButton btnBack;
    @FXML
    private JFXButton btnNext;

    private final DateTimeFormatter timeformat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private final DateTimeFormatter dateformat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final DateTimeFormatter labelformat = DateTimeFormatter.ofPattern("E MMM d, yyyy");

    private final static Logger logger = Logger.getLogger(Loggerutil.class.getName());

    //moved to mainApp
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
//    private ObservableList<Customer> customerData = FXCollections.observableArrayList();
//    private ObservableList<Barber> barberData = FXCollections.observableArrayList();
//    private ObservableList<Pet> petData = FXCollections.observableArrayList();
//    private ObservableList<DisplayAppointment> displayAppointments = FXCollections.observableArrayList();

    @FXML
    void handleApptAddNew(ActionEvent event) {

        mainApp.showAppointmentAddScreen(currentUser);
    }

    @FXML
    void handleApptEdit(ActionEvent event) {

        selectedAppt = tableView.getSelectionModel().getSelectedItem();

        if (selectedAppt != null) {
            mainApp.showAppointmentEditScreen(currentUser, selectedAppt);
        } else {
            System.out.println("Please select an appointment first");
        }

    }

    @FXML
    void handleApptDelete(ActionEvent event) {

//        if (selectedAppt == null) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Nothing selected");
//            alert.setHeaderText("No appointment was selected to delete");
//            alert.setContentText("Please select an appointment to delete");
//            alert.showAndWait();
//        } else {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Confirm Deletion");
//            alert.setHeaderText("Are you sure you want to delete " + selectedAppt.getTitle() + " scheduled for " + selectedAppt.getCustomer().getCustomerName() + "?");
//            alert.showAndWait()
//                    .filter(response -> response == ButtonType.OK)
//                    .ifPresent(response -> {
//                        deleteAppointment(selectedAppt);
//                        mainApp.showAppointmentScreen(currentUser);
//                    }
//                    );
//        }
//        selectedAppt = tableView.getSelectionModel().getSelectedItem();
//        mainApp.showAppointmentEditScreen(currentUser, selAppt);
    }

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public AppointmentListController() {

    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        //        selectedAppt = tableView.getSelectionModel().getSelectedItem();
        //1 - INITIALIZE THE TABLECOLUMNS
        colTitle.setCellValueFactory(f -> f.getValue().titleProperty());
        colDate.setCellValueFactory(f -> f.getValue().startProperty());
        colStart.setCellValueFactory(f -> f.getValue().startProperty());
        colEnd.setCellValueFactory(f -> f.getValue().endProperty());
        colType.setCellValueFactory(f -> f.getValue().descriptionProperty());

        colBarber.setCellValueFactory(f -> f.getValue().getBarber().nameProperty());
        colPet.setCellValueFactory(f -> f.getValue().getPet().nameProperty());
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

        labelStartBound.setText(null);
        labelEndBound.setText(null);
        comboWeekMonth.setValue("Monthly");
        currDate = LocalDate.now();
        nextMonth(currDate);

        // Add monthly or weekly filter to tableview
        comboWeekMonth.setItems(FXCollections.observableArrayList("Weekly", "Monthly"));
        comboWeekMonth.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value) {
                LocalDate cbStartDate = currDate;
                if (comboWeekMonth.getSelectionModel().getSelectedIndex() == 0) {
                    nextWeek(cbStartDate);
                } else if (comboWeekMonth.getSelectionModel().getSelectedIndex() == 1) {
                    nextMonth(cbStartDate);
                }
            }
        });

        btnNext.setOnAction((evt) -> {
            LocalDate cbStartDate = currDate;

            if (comboWeekMonth.getSelectionModel().getSelectedIndex() == 0) {
                nextWeek(cbStartDate);
            } else if (comboWeekMonth.getSelectionModel().getSelectedIndex() == 1) {
                nextMonth(cbStartDate);
            }
        });

        btnBack.setOnAction((evt) -> {
            LocalDate cbEndDate = currDate;
            if (comboWeekMonth.getSelectionModel().getSelectedIndex() == 0) {
                previousWeek(cbEndDate);
            } else if (comboWeekMonth.getSelectionModel().getSelectedIndex() == 1) {
                previousMonth(cbEndDate);
            }
        });

    }

    private void nextMonth(LocalDate cbStartDate) {
///dateFormatter.format(datetime)
        // Lambda expression used to filter data
        currDate = currDate.plusMonths(1);
        FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
        filteredData.setPredicate(row -> {
            LocalDate rowDate = LocalDate.parse(row.getStart2().format(dateformat));
            return rowDate.isAfter(cbStartDate.minusDays(1)) && rowDate.isBefore(currDate);
        });
        tableView.setItems(filteredData);

        labelStartBound.setText(cbStartDate.format(labelformat));
        labelEndBound.setText(currDate.format(labelformat));
    }

    private void previousMonth(LocalDate cbEndDate) {
        currDate = currDate.minusMonths(1);
        FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
        filteredData.setPredicate(row -> {
//            LocalDate rowDate = LocalDate.parse(row.getStart(), dateformat);
//            LocalDate rowDate = LocalDate.parse(row.getStart(), dateformat);
            LocalDate rowDate = LocalDate.parse(row.getStart2().format(dateformat));
            return rowDate.isAfter(currDate.minusDays(1)) && rowDate.isBefore(cbEndDate);
        });
        tableView.setItems(filteredData);

        labelStartBound.setText(currDate.format(labelformat));
        labelEndBound.setText(cbEndDate.format(labelformat));
    }

    private void nextWeek(LocalDate cbStartDate) {
        currDate = currDate.plusWeeks(1);
        FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
        filteredData.setPredicate(row -> {
//            LocalDate rowDate = LocalDate.parse(row.getStart(), dateformat);
            LocalDate rowDate = LocalDate.parse(row.getStart2().format(dateformat));
            return rowDate.isAfter(cbStartDate.minusDays(1)) && rowDate.isBefore(currDate);
        });
        tableView.setItems(filteredData);
        labelStartBound.setText(cbStartDate.format(labelformat));
        labelEndBound.setText(currDate.format(labelformat));
    }

    private void previousWeek(LocalDate cbEndDate) {
        currDate = currDate.minusWeeks(1);
        FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
        filteredData.setPredicate(row -> {
//            LocalDate rowDate = LocalDate.parse(row.getStart(), dateformat);
            LocalDate rowDate = LocalDate.parse(row.getStart2().format(dateformat));
            return rowDate.isAfter(currDate.minusDays(1)) && rowDate.isBefore(cbEndDate);
        });
        tableView.setItems(filteredData);
        labelStartBound.setText(currDate.format(labelformat));
        labelEndBound.setText(cbEndDate.format(labelformat));
    }

    /**
     * Initializes the controller class.This method is automatically called
     * after the fxml file has been loaded. Add observable list data to table
     * view here
     *
     * @param mainApp
     */
    public void setMainController(jCalendar mainApp, User currentUse) {

        this.mainApp = mainApp;
        this.currentUser = currentUser;

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
        try {
            appointmentList.addAll(mainApp.getAppointmentData());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Exception error with getting all appointment data");
            Logger.getLogger(AppointmentListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.getItems().clear();
        tableView.setItems(appointmentList);

        //LISTEN FOR SELECTION CHANGES AND SHOW THE APPT DETAILS WHEN CHANGED
    }

}
