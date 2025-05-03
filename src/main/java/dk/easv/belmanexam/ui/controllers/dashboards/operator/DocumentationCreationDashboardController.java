package dk.easv.belmanexam.ui.controllers.dashboards.operator;

import dk.easv.belmanexam.bll.PhotoDocumentationService;
import dk.easv.belmanexam.exceptions.PhotoException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
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
        flowPaneImageContainer.getChildren().clear();
        String orderNumber = textFieldOrderNumber.getText();
        List<Image> images = photoDocumentationService.getAllImagesByOrderNumber(orderNumber);
        images.forEach(image -> {
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(150);
            imageView.setFitWidth(200);
            flowPaneImageContainer.getChildren().add(imageView);
        });
    }

    @FXML
    private void uploadPhotoFromDevice() throws PhotoException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");

        // Add image file filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(flowPaneImageContainer.getScene().getWindow());
        if (selectedFile != null) {
            photoDocumentationService.saveFileInFolder(selectedFile, orderNumber);
            Image image = new Image(selectedFile.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(150);
            imageView.setFitWidth(200);
            flowPaneImageContainer.getChildren().add(imageView);
        }

    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        textFieldOrderNumber.setText(orderNumber);
    }
}
