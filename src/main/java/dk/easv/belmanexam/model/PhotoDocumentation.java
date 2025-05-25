package dk.easv.belmanexam.model;

import dk.easv.belmanexam.services.utils.Status;
import javafx.beans.property.*;

import java.time.LocalDateTime;

public class PhotoDocumentation {

    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<UserModel> user = new SimpleObjectProperty<>();
    private StringProperty orderNumber = new SimpleStringProperty();
    private SimpleObjectProperty<LocalDateTime> dateTime = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Status> status = new SimpleObjectProperty<>();

    public PhotoDocumentation(){

    }
    public Integer getId() {
        return id.get();
    }
    public void setId(Integer id) { this.id.set(id);}
    public String getOrderNumber() {
        return orderNumber.get();
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber.set(orderNumber);
    }
    public LocalDateTime getDateTime() {
        return dateTime.get();
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime.set(dateTime);
    }
    public Status getStatus() {
        return status.get();
    }
    public void setStatus(Status status) {
        this.status.set(status);
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public StringProperty orderNumberProperty() {
        return orderNumber;
    }
    public ObjectProperty<LocalDateTime> dateTimeProperty() {
        return dateTime;
    }
    public ObjectProperty<Status> statusProperty() {
        return status;
    }
    public UserModel getUser() {return user.get();}
    public void setUser(UserModel user) {this.user.set(user);}
}
