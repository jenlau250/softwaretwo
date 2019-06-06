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
import java.io.IOException;
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
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class AppointmentScreenController {
    
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
    
    @FXML    private Button btnApptDel;
    @FXML    private Button btnApptAdd;
    @FXML    private Button btnApptUpdate;
    @FXML    private Button btnApptSave;
    @FXML    private Button btnApptCancel;

    private jCalendar mainApp;
    private User currentUser;
    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final ZoneId newzid = ZoneId.systemDefault();
    private SplitPane splitPane;
    

    boolean okClicked;
    Appointment selectedAppt;
    private final ZoneId zid = ZoneId.systemDefault();
    private ObservableList<Appointment> appointmentList;
    private ObservableList<Customer> masterData = FXCollections.observableArrayList();
    private final ObservableList<String> startTimes = FXCollections.observableArrayList();
    private final ObservableList<String> endTimes = FXCollections.observableArrayList();
    private final DateTimeFormatter dateDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    ObservableList<Appointment> apptTimeList;
    
    public AppointmentScreenController() {

    }
 
    @FXML
    void handleApptDelete(ActionEvent event) {
	Appointment selAppt = ApptTable.getSelectionModel().getSelectedItem();

	if (selAppt == null) {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle("No Selection");
	    alert.setHeaderText("No Appointment selected for Deletion");
	    alert.setContentText("Please select an Appointment in the Table to delete");
	    alert.showAndWait();
	} else {
	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle("Confirm Deletion");
	    alert.setHeaderText("Are you sure you want to delete " + selAppt.getTitle() + " scheduled for " + selAppt.getStart() + "?");
	    alert.showAndWait() 
		    .filter(response -> response == ButtonType.OK)
		    .ifPresent(response -> {
			deleteAppointment(selAppt);
			mainApp.showAppointmentScreen(currentUser);}
		    );
	}
    }

    @FXML 
    void handleApptEdit(ActionEvent event) {
	Appointment selAppt = ApptTable.getSelectionModel().getSelectedItem();

	if (selAppt != null) {
	    
	    boolean okClicked;
	    mainApp.showAppointmentEntryScreen(selAppt, currentUser);
	   //mainApp.showAppointmentEntryScreen(currentUser);



	} else {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle("No Selection");
	    alert.setHeaderText("No Appointment selected");
	    alert.setContentText("Please select an Appointment in the Table");
	    alert.showAndWait();
	}

    }
    
    @FXML 
    void handleApptAdd(ActionEvent event) {
	Appointment selAppt = ApptTable.getSelectionModel().getSelectedItem();
//        boolean okClicked = mainApp.showAppointmentEntryScreen(currentUser);
        mainApp.showAppointmentScreen(currentUser);
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
     * @param mainApp
     * @param currentUser
     */
    public void setAppointmentScreen(jCalendar mainApp, User currentUser) {

	this.mainApp = mainApp;
	this.currentUser = currentUser;
//	selectedAppt = ApptTable.getSelectionModel().getSelectedItem();
	
	tCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
	tStartDate.setCellValueFactory(new PropertyValueFactory<>("start"));
	tEndDate.setCellValueFactory(new PropertyValueFactory<>("end"));
	tTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
	tDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
	tLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
	tContact.setCellValueFactory(new PropertyValueFactory<>("user"));

	appointmentList = FXCollections.observableArrayList();
	populateAppointmentList();
	FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList, t -> true);
	ApptTable.getItems().setAll(filteredData);

	// Listener to load Appointment Details
	ApptTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	    if (newSelection != null) {
		System.out.println(newSelection);
		showAppointmentDetails(newSelection);
	    }
	});

	
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
//
//	
//	
//	// Load customer details
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

    private void populateAppointmentList() {

	try {
	    PreparedStatement ps = DBConnection.getConn().prepareStatement(
		    "SELECT appointment.appointmentId, appointment.customerId, appointment.title, appointment.description, appointment.location, "
		    + "appointment.`start`, appointment.`end`, customer.customerId, customer.customerName, appointment.createdBy "
		    + "FROM appointment, customer "
		    + "WHERE appointment.customerId = customer.customerId "
		    + "ORDER BY `start`");

	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {

		String tAppointmentId = rs.getString("appointment.appointmentId");
		Timestamp tsStart = rs.getTimestamp("appointment.start");
		ZonedDateTime newzdtStart = tsStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
		ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(newzid);

		Timestamp tsEnd = rs.getTimestamp("appointment.end");
		ZonedDateTime newzdtEnd = tsEnd.toLocalDateTime().atZone(ZoneId.of("UTC"));
		ZonedDateTime newLocalEnd = newzdtEnd.withZoneSameInstant(newzid);

		String tTitle = rs.getString("appointment.title");
		String tDesc = rs.getString("appointment.description");
		String tLocation = rs.getString("appointment.location");
		Customer sCustomer = new Customer(rs.getInt("appointment.customerId"), rs.getString("customer.customerName"));
		
		//String custName = String.valueOf(tCustomer.getCustomerName());
		//add contact/user field
		
		String sContact = rs.getString("appointment.createdBy");

		appointmentList.add(new Appointment(tAppointmentId, newLocalStart.format(timeDTF), newLocalEnd.format(timeDTF), tTitle, tDesc, tLocation, sCustomer, sContact));
		//public Appointment(String appointmentId, String start, String end, String title, String description, String location, Customer customer, String user) 
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, ex);
	} catch (Exception e) {
	    Logger.getLogger(AppointmentScreenController.class.getName()).log(Level.SEVERE, null, e);
	}

    }

    private void deleteAppointment(Appointment appointment) {
	try {
	    PreparedStatement pst = DBConnection.getConn().prepareStatement("DELETE appointment.* FROM appointment WHERE appointment.appointmentId = ?");
	    pst.setString(1, appointment.getAppointmentId());
	    pst.executeUpdate();

	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    private void updateAppointment() {

    }

    private void saveAppointment() {

    }
    
//    public void showAppointmentDetails(Appointment appointment) {
//	okClicked = true;
//	selectedAppt = ApptTable.getSelectionModel().getSelectedItem();
//	
//	String start = appointment.getStart();
//	
//	LocalDateTime startLDT = LocalDateTime.parse(start, dateDTF);
//	String end = appointment.getEnd();
//	LocalDateTime endLDT = LocalDateTime.parse(end, dateDTF);
//	
//	labelAppt.setText("Edit Appointment");
//	txtTitle.setText(appointment.getTitle());
////	tblApptCustomer.getSelectionModel().select(appointment.getCustomer());
//	datePicker.setValue(LocalDate.parse(appointment.getStart(),dateDTF));
//	type.setText(appointment.getDescription());
//	comboStart.getSelectionModel().select(startLDT.toLocalTime().format(timeDTF));
//	comboEnd.getSelectionModel().select(endLDT.toLocalTime().format(timeDTF));
//	txtLocation.setText(appointment.getLocation());
	
//	comboStart.getSelectionModel().select(parseInstantToIndex(appointment.getStart(), 0));
//        comboEnd.getSelectionModel().select(parseInstantToIndex(appointment.getEnd(), 1));
	
//    }
    
      
//    }
    
    
    
}
