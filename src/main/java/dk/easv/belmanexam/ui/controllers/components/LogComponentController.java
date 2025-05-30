package dk.easv.belmanexam.ui.controllers.components;

import dk.easv.belmanexam.entities.Log;
import dk.easv.belmanexam.services.utils.ActionType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDateTime;

public class LogComponentController {
    @FXML
    private Label txtUserFirstName;

    @FXML
    private Label txtUserLastName;

    @FXML
    private Label txtAction;

    @FXML
    private Label txtOrderNumber;

    @FXML
    private Label txtDate;

    public void setTxtUserFirstName(String txtUserFirstName) {
        this.txtUserFirstName.setText(txtUserFirstName);
    }
    public void setTxtUserLastName(String txtUserLastName) {
        this.txtUserLastName.setText(txtUserLastName);
    }
    public void setTxtAction(ActionType actionType) {
        this.txtAction.setText(actionType.toString());
    }
    public void setTxtOrderNumber(String txtOrderNumber) {
        this.txtOrderNumber.setText(txtOrderNumber);
    }
    public void setTxtDate(LocalDateTime dateTime) {
        String date = dateTime.getDayOfMonth() + "/" + dateTime.getMonthValue() + "/" + dateTime.getYear();
        this.txtDate.setText(date);
    }

    public void setLog(Log log) {
        setTxtAction(log.getActionType());
        setTxtOrderNumber(log.getOrderNumber());
        setTxtUserFirstName(log.getUser().getFirstName());
        setTxtUserLastName(log.getUser().getLastName());
        setTxtDate(log.getDateTime());
    }
}
