package dk.easv.belmanexam.ui.controllers.components;

import dk.easv.belmanexam.entities.PhotoDocumentation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDateTime;

public class PersonalDocumentationComponent {
    @FXML
    private Label lblStatus;

    @FXML
    private Label lblRecordDate;

    @FXML
    private Label lblOrderNumber;

    public void setStatus(String status) {
        lblStatus.setText(status);
    }
    public void setRecordDate(String recordDate) {
        lblRecordDate.setText(recordDate);
    }
    public void setOrderNumber(String orderNumber) {
        lblOrderNumber.setText(orderNumber);
    }

    public void setPhotoDocumentation(PhotoDocumentation documentation) {
        lblOrderNumber.setText(documentation.getOrderNumber());
        String s = documentation.getStatus().toString().toLowerCase();
        String capitalized = Character.toUpperCase(s.charAt(0)) + s.substring(1);
        lblStatus.setText(capitalized);
        LocalDateTime localDateTime = documentation.getDateTime();
        lblRecordDate.setText(localDateTime.getDayOfMonth() + "/" + localDateTime.getMonthValue() + "/" + localDateTime.getYear());
    }
}
