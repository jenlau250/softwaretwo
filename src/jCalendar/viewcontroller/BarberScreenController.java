/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import Cache.BarberCache;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import jCalendar.dao.DBConnection;
import jCalendar.jCalendar;
import jCalendar.model.Barber;
import jCalendar.model.User;
import jCalendar.utilities.Loggerutil;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class BarberScreenController {

    private jCalendar mainApp;
    private User currentUser;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<Barber> BarberTable;

    @FXML
    private TableColumn<Barber, String> colID;

    @FXML
    private TableColumn<Barber, String> colName;

    @FXML
    private TableColumn<Barber, String> colPhone;

    @FXML
    private TableColumn<Barber, String> colEmail;
    @FXML
    private TableColumn<Barber, String> colStatus;
    @FXML
    private TableColumn<Barber, String> colNotes;
    @FXML
    private TableColumn<Barber, String> colHireDate;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtNotes;

    @FXML
    private Label labelBarberId;
//
    @FXML
    private Label barberLabel;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXComboBox<String> cbStatus;
    private ObservableList<Barber> barbers = FXCollections.observableArrayList();
    private boolean editMode;

    private final static Logger logger = Logger.getLogger(Loggerutil.class.getName());

    List<String> statusOptions = new ArrayList<>();

    public BarberScreenController() {

    }

//    @FXML
//    private void showBarberDetails(Barber selectedBarber) {
//
//        labelBarberId.setText(String.valueOf(selectedBarber.getBarberId()));
//
//        txtName.setText(selectedBarber.nameProperty().get());
//        txtPhone.setText(selectedBarber.barberPhoneProperty().get());
//        txtEmail.setText(selectedBarber.barberEmailProperty().get());
//        txtStatus.setText(selectedBarber.activeProperty().get());
//        txtNotes.setText(selectedBarber.noteProperty().get());
//        cbStatus.setValue(selectedBarber.activeProperty().get());
//        datePicker.setValue(selectedBarber.getHireDate());
//
//    }
    @FXML
    void handleAdd(ActionEvent event) {

//        barberLabel.setText("Add Barber Details");
//        btnAdd.setDisable(true);
//        btnUpdate.setDisable(true);
//        btnDelete.setDisable(true);
//        btnCancel.setDisable(false);
//        btnSave.setDisable(false);
//        BarberTable.setDisable(true);
//        clearFields();
//        txtName.requestFocus();
//        editMode = false;
//        enableEdits();
//        labelBarberId.setText("Auto Generated");
        mainApp.showBarberAddScreen();

    }

    @FXML
    void handleDelete(ActionEvent event) {

        Barber selectedBarber = BarberTable.getSelectionModel().getSelectedItem();

        if (selectedBarber != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm delete");
            alert.setHeaderText("Are you sure you want to delete " + selectedBarber.getBarberName() + "?");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        deleteBarber(selectedBarber);
//                        mainApp.showBarberScreen();
                        BarberCache.flush();
                    });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Not selected");
            alert.setHeaderText("No barber was selected to delete");
            alert.setContentText("Please select a barber to delete");
            alert.showAndWait();
        }
    }

