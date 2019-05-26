/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jlau2
 */
public class jCalendar extends Application {
    
    static Stage stage;
    
    @Override
    public void start(Stage stage) {
        
	this.stage= stage;
	// Uncomment to change language back to English
	//Locale.setDefault(new Locale("jp","JP"));
	ResourceBundle rb = ResourceBundle.getBundle("jCalendar/utilities/rb");
	
	Parent main = null;
	
	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("viewcontroller/DAOLogin.fxml"));
	    loader.setResources(rb);
	    main = loader.load();
	    
	    Scene scene = new Scene(main);
	    
	    stage.setScene(scene);
	    stage.show();
        } catch (IOException ex) {
	    ex.printStackTrace();
	}
	
	//Parent root = FXMLLoader.load(getClass().getResource("viewcontroller/DAOLogin.fxml"));
        
//        Scene scene = new Scene(root);
//        
//        stage.setScene(scene);
//        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static Stage getStage() {
	return stage;
    }
    
    
}
