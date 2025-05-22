package dk.easv.belmanexam.model;

import dk.easv.belmanexam.services.utils.ActionType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDateTime;

public class Log {
    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<User> user = new SimpleObjectProperty<>();
    private SimpleObjectProperty<LocalDateTime> dateTime = new SimpleObjectProperty<>();
    private SimpleObjectProperty<ActionType> actionType = new SimpleObjectProperty<>();

    public IntegerProperty idProperty() {
        return id;
    }
    public ObjectProperty<User> userProperty() {
        return user;
    }
    public SimpleObjectProperty<LocalDateTime> dateTimeProperty() {
        return dateTime;
    }
    public SimpleObjectProperty<ActionType> actionTypeProperty() {
        return actionType;
    }
    public int getId() {
        return idProperty().get();
    }
    public void setId(int id) {
        this.idProperty().set(id);
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
