package jCalendar.dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jlau2
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jlau2
 */
public class DBConnection {

    private static DBConnection connection;

    private static final String databaseName = "U05NQU";
    private static final String DB_URL = "jdbc:mysql://52.206.157.109/" + databaseName;
    private static final String username = "U05NQU";
    private static final String password = "53688552114";
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    static Connection conn;
    private static Statement stmt = null;

    //Default terms
    private static String[] barbers = {"Sam", "Joe", "Elisa"};
    

    private static boolean tablesExist = false;

    //Constructor
    public DBConnection() {

        getConn();

//        if (tablesExist) {
//            System.out.println("Connection created. Tables already exist.");
//        } else {
//            
//           //create tables
//           
//           tablesExist = true;
//           System.out.println("New tables created. tablesExist changed to true. ");
//        }
    }

    public static void init() throws ClassNotFoundException, SQLException, Exception {
        {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(DB_URL, username, password);
            System.out.println("Connection Successful");

        }

    }

    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {
        conn.close();
        System.out.println("Connection Closed");
    }

    public static Connection getConn() {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(DB_URL, username, password);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("couldn't connect");
            throw new RuntimeException(ex);
        }
    }

    
    
    

}


