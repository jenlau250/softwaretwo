/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.DAO;

import jCalendar.model.City;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author jlau2
 */
public class CityDaoImpl {

//    
//    public static ObservableList<City> getallCities() throws SQLException, Exception {
//	ObservableList<City> cities = FXCollections.observableArrayList();
//	DBConnection.init();
//	String sql = ("SELECT cityId, city FROM city");
//	
//	Query.makeQuery(sql);
//	ResultSet result = Query.getResult();
//	while (result.next()) {
//	    cities.add(new City(result.getInt("city.cityId"), result.getString("city.city")));
//	    
//	    
//	}
//	DBConnection.closeConnection();
//	return cities;	
//	
//    }
//    public int getCityId(String cityName) {
//	DBConnection.init();
//
//	int returnVal = 0;
//	String selectString = "SELECT cityId FROM city WHERE city = :city";
//	HashMap<String, String> params = new HashMap<>();
//	params.put("city", cityName);
//
//	Query q = new Query(selectString, params);
//
//	ResultSet results = 
//	try {
//
////
////    public ResultSet execute(String queryString, HashMap<String, String> params) throws SQLException {
////	Query query = new Query(queryString, params);
////	ResultSet results = this.execute(query);
////	return results;
////    }
//    
//    ResultSet results = this.execute(selectString, params);
//	    while (results.next()) {
//		returnVal = results.getInt("cityId");
//	    }
//	} catch (SQLException ex) {
//	    Logger.getLogger(CityDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
//	}
//	return returnVal;
//    }
//
//    public String getCityName(int cityID) {
//	String returnVal = "";
//	String selectString = "SELECT cityId FROM city WHERE cityId = :cityId";
//	HashMap<String, String> params = new HashMap<>();
//	params.put("cityId", Integer.toString(cityID));
//	try {
//	    ResultSet results = this.execute(selectString, params);
//	    while (results.next()) {
//		returnVal = results.getString("city");
//	    }
//	} catch (SQLException ex) {
//	    Logger.getLogger(CityDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
//	}
//	return returnVal;
//    }

}

