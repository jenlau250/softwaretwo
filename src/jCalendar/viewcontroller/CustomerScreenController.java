/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import jCalendar.dao.CustomerDaoImpl;
import jCalendar.dao.DBConnection;
import jCalendar.dao.Query;
import jCalendar.jCalendar;
import jCalendar.model.Customer;
import jCalendar.model.CustomerPets;
import jCalendar.model.Pet;
import jCalendar.model.User;
import jCalendar.utilities.Loggerutil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class CustomerScreenController {

    private jCalendar mainApp;
    private User currentUser;

    @FXML
    private Label customerLabel;
    @FXML
    private Label labelCusID;
    @FXML
    private Button btnCustomerAdd;
    @FXML
    private Button btnCustomerUpdate;
    @FXML
    private Button btnCustomerDelete;
    @FXML
    private Button btnCustomerSave;
    @FXML
    private Button btnCustomerCancelSave;
    @FXML
    private TableView<Customer> CustomerTable;
    @FXML
    private TableColumn<Customer, String> colCustomerID;
    @FXML
    private TableColumn<Customer, String> colCustomerName;
    @FXML
    private TableColumn<Customer, String> colCustomerPhone;

    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtCustomerPhone; //Address
    @FXML
    private TextField txtCustomerEmail; //Address2
    @FXML
    private TextField txtCustomerStatus; //ZipCode
    @FXML
    private TextField txtCustomerNotes; //Phone

    @FXML
    private ComboBox<Pet> comboPet;

    @FXML
    private ComboBox<String> comboPetType;

    @FXML
    private TextField txtPetDescription;

    private ObservableList<Customer> Customers = FXCollections.observableArrayList();
    private ObservableList<CustomerPets> customerPets = FXCollections.observableArrayList();
    private ObservableList<Pet> Pets = FXCollections.observableArrayList(); //Cities
    private ObservableList<Pet> selectedPets = FXCollections.observableArrayList(); //selectedCountries
    private boolean editMode;

    private final static Logger logger = Logger.getLogger(Loggerutil.class.getName());

    public CustomerScreenController() {

    }

    protected ObservableList<Pet> populatePets() {

        int petId;
        String petName;

        ObservableList<Pet> petList = FXCollections.observableArrayList();
        try (
                 PreparedStatement ps = DBConnection.getConn().prepareStatement(
                        "SELECT pet.petId, pet.petName "
                        + "FROM pet, customerpets "
                        + "WHERE pet.petId = customerpets.petId"
                );  ResultSet rs = ps.executeQuery();) {

                    while (rs.next()) {
                        petId = rs.getInt("pet.petId");
                        petName = rs.getString("pet.petName");
                        petList.add(new Pet(petId, petName));
                    }
                } catch (SQLException sqe) {
                    System.out.println("Check SQL Exception");
                    sqe.printStackTrace();
                } catch (Exception e) {
                    System.out.println("Check Exception");
                }
                return petList;

    }

    @FXML
    private void initializePetTypes() {

        Query.makeQuery("SELECT petType FROM pet");
        ResultSet rs = Query.getResult();
        try {
            while (rs.next()) {
                comboPetType.getItems().add(rs.getString(1));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL Exception with populating pet types.");
        }
    }

    @FXML
    private void showCustomerDetails(Customer selectedCustomer) {

        //populate customer details based on selection
        //**need to rename FXML FIELDS, need to show barber
        txtCustomerName.setText(selectedCustomer.getCustomerName());
        txtCustomerPhone.setText(selectedCustomer.getPhone());
        txtCustomerEmail.setText(selectedCustomer.getEmail());
//	countryCombo.setValue(selectedCustomer.getPet());
//        comboPet.setValue(selectedCustomer.getPet());
//        comboPetType.setValue(selectedCustomer.getPet().getPetType());
//        txtPetDescription.setText(selectedCustomer.getPet().getPetDescription());
        txtCustomerStatus.setText(selectedCustomer.getActive());
        txtCustomerNotes.setText(selectedCustomer.getNotes());
        labelCusID.setText(String.valueOf(selectedCustomer.getCustomerId()));

    }

    @FXML
    void handleAddCustomer(ActionEvent event) {

        customerLabel.setText("Add Customer Details");
        btnCustomerAdd.setDisable(true);
        btnCustomerUpdate.setDisable(true);
        btnCustomerDelete.setDisable(true);
        btnCustomerCancelSave.setDisable(false);
        btnCustomerSave.setDisable(false);
        CustomerTable.setDisable(true);
        clearFields();
        txtCustomerName.requestFocus();
        editMode = false;
        enableEdits();
        labelCusID.setText("Auto Generated");

    }

    @FXML
    void handleDeleteCustomer(ActionEvent event) {

        Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm delete");
            alert.setHeaderText("Are you sure you want to delete " + selectedCustomer.getCustomerName() + "?");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        deleteCustomer(selectedCustomer);
                        mainApp.showCustomerScreen();
                    });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Not selected");
            alert.setHeaderText("No customer was selected to delete");
            alert.setContentText("Please select a customer to delete");
            alert.showAndWait();
        }
    }

    @FXML
    void handleCancelCustomer(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.showAndWait()
                // Lambda use - set OK button to respond
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    CustomerTable.setDisable(false);
                    labelCusID.setText("");
                    btnCustomerAdd.setDisable(false);
                    btnCustomerUpdate.setDisable(false);
                    btnCustomerDelete.setDisable(false);
                    btnCustomerCancelSave.setDisable(false);
                    btnCustomerSave.setDisable(false);
                    customerLabel.setText("Customer Details");
                    clearFields();
                    editMode = false;
                });
    }

    @FXML
    void handleSaveCustomer(ActionEvent event) {

        Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();
        System.out.println("Debug handleSaveCustomer: editMode: " + editMode);
        if (validateCustomer()) {
            if (editMode) {
                updateCustomer(selectedCustomer);
            } else {
                saveNewCustomer();
            }
            mainApp.showCustomerScreen();
        }
    }

    @FXML
    void handleUpdateCustomer(ActionEvent event) {

        enableEdits();
        customerLabel.setText("Update Customer Details");

        Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            editMode = true;
            CustomerTable.setDisable(false);
            btnCustomerAdd.setDisable(true);
            btnCustomerUpdate.setDisable(true);
            btnCustomerDelete.setDisable(true);
            btnCustomerCancelSave.setDisable(false);
            btnCustomerSave.setDisable(false);

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Not selected");
            alert.setHeaderText("No customer was selected");
            alert.setContentText("Please select a customer to update");
            alert.showAndWait();
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

        customerLabel.setText("Customer Details");
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone")); //as named in Customer

        initializePetTypes();

        // Query pet list and add to list
        selectedPets = populatePets();
        comboPet.setItems(selectedPets);
        comboPet.setConverter(new StringConverter<Pet>() {
            @Override
            public String toString(Pet object) {
                return object.nameProperty().get();
            }

            @Override
            public Pet fromString(String string) {
                return comboPet.getItems().stream().filter(ap -> ap.nameProperty().get().equals(string)).findFirst().orElse(null);
            }
        });

        // Show pet name in combo box
//        comboPet.setConverter(new StringConverter<Pet>() {
//
//            @Override
//            public String toString(Pet object) {
//                return object.getPetName();
//            }
//
//            @Override
//            public Pet fromString(String string) {
//                return comboPet.getItems().stream().filter(ap -> ap.getPetName().equals(string)).findFirst().orElse(null);
//            }
//        });
        try {
            Customers.addAll(CustomerDaoImpl.getallCustomers());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Exception error with getting all customer data");
            Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //default settings
        CustomerTable.getItems().setAll(Customers);
        disableEdits();
        labelCusID.setText("");
        btnCustomerCancelSave.setDisable(true);
        btnCustomerSave.setDisable(true);

        CustomerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showCustomerDetails(newSelection);
            }
        });
    }

    private void enableEdits() {

        txtCustomerName.setEditable(true);
        txtCustomerPhone.setEditable(true);
        txtCustomerEmail.setEditable(true);
        txtCustomerStatus.setEditable(true);
        txtCustomerNotes.setEditable(true);
        comboPet.setEditable(true);
        comboPetType.setEditable(true);
        txtPetDescription.setEditable(true);
    }

    private void disableEdits() {

        txtCustomerName.setEditable(false);
        txtCustomerPhone.setEditable(false);
        txtCustomerEmail.setEditable(false);
        txtCustomerStatus.setEditable(false);
        txtCustomerNotes.setEditable(false);;
        comboPet.setEditable(false);
        comboPetType.setEditable(false);
        txtPetDescription.setEditable(false);
    }

    private void clearFields() {
        labelCusID.setText("");
        txtCustomerName.clear();
        txtCustomerPhone.clear();
        txtCustomerEmail.clear();
        comboPet.setValue(null);
        txtCustomerStatus.clear();
        txtCustomerNotes.clear();
        comboPetType.setValue(null);
        txtPetDescription.clear();
    }

    /**
     * Inserts new customer record
     */
    private void saveNewCustomer() {
        System.out.println("currently commented out saving new customer");
        try {

//LEFT OUT PET IMAGES FOR NOW
            PreparedStatement ps = DBConnection.getConn().prepareStatement("INSERT INTO pet "
                    + "(petName, petType, petDescription, createDate, createdBy, lastUpdate, lastUpdateBy) "
                    + " VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, comboPet.getValue().nameProperty().get());
            ps.setString(2, comboPetType.getValue());
            ps.setString(3, txtPetDescription.getText());
            ps.setString(4, currentUser.getUserName());
            ps.setString(5, currentUser.getUserName());

            ps.executeUpdate();

            int newPetId = -1;
            int newCustomerId = -1;
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                newPetId = rs.getInt(1);
                newCustomerId = rs.getInt(1);
            }

            PreparedStatement pst = DBConnection.getConn().prepareStatement("INSERT INTO customer "
                    + "(petId, customerName, customerPhone, customerEmail, notes, active, createDate, createdBy, lastUpdate, lastUpdateBy, customerId) "
                    + " VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?, ?)");

            pst.setInt(1, newPetId);
            pst.setString(2, txtCustomerName.getText());
            pst.setString(3, txtCustomerPhone.getText());
            pst.setString(4, txtCustomerEmail.getText());
            pst.setString(5, txtCustomerNotes.getText());
            pst.setInt(6, 1);
            pst.setString(7, currentUser.getUserName());
            pst.setString(8, currentUser.getUserName());
            pst.setInt(9, newCustomerId);

            int result = pst.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Updates customer records
     */
    private void updateCustomer(Customer selectedCustomer) {
        System.out.println("commented out updating customer");

//	try {
//
//	     PreparedStatement ps = DBConnection.getConn().prepareStatement("UPDATE address, customer, city, country "
//                        + "SET address = ?, address2 = ?, address.cityId = ?, postalCode = ?, phone = ?, address.lastUpdate = CURRENT_TIMESTAMP, address.lastUpdateBy = ? "
//                        + "WHERE customer.customerId = ? AND customer.addressId = customer.customerPhoneId AND address.cityId = city.cityId AND city.countryId = country.countryId");
//
//                ps.setString(1, txtCustomerAddress.getText());
//                ps.setString(2, txtCustomerAddress2.getText());
//                ps.setInt(3, cityCombo.getValue().getCityId());
//                ps.setString(4, txtCustomerZipCode.getText());
//                ps.setString(5, txtCustomerPhone.getText());
//                ps.setString(6, currentUser.getUserName());
//                ps.setString(7, labelCusID.getText());
//
//                int result = ps.executeUpdate();
//
//                PreparedStatement psc = DBConnection.getConn().prepareStatement("UPDATE customer, address, city "
//                + "SET customerName = ?, customer.lastUpdate = CURRENT_TIMESTAMP, customer.lastUpdateBy = ? "
//                + "WHERE customer.customerId = ? AND customer.addressId = customer.customerPhoneId AND address.cityId = city.cityId");
//
//                psc.setString(1, txtCustomerName.getText());
//                psc.setString(2, currentUser.getUserName());
//                psc.setString(3, labelCusID.getText());
//                int results = psc.executeUpdate();
//
//            } catch (SQLException ex) {
//            ex.printStackTrace();
//            }
//    }
//    public int getCountryId(String country, User currentUser) throws SQLException {
//
//	int countryId = 0;
//
//	String sqlstmt = "SELECT countryId FROM country WHERE country = ?";
//
//	try {
//	    PreparedStatement ps = DBConnection.getConn().prepareStatement(sqlstmt);
//	    ps.setString(1, country);
//
//
//	    ResultSet countryIdResult = ps.executeQuery();
//
//	    if (countryIdResult.next()) {
//		countryId = countryIdResult.getInt(1);
//	    } else {
//		String insertCountryStatement = "INSERT INTO country (country, "
//			+ "createDate, createdBy, lastUpdate, lastUpdateBy) "
//			+ "VALUES (?, ?, ?, ?, ?)";
//		ps = DBConnection.getConn().prepareStatement(insertCountryStatement);
//		ps.setString(1, country);
//		ps.setString(2, "CURRENT_TIMESTAMP");
//		ps.setString(3, currentUser.getUserName());
//		ps.setString(4, "CURRENT_TIMESTAMP");
//		ps.setString(5, currentUser.getUserName());
//		ps.executeUpdate();
//
//		countryId = getCountryId(country, currentUser);
//	    }
//	} catch (SQLException e) {
//	    e.printStackTrace();
//	}
//	return countryId;
//    }
    }

    private void deleteCustomer(Customer customer) {

        try {
            PreparedStatement pst = DBConnection.getConn().prepareStatement("DELETE customer.*, pet.* from customer, pet WHERE customer.customerId = ? AND customer.petId = pet.petId");
            pst.setInt(1, customer.getCustomerId());
            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean validateCustomer() {

        //EXCEPTION CONTROL: Validate nonexistent or invalid customer data
        String name = txtCustomerName.getText();
        String phone = txtCustomerPhone.getText();
        String email = txtCustomerEmail.getText();
        Pet pet = comboPet.getValue();
        String status = txtCustomerStatus.getText();
        String notes = txtCustomerNotes.getText();
        String petType = comboPetType.getValue();
        String petDesc = txtPetDescription.getText();

        String errorMessage = "";
//
//        if (name == null || name.length() == 0) {
//            errorMessage += "Please enter customer name.\n";
//        }
//        if (phone == null || phone.length() == 0) {
//            errorMessage += "Please enter an address.\n";
//        }
//        // address 2 is optional
//        if (city == null) {
//            errorMessage += "Please select a valid city.\n";
//        }
//        if (barber == null || barber.length() == 0) {
//            errorMessage += "Please select a valid country.\n";
//        }
//        if (status == null || status.length() == 0) {
//            errorMessage += "Please enter the zip code.\n";
//        } else if (status.length() > 10 || status.length() < 5) {
//            errorMessage += "Zip code must be between 5 and 10 digits.\n";
//        }
//        if (notes == null || notes.length() == 0) {
//            errorMessage += "Please enter a phone number).";
//        } else if (phone.length() < 10 || phone.length() > 20) {
//            errorMessage += "Phone number must be between 10 and 20 digits.\n";
//        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Invalid data");
//            alert.setHeaderText("Please fix the following customer field(s)");
//            alert.setContentText(errorMessage);
//
//            alert.showAndWait();

            System.out.println("Printing new customer saved for "
                    + "Name: " + name + "\n"
                    + "Phone: " + phone + "\n"
                    + "Email: " + email + "\n"
                    + "Pet: " + pet + "\n"
                    + "Status: " + status + "\n"
                    + "Notes: " + notes + "\n"
                    + "Pet Type: " + petType + "\n"
                    + "Pet Desc:: " + petDesc + "\n"
            );

            return false;
        }
    }

}
