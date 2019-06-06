/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import jCalendar.DAO.DBConnection;
import jCalendar.jCalendar;
import jCalendar.model.Appointment;
import jCalendar.model.Customer;
import jCalendar.model.User;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class AppointmentEntryScreenController {

    @FXML    private TableView<Appointment> ApptTable;
    @FXML    private TableColumn<Appointment, String> tContact;
    @FXML    private TableColumn<Appointment, String> tLocation;
    @FXML    private TableColumn<Appointment, String> tDescription;
    @FXML    private TableColumn<Appointment, String> tTitle;
    @FXML    private TableColumn<Appointment, ZonedDateTime> tEndDate;
    @FXML    private TableColumn<Appointment, LocalDateTime> tStartDate;
    @FXML    private TableColumn<Appointment, Customer> tCustomer;
    @FXML    private Label labelAppt;
    
    @FXML    private TableView<Customer> tblApptCustomer;
    @FXML    private TableColumn<Customer, Integer> tblCustomerId;
    @FXML    private TableColumn<Customer, String> tblCustName;
    @FXML    private DatePicker datePicker;
    @FXML    private ComboBox<String> comboEnd;
    @FXML    private ComboBox<String> comboStart;
    @FXML    private TextField type;
    @FXML    private TextField zoneID;
    @FXML    private TextField txtLocation;
    @FXML    private TextField txtTitle;
    
    @FXML    private Button btnApptSave;
    @FXML    private Button btnApptCancel;
    
    private jCalendar mainApp;
    private User currentUser;
    private boolean okClicked = false;
    
    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final ZoneId newzid = ZoneId.systemDefault();
 
    private Stage dialogStage;
    private Appointment selectedAppt;
    private final ZoneId zid = ZoneId.systemDefault();
    private ObservableList<Appointment> appointmentList;
    private ObservableList<Customer> masterData = FXCollections.observableArrayList();
    private final ObservableList<String> startTimes = FXCollections.observableArrayList();
    private final ObservableList<String> endTimes = FXCollections.observableArrayList();
    private final DateTimeFormatter dateDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    ObservableList<Appointment> apptTimeList;
    
    public AppointmentEntryScreenController() {

    }
    
    public boolean isOkClicked() {
	return okClicked;
    }

    
    @FXML
    void handleApptSave(ActionEvent event) {
	if (okClicked) {
//	    updateAppointment();
	} else {
//	    saveAppointment()
	}
    }
    
    @FXML
    void handleApptCancel(ActionEvent event) {
	System.out.println("cancel");

    }
    
    /**
     * Initializes the controller class.
     * @param dialogStage
     * @param currentUser
     */
    public void setDialogStage(Stage dialogStage, User currentUser) {
        this.dialogStage = dialogStage;
        this.currentUser = currentUser;
	//selectedAppt = ApptTable.getSelectionModel().getSelectedItem();
	
	
	// Listener to load Appointment Details
//	ApptTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//	    if (newSelection != null) {
//		System.out.println(newSelection);
//		showAppointmentDetails(newSelection);
//	    }
//	});
	
	// ====================================APPOINTMENT ENTRY DETAILS======================================================='
//	LocalTime time = LocalTime.of(8, 0);
//
//	do {
//	    startTimes.add(time.format(timeDTF));
//	    endTimes.add(time.format(timeDTF));
//	    time = time.plusMinutes(15);
//	} while (!time.equals(LocalTime.of(17, 15)));
//	startTimes.remove(startTimes.size() - 1);
//	endTimes.remove(0);
//
//	datePicker.setValue(LocalDate.now());
//
//	comboStart.setItems(startTimes);
//	comboEnd.setItems(endTimes);
//	comboStart.getSelectionModel().select(LocalTime.of(8, 0).format(timeDTF));
//	comboEnd.getSelectionModel().select(LocalTime.of(8, 15).format(timeDTF));

	
	// Load customer details
//	tblCustName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
//	masterData = populateCustomerList();
//	tblApptCustomer.setItems(masterData);
//	
	
    }    
	
        protected ObservableList<Customer> populateCustomerList() {

	int tCustomerId;
	String tCustomerName;

	ObservableList<Customer> customerList = FXCollections.observableArrayList();
	try (
		PreparedStatement statement = DBConnection.getConn().prepareStatement(
			"SELECT customer.customerId, customer.customerName "
			+ "FROM customer, address, city, country "
			+ "WHERE customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId");
		ResultSet rs = statement.executeQuery();) {

	    while (rs.next()) {
		tCustomerId = rs.getInt("customer.customerId");

		tCustomerName = rs.getString("customer.customerName");

		customerList.add(new Customer(tCustomerId, tCustomerName));

	    }

	} catch (SQLException sqe) {
	    System.out.println("Check your SQL");
	    sqe.printStackTrace();
	} catch (Exception e) {
	    System.out.println("Something besides the SQL went wrong.");
	}

	return customerList;

    }


    private void updateAppointment() {

    }

    private void saveAppointment() {

    }
    
    public void showAppointmentDetails(Appointment appointment) {
	okClicked = true;
	selectedAppt = appointment;
	
	String start = appointment.getStart();
	
	LocalDateTime startLDT = LocalDateTime.parse(start, dateDTF);
	String end = appointment.getEnd();
	LocalDateTime endLDT = LocalDateTime.parse(end, dateDTF);
	
	labelAppt.setText("Edit Appointment");
	txtTitle.setText(appointment.getTitle());
//	tblApptCustomer.getSelectionModel().select(appointment.getCustomer());
	datePicker.setValue(LocalDate.parse(appointment.getStart(),dateDTF));
	type.setText(appointment.getDescription());
	comboStart.getSelectionModel().select(startLDT.toLocalTime().format(timeDTF));
	comboEnd.getSelectionModel().select(endLDT.toLocalTime().format(timeDTF));
	txtLocation.setText(appointment.getLocation());
	
	
    }
    

    
    
}
