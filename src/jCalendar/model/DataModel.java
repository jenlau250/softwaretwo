/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.model;

/**
 *
 * @author Jen
 */
public class DataModel {

    private final static DataModel instance = new DataModel();

    public static DataModel getInstance() {
        return instance;
    }

    // for adding/editing events
    public Appointment selectedAppointment;
//    public int event_day;
//    public int event_month;
//    public int event_year;
//    public int event_term_id;
//    public String event_subject;

//    private final ObservableList<Customer> CustomerList = FXCollections.observableArrayList(Customer
//            -> new Observable[]{Customer.customerIdProperty(), Customer.customerNameProperty()});
//
//    private final ObjectProperty<Customer> currentCustomer = new SimpleObjectProperty<>(null);
//
//    public ObjectProperty<Customer> currentCustomerProperty() {
//        return currentCustomer;
//    }
//
//    public final Customer getCurrentCustomer() {
//        return currentCustomerProperty().get();
//    }
//
//    public final void setCurrentCustomer(Customer Customer) {
//        currentCustomerProperty().set(Customer);
//    }
//
//    public ObservableList<Customer> getCustomerList() {
//        return CustomerList;
//    }
//
//    public void loadData() {
//        // mock...
//        CustomerList.setAll(
//                new Customer("Jacob", "Smith")
//        //                new Customer("Isabella", "Johnson", "isabella.johnson@example.com"),
//        //                new Customer("Ethan", "Williams", "ethan.williams@example.com"),
//        //                new Customer("Emma", "Jones", "emma.jones@example.com"),
//        //                new Customer("Michael", "Brown", "michael.brown@example.com")
//        );
//    }
//
//    public void saveData() {
//    }
}
