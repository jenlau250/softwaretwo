/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import Cache.BarberCache;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private Label barberLabel;

    private ObservableList<Barber> barbers = FXCollections.observableArrayList();
    private boolean editMode;

    private final static Logger logger = Logger.getLogger(Loggerutil.class.getName());

    List<String> statusOptions = new ArrayList<>();

    public BarberScreenController() {

    }

    @FXML
    void handleAdd(ActionEvent event) {

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

    @FXML
    void handleUpdate(ActionEvent event) {

        Barber selectedBarber = BarberTable.getSelectionModel().getSelectedItem();
        if (selectedBarber != null) {
            mainApp.showBarberAddScreen(selectedBarber);
        } else {
            if (selectedBarber != null) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Not selected");
//            alert.setHeaderText("No barber was selected to delete");
                alert.setContentText("Please select a barber to update");
                alert.showAndWait();
            }
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
    }
//
}
