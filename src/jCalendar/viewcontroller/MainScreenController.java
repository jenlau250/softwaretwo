/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import jCalendar.DAO.DBConnection;
import jCalendar.model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import jCalendar.jCalendar;
import jCalendar.model.Appointment;
import jCalendar.model.Customer;
import jCalendar.utilities.Loggerutil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Iterator;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class MainScreenController {

    @FXML    private MenuItem menuCustomers;
    @FXML    private MenuItem menuExit;
    @FXML    private MenuItem menuAppointments;
    @FXML    private TextArea txtAreaReminders;
    @FXML    private Menu menuReports;
    @FXML    private MenuItem menuReportAppt;
    @FXML    private MenuItem menuReportSchedule;
    @FXML    private MenuItem menuReportCustomer;


    private jCalendar mainApp;
    private User currentUser;
    private final ZoneId newzid = ZoneId.systemDefault();
    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    ObservableList<Appointment> reminderList;
    private final static Logger LOGGER = Logger.getLogger(Loggerutil.class.getName());

    // initialize mainApp,currentuser, and empty controller constructor
    public MainScreenController() {
	
    }

    /**
     * Initializes Menu
     *
     * @param mainApp
     * @param currentUser
     */
    public void setMenu(jCalendar mainApp, User currentUser) {
	this.mainApp = mainApp;
	this.currentUser = currentUser;
	reminderList = FXCollections.observableArrayList();
	
	populateReminderList();
	reminder();
	

	menuExit.setOnAction((evt) -> {
	    System.exit(0);
	});
	
	menuCustomers.setOnAction((evt) -> {
	    this.mainApp.showCustomerScreen(this.currentUser);
	});

	menuAppointments.setOnAction((evt) -> {
	    this.mainApp.showAppointmentScreen(this.currentUser);
	});

	
	menuReportAppt.setOnAction((evt) -> {
	    this.mainApp.showReportScreen(this.currentUser, this.menuReportAppt);
	});

	menuReportCustomer.setOnAction((evt) -> {
	    this.mainApp.showReportScreen(this.currentUser, this.menuReportCustomer);
	});
	
	menuReportSchedule.setOnAction((evt) -> {
	    this.mainApp.showReportScreen(this.currentUser, this.menuReportSchedule);
	});



	//logoutUser.setText("Logout: " + currentUser.getUsername());
    }

    private void reminder() {
	LocalDateTime now = LocalDateTime.now();
	//CHANGE BACK TO 15 MINUTES
//	LocalDateTime nowPlus15Min = now.plusMinutes(15);
	LocalDateTime nowPlus15Min = now.plusMonths(1);

	FilteredList<Appointment> filteredData = new FilteredList<>(reminderList);

	filteredData.setPredicate(row -> {
	    LocalDateTime rowDate = LocalDateTime.parse(row.getStart(), timeDTF);
	    return rowDate.isAfter(now.minusMinutes(1)) && rowDate.isBefore(nowPlus15Min);
	}
	);
	if (filteredData.isEmpty()) {
	    System.out.println("No reminders");
	    txtAreaReminders.setText("No Upcoming Appointments within 15 minutes");
	} else {
	    String type = filteredData.get(0).getDescription();
	    String customer = filteredData.get(0).getCustomer().getCustomerName();
	    String start = filteredData.get(0).getStart();
	    
	    txtAreaReminders.setText("Reminder: Upcoming Appointments \n");
	    Iterator<Appointment> itr = filteredData.iterator();
	    while (itr.hasNext()) {
		Appointment a = itr.next();
		txtAreaReminders.appendText(
			 " Description: " + a.getDescription() + "\t"
			+ " Customer: " + a.getCustomer().getCustomerName() + "\t"
			+ " Start Time: " + a.getStart() + "\n"
		);

	    }

	
	    
//	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//	    alert.setTitle("Upcoming Appointment Reminder");
//	    alert.setHeaderText("Reminder: You have the following appointment set for the next 15 minutes.");
//	    alert.setContentText("Your upcoming " + type + " appointment with " + customer
//		    + " is currently set for " + start + ".");
//	    alert.showAndWait();
	}

    }
    
    private void populateReminderList() {
//        System.out.println(User.getUserName());
        try{           
        PreparedStatement pst = DBConnection.getConn().prepareStatement(
        "SELECT appointment.appointmentId, appointment.customerId, appointment.title, appointment.description, appointment.location, "
                + "appointment.`start`, appointment.`end`, customer.customerId, customer.customerName, appointment.createdBy "
                + "FROM appointment, customer "
                + "WHERE appointment.customerId = customer.customerId AND appointment.createdBy = ? "
                + "ORDER BY `start`");
            pst.setString(1, currentUser.getUserName());
            ResultSet rs = pst.executeQuery();
           
            
            while (rs.next()) {
                
                String tAppointmentId = rs.getString("appointment.appointmentId");
                Timestamp tsStart = rs.getTimestamp("appointment.start");
                ZonedDateTime newzdtStart = tsStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
        	ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(newzid);

                Timestamp tsEnd = rs.getTimestamp("appointment.end");
                ZonedDateTime newzdtEnd = tsEnd.toLocalDateTime().atZone(ZoneId.of("UTC"));
        	ZonedDateTime newLocalEnd = newzdtEnd.withZoneSameInstant(newzid);

                String tTitle = rs.getString("appointment.title");
                
                String tType = rs.getString("appointment.description");
                String tLocation = rs.getString("appointment.location");
                
                Customer tCustomer = new Customer(rs.getInt("appointment.customerId"), rs.getString("customer.customerName"));
                
                String tUser = rs.getString("appointment.createdBy");
                      
                reminderList.add(new Appointment(tAppointmentId, newLocalStart.format(timeDTF), newLocalEnd.format(timeDTF), tTitle, tType, tLocation, tCustomer, tUser));   

            }   
            
        } catch (SQLException sqe) {
            System.out.println("Check your SQL");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
            e.printStackTrace();
        }
    }
    
    
}
