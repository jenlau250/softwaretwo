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
import jCalendar.utilities.Loggerutil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class AppointmentScreenController {
    
    @FXML    private TableView<Appointment> ApptTable;
    @FXML    private TableColumn<Appointment, String> tLocation;
    @FXML    private TableColumn<Appointment, String> tType;
    @FXML    private TableColumn<Appointment, String> tTitle;
    @FXML    private TableColumn<Appointment, String> tUser;
    @FXML    private TableColumn<Appointment, LocalDateTime> tEndDate;
    @FXML    private TableColumn<Appointment, ZonedDateTime> tStartDate;
    @FXML    private TableColumn<Appointment, String> tCustomer;
    @FXML    private Label labelAppt;
    @FXML    private Label labelMainAppt;
    @FXML    private Label labelStartBound;
    @FXML    private Label labelEndBound;
    @FXML    private VBox apptVBox;

    @FXML    private DatePicker datePicker;
    @FXML    private ComboBox<String> comboEnd;
    @FXML    private ComboBox<String> comboStart;
    @FXML    private ComboBox<Customer> comboCustomer;
    @FXML    private ComboBox<String> comboType;
    @FXML    private TextField type;
    @FXML    private TextField txtLocation;
    @FXML    private TextField txtTitle;
    @FXML    private ChoiceBox<String> choiceWeekMonth;
    @FXML    private Button btnBack;
    @FXML    private Button btnNext;
    
    @FXML    private Button btnApptDel;
    @FXML    private Button btnApptAdd;
    @FXML    private Button btnApptUpdate;
    @FXML    private Button btnApptSave;
    @FXML    private Button btnApptCancel;

    private jCalendar mainApp;
    private User currentUser;

    private final DateTimeFormatter timeformat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private final DateTimeFormatter dateformat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final DateTimeFormatter labelformat = DateTimeFormatter.ofPattern("E MMM d, yyyy");
    private final ObservableList<String> startTimes = FXCollections.observableArrayList();
    private final ObservableList<String> endTimes = FXCollections.observableArrayList();
    private final ZoneId newZoneId = ZoneId.systemDefault();
    private LocalDate currDate;

    private boolean editClicked;

    private Appointment selectedAppt;
    private ObservableList<Appointment> appointmentList;
    private ObservableList<Customer> masterData = FXCollections.observableArrayList();
    private ObservableList<String> typeOptions = FXCollections.observableArrayList();
    
    private final static Logger logger = Logger.getLogger(Loggerutil.class.getName());

    public AppointmentScreenController() {

    }
 
    @FXML
    void handleApptDelete(ActionEvent event) {
	Appointment selAppt = ApptTable.getSelectionModel().getSelectedItem();

	if (selAppt == null) {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle("Nothing selected");
	    alert.setHeaderText("No appointment was selected to delete");
	    alert.setContentText("Please select an appointment to delete");
	    alert.showAndWait();
	} else {
	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle("Confirm Deletion");
	    alert.setHeaderText("Are you sure you want to delete " + selAppt.getTitle() + " scheduled for " + selAppt.getCustomer().getCustomerName() + "?");
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
	labelAppt.setText("Edit Appointment");
	if (selAppt != null) {
	    editClicked = true;
	    btnApptCancel.setDisable(false);
	    btnApptSave.setDisable(false);
	    btnApptUpdate.setDisable(true);
	    btnApptAdd.setDisable(true);
	    btnApptDel.setDisable(true);
	    apptVBox.setVisible(true);

	} else {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle("Nothing selected");
	    alert.setHeaderText("No appointment was selected");
	    alert.setContentText("Please select an appointment to update");
	    alert.showAndWait();
	}

    }
    
    @FXML
    void handleApptAdd(ActionEvent event) {
	apptVBox.setVisible(true);
	ApptTable.setDisable(true);
	editClicked = false;
	clearApptFields();
	labelAppt.setText("Add Appointment");
	
	btnApptCancel.setDisable(false);
	btnApptSave.setDisable(false);
	btnApptUpdate.setDisable(true);
	btnApptAdd.setDisable(true);
	btnApptDel.setDisable(true);
	
    }

    @FXML
    void handleApptSave(ActionEvent event) {
	
	apptVBox.setVisible(false);
	btnApptUpdate.setDisable(false);
	btnApptAdd.setDisable(false);
	btnApptDel.setDisable(false);
	ApptTable.setDisable(false);

	LocalDate localDate = datePicker.getValue();

	LocalTime startTime = LocalTime.parse(comboStart.getSelectionModel().getSelectedItem(), timeformat);
	LocalTime endTime = LocalTime.parse(comboEnd.getSelectionModel().getSelectedItem(), timeformat);
	LocalDateTime startDate = LocalDateTime.of(localDate, startTime);
	LocalDateTime endDate = LocalDateTime.of(localDate, endTime);
	ZonedDateTime startUTC = startDate.atZone(newZoneId).withZoneSameInstant(ZoneId.of("UTC"));
	ZonedDateTime endUTC = endDate.atZone(newZoneId).withZoneSameInstant(ZoneId.of("UTC"));


	if (validateApptOverlap(startUTC, endUTC, currentUser)) {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle("Appointment Overlap");
	    alert.setHeaderText("Warning: Appointment was not saved");
	    alert.setContentText("Overlaps with existing appointment. Please check again.");
	    alert.showAndWait();

	} else {

	    if (labelAppt.getText().contains("Edit")) {
		System.out.println("Updating..");
		updateAppointment();

	    } else if (labelAppt.getText().contains("Add")) {
		System.out.println("Adding..");
		saveAppointment();
	    }
	}

    }

    
    @FXML
    void handleApptCancel(ActionEvent event) {
	 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle("Confirm cancelling");
	    alert.setHeaderText("Are you sure you want to cancel?");
	    alert.showAndWait() 
		    .filter(response -> response == ButtonType.OK)
		    .ifPresent(response -> {
			btnApptUpdate.setDisable(false);
			btnApptAdd.setDisable(false);
			btnApptDel.setDisable(false);
			ApptTable.setDisable(false);
			apptVBox.setVisible(false);
		    });
	}
    
    /**
     * Initializes the controller class.
     * @param mainApp
     * @param currentUser
     */
    public void setAppointmentScreen(jCalendar mainApp, User currentUser) {

	this.mainApp = mainApp;
	this.currentUser = currentUser;
	
	tCustomer.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getCustomer().getCustomerName()));
	tStartDate.setCellValueFactory(new PropertyValueFactory<>("start"));
	tEndDate.setCellValueFactory(new PropertyValueFactory<>("end"));
	tTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
	tUser.setCellValueFactory(new PropertyValueFactory<>("user"));
	tType.setCellValueFactory(new PropertyValueFactory<>("type"));
	tLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
	appointmentList = FXCollections.observableArrayList();
	
	populateAppointments();
	ApptTable.getItems().setAll(appointmentList);

	// Load Appointment Details
	ApptTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	    if (newSelection != null) {
		showAppointmentDetails(newSelection);
	    }
	});

	
	// Create list of appointment start and end times
	// EXCEPTION: Scheduling hours are only allowed between 8am and 5pm local time.
	DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
		LocalTime time = LocalTime.of(8, 0);
		
	do {
		startTimes.add(time.format(timeDTF));
		endTimes.add(time.format(timeDTF));
		time = time.plusMinutes(15);
	} while(!time.equals(LocalTime.of(17, 15)));
		startTimes.remove(startTimes.size() - 1);
		endTimes.remove(0);
        
        datePicker.setValue(LocalDate.now());

        comboStart.setItems(startTimes);
	comboEnd.setItems(endTimes);
	
	// Load customer details
	masterData = populateCustomers();
	comboCustomer.setItems(masterData);
	comboCustomer.setConverter(new StringConverter<Customer>() {
	    @Override
	    public String toString(Customer object) {
		return object.getCustomerName();
	    }
	    @Override
	    public Customer fromString(String string) {
		return comboCustomer.getItems().stream().filter(ap -> ap.getCustomerName().equals(string)).findFirst().orElse(null);
	    }
	});

	// SET DEFAULTS
	comboStart.getSelectionModel().select(LocalTime.of(8, 0).format(timeDTF));
	comboEnd.getSelectionModel().select(LocalTime.of(8, 15).format(timeDTF));

	btnApptCancel.setDisable(true);
	btnApptSave.setDisable(true);
	labelMainAppt.setText("Click buttons below to add, update, or delete an appointment");
	labelAppt.setText(null);
	labelStartBound.setText(null);
	labelEndBound.setText(null);
	apptVBox.setVisible(false);
	choiceWeekMonth.setValue("Monthly");
		
	currDate = LocalDate.now();
	nextMonth(currDate);
	
	// Add monthly or weekly filter to tableview
        choiceWeekMonth.setItems(FXCollections.observableArrayList("Weekly", "Monthly")); 
        choiceWeekMonth.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

	  // if the item of the list is changed 
	  public void changed(ObservableValue ov, Number value, Number new_value) {
	      LocalDate cbStartDate = currDate;
	      if (choiceWeekMonth.getSelectionModel().getSelectedIndex() == 0) {
		  nextWeek(cbStartDate);
	      } else if (choiceWeekMonth.getSelectionModel().getSelectedIndex() == 1) {
		  nextMonth(cbStartDate);
	      }
	    }
      });
	
	btnNext.setOnAction((evt) -> {
	    LocalDate cbStartDate = currDate;
	    
	    if (choiceWeekMonth.getSelectionModel().getSelectedIndex() == 0) {
		nextWeek(cbStartDate);
	    } else if (choiceWeekMonth.getSelectionModel().getSelectedIndex() == 1) {
		nextMonth(cbStartDate);
	    }
	});
		
	btnBack.setOnAction((evt) -> {
	    LocalDate cbEndDate = currDate;
	    if (choiceWeekMonth.getSelectionModel().getSelectedIndex() == 0) {
		previousWeek(cbEndDate);
	    } else if (choiceWeekMonth.getSelectionModel().getSelectedIndex() == 1) {
		previousMonth(cbEndDate);
	    }
	});
	// create combo type box
	typeOptions.addAll("New", "Follow-up", "Resolution", "Final", "Other");
	comboType.setItems(typeOptions);
    }

    
    private void nextMonth(LocalDate cbStartDate) {
	
	// Lambda expression used to filter data
	currDate = currDate.plusMonths(1);
	FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
	filteredData.setPredicate(row -> {
	    LocalDate rowDate = LocalDate.parse(row.getStart(), dateformat);
	    return rowDate.isAfter(cbStartDate.minusDays(1)) && rowDate.isBefore(currDate);
	});
	ApptTable.setItems(filteredData);

	labelStartBound.setText(cbStartDate.format(labelformat));
	labelEndBound.setText(currDate.format(labelformat));
    }
    
    private void previousMonth(LocalDate cbEndDate) {
	currDate = currDate.minusMonths(1);
	FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
	filteredData.setPredicate(row -> {
	    LocalDate rowDate = LocalDate.parse(row.getStart(), dateformat);
	    return rowDate.isAfter(currDate.minusDays(1)) && rowDate.isBefore(cbEndDate);
	});
	ApptTable.setItems(filteredData);

	labelStartBound.setText(currDate.format(labelformat));
	labelEndBound.setText(cbEndDate.format(labelformat));
    }

     private void nextWeek(LocalDate cbStartDate) {
	currDate = currDate.plusWeeks(1);
	FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
	filteredData.setPredicate(row -> {
	    LocalDate rowDate = LocalDate.parse(row.getStart(), dateformat);
	    return rowDate.isAfter(cbStartDate.minusDays(1)) && rowDate.isBefore(currDate);
	});
	ApptTable.setItems(filteredData);
	labelStartBound.setText(cbStartDate.format(labelformat));
	labelEndBound.setText(currDate.format(labelformat));
    }
    
    private void previousWeek(LocalDate cbEndDate) {
	currDate = currDate.minusWeeks(1);
	FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
	filteredData.setPredicate(row -> {
	    LocalDate rowDate = LocalDate.parse(row.getStart(), dateformat);
	    return rowDate.isAfter(currDate.minusDays(1)) && rowDate.isBefore(cbEndDate);
	});
	ApptTable.setItems(filteredData);
	labelStartBound.setText(currDate.format(labelformat));
	labelEndBound.setText(cbEndDate.format(labelformat));
    }
    
    protected ObservableList<Customer> populateCustomers() {

	int pCustomerId;
	String pCustomerName;

	ObservableList<Customer> customerList = FXCollections.observableArrayList();
	try (
		PreparedStatement statement = DBConnection.getConn().prepareStatement(
			"SELECT customer.customerId, customer.customerName "
			+ "FROM customer, address, city, country "
			+ "WHERE customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId");
		ResultSet rs = statement.executeQuery();) {
	    while (rs.next()) {
		pCustomerId = rs.getInt("customer.customerId");
		pCustomerName = rs.getString("customer.customerName");
		customerList.add(new Customer(pCustomerId, pCustomerName));
	    }
	} catch (SQLException sqe) {
	    System.out.println("Check SQL Exception");
	    sqe.printStackTrace();
	} catch (Exception e) {
	    System.out.println("Check Exception");
	}
	return customerList;

    }

 
    public void showAppointmentDetails(Appointment appointment) {
	editClicked = true;
	selectedAppt = appointment;

	String start = appointment.getStart();
	String end = appointment.getEnd();
	LocalDateTime startLDT = LocalDateTime.parse(start, dateformat);
	LocalDateTime endLDT = LocalDateTime.parse(end, dateformat);
  
	txtTitle.setText(appointment.getTitle());
	datePicker.setValue(LocalDate.parse(appointment.getStart(), dateformat));
	txtLocation.setText(appointment.getLocation());

	comboStart.getSelectionModel().select(startLDT.toLocalTime().format(timeformat));
	comboEnd.getSelectionModel().select(endLDT.toLocalTime().format(timeformat));
	comboCustomer.getSelectionModel().select(appointment.getCustomer());
	comboType.getSelectionModel().select(appointment.getType());
    }

    private void populateAppointments() {

	try {
	    PreparedStatement ps = DBConnection.getConn().prepareStatement(
		    "SELECT appointment.appointmentId, "
		    + "appointment.customerId, "
		    + "appointment.title, "
		    + "appointment.type, "
		    + "appointment.location, "
		    + "appointment.start, "
		    + "appointment.end, "
		    + "customer.customerId, "
		    + "customer.customerName, "
		    + "appointment.createdBy "
		    + "FROM appointment, customer "
		    + "WHERE appointment.customerId = customer.customerId "
		    + "ORDER BY `start`");

	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {

		String tAppointmentId = rs.getString("appointment.appointmentId");
		Timestamp tsStart = rs.getTimestamp("appointment.start");
		Timestamp tsEnd = rs.getTimestamp("appointment.end");

		ZonedDateTime newzdtStart = tsStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
		ZonedDateTime newzdtEnd = tsEnd.toLocalDateTime().atZone(ZoneId.of("UTC"));

		ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(newZoneId);
		ZonedDateTime newLocalEnd = newzdtEnd.withZoneSameInstant(newZoneId);

		String tTitle = rs.getString("appointment.title");
		String tType = rs.getString("appointment.type");
		String tLocation = rs.getString("appointment.location");
		Customer sCustomer = new Customer(rs.getInt("appointment.customerId"), rs.getString("customer.customerName"));

		String sContact = rs.getString("appointment.createdBy");

		appointmentList.add(new Appointment(tAppointmentId, newLocalStart.format(dateformat), newLocalEnd.format(dateformat), tTitle, tType, tLocation, sCustomer, sContact));
	    }
	} catch (SQLException ex) {
	    logger.log(Level.SEVERE,"Check SQL exception");
	} catch (Exception e) {
	    logger.log(Level.SEVERE,"Check exception");
	}

    }

    private void deleteAppointment(Appointment appointment) {
	try {
	    PreparedStatement pst = DBConnection.getConn().prepareStatement("DELETE appointment.* FROM appointment WHERE appointment.appointmentId = ?");
	    pst.setString(1, appointment.getAppointmentId());
	    pst.executeUpdate();

	} catch (SQLException e) {
	    logger.log(Level.WARNING,"Deleting appointment not successful");
	}
    }

    private void updateAppointment() {
	
	editClicked = true;
	
	LocalDate localDate = datePicker.getValue();
	
	LocalTime startTime = LocalTime.parse(comboStart.getSelectionModel().getSelectedItem(), timeformat);
	LocalTime endTime = LocalTime.parse(comboEnd.getSelectionModel().getSelectedItem(), timeformat);
	LocalDateTime startDate = LocalDateTime.of(localDate, startTime);
	LocalDateTime endDate = LocalDateTime.of(localDate, endTime);
	
	ZonedDateTime startUTC = startDate.atZone(newZoneId).withZoneSameInstant(ZoneId.of("UTC"));
	ZonedDateTime endUTC = endDate.atZone(newZoneId).withZoneSameInstant(ZoneId.of("UTC"));
	
	Timestamp startsql = Timestamp.valueOf(startUTC.toLocalDateTime()); 
	Timestamp endsql = Timestamp.valueOf(endUTC.toLocalDateTime());       

	try {

	    PreparedStatement pst = DBConnection.getConn().prepareStatement("UPDATE appointment "
		    + "SET customerId = ?, title = ?, type = ?, location = ?, start = ?, end = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? "
		    + "WHERE appointmentId = ?");

	    pst.setInt(1, comboCustomer.getValue().getCustomerId());
	    pst.setString(2, txtTitle.getText());
	    pst.setString(3, comboType.getValue());
	    pst.setString(4, txtLocation.getText());
	    pst.setTimestamp(5, startsql);
	    pst.setTimestamp(6, endsql);
	    pst.setString(7, currentUser.getUserName());
	    pst.setString(8, selectedAppt.getAppointmentId());
	    int result = pst.executeUpdate();
	    if (result == 1) {
		logger.log(Level.INFO, "Customer update complete");
		mainApp.showAppointmentScreen(currentUser);
	    } else {
		logger.log(Level.WARNING, "Customer update was not successful.");
	    }
	} catch (SQLException ex) {
	    ex.printStackTrace();
	} 
	
    }
    
    private void clearApptFields(){

	comboCustomer.setValue(null);
	comboStart.getSelectionModel().select(LocalTime.of(8, 0).format(timeformat));
	comboEnd.getSelectionModel().select(LocalTime.of(8, 15).format(timeformat));
	datePicker.setValue(LocalDate.now());
	txtTitle.setText("");
	comboType.setValue(null);
	txtLocation.setText("");
		
	
    }
   
    public static Boolean validateApptOverlap(ZonedDateTime sDate, ZonedDateTime eDate, User user) {
	Boolean overlap = false;
	try {
	    PreparedStatement ps = DBConnection.getConn().prepareStatement(
		    "SELECT * FROM appointment "
		    + "WHERE (? BETWEEN start AND end OR ? BETWEEN start AND end OR ? < start AND ? > end) "
	    		    + "AND (createdBy = ?)"
	    );
	    ps.setTimestamp(1, Timestamp.valueOf(sDate.toLocalDateTime()));
	    ps.setTimestamp(2, Timestamp.valueOf(eDate.toLocalDateTime()));
	    ps.setTimestamp(3, Timestamp.valueOf(sDate.toLocalDateTime()));
	    ps.setTimestamp(4, Timestamp.valueOf(eDate.toLocalDateTime()));
	    ps.setString(5, user.getUserName());
	    ResultSet rs = ps.executeQuery();

	    if (rs.absolute(1)) {
		overlap = true;
	    }

	} catch (SQLException ex) {
	    System.out.println("Check SQL Exception " + ex);
	}

	return overlap;

    }
	
    private void saveAppointment() {
	System.out.println(editClicked);
	
	LocalDate selDate = datePicker.getValue();
	
	LocalTime selStart = LocalTime.parse(comboStart.getSelectionModel().getSelectedItem(), timeformat);
	LocalTime selEnd = LocalTime.parse(comboEnd.getSelectionModel().getSelectedItem(), timeformat);

	LocalDateTime startDT = LocalDateTime.of(selDate, selStart);
	LocalDateTime endDT = LocalDateTime.of(selDate, selEnd);
	// Convert to UTC for database
	ZonedDateTime startUTC = startDT.atZone(newZoneId).withZoneSameInstant(ZoneId.of("UTC"));
	ZonedDateTime endUTC = endDT.atZone(newZoneId).withZoneSameInstant(ZoneId.of("UTC"));
        // Convert to SQL 
	Timestamp startsql = Timestamp.valueOf(startUTC.toLocalDateTime()); 
	Timestamp endsql = Timestamp.valueOf(endUTC.toLocalDateTime());     

	try {

	    PreparedStatement ps = DBConnection.getConn().prepareStatement("INSERT INTO appointment "
		    + "(customerId, title, type, location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)"
		    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)");
	    
	    ps.setInt(1, comboCustomer.getValue().getCustomerId());
	    ps.setString(2, txtTitle.getText());
	    ps.setString(3, comboType.getValue());
	    ps.setString(4, txtLocation.getText());
	    ps.setString(5, "");
	    ps.setString(6, "");
	    ps.setTimestamp(7, startsql);
	    ps.setTimestamp(8, endsql);
	    ps.setString(9, currentUser.getUserName());
	    ps.setString(10, currentUser.getUserName());
	    int result = ps.executeUpdate();
	    
	    if (result == 1) {
		logger.log(Level.INFO, "Appointment save complete for " + comboCustomer.getValue().getCustomerName());
		mainApp.showAppointmentScreen(currentUser);
	    } else {
		logger.log(Level.WARNING, "Appointment save was unsuccessful");
	    }
	} catch (SQLException ex) {
	    ex.printStackTrace();
	}

    }
    
}
