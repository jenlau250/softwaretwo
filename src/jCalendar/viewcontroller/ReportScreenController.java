/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import jCalendar.DAO.DBConnection;
import jCalendar.jCalendar;
import jCalendar.model.Appointment;
import jCalendar.model.City;
import jCalendar.model.Customer;
import jCalendar.model.User;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static javafx.scene.input.KeyCode.T;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class ReportScreenController {

    private jCalendar mainApp;
    private User currentUser;
    private MenuItem menu;
    
    @FXML    private Label labelReportMenu;
    @FXML    private Label labelSub;
    
    @FXML    private TabPane tabMenu;
    @FXML    private Tab tabScheduleDetails;
    @FXML    private Tab selTab;
    @FXML    private Tab tabApptType;
    @FXML    private Tab tabCustomerDetail;
    
    @FXML    private DatePicker reportDatePicker;
    @FXML    private TableView<Appointment> tblSchedule;
    @FXML    private TableColumn<Appointment, String> colEnd;
    @FXML    private TableColumn<Appointment, String> colType;
    @FXML    private TableColumn<Appointment, String> colStart;
    @FXML    private TableColumn<Appointment, String> colTitle;
    @FXML    private TableColumn<Appointment, String> colCustomer;
    @FXML    private ComboBox comboMonth;
    
    @FXML    private TableView<Appointment> tblApptType;
    @FXML    private TableColumn<Appointment, String> colApptType;
    @FXML    private TableColumn<Appointment, String> colApptMonth;
    @FXML    private TableColumn<Appointment, String> colApptCount;
    
    @FXML    private TableView<Customer> tblCustData;
    @FXML    private TableColumn<Customer, String> colCustCount;
    @FXML    private TableColumn<Customer, City> colCustCity;
    
    HashMap<String, Integer> custData;
    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final DateTimeFormatter dateDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final ZoneId newzid = ZoneId.systemDefault();
    private ObservableList<Appointment> apptList;
    private ObservableList<Customer> custList;
    private ObservableList<Appointment> schedule;
    private ObservableSet<String> monthSet;
    FilteredList<Appointment> filteredData;
    ObservableList<Appointment> items;
    ObservableList<String> myList;

    public ReportScreenController() {
	
    }
    
    /**
     * Initializes the controller class.
     */
    public void setReportScreen(jCalendar mainApp, User currentUser) {

	this.mainApp = mainApp;
	this.currentUser = currentUser;
	
//	String selectedReport = menu.getText();
//	labelReportMenu.setText(selectedReport);
	System.out.println(selTab);
        
        //methods called to populate data on each tab        
        populateApptTypeList();
        populateCustReport();
        populateSchedule();      
        
        colStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
	colCustomer.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getCustomer().getCustomerName()));
	
        
        colApptMonth.setCellValueFactory(new PropertyValueFactory<>("Month"));
        colApptType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colApptCount.setCellValueFactory(new PropertyValueFactory<>("Count"));
	
	colCustCount.setCellValueFactory(new PropertyValueFactory<>("citycount"));
	colCustCity.setCellValueFactory(new PropertyValueFactory<>("city"));
  

	ObservableList<String> newList = FXCollections.observableArrayList(monthSet);
	comboMonth.setItems(newList);
	
	// Update the message Label when the selected item changes
        comboMonth.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> ov,
                    final String oldvalue, final String newvalue)
            {
//                monthChanged(ov, oldvalue, newvalue);
		System.out.println(newvalue);
		filterMonth(newvalue);
		
        }});
	

	
	
	
	
	

	
    }
    


    private void filterMonth(String month) {
	FilteredList<Appointment> filteredData = new FilteredList<>(schedule);
	filteredData.setPredicate(row -> {
	    LocalDate rowDate = LocalDate.parse(row.getStart(), dateDTF);
	    String rowMonth = rowDate.getMonth().name();
	    return rowMonth.equals(month);
	});

	tblSchedule.setItems(filteredData);
    }
	
    
    
    
    private void populateApptTypeList() {
	apptList = FXCollections.observableArrayList();

	try {

	    PreparedStatement statement = DBConnection.getConn().prepareStatement(
		    "SELECT MONTHNAME(`start`) AS \"month\", type AS \"type\", COUNT(*) as \"count\" "
		    + "FROM appointment "
		    + "GROUP BY MONTHNAME(`start`), type");
	    ResultSet rs = statement.executeQuery();

	    while (rs.next()) {

		String month = rs.getString("month");

		String type = rs.getString("type");

		String count = rs.getString("count");

		apptList.add(new Appointment(month, type, count));

	    }

	} catch (SQLException sqe) {
	    System.out.println("Check your SQL");
	    sqe.printStackTrace();
	} catch (Exception e) {
	    System.out.println("Something besides the SQL went wrong.");
	}

	tblApptType.getItems().setAll(apptList);
    }
    
 
    private void populateCustReport() {
        
        custList = FXCollections.observableArrayList();
//	custData = new HashMap<>();
      
            try { PreparedStatement pst = DBConnection.getConn().prepareStatement(
                  "SELECT city.cityId, city.city, COUNT(city.city) as \"count\" "
                + "FROM customer, address, city "
                + "WHERE customer.addressId = address.addressId "
                + "AND address.cityId = city.cityId "
                + "GROUP BY city"); 
                ResultSet rs = pst.executeQuery();


                while (rs.next()) {
                        City city = new City(rs.getInt("cityId"), rs.getString("city"));
                        String count = rs.getString("count");
                        custList.add(new Customer(city, count));
                }

            } catch (SQLException sqe) {
                System.out.println("Check your SQL");
                sqe.printStackTrace();
            } catch (Exception e) {
                System.out.println("Something besides the SQL went wrong.");
                e.printStackTrace();
            }             
//        tblCustData.addAll(custData);
//	  colApptMonth
        tblCustData.setItems(custList);
	
    }
    

    
    private void populateSchedule() {
      
        schedule = FXCollections.observableArrayList();
        monthSet = FXCollections.observableSet();
        
        try{
            
        PreparedStatement pst = DBConnection.getConn().prepareStatement(
        "SELECT appointment.appointmentId, appointment.customerId, appointment.title, appointment.type, appointment.location, "
                + "appointment.`start`, appointment.`end`, customer.customerId, customer.customerName, appointment.createdBy "
                + "FROM appointment, customer "
                + "WHERE appointment.customerId = customer.customerId AND appointment.`start` >= CURRENT_DATE AND appointment.createdBy = ?"
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
                
                String tType = rs.getString("appointment.type");
                String tLocation = rs.getString("appointment.location");
                
                Customer tCustomer = new Customer(rs.getInt("appointment.customerId"), rs.getString("customer.customerName"));
             
                String tUser = rs.getString("appointment.createdBy");
                      
                schedule.add(new Appointment(tAppointmentId, newLocalStart.format(dateDTF), newLocalEnd.format(dateDTF), tTitle, tType, tLocation, tCustomer, tUser));
		monthSet.add(newLocalStart.getMonth().name());
		
		
            }
            
        } catch (SQLException sqe) {
            System.out.println("Check your SQL");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }
        
	
	
	
	tblSchedule.getItems().setAll(schedule);
	
	
    }

}
