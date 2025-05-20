package dk.easv.belmanexam.ui.controllers.operator.dashboards;

import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.exceptions.PhotoException;
import dk.easv.belmanexam.services.utils.Status;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.components.PhotoInputComponentController;
import dk.easv.belmanexam.ui.controllers.components.OrderListComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DocumentationCreationDashboardController {

    private final String[] sides = {"Top", "Front", "Left", "Right", "Back"};

    private String parentFolderId;
    private List<File> photos = new ArrayList<>();
    private List<PhotoInputComponentController> photoInputComponents = new ArrayList<>();
    private OrderListComponent parentController;
    private PhotoDocumentationManagementService photoDocumentationManagementService;
    private String orderNumber;
    @FXML
    private TextField textFieldOrderNumber;

    @FXML
    private FlowPane flowPaneImageContainer;

    private void initialize() {
        flowPaneImageContainer.getChildren().clear();
        for(String side: sides) {
            Pair<Parent, PhotoInputComponentController> p = FXMLManager.INSTANCE.loadFXML(FXMLPath.PHOTO_INPUT_COMPONENT);
            p.getValue().setSideLabel(side);
            flowPaneImageContainer.getChildren().add(p.getKey());
            photoInputComponents.add(p.getValue());
        }
    }

//    @FXML
//    private void uploadPhotoViaCloud() throws PhotoException {
//        flowPaneImageContainer.getChildren().clear();
//        String orderNumber = textFieldOrderNumber.getText();
//        List<Image> photos = photoDocumentationManagementService.getAllImagesByOrderNumber(orderNumber);
//        photos.forEach(this::addPhoto);
//    }
//
//    @FXML
//    private void uploadPhotoFromDevice() throws PhotoException {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Select Image File");
//
//        // Add image file filters
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
//        File selectedFile = fileChooser.showOpenDialog(flowPaneImageContainer.getScene().getWindow());
//        if (selectedFile != null) {
////            photoDocumentationServiceImpl.saveFileInFolder(selectedFile, orderNumber);
//            Image image = new Image(selectedFile.toURI().toString());
//            savePhotoInformation(selectedFile);
//            addPhoto(image);
//        }
//
//    }
//    @FXML
//    private void addPhoto(Image image) {
//        ImageView imageView = new ImageView(image);
//        imageView.setFitHeight(150);
//        imageView.setFitWidth(200);
//        flowPaneImageContainer.getChildren().add(imageView);
//        imageView.setOnMouseClicked(event -> {
//            Pair<Parent, ImageDisplayDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.IMAGE_DISPLAY_DASHBOARD);
//            p.getValue().setDetails(image);
//            ViewManager.INSTANCE.switchDashboard(FXMLPath.IMAGE_DISPLAY_DASHBOARD, "Selected Photo");
//        });
//    }
    private void savePhotoInformation(File selectedFile) {
        photos.add(selectedFile);
    }
    @FXML
    private void showParentView(){
        ViewManager.INSTANCE.switchDashboard(FXMLPath.ORDERS_DASHBOARD, "Belsign");
    }

    @FXML
    private void onClickSubmitDocumentation() throws PhotoException {
        PhotoDocumentation photoDocumentation = new PhotoDocumentation();
        photoDocumentation.setStatus(Status.PENDING);
        photoDocumentation.setOrderNumber(orderNumber);
        photoDocumentation.setDateTime(LocalDateTime.now());
        photoDocumentationManagementService.add(photoDocumentation);
//        if(!photos.isEmpty()){
//        photos.forEach(file -> {
//            try {
//                photoDocumentationManagementService.saveFileInFolder(file, orderNumber);
//            } catch (PhotoException e) {
//                throw new RuntimeException(e);
//            }
//        });}
        this.parentFolderId = photoDocumentationManagementService.createFolder(orderNumber);
        for(PhotoInputComponentController photoInputComponent: photoInputComponents) {
            File file = photoInputComponent.getFile();
            if(file != null) {
                try {
                    String sideFolder = photoDocumentationManagementService.createFolderInFolder(photoInputComponent.getSide(), parentFolderId);
                    photoDocumentationManagementService.saveFileInFolder(file, sideFolder);
            } catch (PhotoException e) {
                throw new RuntimeException(e);
            }
            }
        }
        if(parentController != null) {
            parentController.changeStatus();
        }
        ViewManager.INSTANCE.switchDashboard(FXMLPath.ORDERS_DASHBOARD, "Belsign");

    }

    public void setDetails(String orderNumber, OrderListComponent parentController) {
        initialize();
        photos.clear();
        this.orderNumber = orderNumber;
        textFieldOrderNumber.setText(orderNumber);
        this.parentController = parentController;
    }

    public void setServices(PhotoDocumentationManagementService photoDocumentationManagementService) {
        this.photoDocumentationManagementService = photoDocumentationManagementService;
    }


}
