/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import jCalendar.dao.DBConnection;
import jCalendar.jCalendar;
import jCalendar.model.Appointment;
import jCalendar.model.Barber;
import jCalendar.model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class ReportScreenController {

    private jCalendar mainApp;
//    private User currentUser;
    private MenuItem menu;

    @FXML
    private Label labelReportMenu;
    @FXML
    private Label labelSub;

    @FXML
    private TabPane tabMenu;
    @FXML
    private Tab tabScheduleDetails;
    @FXML
    private Tab selTab;
    @FXML
    private Tab tabApptType;
    @FXML
    private Tab tabCustomerDetail;

    @FXML
    private DatePicker reportDatePicker;
    @FXML
    private TableView<Appointment> tblSchedule;
    @FXML
    private TableColumn<Appointment, String> colEnd;
    @FXML
    private TableColumn<Appointment, String> colType;
    @FXML
    private TableColumn<Appointment, String> colStart;
    @FXML
    private TableColumn<Appointment, String> colTitle;
    @FXML
    private TableColumn<Appointment, String> colCustomer;
    @FXML
    private ComboBox comboMonth;

    @FXML
    private TableView<Appointment> tblApptType;
    @FXML
    private TableColumn<Appointment, String> colApptType;
    @FXML
    private TableColumn<Appointment, String> colApptMonth;
    @FXML
    private TableColumn<Appointment, String> colApptCount;

    @FXML
    private TableView<Customer> tblCustData;
    @FXML
    private TableColumn<Customer, String> colCustCount;
    @FXML
    private TableColumn<Customer, Barber> colPet;

    private final DateTimeFormatter dtformat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final ZoneId newZoneId = ZoneId.systemDefault();
    private ObservableList<Appointment> apptList;
    private ObservableList<Customer> custList;
    private ObservableList<Appointment> schedule;
    private ObservableSet<String> monthSet;
    FilteredList<Appointment> filteredData;

    public ReportScreenController() {

    }

    /**
     * Initializes the controller class.
     *
     * @param mainApp
     */
    public void setMainController(jCalendar mainApp) {

        this.mainApp = mainApp;
//        this.currentUser = currentUser;

        populateApptTypeList();
        populateCustReport();
        populateSchedule();

        colStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCustomer.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getCustomer().getCustomerName()));
        colApptMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        colApptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colApptCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        colCustCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        colPet.setCellValueFactory(new PropertyValueFactory<>("pet"));

        ObservableList<String> newList = FXCollections.observableArrayList(monthSet);

        comboMonth.setItems(newList);
        // Update month filter data
        comboMonth.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov,
                    final String oldvalue, final String newvalue) {
                filterMonth(newvalue);

            }
        });

    }

    private void filterMonth(String month) {
        FilteredList<Appointment> filteredData = new FilteredList<>(schedule);

        DateTimeFormatter DATEFORMATTOLOCAL = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        filteredData.setPredicate(row -> {
            LocalDate rowDate = row.getStartDate();
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
            sqe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tblApptType.getItems().setAll(apptList);
    }

    private void populateCustReport() {

        custList = FXCollections.observableArrayList();

        try {
            PreparedStatement pst = DBConnection.getConn().prepareStatement(
                    "SELECT customer.customerName, pet.petId, pet.petName, COUNT(pet.petName) as \"count\" "
                    + "FROM customer, pet "
                    + "WHERE customer.customerId = pet.customerId "
                    + "GROUP BY petName");
//                  "SELECT city.cityId, city.city, COUNT(city.city) as \"count\" "
//                + "FROM customer, address, city "
//                + "WHERE customer.addressId = customer.customerPhoneId "
//                + "AND address.cityId = city.cityId "
//                + "GROUP BY city");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String customerName = rs.getString("customer.customerName");
//                Pet pet = new Pet(rs.getString("petId"), rs.getString("petName"));
                String count = rs.getString("count");
                custList.add(new Customer(customerName, count));
            }

        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tblCustData.setItems(custList);
    }

    private void populateSchedule() {

        schedule = FXCollections.observableArrayList();
        monthSet = FXCollections.observableSet();

        try {

            PreparedStatement pst = DBConnection.getConn().prepareStatement(
                    "SELECT appointment.appointmentId, appointment.customerId, appointment.barberId, appointment.calendarId, appointment.title, appointment.type, appointment.location, "
                    + "appointment.`start`, appointment.`end`, customer.customerId, customer.customerName, appointment.createdBy "
                    + "FROM appointment, customer "
                    + "WHERE appointment.customerId = customer.customerId AND appointment.`start` >= CURRENT_DATE "
                    + "ORDER BY `start`");

//                "SELECT appointment.appointmentId, appointment.customerId, appointment.title, appointment.type, appointment.location, "
//                    + "appointment.`start`, appointment.`end`, customer.customerId, customer.customerName, appointment.createdBy "
//                    + "FROM appointment, customer "
//                    + "WHERE appointment.customerId = customer.customerId AND appointment.`start` >= CURRENT_DATE AND appointment.createdBy = ?"
//                    + "ORDER BY `start`");
//
//            pst.setString(1, currentUser.getUserName()); CHANGE TO POPULATE SCHEDULE BY BARBER
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Timestamp sStart = rs.getTimestamp("appointment.start");
                Timestamp sEnd = rs.getTimestamp("appointment.end");
                ZonedDateTime newZoneStart = sStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newZoneEnd = sEnd.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newLocalStart = newZoneStart.withZoneSameInstant(newZoneId);
                ZonedDateTime newLocalEnd = newZoneEnd.withZoneSameInstant(newZoneId);

                String sAppointmentId = rs.getString("appointment.appointmentId");
                String sTitle = rs.getString("appointment.title");
                String sCustomer = rs.getString("customer.customerName");
                String sBarber = rs.getString("barber.barberName");
                String sType = rs.getString("appointment.type");
//ERROR, fix the dates later
//                schedule.add(new Appointment(sAppointmentId, newLocalStart, newLocalEnd.format(dtformat), sTitle, sType, sCustomer, sBarber));
                monthSet.add(newLocalStart.getMonth().name());

            }

        } catch (SQLException sqe) {
            System.out.println("Check SQL exception");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception error");
        }

        tblSchedule.getItems().setAll(schedule);
    }
}
