/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import Cache.CustomerCache;
import Cache.PetCache;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import jCalendar.dao.DBConnection;
import jCalendar.jCalendar;
import jCalendar.model.Customer;
import jCalendar.model.Pet;
import jCalendar.model.User;
import jCalendar.utilities.Loggerutil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class CustomerScreenController {

    private jCalendar mainApp;
    private User currentUser;
    private Customer customer;

    @FXML
    private Label customerLabel;
    @FXML
    private Label labelCusID;
    @FXML
    private JFXButton btnCustomerAdd;
    @FXML
    private JFXButton btnCustomerUpdate;
    @FXML
    private JFXButton btnCustomerDelete;
    @FXML
    private JFXButton btnCustomerSave;
    @FXML
    private JFXButton btnCustomerCancelSave;
    @FXML
    private TableView<Customer> CustomerTable;
    @FXML
    private TableColumn<Customer, String> colCustomerID;
    @FXML
    private TableColumn<Customer, String> colCustomerName;
    @FXML
    private TableColumn<Customer, String> colCustomerPhone;

    @FXML
    private TableColumn<Customer, String> colCustomerEmail;
    @FXML
    private TableColumn<Customer, String> colCustomerStatus;

    @FXML
    private JFXTextField txtCustomerName;
    @FXML
    private JFXTextField txtCustomerPhone; //Address
    @FXML
    private JFXTextField txtCustomerEmail; //Address2
    @FXML
    private JFXTextField txtCustomerStatus; //ZipCode
    @FXML
    private JFXTextField txtCustomerNotes; //Phone

    @FXML
    private JFXComboBox<Pet> comboPet;

    @FXML
    private JFXComboBox<String> comboPetType;

    @FXML
    private JFXTextField txtSearchField;

    @FXML
    private static VBox petVBox;

    @FXML
    private TextField txtPetDescription;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<Pet> petList = FXCollections.observableArrayList(); //Cities
    private ObservableList<Pet> customerPets = FXCollections.observableArrayList(); //selectedCountries
    private ObservableList<Pet> selectedPets = FXCollections.observableArrayList(); //selectedCountries
    private static ObservableList<String> petNames = FXCollections.observableArrayList(); //selectedCountries
    private boolean editMode;

    private final static Logger logger = Logger.getLogger(Loggerutil.class.getName());

    public CustomerScreenController() {

    }

    @FXML
    private void initializePetTypes() {

        comboPetType.getItems().addAll("Dog", "Puppy", "Cat", "Kitten", "Other");
    }

    @FXML
    private void showCustomerDetails(Customer selectedCustomer) {

        //populate customer details based on selection
        //**need to rename FXML FIELDS, need to show barber
        labelCusID.setText(String.valueOf(selectedCustomer.getCustomerId()));
        txtCustomerName.setText(selectedCustomer.getCustomerName());
        txtCustomerPhone.setText(selectedCustomer.getPhone());
        txtCustomerEmail.setText(selectedCustomer.getEmail());
        txtCustomerStatus.setText(selectedCustomer.getActive());
        txtCustomerNotes.setText(selectedCustomer.getNotes());
//	countryCombo.setValue(selectedCustomer.getPet());
//        comboPet.setItems(selectedCustomer.petProperty().get());
        comboPet.setItems(selectedCustomer.getPets());
        comboPet.getSelectionModel().selectFirst();
//        comboPetType.setValue(selectedCustomer.getPet().);
//        txtPetDescription.setText(selectedCustomer.getPet().descProperty().get());
        System.out.println("showcustomerdetails" + selectedCustomer);

    }

    @FXML
    private void showPetDetails(Pet selectedPet) {

//        comboPet.setItems(selectedPets);
//        comboPet.getSelectionModel().selectFirst();
        comboPetType.setValue(selectedPet.typeProperty().get());
//        comboPetType.setItems(selectedPets);
        txtPetDescription.setText(selectedPet.descProperty().get());
        System.out.println("showcustomerdetails" + selectedPet);

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

        showEditableComboBoxWithAutoAdd();

    }

    @FXML
    void handleDeleteCustomer(ActionEvent event) {

//        Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();
//
//        if (selectedCustomer != null) {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Confirm delete");
//            alert.setHeaderText("Are you sure you want to delete " + selectedCustomer.getCustomerName() + "?");
//            alert.showAndWait()
//                    .filter(response -> response == ButtonType.OK)
//                    .ifPresent(response -> {
//                        deleteCustomer(selectedCustomer);
//                        mainApp.showCustomerScreen();
//                    });
//        } else {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Not selected");
//            alert.setHeaderText("No customer was selected to delete");
//            alert.setContentText("Please select a customer to delete");
//            alert.showAndWait();
//        }
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

        saveNewCustomer();

        CustomerCache.flush();
        PetCache.flush();
//        Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();
//        System.out.println("Debug handleSaveCustomer: editMode: " + editMode);
//        if (validateCustomer()) {
//            if (editMode) {
//                updateCustomer(selectedCustomer);
//            } else {
//                saveNewCustomer();
//            }
//            mainApp.showCustomerScreen();
//        }
    }

    @FXML
    void handleUpdateCustomer(ActionEvent event) {

//        enableEdits();
//        customerLabel.setText("Update Customer Details");
//
//        Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();
//
//        if (selectedCustomer != null) {
//            editMode = true;
//            CustomerTable.setDisable(false);
//            btnCustomerAdd.setDisable(true);
//            btnCustomerUpdate.setDisable(true);
//            btnCustomerDelete.setDisable(true);
//            btnCustomerCancelSave.setDisable(false);
//            btnCustomerSave.setDisable(false);
//
//        } else {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Not selected");
//            alert.setHeaderText("No customer was selected");
//            alert.setContentText("Please select a customer to update");
//            alert.showAndWait();
//        }
    }

    /**
     * Initializes the controller class.
     *
     * @param mainApp
     * @param currentUser
     */
    public void setMainController(jCalendar mainApp) {

        this.mainApp = mainApp;

        initCol();
        initializePetTypes();

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Customer> filteredData = new FilteredList<>(CustomerCache.getAllCustomers(), p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        txtSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (customer.getCustomerName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
//                } else if (customer.getLastName().toLowerCase().contains(lowerCaseFilter)) {
//                    return true; // Filter matches last name.
//                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Customer> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(CustomerTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        CustomerTable.setItems(sortedData);

//        CustomerTable.getItems().setAll(CustomerCache.getAllCustomers());
//        comboPet.setItems(selectedPets);
        disableEdits();
        labelCusID.setText("");
        btnCustomerCancelSave.setDisable(true);
        btnCustomerSave.setDisable(true);

        convertComboBoxString();

    }

    private void initCol() {

        customerLabel.setText("Customer Details");
        colCustomerID.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().customerIdProperty().get()));

        colCustomerName.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().customerNameProperty().get()));

        colCustomerPhone.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().customerPhoneProperty().get()));

        colCustomerEmail.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().customerEmailProperty().get()));
        colCustomerStatus.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().activeProperty().get()));

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
        txtCustomerStatus.clear();
        txtCustomerNotes.clear();
        comboPet.setValue(null);
        comboPetType.setValue(null);
        txtPetDescription.clear();
    }

    /**
     * Inserts new customer record
     */
    private void saveNewCustomer() {
        System.out.println("currently commented out saving new customer");
        try {

            PreparedStatement pst = DBConnection.getConn().prepareStatement("INSERT INTO customer "
                    + "( customerName, customerPhone, customerEmail, notes, active, createDate, createdBy, lastUpdate, lastUpdateBy) "
                    + " VALUES ( ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, txtCustomerName.getText());
            pst.setString(2, txtCustomerPhone.getText());
            pst.setString(3, txtCustomerEmail.getText());
            pst.setString(4, txtCustomerNotes.getText());
            pst.setInt(5, 1); //TO UPDATE: let user change status
//            pst.setString(6, currentUser.getUserName()); //temp workaround for now
            pst.setString(6, "test");
            pst.setString(7, "test");

            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();

            int newPetId = -1;
            int newCustomerId = -1;

            if (rs.next()) {
                newPetId = rs.getInt(1);
                newCustomerId = rs.getInt(1);
            }

            String userInputPet = comboPet.getEditor().getText();
            System.out.println(userInputPet);
            PreparedStatement ps = DBConnection.getConn().prepareStatement("INSERT INTO pet "
                    + "(petName, petType, petDescription, createDate, createdBy, lastUpdate, lastUpdateBy, customerId) "
                    + " VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?, ?)");
            ps.setString(1, userInputPet);
            ps.setString(2, comboPetType.getValue());
            ps.setString(3, txtPetDescription.getText());
            ps.setString(4, "test");
            ps.setString(5, "test");
            ps.setInt(6, newCustomerId);
//            ps.setString(4, currentUser.getUserName()); //UPDATE LATER
//            ps.setString(5, currentUser.getUserName()); //UPDATE LATER

            ps.executeUpdate();

            System.out.println("Attempting to save new record.. "
                    //                    + "Name: " + name + "\n"
                    //                    + "Phone: " + phone + "\n"
                    //                    + "Email: " + email + "\n"
                    + "Pet: " + userInputPet + "\n"
                    + "Pet Type: " + comboPetType.getValue() + "\n"
                    + "Pet Notes: " + txtPetDescription.getText() + "\n"
            );

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
            pst.setString(1, customer.getCustomerId());
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

    private void customerSelected(Customer c) {
//populate text fields for selected customer
//        selectedPets.clear();
//        selectedPets.addAll(c.getPets());

        txtCustomerName.setText(c.getCustomerName());
        txtCustomerPhone.setText(c.getPhone());
        txtCustomerEmail.setText(c.getEmail());
        txtCustomerStatus.setText(c.getActive());
        txtCustomerNotes.setText(c.getNotes());

//        selectedPets.addAll(PetCache.getSelectedPets(c));
        comboPet.setItems(PetCache.getSelectedPets(c));
        petNames.addAll(PetCache.getPetNames(c));
//        comboPet.setItems(selectedPets);

        comboPet.getSelectionModel().selectFirst();

//        showEditableComboBoxWithAutoAdd(c);
    }

    private void petSelected(Pet p) {
        //populate text fields for selected pet
        comboPetType.setValue(p.getPetType());
        txtPetDescription.setText(p.getPetDesc());
    }

    private void convertComboBoxString() {
        CustomerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                Customer c = CustomerTable.getSelectionModel().getSelectedItem();
                customerSelected(c);
//                showCustomerDetails(c);

            }
        });

        comboPet.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                Pet p = comboPet.getSelectionModel().getSelectedItem();
                petSelected(p);

            }
        });

        comboPet.getEditor().textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                System.out.println("Text changed to" + newValue);
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
//                return comboPet.getValue();
                return comboPet.getItems().stream().filter(ap -> ap.nameProperty().get().equals(string)).findFirst().orElse(null);
            }
        });
    }

    static void showEditableComboBoxWithAutoAdd() {
        petVBox.getChildren().clear();

        JFXComboBox jvmLangsEditableComboBox = new JFXComboBox<String>(petNames);

        // set to editable
        jvmLangsEditableComboBox.setEditable(true);

        // provide StringConverter
        jvmLangsEditableComboBox.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String obj) {
                if (obj != null) {
                    return obj;
                }
                return "";
            }

            @Override
            public String fromString(String string) {
                if (!petNames.contains(string)) {
                    petNames.add(string);
                }
                return string;
            }
        });

        Label valueLabel = new Label();
        valueLabel.textProperty().bind(jvmLangsEditableComboBox.valueProperty().asString());

        petVBox.getChildren().add(
                new HBox(10,
                        new Label("ComboBox<String> JVM Languages"),
                        jvmLangsEditableComboBox,
                        valueLabel
                )
        );
    }
}
