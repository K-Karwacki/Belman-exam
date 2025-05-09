package dk.easv.belmanexam.ui.models;

import dk.easv.belmanexam.be.PhotoDocumentation;
import dk.easv.belmanexam.be.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class PhotoDocumentationModel {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleObjectProperty<LocalTime> time = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<User> user = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<OrderModel> order = new SimpleObjectProperty<>();
//    private final SimpleStringProperty description = new SimpleStringProperty();

    public PhotoDocumentationModel() {
        this.id.set(-1);
        this.time.set(null);
        this.date.set(null);
        this.user.set(null);
        this.order.set(null);
//        this.description.set(null);
    }

    public PhotoDocumentationModel(PhotoDocumentation photoDocumentation){
        this.id.set(photoDocumentation.getId());
        this.time.set(photoDocumentation.getDate().toLocalTime());
        this.date.set(photoDocumentation.getDate().toLocalDate());
        this.user.set(photoDocumentation.getUser());
        this.order.set(new OrderModel(photoDocumentation.getOrder()));
//        this.description.set(photoDocumentation.getDescription());
    }
    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public SimpleIntegerProperty idProperty() {
        return id;
    }
    public void setTime(LocalTime time) {
        this.time.set(time);
    }
    public SimpleObjectProperty<LocalTime> timeProperty() {
        return time;
    }
    public LocalDate getDate() {
        return date.get();
    }
    public void setDate(LocalDate date) {
        this.date.set(date);
    }
    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }
    public User getUser() {
        return user.get();
    }
    public void setUser(User user) {
        this.user.set(user);
    }
    public SimpleObjectProperty<User> userProperty() {
        return user;
    }
    public OrderModel getOrder() {
        return order.get();
    }
    public void setOrder(OrderModel order) {
        this.order.set(order);
    }
}
