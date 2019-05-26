/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import jCalendar.DAO.UserDaoImpl;
import jCalendar.model.User;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

    @FXML    private TableColumn<?, ?> UserName;
    @FXML    private Label labelUserId;
    @FXML    private Button buttonLogin;
    @FXML    private Button buttonCancel;
    @FXML    private TextField textUserId;
    @FXML    private TableView<User> UserTable;
    @FXML    private TextField textUserPw;
    @FXML    private TableColumn<?, ?> ID;
    @FXML    private TableColumn<?, ?> Password;
    @FXML    private Label labelUserPw;
    ObservableList<User> Users= FXCollections.observableArrayList();

    
    
    @FXML
    void handleActionLogin(ActionEvent event) {
// Show error message If user name or password is blank
	if ((textUserId.getText().length() == 0) || (textUserPw.getText().length() == 0)) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle(java.util.ResourceBundle.getBundle("jCalendar/utilities/Bundle").getString("MISSING LOGIN INFORMATION"));
	    alert.setHeaderText(null);
	    alert.setContentText(java.util.ResourceBundle.getBundle("jCalendar/utilities/Bundle").getString("PLEASE ENTER USER NAME AND PASSWORD TO LOGIN."));
	    alert.showAndWait();

	} else {
	    String userNameInput = textUserId.getText();
	    String userPwInput = textUserPw.getText();

// Login successful if user name and password matches
	    boolean validLogin = false;
	    for (User user : Users) {
		if ((user.getUserName().equals(userNameInput)) && user.getPassword().equals(userPwInput)) {
		    validLogin = true;

		}
	    }

	    //System.out.println(validLogin);
	    if (validLogin) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(java.util.ResourceBundle.getBundle("jCalendar/utilities/Bundle").getString("SUCCESSFUL LOGIN"));
		alert.setHeaderText(null);
		alert.setContentText(java.util.ResourceBundle.getBundle("jCalendar/utilities/Bundle").getString("LOGIN WAS SUCCESSFUL."));
		alert.showAndWait();
	    } else {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(java.util.ResourceBundle.getBundle("jCalendar/utilities/Bundle").getString("LOGIN ATTEMPT WAS UNSUCCESSFUL"));
		alert.setHeaderText(null);
		alert.setContentText(java.util.ResourceBundle.getBundle("jCalendar/utilities/Bundle").getString("PLEASE TRY TO LOGIN AGAIN."));
		alert.showAndWait();
	    }


	    
	}
    }

    
    
    
    
        
    @FXML
    void handleActionCancel(ActionEvent event) {
	System.exit(0);
    }
    

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    ID.setCellValueFactory(new PropertyValueFactory<>(java.util.ResourceBundle.getBundle("jCalendar/utilities/Bundle").getString("USERID")));
    // CustomerName.setCellValueFactory(new PropertyValueFactory<>("address"));
       UserName.setCellValueFactory(new PropertyValueFactory<>(java.util.ResourceBundle.getBundle("jCalendar/utilities/Bundle").getString("USERNAME")));
//       CustomerAddress2.setCellValueFactory(new PropertyValueFactory<>("customerAddress2"));
       Password.setCellValueFactory(new PropertyValueFactory<>(java.util.ResourceBundle.getBundle("jCalendar/utilities/Bundle").getString("PASSWORD")));
       
// populate user table from SQL 

        try {
            Users.addAll(UserDaoImpl.getAllUsers());
            
  
        } catch (Exception ex) {
            Logger.getLogger(DAOLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
                    UserTable.setItems(Users);
                    //Using Lambda for efficient selection off a tableview
    }    
    

    
    
}
