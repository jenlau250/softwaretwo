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
import java.time.LocalDateTime;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class AppointmentScreenController {
    
    @FXML    private TableColumn<Appointment, String> tContact;
    @FXML    private TableColumn<Appointment, ?> tLocation;
    @FXML    private TableView<Appointment> ApptTable;
    @FXML    private TableColumn<Appointment, String> tDescription;
    @FXML    private TableColumn<Appointment, String> tTitle;
    @FXML    private TableColumn<Appointment, ZonedDateTime> tEndDate;
    @FXML    private TableColumn<Appointment, LocalDateTime> tStartDate;
    @FXML    private TableColumn<Appointment, Customer> tCustomer;
    
    @FXML    private Button btnApptDel;
    @FXML    private Button btnApptAdd;
    @FXML    private Button btnApptUpdate;
    @FXML    private Button btnApptSave;
    @FXML    private Button btnApptCancel;

    private jCalendar mainApp;
    private User currentUser;
    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final ZoneId newzid = ZoneId.systemDefault();
    ObservableList<Appointment> appointmentList;


    
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
	System.out.println("edit");
	
    }
    
    @FXML 
    void handleApptAdd(ActionEvent event) {
	System.out.println("add");
	
    }

    @FXML
    void handleApptSave(ActionEvent event) {
	System.out.println("save");

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
	
	tCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
	tStartDate.setCellValueFactory(new PropertyValueFactory<>("start"));
	tEndDate.setCellValueFactory(new PropertyValueFactory<>("end"));
	tTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
	tDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
	tLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
	tContact.setCellValueFactory(new PropertyValueFactory<>("user"));

	appointmentList = FXCollections.observableArrayList();
	showAppointments();
	FilteredList<Appointment> filteredData = new FilteredList<>(appointmentList, t -> true);
	ApptTable.getItems().setAll(filteredData);

//	tCustomer.textProperty().addListener(obs -> {
//	    filter(filteredData);
//	});
//	tCustomer.textProperty().addListener(
//		(observable, oldValue, newValue) -> {
//		    filteredData.setPredicate(t -> {
//
//			if (newValue == null || newValue.isEmpty()) {
//			    return true;
//			}
//			String objectvalues = 
//
//			if (objectvalues.toLowerCase().indexOf(newValue) != -1) {
//			    return true;
//			}
//
//			return false;
//		    });
//		});
	//Convert Customer to show name
//	ObjectProperty<Predicate<Customer>> nameFilter = new SimpleObjectProperty<>();
//	nameFilter.bind(Bindings.createObjectBinding(() ->
//	cus -> cus.getCustomerName()));
	
//       
//       tCustomer.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, Customer>, ObservableValue<Customer>>() {
//	   public ObservableValue<String> call(CellDataFeatures<Appointment, Customer> param) {
//	       return new SimpleStringProperty(param.getCustomerName());
//	   		}  
//		});
	   

    }    
// 
//    private void filter(FilteredList<Appointment> filteredData) {
//	filteredData.setPredicate(Appointment -> {
//	    // If all filters are empty then display all Purchase Orders
//	    if ((tCustomer. == null || txtFilter.getText().isEmpty())
//		    && (txtFilter2.getText() == null || txtFilter2.getText().isEmpty())) {
//		return true;
//	    }
//
//	    // Convert filters to lower case
//	    String lowerCaseFilter = txtFilter.getText().toLowerCase();
//	    String lowerCaseFilter2 = txtFilter2.getText().toLowerCase();
//
//	    //If fails any given criteria, fail completely
//	    if (txtFilter.getText().length() > 0) {
//		if (PurchaseOrder.get("vendor_name").toLowerCase().contains(lowerCaseFilter) == false) {
//		    return false;
//		}
//	    }
//	    if (txtFilter2.getText().length() > 0) {
//		if (PurchaseOrder.get("PONumber").toLowerCase().contains(lowerCaseFilter2) == false) {
//		    return false;
//		}
//	    }
//
//	    return true; // Matches given criteria
//	});
    
    private void showAppointments() {

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
		//   public Appointment(String appointmentId, String start, String end, String title, String description, Customer customer, String user)
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

}


