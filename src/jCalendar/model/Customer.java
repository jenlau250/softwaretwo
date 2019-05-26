/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.model;

import java.util.Calendar;

/**
 *
 * @author jlau2
 */
public class Customer {


    private int customerId;
    private String customerName;
    private String address;
    private boolean active;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;

    
    
    public Customer(int customerId, String customerName, String address, boolean active, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
	this.customerId = customerId;
	this.customerName = customerName;
	this.address = address;
	this.active = active;
	this.createDate = createDate;
	this.createdBy = createdBy;
	this.lastUpdate = lastUpdate;
	this.lastUpdateBy = lastUpdateBy;
    }

    public int getCustomerId() {
	return customerId;
    }

    public void setCustomerId(int customerId) {
	this.customerId = customerId;
    }

    public String getCustomerName() {
	return customerName;
    }

    public void setCustomerName(String customerName) {
	this.customerName = customerName;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public boolean isActive() {
	return active;
    }

    public void setActive(boolean active) {
	this.active = active;
    }

    public Calendar getCreateDate() {
	return createDate;
    }

    public void setCreateDate(Calendar createDate) {
	this.createDate = createDate;
    }

    public String getCreatedBy() {
	return createdBy;
    }

    public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
    }

    public Calendar getLastUpdate() {
	return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
	this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
	return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
	this.lastUpdateBy = lastUpdateBy;
    }
    
    
    
}
