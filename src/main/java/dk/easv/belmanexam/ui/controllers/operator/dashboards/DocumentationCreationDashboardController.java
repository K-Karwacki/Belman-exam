package dk.easv.belmanexam.ui.controllers.operator.dashboards;

import dk.easv.belmanexam.auth.UserSession;
import dk.easv.belmanexam.entities.PhotoDocumentation;
import dk.easv.belmanexam.ui.models.UserModel;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.exceptions.PhotoException;
import dk.easv.belmanexam.services.utils.Status;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.components.PhotoInputComponentController;
import dk.easv.belmanexam.ui.controllers.components.OrderListComponent;
import dk.easv.belmanexam.utils.ImageConverter;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        photoDocumentation.setUser(new UserModel(UserSession.INSTANCE.getLoggedUser()));
        photoDocumentationManagementService.add(photoDocumentation);
        this.parentFolderId = photoDocumentationManagementService.createFolder(orderNumber);
        for(PhotoInputComponentController photoInputComponent: photoInputComponents) {
            File file = photoInputComponent.getFile();
            if(file != null) {
                Image image = new Image(file.toURI().toString());
                byte[] image_data = ImageConverter.convertToByteArray(image);
                long documentation_id = photoDocumentationManagementService.getByOrderNumber(orderNumber).get().getId();
                photoDocumentationManagementService.addPhoto(image_data, documentation_id, photoInputComponent.getSide(), "");
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
