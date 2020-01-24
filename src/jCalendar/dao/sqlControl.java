///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package jCalendar.dao;
//
//import jCalendar.model.Appointment;
//import jCalendar.model.Barber;
//import jCalendar.model.Customer;
//import jCalendar.model.Data;
//import jCalendar.model.Pet;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// *
// * @author Jen
// */
//public class sqlControl {
//
//    private static Statement stmt;
//    private static ResultSet rs;
//    //region #hashmaps
//    //Doctor -> Barber
//    //Patient -> Customer
//    //Animal, Kind -> Pet
//    private HashMap<String, Barber> barbers = new HashMap<>();
//    private HashMap<String, Customer> customers = new HashMap<>();
//    private HashMap<String, Pet> pets = new HashMap<>();
//    private HashMap<String, Appointment> tickets = new HashMap<>();
//
//    //endregion
//    //region #colors for console
//    public static final String getAllCustomers = "SELECT * FROM customer;";
//    public static final String getAllPets = "SELECT * FROM pet;";
//    public static final String getAllAppointments = "SELECT * FROM appointment;";
//    public static final String getAllBarbers = "SELECT * FROM barber;";
//
//    public static void prepare() {
////        connectToDatabase();
////        System.out.println(ANSI_CYAN+"Создание таблицы для триггера..."+ANSI_RESET);
////        createProcedure(Query.tableForTrigger);
////        System.out.println(ANSI_CYAN+"Создание триггера..."+ANSI_RESET);
////        createProcedure(Query.createTrigger);
////        System.out.println(ANSI_CYAN+"Создание процедур..."+ANSI_RESET);
////        createProcedure(Query.createDoctorProcedure);
////        createProcedure(Query.createAnimalProcedure);
////        createProcedure(Query.createCabinetProcedure);
////        createProcedure(Query.createOwnerProcedure);
////        createProcedure(Query.createKindProcedure);
////        createProcedure(Query.createLastUpdateProcedure);
////        System.out.println(ANSI_CYAN+"Создание завершено ✔"+ANSI_RESET);
//    }
//
//    private static void connectToDatabase() {
////        try {
////            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PetClinic", "h3x", "pass");
////            stmt = con.createStatement();
////        } catch (Exception e){
////            System.out.println("Connection failed");
////        }
//    }
//
//    public static void addBarber(String name, String phone, String email, String notes, String active, String hireDate) {
//        // add timestamps, user created stuff later.
//        UpdateQuery("insert into `barber` values " + "(NULL,'" + name + "','" + phone + "','" + email + "','" + notes + "'," + "1,'" + hireDate + "');");
//    }
//
////    public static void addOwner(String name, String phone, String email, String notes, String active, String login) {
////        UpdateQuery("insert into `owner` values " + "(NULL,'" + name + "','" + lastname + "','" + num + "','" + gender + "'," + "TRUE,'" + login + "');");
////    }
////
////    public static void addUser(String username, String password, String role) {
////        UpdateQuery("insert into `user` values " + "('" + username + "','" + password + "','" + role + "');");
////    }
////
////    public static void addAnimal(String animalNameText, String animalAgeText, String animalWeightText, String owner_id, String kind_id) {
////        UpdateQuery("insert into `animal` values " + "(NULL,'" + animalNameText + "','" + animalAgeText + "','" + animalWeightText + "',TRUE,'" + kind_id + "','" + owner_id + "');");
////    }
//    public HashMap<String, Barber> getBarber() throws SQLException {
//
//        try {
//            PreparedStatement ps = DBConnection.getConn().prepareStatement(getAllBarbers);
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                barbers.put(rs.getString("barberId"),
//                        new Barber(rs.getString("barberId"),
//                                rs.getString("barberId"),
//                                rs.getString("barberName"),
//                                rs.getString("barberPhone"),
//                                rs.getString("barberEmail"),
//                                rs.getString("notes"),
//                                rs.getString("active"),
//                                rs.getString("hireDate")));
//            }
//        } catch (SQLException sqe) {
//            System.out.println("Check SQL Exception with Barbers DAO");
//            sqe.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("Check Exception");
//        }
//        return barbers;
//
//    }
//
//    //    public HashMap<String, Kind> getKind() {
//    //        ExecuteQuery(Query.callKindProcedure);
//    //        try {
//    //            while (rs.next()) {
//    //                kinds.put(rs.getString("kind_id"),
//    //                        new Kind(rs.getString("kind_id"),rs.getString("kind_name")));
//    //            }
//    //        } catch (Exception e) {
//    //            System.out.println(e.getMessage());
//    //        }
//    //        return kinds;
//    //
//    //    }
////    public HashMap<String, Customer> getCustomer() {
////
////        try {
////            PreparedStatement ps = DBConnection.getConn().prepareStatement(getAllCustomers);
////            rs = ps.executeQuery();
////            while (rs.next()) {
////                customers.put(rs.getString("customerId"),
////                        new Customer(rs.getString("customerId"), rs.getString("customerName"),
////                                rs.getString("customerPhone"), rs.getString("customerEmail"),
////                                rs.getString("notes"), rs.getString("active")));
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return customers;
////    }
////
////    public HashMap<String, Pet> getPet() {
////        try {
////            PreparedStatement ps = DBConnection.getConn().prepareStatement(getAllPets);
////            rs = ps.executeQuery();
////            while (rs.next()) {
////                pets.put(rs.getString("petId"),
////                        new Pet(rs.getString("petId"), rs.getString("petName"),
////                                rs.getString("petType"),
////                                rs.getString("petDescription"), rs.getString("customerId")));
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return pets;
////    }
////    public static void addAppointment(String date_order, String time, String animal_id, String owner_id, String doctor_id) {
////        UpdateQuery("insert into `ticket` values " + "(NULL,'" + date_order + "','" + time + "',1,'" + owner_id + "','" + animal_id + "'," + doctor_id + ");");
////
////    }
////
////    public static void RemoveOwnerAndAnimal(String id, String petid) {
////        UpdateQuery("delete from ticket where owner_id='" + id + "';");
////        UpdateQuery("delete from animal where animal_id ='" + petid + "';");
////        UpdateQuery("delete from owner where owner_id ='" + id + "';");
////    }
////    private static void UpdateQuery(String query) {
////        try {
////            stmt.executeUpdate(query);
////        } catch (Exception e) {
////            System.out.println(ANSI_RED + e.toString() + ANSI_RESET);
////        } finally {
////            System.out.println(ANSI_BLUE + "Запрос на обновление: " + ANSI_CYAN + query + "    " + ANSI_BLUE + "✔" + ANSI_RESET);
////        }
////    }
////    public Customer getPatientByLogin(String login) {
////        Customer customer = null;
////        ExecuteQuery("select * from owner where user_login='" + login + "';");
////        try {
////            while (rs.next()) {
////                customer = new Customer(rs.getString("owner_id"), rs.getString("owner_name"),
////                        rs.getString("owner_lastname"), rs.getString("owner_num"),
////                        rs.getString("owner_gender"), rs.getString("user_login"));
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return customer;
////    }
////
////    public Doctor getDoctorByLogin(String login) {
////        Doctor doctor = new Doctor(null, null, null, null, null, null, null);
////        ExecuteQuery("select * from doctor where user_login='" + login + "';");
////        try {
////            while (rs.next()) {
////                doctor = new Doctor(DoctorSpecialization.Ветеринар, rs.getString("doctor_id"), rs.getString("doctor_name"),
////                        rs.getString("doctor_lastname"), rs.getString("doctor_num"),
////                        rs.getString("doctor_gender"), rs.getString("cabinet_id"));
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return doctor;
////    }
////    public ObservableList<Pet> getPetsForTableView(String customerId) {
////        ObservableList<Pet> petlist = FXCollections.observableArrayList();
////        ExecuteQuery("select * from pet where customerId='" + customerId + "' and active = 1;");
////        try {
////            while (rs.next()) {
////                petlist.add(new Pet(rs.getString("petName"), rs.getString("petType")));
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return petlist;
////    }
////
////    public ObservableList<Appointment> getTicketsForTableView(String customerId) {
////        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
////        ExecuteQuery("select * from appointment where customerId='" + customerId + "' and ticket_status = 1;");
////        try {
////            while (rs.next()) {
////                apptList.add(new Appointment(rs.getString("ticket_date") + '\n' + rs.getString("ticket_time"),
////                        getDoctorNameForTableView(rs.getString("doctor_id"))));
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return apptList;
////    }
////
////    public ObservableList<Appointment> getTicketsForTableViewForDoctor(String doctor_id) {
////        ObservableList<Appointment> ticketlist = FXCollections.observableArrayList();
////        ExecuteQuery("select * from ticket join animal,kind where doctor_id ='" + doctor_id + "' and ticket.animal_id = animal.animal_id and animal.kind_id = kind.kind_id"
////                + " and ticket_status = 1;");
////        try {
////            while (rs.next()) {
////                apptList.add(new Appointment(rs.getString("ticket_date") + '\n' + rs.getString("ticket_time"),
////                        rs.getString("kind_name")));
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return apptList;
////    }
//    private String getBarberNameForTableView(String barberId) {
//        String name = "";
//        Map<String, Barber> barbersql = Data.barbers;
//        for (Map.Entry<String, Barber> entry : barbersql.entrySet()) {
//            if (entry.getValue().getBarberId().equals(barberId)) {
//                name = entry.getValue().getBarberName();
//            }
//        }
//
//        return name;
//    }
//
////    public HashMap<String, Pet> getAnimalByOwnerId(String customerId) {
////        ExecuteQuery("select * from pet where customerId='" + customerId + "' and animal_status = 1;");
////        try {
////            while (rs.next()) {
////                pets.put(rs.getString("petId"),
////                        new Pet(rs.getString("petId"), rs.getString("petName"),
////                                rs.getString("petType"),
////                                rs.getString("petDescription"), rs.getString("animal_status"),
////                                rs.getString("kind_id"), rs.getString("owner_id")));
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return pets;
////    }
////
////    public HashMap<String, TicketEntity> getTicketsByDoctorid(String doctor_id) {
////        ExecuteQuery("select * from ticket where doctor_id='" + doctor_id + "' and ticket_status = 1;");
////        try {
////            while (rs.next()) {
////                tickets.put(rs.getString("ticket_id"),
////                        new TicketEntity(rs.getString("ticket_id"), rs.getString("ticket_date"),
////                                rs.getString("ticket_time"),
////                                rs.getString("ticket_status"), rs.getString("owner_id"),
////                                rs.getString("animal_id"), rs.getString("doctor_id")));
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return tickets;
////    }
////
////    public static String getNameOfAnimalById(String animal_id) {
////        String out = "";
////        ExecuteQuery("select animal_name from animal where animal_id='" + animal_id + "' and animal_status = 1;");
////        try {
////            while (rs.next()) {
////                out = rs.getString("animal_name");
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return out;
////    }
////
////    public static String getOwnerNameById(String owner_id) {
////        String out = "";
////        ExecuteQuery("select owner_name,owner_lastname from owner where owner_id='" + owner_id + "' and owner_status = 1;");
////        try {
////            while (rs.next()) {
////                out = rs.getString("owner_name");
////                out += " " + rs.getString("owner_lastname");
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return out;
////    }
////
////    public static String getKindByAnimalId(String animal_id) {
////        String out = "";
////        ExecuteQuery("select kind_name from kind join animal where animal.kind_id = kind.kind_id and animal_id = '" + animal_id + "';");
////        try {
////            while (rs.next()) {
////                out = rs.getString("kind_name");
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return out;
////    }
////
////    public static Patient getPatientByPatientId(String owner_id) {
////        Patient p = null;
////        ExecuteQuery("select * from owner where owner_id = '" + owner_id + "' and owner_status = 1;");
////        try {
////            while (rs.next()) {
////                p = new Patient(rs.getString("owner_id"), rs.getString("owner_name"),
////                        rs.getString("owner_lastname"), rs.getString("owner_num"),
////                        rs.getString("owner_gender"), rs.getString("user_login"));
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return p;
////    }
////
////    public static Animal getAnimalByAnimal_id(String animal_id) {
////        Animal a = null;
////        ExecuteQuery("select * from animal where animal_id = '" + animal_id + "' and animal_status= 1;");
////        try {
////            while (rs.next()) {
////                a = new Animal(rs.getString("animal_id"), rs.getString("animal_name"),
////                        rs.getString("animal_age"),
////                        rs.getString("animal_weight"), rs.getString("animal_status"),
////                        rs.getString("kind_id"), rs.getString("owner_id"));
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return a;
////    }
////
////    private static void createProcedure(String query) {
////        try {
////            stmt.executeUpdate(query);
////        } catch (Exception e) {
////            System.out.println("failed");
////        } finally {
////            System.out.println("failed");
////        }
////    }
////
////    private static void ExecuteQuery(String query) {
////        try {
////            rs = stmt.executeQuery(query);
////        } catch (Exception e) {
////            System.out.println("failed");
////        } finally {
////            System.out.println("failed");
////        }
////    }
////    public static void deleteAppointment(String appointmentId)
////    {
////        UpdateQuery("update ticket set ticket_status='0' where ticket_id ='"+appointmentId+"';");
////    }
////
////    public static void updateAppointment(String start,String end, String appointmentId)
////    {
//////        UpdateQuery("update ticket set ticket_time='"+time+"', ticket_date='"+date+"' where ticket_id ='"+ticket_id+"';");
////    }
////
////    public void deletePetbyId(String petId){
////        UpdateQuery("update animal set animal_status='0' where animal_id ='"+petId+"';");
////        UpdateQuery("update ticket set ticket_status='0' where animal_id ='"+petId+"';");
////    }
////
////    public static void updateCustomer(Customer p){
////        UpdateQuery("update owner set owner_name = '"+p.getName()+"',owner_lastname = '"+p.getLastName()+"',owner_gender ='"+
////                p.getGender()+"',owner_num = '"+p.getNum()+"' where owner_id ='"+p.getID()+"';");
////    }
////
////    public static void deleteCustomer(Customer p)
////    {
////        UpdateQuery("update owner set owner_status = 0 where owner_id ='"+p.getID()+"';");
////    }
//////
////    public static String isAccountEnabled(String login){
////        String count = "";
////        ExecuteQuery("select count(*) from owner,doctor where owner.user_login = '"+login+"' and owner.owner_status = 1 or" +
////                " doctor.user_login = '"+login+"';");
////        try {
////            while (rs.next()) {
////                count = rs.getString("count(*)");
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return count;
////    }
////
//    public static void updateBarber(Barber d) {
//        UpdateQuery("update doctor set doctor_name= '" + d.getName() + "',doctor_lastname= '" + d.getLastName() + "',doctor_gender='"
//                + d.getGender() + "',doctor_num= '" + d.getNum() + "' where doctor_id='" + d.getID() + "';");
//    }
//
////    public static String lastUpdate(String id)
////    {
////        String out = "";
////        ExecuteQuery("call getLastUpdate('"+id+"');");
////        try {
////            while (rs.next()) {
////                out = rs.getString("changedat");
////            }
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////        return out;
////    }
//}
