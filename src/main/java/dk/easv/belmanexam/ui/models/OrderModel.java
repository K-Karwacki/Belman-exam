package dk.easv.belmanexam.ui.models;

import dk.easv.belmanexam.be.Customer;
import dk.easv.belmanexam.be.Order;
import dk.easv.belmanexam.bll.utils.Status;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class OrderModel {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleStringProperty orderNumber = new SimpleStringProperty();
    private final SimpleObjectProperty<Status> status = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<LocalTime> time = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Customer> customer = new SimpleObjectProperty<>();

    public OrderModel(Order order) {
        this.id.set(order.getId());
        this.orderNumber.set(order.getOrderNumber());
        this.status.set(order.getStatus());
        this.time.set(order.getDate().toLocalTime());
        this.date.set(order.getDate().toLocalDate());
        this.customer.set(order.getCustomer());
    }
    public OrderModel(){
        id.set(-1);
        orderNumber.set(null);
        status.set(null);
        time.set(null);
        date.set(null);
        customer.set(null);
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber.set(orderNumber);
    }
    public void setStatus(Status status) {
        this.status.set(status);
    }
    public void setTime(LocalTime time) {
        this.time.set(time);
    }
    public void setDate(LocalDate date) {
        this.date.set(date);
    }
    public void setCustomer(Customer customer) {
        this.customer.set(customer);
    }
    public SimpleIntegerProperty idProperty() {
        return id;
    }
    public SimpleStringProperty orderNumberProperty() {
        return orderNumber;
    }
    public SimpleObjectProperty<Status> statusProperty() {
        return status;
    }
    public SimpleObjectProperty<LocalTime> timeProperty() {
        return time;
    }
    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }
    public SimpleObjectProperty<Customer> customerProperty() {
        return customer;
    }
    public Status getStatus() {
        return status.get();
    }
    public String getOrderNumber() {
        return orderNumber.get();
    }
    public LocalTime getTime() {
        return time.get();
    }
    public LocalDate getDate() {
        return date.get();
    }
    public Customer getCustomer() {
        return customer.get();
    }


}
