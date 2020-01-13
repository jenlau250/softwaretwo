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
public class Country {

    private int countryId;
    private String countryName;

    public Country() {

    }

    public Country(int countryId, String countryName) {
	this.countryName = countryName;
	this.countryId = countryId;
    }

    public int getCountryId() {
	return countryId;
    }

    public void setCountryId(int countryId) {
	this.countryId = countryId;
    }

    public String getCountryName() {
	return countryName;
    }

    public void setCountry(String countryName) {
	this.countryName = countryName;
    }

    @Override
    public String toString() {
	return String.valueOf(countryName);
    }
    

}
