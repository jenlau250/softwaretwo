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
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.ChoiceBox;
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
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class AppointmentScreenController {
    
    @FXML    private TableView<Appointment> ApptTable;
//    @FXML    private TableColumn<Appointment, String> tContact;
    @FXML    private TableColumn<Appointment, String> tLocation;
    @FXML    private TableColumn<Appointment, String> tDescription;
    @FXML    private TableColumn<Appointment, String> tTitle;
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

    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private final DateTimeFormatter labelDTF = DateTimeFormatter.ofPattern("E MMM d, yyyy");
    private final ObservableList<String> startTimes = FXCollections.observableArrayList();
    private final ObservableList<String> endTimes = FXCollections.observableArrayList();
    private final DateTimeFormatter dateDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final ZoneId newzid = ZoneId.systemDefault();

    boolean editClicked;
    LocalDate currDate;
    Appointment selectedAppt;
    private ObservableList<Appointment> appointmentList;
    private ObservableList<Customer> masterData = FXCollections.observableArrayList();
    private ObservableList<String> typeOptions = FXCollections.observableArrayList();

   
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
	    alert.setTitle("No Selection");
	    alert.setHeaderText("No Appointment selected");
	    alert.setContentText("Please select an Appointment in the Table");
	    alert.showAndWait();
	}

    }
    
    @FXML
    void handleApptAdd(ActionEvent event) {
//        mainApp.showAppointmentScreen(currentUser);
	apptVBox.setVisible(true);
	// make appointment table unselectable
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
	System.out.println("trigger is " + editClicked);
	apptVBox.setVisible(false);
	btnApptUpdate.setDisable(false);
	btnApptAdd.setDisable(false);
	btnApptDel.setDisable(false);
	ApptTable.setDisable(false);

	if (labelAppt.getText().contains("Edit")) {
	    System.out.println("updating..");
	    updateAppointment();

	} else if (labelAppt.getText().contains("Add")) {
	    System.out.println("adding..");
	    saveAppointment();

	}
	
	
//	appointmentList.clear();
//	populateAppointmentList();
//	FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList, t -> true);
//	ApptTable.getItems().setAll(filteredData);

    }

    
    @FXML
    void handleApptCancel(ActionEvent event) {
	 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle("Confirm Cancel");
	    alert.setHeaderText("Are you sure you want to cancel?");
	    alert.showAndWait() 
		    .filter(response -> response == ButtonType.OK)
		    .ifPresent(response -> {
			btnApptUpdate.setDisable(false);
			btnApptAdd.setDisable(false);
			btnApptDel.setDisable(false);
			ApptTable.setDisable(false);
			apptVBox.setVisible(false);
//			mainApp.showAppointmentScreen(currentUser);}
		    });
	}

    
//    public void hideInputFields() {
//	
//	labelAppt.setVisible(false);
//	txtTitle.setVisible(false);
//	type.setVisible(false);
//	txtLocation.setVisible(false);
//	datePicker.setVisible(false);
//	comboCustomer.setVisible(false);
//	comboEnd.setVisible(false);
//	comboStart.setVisible(false);
//	btnApptSave.setVisible(false);
//	btnApptCancel.setVisible(false);
//	
//    }
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
	tDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
	tLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
//	tContact.setCellValueFactory(new PropertyValueFactory<>("user"));

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
	// set to select data pull timesD
	
	//default set times 8:00 - 8:15am
	comboStart.getSelectionModel().select(LocalTime.of(8, 0).format(timeDTF));
	comboEnd.getSelectionModel().select(LocalTime.of(8, 15).format(timeDTF));
	
	
	// Load customer details
	masterData = populateCustomerList();
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
	// disable save and remove buttons
	btnApptCancel.setDisable(true);
	btnApptSave.setDisable(true);
	labelMainAppt.setText("Click buttons below to add, update, or delete an appointment");
	labelAppt.setText(null);
	labelStartBound.setText(null);
	labelEndBound.setText(null);
	
	// hide appointment details
