/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.model;

import java.util.Date;

/**
 *
 * @author jlau2
 */
public class Barber {
    private int barberId;
    private String barberName;
    private String notes;
    private String active;
    private Date hireDate;
    
    public Barber(){

    }

    public Barber(int barberId, String name) {
	this.barberId = barberId;
	this.barberName = name;
    }

    public int getBarberId() {
        return barberId;
    }

    public void setBarberId(int barberId) {
        this.barberId = barberId;
    }

    public String getBarberName() {
        return barberName;
    }

    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }


    
    
    @Override
    public String toString() {
	return String.valueOf(barberName);
    }

    
}
