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
    @FXML    private Button button1;
    @FXML    private Button button2;
    @FXML    private TextField textUserId;
    @FXML    private TableView<User> UserTable;
    @FXML    private TextField textUserPw;
    @FXML    private TableColumn<User, Integer> ID;
    @FXML    private TableColumn<User, String> Password;
    @FXML    private Label labelUserPw;
    ObservableList<User> Users= FXCollections.observableArrayList();
    User user = new User();
    // Reference back to main screen
    ResourceBundle rb = ResourceBundle.getBundle("jCalendar/utilities/rb");
    private jCalendar mainApp;
    private final static Logger LOGGER = Logger.getLogger(Loggerutil.class.getName());
    
    
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
	    if (validate_login(userNameInput, userPwInput)) {

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(rb.getString("SUCCESSFUL LOGIN"));
		alert.setHeaderText(null);
		alert.setContentText(rb.getString("LOGIN WAS SUCCESSFUL."));
		alert.showAndWait();
		LOGGER.log(Level.INFO, "{0} logged in", userNameInput);

		mainApp.showMain();
	    } else {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(rb.getString("LOGIN ATTEMPT WAS UNSUCCESSFUL"));
		alert.setHeaderText(null);
		alert.setContentText(rb.getString("PLEASE TRY TO LOGIN AGAIN."));
		alert.showAndWait();

//	    System.out.println(loginUser);
	    }
	}
    }
   

    public boolean validate_login(String username, String password) {
	try {
//       Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
//       Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javademo?" + "user=root&password=");     
	    PreparedStatement pst = DBConnection.getConn().prepareStatement("select * from user where userName=? and password=?");
	    pst.setString(1, username);
	    pst.setString(2, password);
	    ResultSet rs = pst.executeQuery();
	    if (rs.next()) {
		return true;
	    } else {
		return false;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}

    }
	


	    

//    
//    User validateUser (String username, String password) {
//	try {
//	    PreparedStatement pst = DBConnection.getConn().prepareStatement("SELECT * FROM user WHERE username=? and password=?");
//	    pst.setString(1, username);
//	    pst.setString(2. password);
//	    ResultSet rs = pst.executeQuery();
//	    if(rs.next()) {
//		user.setUserName(rs.getString("username"));
//		user.setPassword(rs.getString("password"));
//		user.setUserID(rs.getString("userId"));
//		
//		
//	    }
//	}
//    }
    
    /**
     * Searches for matching username and password in database
     *
     * @param username
     * @param password
     * @return user if match found
     */
  

    @FXML
    void handleActiontest(ActionEvent event) {
	System.out.println("test");
	mainApp.showMain();
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
	//this.rb = rb;
	buttonLogin.setText(rb.getString("loginbutton"));
	buttonCancel.setText(rb.getString("cancelbutton"));
	labelUserId.setText(rb.getString("labelusername"));
	labelUserPw.setText(rb.getString("labeluserpw"));
	
	ID.setCellValueFactory(new PropertyValueFactory<>("userId"));
//	// CustomerName.setCellValueFactory(new PropertyValueFactory<>("address"));
	UserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
////       CustomerAddress2.setCellValueFactory(new PropertyValueFactory<>("customerAddress2"));
	Password.setCellValueFactory(new PropertyValueFactory<>("password"));

// populate user table from SQL 
	try {
	    Users.addAll(UserDaoImpl.getAllUsers());

	} catch (Exception ex) {
	    LOGGER.log(Level.SEVERE, null, ex);
	    //Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
	}
	UserTable.setItems(Users);
	//Using Lambda for efficient selection off a tableview
    }
	
    
    
  
    
}
