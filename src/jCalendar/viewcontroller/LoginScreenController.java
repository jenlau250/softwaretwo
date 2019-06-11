/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.viewcontroller;

import jCalendar.DAO.DBConnection;
import jCalendar.DAO.UserDaoImpl;
import jCalendar.jCalendar;
import jCalendar.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jCalendar.utilities.Loggerutil;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
public class LoginScreenController {

    @FXML    private TableColumn<User, String> UserName;
    @FXML    private Label labelUserId;
    @FXML    private Button buttonLogin;
    @FXML    private Button buttonCancel;
    @FXML    private TextField textUserId;
    @FXML    private TextField textUserPw;
    @FXML    private Label labelUserPw;
    ObservableList<User> Users= FXCollections.observableArrayList();
    User user = new User();
    // Reference back to main screen
    ResourceBundle rb = ResourceBundle.getBundle("jCalendar/utilities/rb");
    private jCalendar mainApp;
    private final static Logger logger = Logger.getLogger(Loggerutil.class.getName());
    
    
    public LoginScreenController() {
	
    }
    
    @FXML
    void handleActionLogin(ActionEvent event) {
//	Stage s = jCalendar.getStage();

// Show error message If user name or password is blank
	String userNameInput = textUserId.getText();
	String userPwInput = textUserPw.getText();

	if ((textUserId.getText().length() == 0) || (textUserPw.getText().length() == 0)) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle(rb.getString("MISSING LOGIN INFORMATION"));
	    alert.setHeaderText(null);
	    alert.setContentText(rb.getString("PLEASE ENTER USER NAME AND PASSWORD TO LOGIN."));
	    alert.showAndWait();

	} else {

// Login successful if user name and password matches
// change to validate in SQL db
	    //  User loginUser = validateUserLogin(userNameInput, userPwInput);
	    User validateUser = validate_login(userNameInput, userPwInput);
	    if (validateUser == null) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(rb.getString("LOGIN ATTEMPT WAS UNSUCCESSFUL"));
		alert.setHeaderText(null);
		alert.setContentText(rb.getString("PLEASE TRY TO LOGIN AGAIN."));
		alert.showAndWait();

	    } else {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(rb.getString("SUCCESSFUL LOGIN"));
		alert.setHeaderText(null);
		alert.setContentText(rb.getString("LOGIN WAS SUCCESSFUL."));
		alert.showAndWait();
		
		mainApp.showMain(validateUser);
	
		logger.log(Level.INFO, "{0} logged in", userNameInput);
	    }
	}
    }
   
    /**
     * Searches for matching username and password in database
     *
     * @param username
     * @param password
     * @return user if match found
     */
    User validate_login(String username, String password) {
	try {
//       Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
//       Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javademo?" + "user=root&password=");     
	    PreparedStatement pst = DBConnection.getConn().prepareStatement("select * from user where userName=? and password=?");
	    pst.setString(1, username);
	    pst.setString(2, password);
	    ResultSet rs = pst.executeQuery();
	    if (rs.next()) {
		user.setUserName(rs.getString("userName"));
		user.setPassword(rs.getString("password"));
		user.setUserId(rs.getInt("userId"));
	    } else {
		return null;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    
	} return user;

    }
	


    
    @FXML
    void handleActionCancel(ActionEvent event) {
	
	System.exit(0);
    }


    /**
     * Initializes the controller class.
     * @param mainApp
     */
    public void setLogin(jCalendar mainApp) {
	this.mainApp = mainApp;
	buttonLogin.setText(rb.getString("loginbutton"));
	buttonCancel.setText(rb.getString("cancelbutton"));
	labelUserId.setText(rb.getString("labelusername"));
	labelUserPw.setText(rb.getString("labeluserpw"));
	
//	UserTable.setItems(Users);
	//Using Lambda for efficient selection off a tableview

	buttonCancel.setOnAction((evt) -> {
	    System.exit(0);
	});

	
    }
	
    
    
  
    
}
