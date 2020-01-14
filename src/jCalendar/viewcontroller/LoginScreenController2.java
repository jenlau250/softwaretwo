package jCalendar.viewcontroller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import jCalendar.dao.DBConnection;
import jCalendar.jCalendar;
import jCalendar.model.User;
import jCalendar.utilities.Loggerutil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class LoginScreenController2 {

    private jCalendar mainApp;

    @FXML
    private JFXTextField textUserId;
    @FXML
    private JFXPasswordField textUserPw;
    @FXML
    private JFXButton buttonLogin;
    @FXML
    private JFXButton buttonCancel;

    ObservableList<User> Users = FXCollections.observableArrayList();

    User user = new User();

    // Reference back to main screen
    ResourceBundle rb = ResourceBundle.getBundle("jCalendar/utilities/rb");

    private final static Logger logger = Logger.getLogger(Loggerutil.class.getName());

    public LoginScreenController2() {

    }

@FXML
    void handleActionLogin(ActionEvent event) {

// Show error message If user name or password is blank
	String userNameInput = textUserId.getText();
	String userPwInput = textUserPw.getText();

	// EXCEPTION CONTROL: Entering incorrect username and password
	if ((textUserId.getText().length() == 0) || (textUserPw.getText().length() == 0)) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle(rb.getString("MISSING LOGIN INFORMATION"));
	    alert.setHeaderText(null);
	    alert.setContentText(rb.getString("PLEASE ENTER USER NAME AND PASSWORD TO LOGIN."));
	    alert.showAndWait();

	} else {

	    User validateUser = validateLogin(userNameInput, userPwInput);
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
		
		//Log user login name and timestamp
		logger.log(Level.INFO, userNameInput + " logged in on " + Loggerutil.currentTimestamp() );

	    }
	}
    }
   
    User validateLogin(String username, String password) {
	try {
	    PreparedStatement ps = DBConnection.getConn().prepareStatement("SELECT * FROM user WHERE userName=? and password=?");
	    ps.setString(1, username);
	    ps.setString(2, password);
	    ResultSet rs = ps.executeQuery();
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

    /**
     * Initializes the controller class.
     *
     * @param mainApp
     */
    public void setLogin(jCalendar mainApp) {
        this.mainApp = mainApp;
        buttonLogin.setText(rb.getString("loginbutton"));
        buttonCancel.setText(rb.getString("cancelbutton"));

//	Lambda use - set exit action to cancel button
        buttonCancel.setOnAction((evt) -> {
            System.exit(0);
        });

    }

}
