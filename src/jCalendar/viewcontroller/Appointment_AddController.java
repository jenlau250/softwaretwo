/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import Cache.AppointmentCache;
import Cache.BarberCache;
import Cache.CustomerCache;
import Cache.PetCache;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import jCalendar.dao.DBConnection;
import jCalendar.jCalendar;
import jCalendar.model.Appointment;
import jCalendar.model.Barber;
import jCalendar.model.Customer;
import jCalendar.model.Pet;
import jCalendar.model.User;
import jCalendar.utilities.DateTimeUtil;
import jCalendar.utilities.Loggerutil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Jen
 */
public class Appointment_AddController {

    // Controllers
//    private AppointmentScreenController mainController;
    private jCalendar mainApp;
    private User currentUser;
    private Appointment selectedAppt;
    private boolean editClicked;
    @FXML
    private Label topLabel;
    @FXML
    private JFXButton btnApptSave;
    @FXML
    private JFXButton btnApptCancel;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXComboBox<String> comboStart;
    @FXML
    private JFXComboBox<String> comboEnd;
    @FXML
    private JFXComboBox<String> comboType;
    @FXML
    private JFXComboBox<Barber> comboBarber;
    @FXML
    private JFXTextField txtTitle;
    @FXML
    private JFXTextField txtDesc;
    @FXML
    private ImageView btnClose;

    @FXML
    private JFXRadioButton choiceExistingCustomer;
    @FXML
    private ToggleGroup NewOrExistingCustomer;
    @FXML
    private JFXRadioButton choiceNewCustomer;

    @FXML
    private JFXComboBox<Customer> comboExistCustomer;

    @FXML
    private JFXComboBox<Pet> comboPet;

    @FXML
    private VBox vBoxCustomer;
    private final DateTimeFormatter timeformat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private final DateTimeFormatter dateformat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final DateTimeFormatter labelformat = DateTimeFormatter.ofPattern("E MMM d, yyyy");

//    @FXML
//    private JFXTextField txtNewCustomer;
//
//    @FXML
//    private JFXTextField txtPhone;
//
//    @FXML
//    private JFXTextField txtEmail;
    private final static Logger logger = Logger.getLogger(Loggerutil.class.getName());

