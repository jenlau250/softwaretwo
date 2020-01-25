/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import jCalendar.dao.DBConnection;
import jCalendar.dao.PetDaoImpl;
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
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane rootPane;
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
     */
    public void setMainController(jCalendar mainApp, User currentUser) {

        this.mainApp = mainApp;
        this.currentUser = currentUser;

        clearFields();
        initFields();

        System.out.println("Saved for App Screen mainApp, user, Appt " + mainApp + " " + currentUser);

        if (selectedAppt != null) {
//EDIT APPT
            System.out.println("Printing selected appt " + selectedAppt);
            topLabel.setText("Edit Appointment");
            System.out.println("to edit");
//            editClicked = true;
//            btnApptCancel.setDisable(false);
//            btnApptSave.setDisable(false);
//            btnApptUpdate.setDisable(true);
//            btnApptAdd.setDisable(true);
//            btnApptDel.setDisable(true);
//            apptVBox.setVisible(true);

        } else {
            System.out.println("to add");
            //IF NO SELECTED APPT, then ADD

//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Nothing selected");
//            alert.setHeaderText("No appointment was selected");
//            alert.setContentText("Please select an appointment to update");
//            alert.showAndWait();
        }

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
//                        return null;
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
//                return null;
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

        //POTENTIAL ISSUE: setting combobox could return null pointer exception
        //solved by moving initializing data to setMainController() method
        //Update Pet Combo based on current selection of Existing Customer
        comboExistCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                System.out.println("old value is: " + oldValue);
            } else {
                comboPet.setItems(new PetDaoImpl().getPetsByCustomer(newValue.customerIdProperty().get()));
//                System.out.println("newValue is " + newValue);
//                int customerId = comboExistCustomer.getValue().customerIdProperty().get();
//                System.out.println("printing customerId " + customerId);

            }
        });

        //Add Customer radio button listener
        //set default
//        choiceExistingCustomer.setSelected(true);
//        comboExistCustomer.setVisible(true);
//        txtNewCustomer.setVisible(false);
//
//        txtNewCustomer.managedProperty().bind(txtNewCustomer.visibleProperty());
        //HIDE - test if this is causing combo box issues
//        comboExistCustomer.managedProperty().bind(comboExistCustomer.visibleProperty());
//
//        choiceExistingCustomer.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
//            if (isSelected) {
//                comboExistCustomer.setVisible(true);
//                txtNewCustomer.setVisible(false);
//                System.out.println("existing customer selected");
//            }
//        });
//
//        choiceNewCustomer.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
//            if (isSelected) {
//                comboExistCustomer.setVisible(false);
//                txtNewCustomer.setVisible(true);
//                System.out.println("new customer selected");
//            }
//        });
        //Set close button to exit
        btnClose.setOnMouseClicked((evt) -> {
//            Stage stage = (Stage) rootPane.getScene().getWindow();
//            stage.close();
            mainApp.showAppointmentListScreen(currentUser);
        });
    }

    @FXML
    private void clearFields() {

//        comboExistCustomer.setValue(null);
        comboBarber.getItems().clear();
        comboType.getItems().clear();
        comboExistCustomer.getItems().clear();
        comboStart.getItems().clear();
        comboEnd.getItems().clear();
        comboPet.getItems().clear();
        txtDesc.clear();
        txtTitle.clear();
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    private void initFields() {

        //datePicker
        //Start and End Times dropdown
        comboStart.setItems(Appointment.getDefaultStartTimes());
        comboEnd.setItems(Appointment.getDefaultEndTimes());

        //Get Appointment Type
        comboType.setItems(Appointment.getApptTypes());
//        txtTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
//        txtDesc.setCellValueFactory(new PropertyValueFactory<>("notes"));
////
//        tCustomer.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getCustomer().getCustomerName()));
//        tStartDate.setCellValueFactory(new PropertyValueFactory<>("start"));

        //CLEAR AND INITIALIZE DEFAULT FIELDS
//        customers.clear();
//        customers.addAll(mainApp.getCustomerData());
//        comboExistCustomer.setItems(mainApp.getCustomerData());
        comboExistCustomer.getItems().addAll(mainApp.getCustomerData());
//        populatePetsLists();

//if not null, change customer list
//
//        getPetsByCustomer(customerId);
        comboBarber.setItems(mainApp.getBarberData());
    }

    @FXML
    void handleApptCancel(ActionEvent event) {
        // Close the window
//        Stage stage = (Stage) rootPane.getScene().getWindow();
//        stage.close();
//
        mainApp.showAppointmentListScreen(currentUser);
    }

    @FXML
    void handleApptSave(ActionEvent event) {

        //HANDLE NULL POINTER EXCEPTION AT DATETIMEUTIL
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

        //Convert datetime for database
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime startUTC = startDate.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endUTC = endDate.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));

        if (validateApptOverlap(startUTC, endUTC)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Appointment Overlap");
            alert.setHeaderText("Warning: Appointment was not saved");
            alert.setContentText("Overlaps with existing appointment. Please check again.");
            alert.showAndWait();

        } else {

            saveAppointment();
//            if (topLabel.getText().contains("Edit")) {
//                System.out.println("Updating..");
//                updateAppointment();
//
//            } else if (topLabel.getText().contains("Add")) {
//                System.out.println("Adding..");
//                saveAppointment();
//            }
        }
    }
