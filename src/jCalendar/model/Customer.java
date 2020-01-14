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
public class Customer {

    private int customerId;
    private String customerName;
    private String phone;
    private String email;
    private String active;
    private String notes;
    private Pet pet;
    private String count;

    public Customer() {

    }

    public Customer(int customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public Customer(Pet pet, String count) {
        this.pet = pet;
        this.count = count;
    }

    public Customer(int customerId, String customerNameG, String phone, String email, String notes, String active, Pet pet) {
        this.customerId = customerId;
        this.customerName = customerNameG;
        this.phone = phone;
        this.email = email;
        this.notes = notes;
        this.active = active;
        this.pet = pet;
                
               
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    
    public Pet getPet() {
	return pet;
        
    }

    @Override
    public String toString() {
        return String.valueOf(customerName);
    }

}
