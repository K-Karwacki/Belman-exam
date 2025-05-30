package dk.easv.belmanexam.entities;

import dk.easv.belmanexam.services.utils.ActionType;
import dk.easv.belmanexam.ui.models.UserModel;

import java.time.LocalDateTime;

public class Log {
    private int id;
    private UserModel user;
    private String orderNumber;
    private LocalDateTime date;
    private ActionType actionType;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public UserModel getUser() { // Updated getter
        return user;
    }
    public void setUser(UserModel user) { // Updated setter
        this.user = user;
    }
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {

        this.orderNumber = orderNumber;
    }
    public LocalDateTime getDateTime() {
        return date;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.date = dateTime;
    }
    public ActionType getActionType() {
        return actionType;
    }
    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
}