//	hideInputFields();
	apptVBox.setVisible(false);
		
	
	currDate = LocalDate.now();
	nextMonth(currDate);
	
  
        // create a choiceBox 
        choiceWeekMonth.setItems(FXCollections.observableArrayList("Weekly", "Monthly")); 
	choiceWeekMonth.setValue("Monthly");

        // add a listener 
      choiceWeekMonth.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

	  // if the item of the list is changed 
	  public void changed(ObservableValue ov, Number value, Number new_value) {
	      // set the text for the label to the selected item 
	      System.out.println(new_value);
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
	

	currDate = currDate.plusMonths(1);
	FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
	filteredData.setPredicate(row -> {
	    LocalDate rowDate = LocalDate.parse(row.getStart(), dateDTF);
	    return rowDate.isAfter(cbStartDate.minusDays(1)) && rowDate.isBefore(currDate);
	});
	ApptTable.setItems(filteredData);

	labelStartBound.setText(cbStartDate.format(labelDTF));
	labelEndBound.setText(currDate.format(labelDTF));
    }
    
    private void previousMonth(LocalDate cbEndDate) {
	currDate = currDate.minusMonths(1);
	FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
	filteredData.setPredicate(row -> {
	    LocalDate rowDate = LocalDate.parse(row.getStart(), dateDTF);
	    return rowDate.isAfter(currDate.minusDays(1)) && rowDate.isBefore(cbEndDate);
	});
	ApptTable.setItems(filteredData);

	labelStartBound.setText(currDate.format(labelDTF));
	labelEndBound.setText(cbEndDate.format(labelDTF));
    }

     private void nextWeek(LocalDate cbStartDate) {
	

	currDate = currDate.plusWeeks(1);
	FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
	filteredData.setPredicate(row -> {
	    LocalDate rowDate = LocalDate.parse(row.getStart(), dateDTF);
	    return rowDate.isAfter(cbStartDate.minusDays(1)) && rowDate.isBefore(currDate);
	});
	ApptTable.setItems(filteredData);

	labelStartBound.setText(cbStartDate.format(labelDTF));
	labelEndBound.setText(currDate.format(labelDTF));
    }
    
    private void previousWeek(LocalDate cbEndDate) {
	currDate = currDate.minusWeeks(1);
	FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList);
	filteredData.setPredicate(row -> {
	    LocalDate rowDate = LocalDate.parse(row.getStart(), dateDTF);
	    return rowDate.isAfter(currDate.minusDays(1)) && rowDate.isBefore(cbEndDate);
	});
	ApptTable.setItems(filteredData);

	labelStartBound.setText(currDate.format(labelDTF));
	labelEndBound.setText(cbEndDate.format(labelDTF));
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
	editClicked = true;
	selectedAppt = appointment;

	String start = appointment.getStart();

	LocalDateTime startLDT = LocalDateTime.parse(start, dateDTF);
	String end = appointment.getEnd();
	LocalDateTime endLDT = LocalDateTime.parse(end, dateDTF);
  
	txtTitle.setText(appointment.getTitle());
//	tblApptCustomer.getSelectionModel().select(appointment.getCustomer());
//	zoneID.getSelectionModel().select(appointment.getCustomer());
	datePicker.setValue(LocalDate.parse(appointment.getStart(), dateDTF));
