/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

/**
 * FXML Controller class
 *
 * @author jlau2
 */
public class DAOCustomerController implements Initializable {

    @FXML
    private Button btnCustomerAdd;
    @FXML
    private Button btnCustomerUpdate;
    @FXML
    private Button btnCustomerDelete;
    @FXML
    private TableColumn<?, ?> txtCustomerID;
    @FXML
    private TableColumn<?, ?> txtCustomerName;
    @FXML
    private TableColumn<?, ?> txtCustomerAddress;
    @FXML
    private TableColumn<?, ?> txtCustomerActive;
    @FXML
    private TableColumn<?, ?> txtCustomerCreateBy;
    @FXML
    private TableColumn<?, ?> txtCustomerCreateDate;
    @FXML
    private TableColumn<?, ?> txtCustomerLastUpdateBy;
    @FXML
    private TableColumn<?, ?> txtCustomerLastUpdateDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
    }    
    
}
