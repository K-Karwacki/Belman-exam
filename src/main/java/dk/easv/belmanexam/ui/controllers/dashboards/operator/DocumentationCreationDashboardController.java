package dk.easv.belmanexam.ui.controllers.dashboards.operator;

import dk.easv.belmanexam.bll.PhotoDocumentationService;
import dk.easv.belmanexam.exceptions.PhotoException;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.components.OrderListComponent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DocumentationCreationDashboardController {

    private List<File> photos = new ArrayList<>();
    private OrderListComponent parentController;
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
        List<Image> photos = photoDocumentationService.getAllImagesByOrderNumber(orderNumber);
        photos.forEach(this::addPhoto);
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
//            photoDocumentationService.saveFileInFolder(selectedFile, orderNumber);
            Image image = new Image(selectedFile.toURI().toString());
            savePhotoInformation(selectedFile);
            addPhoto(image);
        }

    }
    @FXML
    private void addPhoto(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setFitWidth(200);
        flowPaneImageContainer.getChildren().add(imageView);
        imageView.setOnMouseClicked(event -> {
            Pair<Parent, ImageDisplayDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.IMAGE_DISPLAY_DASHBOARD);
            p.getValue().setDetails(image);
            ViewManager.INSTANCE.switchDashboard(FXMLPath.IMAGE_DISPLAY_DASHBOARD, "Selected Photo");
        });
    }
    private void savePhotoInformation(File selectedFile) {
        photos.add(selectedFile);
    }
    @FXML
    private void showParentView(){
        ViewManager.INSTANCE.switchDashboard(FXMLPath.ORDERS_DASHBOARD, "Belsign");
    }

    @FXML
    private void onClickSubmitDocumentation(){
        if(!photos.isEmpty()){
        photos.forEach(file -> {
            try {
                photoDocumentationService.saveFileInFolder(file, orderNumber);
            } catch (PhotoException e) {
                throw new RuntimeException(e);
            }
        });}
        if(parentController != null) {
            parentController.changeStatus();
        }
        ViewManager.INSTANCE.switchDashboard(FXMLPath.ORDERS_DASHBOARD, "Belsign");

    }

    public void setDetails(String orderNumber, OrderListComponent parentController) {
        flowPaneImageContainer.getChildren().clear();
        photos.clear();
        this.orderNumber = orderNumber;
        textFieldOrderNumber.setText(orderNumber);
        this.parentController = parentController;
    }

}
