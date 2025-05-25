package dk.easv.belmanexam.model;

import dk.easv.belmanexam.services.utils.ActionType;
import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Log {
    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<UserModel> user = new SimpleObjectProperty<>();
    private StringProperty orderNumber = new SimpleStringProperty();
    private ObjectProperty<LocalDateTime> dateTime = new SimpleObjectProperty<>();
    private ObjectProperty<ActionType> actionType = new SimpleObjectProperty<>();

    public IntegerProperty idProperty() {
        return id;
    }
    public ObjectProperty<LocalDateTime> dateTimeProperty() {
        return dateTime;
    }
    public ObjectProperty<ActionType> actionTypeProperty() {
        return actionType;
    }
    public int getId() {
        return idProperty().get();
    }
    public void setId(int id) {
        this.idProperty().set(id);
    }
    public UserModel getUser() { // Updated getter
        return user.get();
    }
    public void setUser(UserModel user) { // Updated setter
        this.user.set(user);
    }
    public String getOrderNumber() {
        return orderNumber.get();
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber.set(orderNumber);
    }
    public LocalDateTime getDateTime() {
        return dateTimeProperty().get();
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTimeProperty().set(dateTime);
    }
    public ActionType getActionType() {
        return actionTypeProperty().get();
    }
    public void setActionType(ActionType actionType) {
        this.actionTypeProperty().set(actionType);
    }
}