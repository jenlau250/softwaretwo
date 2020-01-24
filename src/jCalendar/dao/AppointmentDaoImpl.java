/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.dao;

import jCalendar.model.Appointment;
import jCalendar.model.Barber;
import jCalendar.model.Customer;
import jCalendar.model.Pet;
import jCalendar.utilities.Loggerutil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jen
 */
public class AppointmentDaoImpl {

    private static final DateTimeFormatter dateformat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    private final static Logger logger = Logger.getLogger(Loggerutil.class.getName());

    public ObservableList<Appointment> loadApptData() {

        ObservableList<Appointment> apptList = FXCollections.observableArrayList();

        try {

            DBConnection.init();
            PreparedStatement ps = DBConnection.getConn().prepareStatement(
                    "SELECT * FROM appointment, barber, customer, pet "
                    + "WHERE appointment.barberId = barber.barberId "
                    + "AND appointment.customerId = customer.customerId "
                    + "AND appointment.petId = pet.petId");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("appointmentId");
//                int customerId = rs.getInt("customerId");
//                int barberId = rs.getInt("barberId");
                String title = rs.getString("title");

                LocalDateTime start = rs.getObject("start", LocalDateTime.class);
                LocalDateTime end = rs.getObject("end", LocalDateTime.class);
                String desc = rs.getString("description");
                String type = rs.getString("type");

                Barber barber = new Barber(
                        rs.getInt("barber.barberId"),
                        rs.getString("barber.barberName"),
                        rs.getString("barber.barberPhone"),
                        rs.getString("barber.barberEmail"),
                        rs.getString("barber.notes"),
                        rs.getString("barber.active"),
                        rs.getObject("barber.hireDate", LocalDate.class)
                );

//                Pet pet = new Pet(
//                        rs.getInt("pet.petId"),
//                        rs.getString("pet.petName"),
//                        rs.getString("pet.petType"),
//                        rs.getString("pet.petDescription")
//                );
                Customer customer = new Customer(
                        rs.getString("customer.customerId"),
                        rs.getString("customerName"),
                        rs.getString("customerPhone"),
                        rs.getString("customerEmail"),
                        new Pet(
                                rs.getString("pet.petId"),
                                rs.getString("pet.petName"),
                                rs.getString("pet.petType"),
                                rs.getString("pet.petDescription"))
                );

//                apptList.add(new Appointment(title, start, end, desc, type, barber, customer, pet));
                apptList.add(new Appointment(appointmentId, title, start, end, desc, type, barber, customer));

            }
            DBConnection.closeConnection();

        } catch (SQLException sqe) {
            System.out.println("Check SQL Exception with add Appointments 2");
            sqe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Check Exception");
        }
        return apptList;

    }

    public static ObservableList<Barber> populateBarbers() {

        int barberId;
        String barberName;

        ObservableList<Barber> barberList = FXCollections.observableArrayList();
        try (
                PreparedStatement ps = DBConnection.getConn().prepareStatement(
                        "SELECT barber.barberId, barber.barberName "
                        + "FROM barber, appointment "
                        + "WHERE barber.barberId = appointment.barberId"
                ); ResultSet rs = ps.executeQuery();) {

                    while (rs.next()) {
                        barberId = rs.getInt("barber.barberId");
                        barberName = rs.getString("barber.barberName");
                        barberList.add(new Barber(barberId, barberName));
                    }
                } catch (SQLException sqe) {
                    System.out.println("Check SQL Exception with populateBarbers");
                    sqe.printStackTrace();
                } catch (Exception e) {
                    System.out.println("Check Exception");
                }
                return barberList;

    }

    public static ObservableList populateAppointments() {
////*need to add barberId

        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(
                    "SELECT appointment.appointmentId, "
                    + "appointment.customerId, "
                    + "appointment.title, "
                    + "appointment.type, "
                    + "appointment.location, "
                    + "appointment.start, "
                    + "appointment.end, "
                    + "appointment.createdBy "
                    + "customer.customerId, "
                    + "customer.customerName, "
                    + "FROM appointment, customer "
                    + "WHERE appointment.customerId = customer.customerId "
                    + "ORDER BY `start`");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int tAppointmentId = rs.getInt("appointment.appointmentId");
                // get utc timestamps from database
                String tsStart = rs.getString("appointment.start");
                String tsEnd = rs.getString("appointment.end");

                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime rDate = LocalDateTime.parse(tsStart, df);
                LocalDateTime rDate2 = LocalDateTime.parse(tsEnd, df);
                // date stored as UTC
                ZonedDateTime zonedDate = rDate.atZone(ZoneId.of("UTC"));
                ZonedDateTime zonedDate2 = rDate2.atZone(ZoneId.of("UTC"));

                // now convert for local time
                ZoneId zid = ZoneId.systemDefault();
                ZonedDateTime newLocalStart = zonedDate.withZoneSameInstant(zid);
                ZonedDateTime newLocalEnd = zonedDate2.withZoneSameInstant(zid);

                String tTitle = rs.getString("appointment.title");
                String tType = rs.getString("appointment.type");
                String tLocation = rs.getString("appointment.location");
                Customer sCustomer = new Customer(rs.getString("appointment.customerId"), rs.getString("customer.customerName"));

                String sContact = rs.getString("appointment.createdBy");

                appointmentList.add(new Appointment(tAppointmentId, newLocalStart.format(dateformat), newLocalEnd.format(dateformat), tTitle, tType, tLocation, sCustomer, sContact));

            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Check SQL exception/ issue with populateAppointment()");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Check exception");
        }

        return appointmentList;
    }

    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception {

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String sqlStatement
                = ("SELECT appointment.appointmentId, "
                + "appointment.title, "
                + "appointment.start, "
                + "appointment.end, "
                + "appointment.type, "
                + "appointment.location, "
                + "appointment.month, "
                + "customer.customerId, "
                + "customer.customerName, "
                + "pet.petId, "
                + "pet.petName, "
                + "pet.petType, "
                + "pet.petDescription "
                + "FROM appointment, customer, pet "
                + "WHERE appointment.customerId = customer.customerId "
                + "AND customer.petId = pet.petId");

        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();

        while (result.next()) {
            int appointmentId = result.getInt("appointment.appointmentId");
            String tTitle = result.getString("appointment.title");
            String tType = result.getString("appointment.type");
            String tLocation = result.getString("appointment.location");
            String tMonth = result.getString("appointment.month");
            String tsStart = result.getString("appointment.start");
            String tsEnd = result.getString("appointment.end");

            //CONVERT START AND END TIME STRINGS
            // get utc timestamps from database
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime rDate = LocalDateTime.parse(tsStart, df);
            LocalDateTime rDate2 = LocalDateTime.parse(tsEnd, df);
            // date stored as UTC
            ZonedDateTime zonedDate = rDate.atZone(ZoneId.of("UTC"));
            ZonedDateTime zonedDate2 = rDate2.atZone(ZoneId.of("UTC"));

            // now convert for local time
            ZoneId zid = ZoneId.systemDefault();
            ZonedDateTime newLocalStart = zonedDate.withZoneSameInstant(zid);
            ZonedDateTime newLocalEnd = zonedDate2.withZoneSameInstant(zid);

            String startSave = newLocalStart.format(dateformat);
            String startEnd = newLocalEnd.format(dateformat);

            Customer customer = new Customer(result.getString("customer.customerId"), result.getString("customer.customerName"));

            Pet pet = new Pet(result.getString("pet.petId"), result.getString("pet.petName"), result.getString("pet.petType"), result.getString("pet.petDescription"));
//            int barberId = result.getInt("barberId");
//	    String zipcode = result.getString("pet.postalCode")

            Appointment appointmentResult = new Appointment(appointmentId, tTitle, tType, tLocation, tMonth, startSave, startEnd, customer, pet);

            allAppointments.add(appointmentResult);
        }

        return allAppointments;
    }

}
