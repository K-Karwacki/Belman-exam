package dk.easv.belmanexam.model;

import dk.easv.belmanexam.services.utils.Status;
import javafx.beans.property.*;

import java.time.LocalDateTime;

public class PhotoDocumentation {

    private LongProperty id = new SimpleLongProperty();
    private StringProperty operatorId = new SimpleStringProperty();
    private ObjectProperty<UserModel> user = new SimpleObjectProperty<>();
    private StringProperty orderNumber = new SimpleStringProperty();
    private SimpleObjectProperty<LocalDateTime> dateTime = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Status> status = new SimpleObjectProperty<>();
    private String operatorID;

    public PhotoDocumentation(){
        id.set(-1);
        user.set(new UserModel(new User()));
        orderNumber.set("");
        dateTime.set(LocalDateTime.of(2012,12,31,11,59, 59,99));
        status.set(Status.PENDING);
        operatorID = "";
    }
    public Long getId() {
        return id.get();
    }
    public void setId(Long id) { this.id.set(id);}
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
    public LongProperty idProperty() {
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

    public void setOperatorID(String operatorID)
    {
        this.operatorID = operatorID;
    }

    public String getOperatorID()
    {
        return operatorID;
    }
}
