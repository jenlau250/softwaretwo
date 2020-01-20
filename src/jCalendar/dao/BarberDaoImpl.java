/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.dao;

import jCalendar.model.Barber;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jen
 */
public class BarberDaoImpl {
    
     public static ObservableList<Barber> addBarbers() {

        ObservableList<Barber> barberList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(
                    "SELECT barberId, barberName, notes, active, hireDate "
                    + "FROM barber");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("barberId");
                String name = rs.getString("barberName");
                String notes = rs.getString("notes");
                String active = rs.getString("active");
                LocalDate hireDate = rs.getObject("hireDate", LocalDate.class);
                barberList.add(new Barber(id, name, notes, active, hireDate));

            }

        } catch (SQLException sqe) {
            System.out.println("Check SQL Exception with Barbers DAO");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Check Exception");
        }
        return barberList;

    }
}
