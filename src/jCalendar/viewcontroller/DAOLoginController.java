/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import jCalendar.DAO.UserDaoImpl;
import jCalendar.model.User;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class DAOLoginController implements Initializable {

    @FXML
    private TableColumn<?, ?> UserName;

    @FXML
    private Label labelUserId;

    @FXML
    private Button buttonLogin;

    @FXML
    private TextField textUserId;

    @FXML
    private TableView<User> UserTable;

    @FXML
    private TextField textUserPw;

    @FXML
    private TableColumn<?, ?> ID;

    @FXML
    private TableColumn<?, ?> Password;

    @FXML
    private Label labelUserPw;

    @FXML
    void handleActionLogin(ActionEvent event) {

    }

    @FXML
    ObservableList<User> Users= FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    ID.setCellValueFactory(new PropertyValueFactory<>("userId"));
    // CustomerName.setCellValueFactory(new PropertyValueFactory<>("address"));
       UserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
//       CustomerAddress2.setCellValueFactory(new PropertyValueFactory<>("customerAddress2"));
       Password.setCellValueFactory(new PropertyValueFactory<>("password"));
       

        try {
            Users.addAll(UserDaoImpl.getAllUsers());
            
  
        } catch (Exception ex) {
            Logger.getLogger(DAOLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
                    UserTable.setItems(Users);
                    //Using Lambda for efficient selection off a tableview
     
    }    
    
}
