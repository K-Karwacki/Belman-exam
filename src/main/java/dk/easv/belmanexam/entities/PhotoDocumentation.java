package dk.easv.belmanexam.entities;

import dk.easv.belmanexam.services.utils.Status;
import dk.easv.belmanexam.ui.models.UserModel;

import java.time.LocalDateTime;


public class PhotoDocumentation {
    private Long id;

    private String operatorId;

    private UserModel user;

    private String orderNumber;

    private LocalDateTime dateTime;

    private Status status;

    public PhotoDocumentation() {
        this.id = -1L;
        this.user = new UserModel(new User());
        this.orderNumber = "";
        this.dateTime = LocalDateTime.of(2012, 12, 31, 11, 59, 59, 99_000_000);
        this.status = Status.PENDING;
        this.operatorId = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}