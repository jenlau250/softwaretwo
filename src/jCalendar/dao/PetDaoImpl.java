/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.dao;

import jCalendar.model.Pet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jen
 */
public class PetDaoImpl {

    public static ObservableList<Pet> addPets() {

        ObservableList<Pet> petList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(
                    "SELECT petId, petName, petType, petDescription "
                    + "FROM pet");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("petId");
                String name = rs.getString("petName");
                String type = rs.getString("petType");
                String desc = rs.getString("petDescription");
                petList.add(new Pet(id, name, type, desc));

            }

        } catch (SQLException sqe) {
            System.out.println("Check SQL Exception with pet dao impl");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Check Exception");
        }
        return petList;

    }
}