//
//    private void populatePetsLists() {
//
//        //clear and load pet box
//        comboPet.getItems().clear();
//
//        int selCustomerId = comboExistCustomer.getValue().customerIdProperty().get();
//        System.out.println(selCustomerId);
//
////        comboPet.getItems().removeIf((c) -> !Utils.isEmpty());
//        //if combo box is not null
//        comboPet.getItems().addAll(getPetsByCustomer(selCustomerId));
//        comboPet.setItems(getPetsByCustomer(selCustomerId));
//        String sql = "SELECT pet.petId, pet.petName "
//                + "FROM pet "
//                + "WHERE petId = \"" + selectedPetId + "\"";
//
//        Query.makeQuery(sql);
//        System.out.println("Sql statement is " + sql);
//        ResultSet result = Query.getResult();
//
//        try {
//            while (result.next()) {
//                selectedPets.add(new Pet(result.getInt("pet.petId"), result.getString("pet.petName")));
//            }
//        } catch (SQLException ex) {
//            logger.log(Level.SEVERE, "SQL Exception with populating pet combo box.");
//        }
////         Set city options in cb.
//        comboPet.setItems(selectedPets);
//    }

    private void updateAppointment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void saveAppointment() {

        //TEMP ADD CURRENT USER AS "TEST"
        currentUser.setUserName("test");
        System.out.println("Current user for Appt SAVE Screen " + currentUser);

        //Convert datepicker and time to LocalDateTime
        String selDate = datePicker.getValue().toString();
        String selStart = selDate + " " + comboStart.getValue();
        String selEnd = selDate + " " + comboEnd.getValue();

        LocalDateTime startTime = DateTimeUtil.parseStringToLocalDateTime(selStart);
        LocalDateTime endTime = DateTimeUtil.parseStringToLocalDateTime(selEnd);

        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime startUTC = startTime.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endUTC = endTime.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));

        Timestamp startsql = Timestamp.valueOf(startUTC.toLocalDateTime());
        Timestamp endsql = Timestamp.valueOf(endUTC.toLocalDateTime());

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringStart = dateFormatter.format(startsql);
        String stringEnd = dateFormatter.format(endsql);

        String sType = comboType.getValue();
        //String sBarber = comboBarber.getValue().toString();
        int sBarber = comboBarber.getValue().barberIdProperty().get();
        String sTitle = txtTitle.getText();
        String sDesc = txtDesc.getText();
        String sCustomer = comboExistCustomer.getValue().customerIdProperty().get();
        String sPet = comboPet.getValue().petIdProperty().get();
//        String sContact = currentUser.getUserName(); //CHANGE LATER

        System.out.println("Printing record to save: "
                + " Title " + sTitle + " "
                + " Desc " + sDesc + " "
                + " Type " + sType + " "
                + " Barber " + sBarber + " " + comboBarber.getValue().nameProperty().get()
                + " Customer " + sCustomer + " " + comboExistCustomer.getValue().customerNameProperty().get()
                + " Pet " + sPet + " " + comboPet.getValue().nameProperty().get()
        );

        //RESULT:
//           [java] Printing record to save:  Title Test  Desc test  Type Extended
//Barber StringProperty [value: Cutty]
//Customer Customer id 2 name StringProperty [value: Sam] phone StringProperty [value: null] email StringProperty [value: null]
//Pet jCalendar.model.Pet@654b8b25
//        Appointment appt = new Appointment(String sTitle, stringStart, stringEnd, sDesc, sType, sBarber, sCustomer);
        //Print appointment times
        System.out.println("Appointment times to save :" + stringStart + " and " + stringEnd);
        System.out.println("Current user :" + currentUser.getUserName());
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
            pst.setString(9, currentUser.getUserName());
            pst.setString(10, currentUser.getUserName());
            int result = pst.executeUpdate();
            if (result == 1) {//one row was affected; namely the one that was inserted!
                System.out.println("YAY! New Appointment Save");
//                System.out.println("saved " + comboCustomer.getValue().getCustomerName());
//                mainApp.showAppointmentScreen(currentUser);

            } else {
                System.out.println("BOO! New Appointment Save");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        mainApp.refreshView();
    }

    public Boolean validateApptOverlap(ZonedDateTime sDate, ZonedDateTime eDate) {
        Boolean overlap = false;

//ADD VALIDATION LOGIC FOR SAME BARBER
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(
                    "SELECT * FROM appointment "
                    + "WHERE (? BETWEEN start AND end OR ? BETWEEN start AND end OR ? < start AND ? > end AND barberId = ?) "
            );
            ps.setTimestamp(1, Timestamp.valueOf(sDate.toLocalDateTime()));
            ps.setTimestamp(2, Timestamp.valueOf(eDate.toLocalDateTime()));
            ps.setTimestamp(3, Timestamp.valueOf(sDate.toLocalDateTime()));
            ps.setTimestamp(4, Timestamp.valueOf(eDate.toLocalDateTime()));
//            ps.setString(6,, barberId);
            ResultSet rs = ps.executeQuery();

            if (rs.absolute(1)) {
                overlap = true;
            }

        } catch (SQLException ex) {
            System.out.println("Check SQL Exception " + ex);
        }

        return overlap;

    }

    //constructor is called first, The initialize method is called after all @FXML annotated members have been injected
    @FXML
    public void initialize() {

    }

}
