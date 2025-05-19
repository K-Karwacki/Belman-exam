package dk.easv.belmanexam.ui.controllers.operator.dashboards;

import com.gluonhq.attach.pictures.PicturesService;
import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.exceptions.PhotoException;
import dk.easv.belmanexam.services.utils.Status;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.operator.OrderListComponent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DocumentationCreationDashboardController {

    private List<File> photos = new ArrayList<>();
    private OrderListComponent parentController;
    private PhotoDocumentationManagementService photoDocumentationManagementService;
    private String orderNumber;

    @FXML
    private TextField textFieldOrderNumber;

    @FXML
    private FlowPane flowPaneImageContainer;

    @FXML
    public void uploadPhotoViaCloud() throws PhotoException {
        flowPaneImageContainer.getChildren().clear();
        String orderNumber = textFieldOrderNumber.getText();
        List<Image> photos = photoDocumentationManagementService.getAllImagesByOrderNumber(orderNumber);
        photos.forEach(this::addPhoto);
    }


    @FXML
    public void uploadPhotoFromDevice() throws PhotoException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");

        // Add image file filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(flowPaneImageContainer.getScene().getWindow());
        if (selectedFile != null) {
//            photoDocumentationServiceImpl.saveFileInFolder(selectedFile, orderNumber);
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
    public void onClickSubmitDocumentation(){
        PhotoDocumentation photoDocumentation = new PhotoDocumentation();
        photoDocumentation.setStatus(Status.PENDING);
        photoDocumentation.setOrderNumber(orderNumber);
        photoDocumentation.setDateTime(LocalDateTime.now());
        photoDocumentationManagementService.add(photoDocumentation);
        if(!photos.isEmpty()){
        photos.forEach(file -> {
            try {
                photoDocumentationManagementService.saveFileInFolder(file, orderNumber);
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
//        flowPaneImageContainer.getChildren().clear();
//        photos.clear();
        this.orderNumber = orderNumber;
        textFieldOrderNumber.setText(orderNumber);
        this.parentController = parentController;
    }

    public void setServices(PhotoDocumentationManagementService photoDocumentationManagementService) {
        this.photoDocumentationManagementService = photoDocumentationManagementService;
    }



    public void onTouchAddPhoto(TouchEvent touchEvent)
    {
        System.out.println("touched");
        Optional<PicturesService> picturesService = PicturesService.create();
        if(picturesService.isPresent()){
            Optional<File> picture = picturesService.get().getImageFile();
        }else{
            System.out.println("Not supported operation");
        }
    }
}