//    @FXML
//    void handleCancel(ActionEvent event) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Confirm cancel");
//        alert.setHeaderText("Are you sure you want to cancel?");
//        alert.showAndWait()
//                // Lambda use - set OK button to respond
//                .filter(response -> response == ButtonType.OK)
//                .ifPresent(response -> {
//                    BarberTable.setDisable(false);
//                    labelBarberId.setText("");
//                    btnAdd.setDisable(false);
//                    btnUpdate.setDisable(false);
//                    btnDelete.setDisable(false);
//                    btnCancel.setDisable(false);
//                    btnSave.setDisable(false);
//                    barberLabel.setText("Barber Details");
//                    clearFields();
//                    editMode = false;
//                });
//    }
//
//    @FXML
//    void handleSave(ActionEvent event) {
//
//        Barber selectedBarber = BarberTable.getSelectionModel().getSelectedItem();
////        System.out.println("Debug handeSaveBarber: editMode: " + editMode);
//        if (validateBarber()) {
//            if (editMode) {
//                updateBarber(selectedBarber);
//            } else {
//                saveNewBarber();
//            }
//            mainApp.showBarberScreen();
//        }
//    }
    @FXML
    void handleUpdate(ActionEvent event) {

//        enableEdits();
//        barberLabel.setText("Update Barber Details");
        Barber selectedBarber = BarberTable.getSelectionModel().getSelectedItem();
//
//        if (selectedBarber != null) {
//            editMode = true;
//            BarberTable.setDisable(false);
//            btnAdd.setDisable(true);
//            btnUpdate.setDisable(true);
//            btnDelete.setDisable(true);
//            btnCancel.setDisable(false);
//            btnSave.setDisable(false);
//
//        } else {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Not selected");
//            alert.setHeaderText("No barber was selected");
//            alert.setContentText("Please select a barber to update");
//            alert.showAndWait();
//        }

        if (selectedBarber != null) {
            mainApp.showBarberAddScreen(selectedBarber);
        } else {
            System.out.println("Please select a barber to update.");
        }

    }

    /**
     * Initializes the controller class.
     *
     * @param mainApp
     * @param currentUser
     */
    public void setMainController(jCalendar mainApp) {

        this.mainApp = mainApp;
//        this.currentUser = currentUser;

        initCol();

        BarberTable.setItems(BarberCache.getAllBarbers());

//        disableEdits();
//        labelBarberId.setText("");
//        btnCancel.setDisable(true);
//        btnSave.setDisable(true);
//        BarberTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//
//                showBarberDetails(newSelection);
//
//            }
//        });
//        Barber selectedBarber = new Barber();
//        datePicker.valueProperty().bindBidirectional(selectedBarber.hireDateProperty());
    }

    private void initCol() {

        //Load Barber Tableview
        barberLabel.setText("Barber Details");
        colID.setCellValueFactory(new PropertyValueFactory<>("barberId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("barberName"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("barberPhone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("barberEmail"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("active"));
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        colHireDate.setCellValueFactory(new PropertyValueFactory<>("hireDate"));

    }

//    private void enableEdits() {
//
//        txtName.setEditable(true);
//        txtPhone.setEditable(true);
//        txtEmail.setEditable(true);
//        txtStatus.setEditable(true);
//        txtNotes.setEditable(true);
//        datePicker.setEditable(true);
//
//    }
//
//    private void disableEdits() {
//
//        txtName.setEditable(false);
//        txtPhone.setEditable(false);
//        txtEmail.setEditable(false);
//        txtStatus.setEditable(false);
//        txtNotes.setEditable(false);
//        datePicker.setEditable(false);
//
//    }
//
//    private void clearFields() {
//        labelBarberId.setText("");
//        txtName.clear();
//        txtPhone.clear();
//        txtEmail.clear();
//        txtStatus.clear();
//        txtNotes.clear();
//        datePicker.setValue(LocalDate.now());
//
//    }
//    /**
//     * Inserts new barber record
//     */
//    private void saveNewBarber() {
//
//        try {
//
//            String status = cbStatus.getValue();
//            LocalDate selHireDate = datePicker.getValue();
//
//            java.sql.Date sqlDate = Date.valueOf(selHireDate);
//
//            PreparedStatement pst = DBConnection.getConn().prepareStatement("INSERT INTO barber "
//                    + "(barberName, barberPhone, barberEmail, notes, active, hireDate) "
//                    + " VALUES (?, ?, ?, ?, ?, ?)");
//
//            pst.setString(1, txtName.getText());
//            pst.setString(2, txtPhone.getText());
//            pst.setString(3, txtEmail.getText());
//            pst.setString(4, txtNotes.getText());
//            pst.setString(5, status);
//            pst.setDate(6, sqlDate);
//
////            System.out.println("printing save barber query " + pst);
////            pst.setString(7, currentUser.getUserName());
////
////            PreparedStatement pst = DBConnection.getConn().prepareStatement("INSERT INTO barber "
////                    + "(barberName, barberPhone, barberEmail, notes, active, hireDate, createDate, createdBy, lastUpdate, lastUpdateBy) "
////                    + " VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?, ?)");
//            int result = pst.executeUpdate();
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
////        BarberTable.setItems(mainApp.getBarberData());
////        mainApp.refreshView();
//        BarberCache.flush();
//    }
//
//    /**
//     * Updates customer records
//     */
//    private void updateBarber(Barber b) {
//
//        LocalDate selHireDate = datePicker.getValue();
//
//        java.sql.Date sqlDate = Date.valueOf(selHireDate);
//        System.out.println("sql date" + sqlDate);
//        System.out.println("attempting to update " + b.getBarberName());
//        try {
//
//            PreparedStatement ps = DBConnection.getConn().prepareStatement("UPDATE barber "
//                    + "SET barberName = ?, barberPhone = ?, barberEmail = ?, notes = ?, active = ?, hireDate = ? "
//                    + "WHERE barberId = " + b.getBarberId());
//
//            ps.setString(1, txtName.getText());
//            ps.setString(2, txtPhone.getText());
//            ps.setString(3, txtEmail.getText());
//            ps.setString(4, txtNotes.getText());
//            ps.setString(5, txtStatus.getText());
//            ps.setDate(6, sqlDate);
//
//            int result = ps.executeUpdate();
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        BarberCache.flush();
//
//    }
    private void deleteBarber(Barber selectedBarber) {

        try {
            PreparedStatement pst = DBConnection.getConn().prepareStatement(
                    "DELETE FROM barber WHERE barberId = ?");
            pst.setInt(1, selectedBarber.getBarberId());
            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        mainApp.refreshView();
        BarberCache.flush();

//        mainApp.getBarberData().clear();
//        BarberTable.setItems(mainApp.getBarberData());
    }
//
//    private boolean validateBarber() {
//
//        //EXCEPTION CONTROL: Validate nonexistent or invalid customer data
//        String name = txtName.getText();
//        String phone = txtPhone.getText();
//        String email = txtEmail.getText();
//        String status = txtStatus.getText();
//        String notes = txtNotes.getText();
//        String hireDate = datePicker.getValue().toString();
//
//        String errorMessage = "";
////
//        if (name == null || name.length() == 0) {
//            errorMessage += "Please enter customer name.\n";
//        }
//        if (email == null || email.length() == 0) {
//            errorMessage += "Please enter an email address.\n";
//        }
////notes optional
////        if (hireDate == null || hireDate.length() == 0) {
////            errorMessage += "Please enter a hire date.\n";
////        }
//
//        if (status == null || status.length() == 0) {
//            errorMessage += "Please enter the employee status.\n";
//        } else if (status.length() > 1) {
//            errorMessage += "Must be less than one digit.\n";
//        }
//        if (phone == null || phone.length() == 0) {
//            errorMessage += "Please enter a phone number).";
//        } else if (phone.length() < 10 || phone.length() > 20) {
//            errorMessage += "Phone number must be between 10 and 20 digits.\n";
//        }
//
//        if (errorMessage.length() == 0) {
//            return true;
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Invalid data");
//            alert.setHeaderText("Please fix the following customer field(s)");
//            alert.setContentText(errorMessage);
//
//            alert.showAndWait();
//
//            System.out.println("Printing new barber saved for "
//                    + "Name: " + name + "\n"
//                    + "Phone: " + phone + "\n"
//                    + "Email: " + email + "\n"
//                    + "Status: " + status + "\n"
//                    + "Notes: " + notes + "\n"
//                    + "Hire Date: " + hireDate + "\n"
//            );
//
//            return false;
//        }
//    }
}
