package dk.easv.belmanexam.ui.controllers.dashboards.operator;

import dk.easv.belmanexam.bll.PhotoDocumentationService;
import dk.easv.belmanexam.exceptions.PhotoException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class DocumentationCreationDashboardController {

    private final PhotoDocumentationService photoDocumentationService = new PhotoDocumentationService();
    private String orderNumber;
    @FXML
    private TextField textFieldOrderNumber;

    @FXML
    private FlowPane flowPaneImageContainer;

    @FXML
    private void uploadPhotoViaCloud() throws PhotoException {
        try {
            flowPaneImageContainer.getChildren().clear();
            String orderNumber = textFieldOrderNumber.getText();
            List<Image> images = photoDocumentationService.getAllImagesByOrderNumber(orderNumber);
            images.forEach(image -> flowPaneImageContainer.getChildren().add(new ImageView(image)));
        }
        catch (IOException | GeneralSecurityException e) {
            throw new PhotoException(e);
        }
    }

    @FXML
    private void uploadPhotoFromDevice(){

    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        textFieldOrderNumber.setText(orderNumber);
    }
}
