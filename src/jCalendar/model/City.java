/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.model;

/**
 *
 * @author jlau2
 */
public class City {
    private int cityId;
    private String cityName;
    private int countryId;
    
    public City(){

    }

    public City(int cityId, String cityName, int countryId) {
	this.cityId = cityId;
	this.cityName = cityName;
	this.countryId = countryId;
    }

    public City(int cityId, String city) {
	this.cityId = cityId;
	this.cityName = city;
    }

    public int getCityId() {
	return cityId;
    }

    public void setCityId(int cityId) {
	this.cityId = cityId;
    }
    
    public String getCityName() {
	return cityName;
    }

    public void setCity(String cityName) {
	this.cityName = cityName;
    }

//    public int getCountryId() {
//	return countryId;
//    }
//
//    public void setCountryId(int countryId) {
//	this.countryId = countryId;
//    }

    @Override
    public String toString() {
	return String.valueOf(cityName);
    }

    
}