    private ObservableList<Appointment> data;
    private ObservableList<Pet> selectedPets = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

//    Customer customer;
    /**
     * Initializes the controller class.
     *
     * @param mainApp
     * @param currentUser
     */
    public void setMainController(jCalendar mainApp, User currentUser) {

        this.mainApp = mainApp;
        this.currentUser = currentUser;
//initialize common fields between add and edit here
        editClicked = false;

        if (!editClicked) {

            topLabel.setText("Add New Appointment");

        }

        Appointment appt = new Appointment();
        datePicker.valueProperty().bindBidirectional(appt.startDateProperty());

        initNewFields();

//        this causes errors--> comboExistPet.setItems(selectedPets);
        System.out.println("From SetMainController: Edit mode is " + editClicked);
//        labelBarberId.setText("");

        //ADD LISTENER FOR NEW OR EXISTING CUSTOMER
        //set default
//        choiceExistingCustomer.setSelected(true);
//        comboExistCustomer.setVisible(true);
//        txtNewCustomer.setVisible(false);
//        txtNewCustomer.managedProperty().bind(txtNewCustomer.visibleProperty());
        //HIDE - test if this is causing combo box issues
//        comboExistCustomer.managedProperty().bind(comboExistCustomer.visibleProperty());
//
//        choiceExistingCustomer.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
//            if (isSelected) {
////                vBoxCustomer.setVisible(true);
////
//                comboExistCustomer.setVisible(true);
//                comboExistPet.setVisible(true);
//                System.out.println("existing customer selected");
//            }
//        });
//
//        choiceNewCustomer.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
//            if (isSelected) {
//                vBoxCustomer.setVisible(true);
//                comboExistCustomer.setVisible(false);
//                comboExistPet.setVisible(false);
//                System.out.println("new customer selected");
//            }
//        });
        //CLOSE PANE ON EXIT
        System.out.println("running initialize()");
        comboExistCustomer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                Customer c = comboExistCustomer.getSelectionModel().getSelectedItem();
                populatePetCombo(c);

            }
        });

        btnClose.setOnMouseClicked((evt) -> {
            mainApp.showAppointmentListScreen();
        });

        convertComboBoxtoString();

    }

    @FXML
    private void clearFields() {

        datePicker.setValue(LocalDate.now());
        comboStart.getItems().clear();
        comboEnd.getItems().clear();
        comboType.getItems().clear();
        comboBarber.getItems().clear();
        txtTitle.clear();
        txtDesc.clear(); //Notes
        comboExistCustomer.getItems().clear();
        comboPet.getItems().clear();

    }

    @FXML
    private void showAppointmentDetails(Appointment appt) {

        System.out.println("Running showAppointmentsDetails");
        datePicker.setValue(appt.getStartDate());

        //convert local time to string
        String startTime = DateTimeUtil.parseTimeToStringFormat(appt.getStart().toLocalTime());
        String endTime = DateTimeUtil.parseTimeToStringFormat(appt.getEnd().toLocalTime());
        comboStart.setValue(startTime);
        comboEnd.setValue(endTime);
        System.out.println(appt.getEnd().toLocalTime().toString());
        comboType.setValue(appt.typeProperty().get());

        txtTitle.setText(appt.titleProperty().get());
        txtDesc.setText(appt.descriptionProperty().get()); //Notes
        comboBarber.setValue(appt.getBarber());
        comboExistCustomer.setValue(appt.getCustomer());

        //Update Pet Observable List based on selected appt
//        comboExistPet.setValue(appt.getPet());
    }

    private void initNewFields() {

        comboStart.setItems(Appointment.getDefaultStartTimes());
        comboEnd.setItems(Appointment.getDefaultEndTimes());
        //Get Appointment Type options
        comboType.setItems(Appointment.getApptTypes());
//Only show ACTIVE barbers when adding new appontments
        comboBarber.setItems(BarberCache.getAllActiveBarbers());
//            comboExistCustomer.getItems().addAll(mainApp.getCustomerData());
        comboExistCustomer.setItems(CustomerCache.getAllCustomers());

    }

    @FXML
    void handleApptCancel(ActionEvent event
    ) {
        mainApp.showAppointmentListScreen();
    }

    @FXML
    void handleApptSave(ActionEvent event) {

//        System.out.println("printing local time " + startTime + " to " + endTime + "\n");
//        System.out.println("printing zoned time " + startUTC + " to " + endUTC + "\n");
//        System.out.println("printing sql time " + startsql + " to " + endsql + "\n");
//        System.out.println("printing string sql time " + stringStart + " to " + stringEnd + "\n");
        if (validateAppt()) {

            //Get Fields
            LocalDate localDate = datePicker.getValue();
            String startTime = comboStart.getSelectionModel().getSelectedItem();
            String endTime = comboEnd.getSelectionModel().getSelectedItem();

            //Convert String times to LocalTime
            LocalTime localStart = DateTimeUtil.parseStringToTimeFormat(startTime);
            LocalTime localEnd = DateTimeUtil.parseStringToTimeFormat(endTime);

            //Convert to LocalDateTime
            LocalDateTime startDate = LocalDateTime.of(localDate, localStart);
            LocalDateTime endDate = LocalDateTime.of(localDate, localEnd);

            //Convert datetime for database ex: 2020-02-11T22:00Z[UTC]
            ZoneId zid = ZoneId.systemDefault();
            ZonedDateTime startUTC = startDate.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endUTC = endDate.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));

            Timestamp startsql = Timestamp.valueOf(startUTC.toLocalDateTime());
            Timestamp endsql = Timestamp.valueOf(endUTC.toLocalDateTime());

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String stringStart = dateFormatter.format(startsql);
            String stringEnd = dateFormatter.format(endsql);

            Barber b = comboBarber.getSelectionModel().getSelectedItem();

            if (validateApptOverlap(stringStart, stringEnd, b)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Appointment Overlap");
                alert.setHeaderText("Warning: Appointment was not saved");
                alert.setContentText("Overlaps with existing appointment set for " + b.getBarberName());
                alert.showAndWait();

            } else {

                if (editClicked) {
                    updateAppointment(startsql, endsql);
                } else {
                    saveNewAppointment(startsql, endsql);
                }

                //Return to list
                mainApp.showAppointmentListScreen();
            }

        }
    }

    public void setSelectedAppointment(Appointment appt) {
        //initialize update fields here
        editClicked = true;
        topLabel.setText("Edit Appointment Details");

        showAppointmentDetails(appt);

    }

    private void updateAppointment(Timestamp startsql, Timestamp endsql) {
        System.out.println("Attempting to update appointment..");
        //TEMP ADD CURRENT USER AS "TEST"
//        currentUser.setUserName("test");
//        System.out.println("Current user for Appt SAVE Screen " + currentUser);

        String sType = comboType.getValue();
        int sBarber = comboBarber.getValue().getBarberId();
        String sTitle = txtTitle.getText();
        String sDesc = txtDesc.getText();
        String sCustomer = comboExistCustomer.getValue().getCustomerId();
        String sPet = comboPet.getValue().getPetId();

//        String sContact = currentUser.getUserName(); //CHANGE LATER
        System.out.println("Printing record to save: "
                + " Title " + sTitle + " "
                + " Desc " + sDesc + " "
                + " Type " + sType + " "
                + " Barber " + sBarber + " " + comboBarber.getValue().nameProperty().get()
                + " Customer " + sCustomer + " " + comboExistCustomer.getValue().customerNameProperty().get()
                + " Pet " + sPet + " " + comboPet.getValue().nameProperty().get()
        );

        try {

            PreparedStatement pst = DBConnection.getConn().prepareStatement("INSERT INTO appointment "
                    + "(customerId, barberId, petId, title, start, end, description, type, lastUpdate, lastUpdateBy)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)");

            pst.setString(1, sCustomer);
            pst.setInt(2, sBarber);
            pst.setString(3, sPet);

            pst.setString(4, sTitle);

            pst.setTimestamp(5, startsql);
            pst.setTimestamp(6, endsql);

            pst.setString(7, sDesc);
            pst.setString(8, sType);
            pst.setString(9, "test");
//            pst.setString(9, currentUser.getUserName());
            int result = pst.executeUpdate();
            if (result == 1) {//one row was affected; namely the one that was inserted!
                System.out.println("YAY! Updated Appt");

            } else {
                System.out.println("BOO! didn't update appt");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

//        mainApp.refreshView();
        AppointmentCache.flush();
    }

    private void saveNewAppointment(Timestamp startsql, Timestamp endsql) {
        System.out.println("Attempting to save appointment..");
        //TEMP ADD CURRENT USER AS "TEST"
//        currentUser.setUserName("test");
//        System.out.println("Current user for Appt SAVE Screen " + currentUser);

        String sType = comboType.getValue();
        int sBarber = comboBarber.getValue().getBarberId();
        String sTitle = txtTitle.getText();
        String sDesc = txtDesc.getText();
        String sCustomer = comboExistCustomer.getValue().getCustomerId();
        String sPet = comboPet.getValue().getPetId();

//        String sContact = currentUser.getUserName(); //CHANGE LATER
        System.out.println("Printing record to save: "
                + " Title " + sTitle + " "
                + " Desc " + sDesc + " "
                + " Type " + sType + " "
                + " Barber " + sBarber + " " + comboBarber.getValue().nameProperty().get()
                + " Customer " + sCustomer + " " + comboExistCustomer.getValue().customerNameProperty().get()
                + " Pet " + sPet + " " + comboPet.getValue().nameProperty().get()
        );

        try {

            PreparedStatement pst = DBConnection.getConn().prepareStatement("INSERT INTO appointment "
                    + "(customerId, barberId, petId, title, start, end, description, type, createDate, createdBy, lastUpdate, lastUpdateBy)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)");

            pst.setString(1, sCustomer);
            pst.setInt(2, sBarber);
            pst.setString(3, sPet);

            pst.setString(4, sTitle);

            pst.setTimestamp(5, startsql);
            pst.setTimestamp(6, endsql);

            pst.setString(7, sDesc);
            pst.setString(8, sType);
            pst.setString(9, "test");
            pst.setString(10, "test");
//            pst.setString(9, currentUser.getUserName()); //UPDATE LATER
//            pst.setString(10, currentUser.getUserName());
            int result = pst.executeUpdate();
            if (result == 1) {//one row was affected; namely the one that was inserted!
                System.out.println("YAY! New Appointment Save");

            } else {
                System.out.println("BOO! New Appointment Save");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

//        mainApp.refreshView();
        AppointmentCache.flush();
    }

    private boolean validateAppt() {

        //Validate required fields
        String name = txtTitle.getText();
        // optional String desc = txtDesc.getText();
        Barber barber = comboBarber.getValue();
        Pet pet = comboPet.getValue();
        Customer customer = comboExistCustomer.getValue();
        String start = comboStart.getValue();
        String end = comboEnd.getValue();
        LocalDate date = datePicker.getValue();
        String type = comboType.getValue();

        String errorMessage = "";

        //edit: add date logic
        if (name == null || name.length() == 0) {
            errorMessage += "Please enter a title.\n";
        }

        if (start == null || start.length() == 0) {
            errorMessage += "Please enter a start time.\n";
        }

        if (end == null || end.length() == 0) {
            errorMessage += "Please enter an end time.\n";
        }

        //edit: check for localdate
        if (date == null) {
            errorMessage += "Please choose appointment date.\n";
        }

        //edit: check for objects
        if (barber == null) {
            errorMessage += "Please select a barber.\n";
        }

        //edit: check for objects
        if (pet == null) {
            errorMessage += "Please select a pet.\n";
        }

        //edit: check for objects
        if (customer == null) {
            errorMessage += "Please select a customer.\n";
        }
        if (type == null || type.length() == 0) {
            errorMessage += "Please select appointment type.\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid data");
            alert.setHeaderText("Please fix the following customer field(s)");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            System.out.println("Validating "
                    + "Title: " + name + "\n"
                    + "Appointment TIme: " + date + " from " + start + " to " + end + "\n"
                    + "Type: " + type + "\n"
                    + "Barber: " + barber.getBarberName() + "\n"
                    + "Pet: " + pet.getPetName() + "\n"
                    + "Customer: " + customer.getCustomerName() + "\n"
            );

            return false;
        }
    }

    public Boolean validateApptOverlap(String sDate, String eDate, Barber b) {
        System.out.println("Validating appointment times.. ");
        Boolean overlap = false;

        System.out.println("Timestamp WHERE " + sDate + " between start and end or " + eDate
                + "\n between start and end or " + sDate + " < start and " + eDate + " with barberId " + b.getBarberId()
        );
//        System.out.println("Timestamp " + sDate.toLocalDateTime());

//Appointment start and end time can't overlap for same barber
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(
                    "SELECT * FROM appointment "
                    + "WHERE (? BETWEEN start AND end AND barberId = ? "
                    + "OR ? BETWEEN start AND end AND barberId = ? "
                    + "OR ? < start AND ? > end AND barberId = ?) "
            );
            ps.setString(1, sDate);
            ps.setInt(2, b.getBarberId());
            ps.setString(3, eDate);
            ps.setInt(4, b.getBarberId());
            ps.setString(5, sDate);
            ps.setString(6, eDate);
            ps.setInt(7, b.getBarberId());
            ResultSet rs = ps.executeQuery();

            if (rs.absolute(1)) {

                overlap = true;
            }

        } catch (SQLException ex) {
            System.out.println("Check SQL Exception " + ex);
        }

        System.out.println("Results of overlap? " + overlap);
        return overlap;

    }

    //constructor is called first, The initialize method is called after all @FXML annotated members have been injected
    @FXML
    public void initialize() {

    }

    private void populatePetCombo(Customer c) {
        ObservableList<Pet> pets = PetCache.getSelectedPets(c);
        Pet p = new Pet("-1", "New Pet", "x", "x");
        pets.add(p);
//
        comboPet.setItems(PetCache.getSelectedPets(c));
        comboPet.getSelectionModel().selectFirst();
//        selectedPets.clear();
//        selectedPets.addAll(PetCache.getSelectedPets(c));
//        selectedPets.add(0, p);

    }

    private void convertComboBoxtoString() {
        //Add String converter to convert barber and customer objects
        comboBarber.setConverter(new StringConverter<Barber>() {
            @Override
            public String toString(Barber object) {
                if (object == null) {
                    return null;
                } else {
                    return object.nameProperty().get();
                }
            }

            @Override
            public Barber fromString(String string) {
                return comboBarber.getItems().stream().filter(ap -> ap.nameProperty().get().equals(string)).findFirst().orElse(null);
            }
        });

        comboExistCustomer.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer object) {
                if (object == null) {
                    return null;
                } else {
                    return object.customerNameProperty().get();
                }
            }

            @Override
            public Customer fromString(String string) {
                return comboExistCustomer.getItems().stream().filter(ap -> ap.customerNameProperty().get().equals(string)).findFirst().orElse(null);
            }
        });

        comboPet.setConverter(new StringConverter<Pet>() {
            @Override
            public String toString(Pet object) {
                if (object == null) {
                    return null;
                } else {
                    return object.nameProperty().get();
                }
            }

            @Override
            public Pet fromString(String string) {
                return comboPet.getItems().stream().filter(ap -> ap.nameProperty().get().equals(string)).findFirst().orElse(null);
            }
        });
    }

}