//	type.setText(appointment.getDescription());
	txtLocation.setText(appointment.getLocation());

	comboStart.getSelectionModel().select(startLDT.toLocalTime().format(timeDTF));
	comboEnd.getSelectionModel().select(endLDT.toLocalTime().format(timeDTF));
	comboCustomer.getSelectionModel().select(appointment.getCustomer());
	comboType.getSelectionModel().select(appointment.getDescription());
	
	
    }


    private void populateAppointmentList() {

	try {
	    PreparedStatement ps = DBConnection.getConn().prepareStatement(
		    "SELECT appointment.appointmentId, "
			    + "appointment.customerId, "
			    + "appointment.title, "
			    + "appointment.description, "
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
		
		
	      //Adding UTC time zone as the Time Zone to localDateTime
		ZonedDateTime newzdtStart = tsStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
		
		//Converting ZonedDateTime to its equivalent in default system time zone
		ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(newzid);

		Timestamp tsEnd = rs.getTimestamp("appointment.end");
		ZonedDateTime newzdtEnd = tsEnd.toLocalDateTime().atZone(ZoneId.of("UTC"));
		ZonedDateTime newLocalEnd = newzdtEnd.withZoneSameInstant(newzid);

		String tTitle = rs.getString("appointment.title");
		String tDesc = rs.getString("appointment.description");
		String tLocation = rs.getString("appointment.location");
		Customer sCustomer = new Customer(rs.getInt("appointment.customerId"), rs.getString("customer.customerName"));
	
		String sContact = rs.getString("appointment.createdBy");

		appointmentList.add(new Appointment(tAppointmentId, newLocalStart.format(dateDTF), newLocalEnd.format(dateDTF), tTitle, tDesc, tLocation, sCustomer, sContact));
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
	editClicked = true;
	LocalDate localDate = datePicker.getValue();
	LocalTime startTime = LocalTime.parse(comboStart.getSelectionModel().getSelectedItem(), timeDTF);
	LocalTime endTime = LocalTime.parse(comboEnd.getSelectionModel().getSelectedItem(), timeDTF);

	LocalDateTime startDT = LocalDateTime.of(localDate, startTime);
	LocalDateTime endDT = LocalDateTime.of(localDate, endTime);

	ZonedDateTime startUTC = startDT.atZone(newzid).withZoneSameInstant(ZoneId.of("UTC"));
	ZonedDateTime endUTC = endDT.atZone(newzid).withZoneSameInstant(ZoneId.of("UTC"));

	Timestamp startsqlts = Timestamp.valueOf(startUTC.toLocalDateTime()); //this value can be inserted into database
	Timestamp endsqlts = Timestamp.valueOf(endUTC.toLocalDateTime()); //this value can be inserted into database        

	try {

	    PreparedStatement pst = DBConnection.getConn().prepareStatement("UPDATE appointment "
		    + "SET customerId = ?, title = ?, description = ?, location = ?, start = ?, end = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? "
		    + "WHERE appointmentId = ?");

	    pst.setInt(1, comboCustomer.getValue().getCustomerId());
	    pst.setString(2, txtTitle.getText());
	    pst.setString(3, comboType.getValue());
	    pst.setString(4, txtLocation.getText());
	    pst.setTimestamp(5, startsqlts);
	    pst.setTimestamp(6, endsqlts);
	    pst.setString(7, currentUser.getUserName());
	    pst.setString(8, selectedAppt.getAppointmentId());
	    int result = pst.executeUpdate();
	    if (result == 1) {//one row was affected; namely the one that was inserted!
		System.out.println("YAY! Update Appointment Save");
		mainApp.showAppointmentScreen(currentUser);
	    } else {
		System.out.println("BOO! Update Appointment Save");
	    }
	} catch (SQLException ex) {
	    ex.printStackTrace();
	} 
	
    }
    
    private void clearApptFields(){

	comboCustomer.setValue(null);
	comboStart.getSelectionModel().select(LocalTime.of(8, 0).format(timeDTF));
	comboEnd.getSelectionModel().select(LocalTime.of(8, 15).format(timeDTF));
	datePicker.setValue(LocalDate.now());
	txtTitle.setText("");
	comboType.setValue(null);
	txtLocation.setText("");
		
	
    }
    private void saveAppointment() {
	System.out.println(editClicked);
	
	// Get the date and times from the date picker and combo box
	LocalDate selDate = datePicker.getValue();
	LocalTime selStart = LocalTime.parse(comboStart.getSelectionModel().getSelectedItem(), timeDTF);
	LocalTime selEnd = LocalTime.parse(comboEnd.getSelectionModel().getSelectedItem(), timeDTF);

	
        // Convert the times selected to datetime
	LocalDateTime startDT = LocalDateTime.of(selDate, selStart);
	LocalDateTime endDT = LocalDateTime.of(selDate, selEnd);
	
	// Convert the datetimes to UTC for storage in the database
	ZonedDateTime startUTC = startDT.atZone(newzid).withZoneSameInstant(ZoneId.of("UTC"));
	ZonedDateTime endUTC = endDT.atZone(newzid).withZoneSameInstant(ZoneId.of("UTC"));
	
        // Convert to SQL format
	Timestamp startsqlts = Timestamp.valueOf(startUTC.toLocalDateTime()); //this value can be inserted into database
	Timestamp endsqlts = Timestamp.valueOf(endUTC.toLocalDateTime()); //this value can be inserted into database        

	try {

	    PreparedStatement pst = DBConnection.getConn().prepareStatement("INSERT INTO appointment "
		    + "(customerId, title, description, location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)"
		    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)");
	    
	    pst.setInt(1, comboCustomer.getValue().getCustomerId());
	    pst.setString(2, txtTitle.getText());
	    pst.setString(3, comboType.getValue());
	    pst.setString(4, txtLocation.getText());
	    pst.setString(5, "");
	    pst.setString(6, "");
	    pst.setTimestamp(7, startsqlts);
	    pst.setTimestamp(8, endsqlts);
	    pst.setString(9, currentUser.getUserName());
	    pst.setString(10, currentUser.getUserName());
	    int result = pst.executeUpdate();
	    if (result == 1) {//one row was affected; namely the one that was inserted!
		System.out.println("YAY! New Appointment Save");
		System.out.println("saved " + comboCustomer.getValue().getCustomerName());
		mainApp.showAppointmentScreen(currentUser);
		
	    } else {
		System.out.println("BOO! New Appointment Save");
	    }
	} catch (SQLException ex) {
	    ex.printStackTrace();
	}

    }


    
    
    
}
