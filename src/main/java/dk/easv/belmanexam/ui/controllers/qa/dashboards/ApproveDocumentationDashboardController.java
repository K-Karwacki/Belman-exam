package dk.easv.belmanexam.ui.controllers.qa.dashboards;

import dk.easv.belmanexam.exceptions.PhotoException;
import dk.easv.belmanexam.entities.Photo;
import dk.easv.belmanexam.entities.PhotoDocumentation;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.services.utils.Status;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;

import dk.easv.belmanexam.ui.controllers.components.PhotoOutputComponentController;
import dk.easv.belmanexam.utils.ImageConverter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;

import java.io.IOException;
import java.util.*;

public class ApproveDocumentationDashboardController {

    private PhotoDocumentationManagementService photoDocumentationManagementService;
    private PhotoDocumentation photoDocumentation;
    private final Map<Long, Collection<Photo>> imageCache = new HashMap<>();


    @FXML
    private TextField textFieldOrderNumber;

    @FXML
    private TextField textFieldComment;

    @FXML
    private ImageView imgViewLoadingGif;

    @FXML
    private FlowPane flowPaneImageContainer;

    @FXML
    private void showParentView() {
        ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_DASHBOARD, "BelSign");
    }

    public void setDetails(PhotoDocumentation photoDocumentation) {
        this.photoDocumentation = photoDocumentation;
        this.textFieldOrderNumber.setText(photoDocumentation.getOrderNumber());
        imgViewLoadingGif.setVisible(true);
        Thread t = new Thread(() -> {
            try {
                loadImages(photoDocumentation.getId());
            } catch (PhotoException e) {
                throw new RuntimeException(e);
            } finally {
                Platform.runLater(() -> imgViewLoadingGif.setVisible(false));
            }
        });
        t.start();
    }

    public void loadImages(long documentation_id) throws PhotoException {
        flowPaneImageContainer.getChildren().clear();
        Collection<Photo> photos;
        if (imageCache.containsKey(photoDocumentation.getId())) {
            photos = imageCache.get(photoDocumentation.getId());
        } else {
            photos = photoDocumentationManagementService.getAllImagesByDocumentationId(documentation_id);
            imageCache.put(photoDocumentation.getId(), photos);
        }
        Platform.runLater(() -> flowPaneImageContainer.getChildren().clear());
        for (Photo photo : photos) {
            Platform.runLater(() -> {
                Pair<Parent, PhotoOutputComponentController> p = FXMLManager.INSTANCE.loadFXML(FXMLPath.PHOTO_OUTPUT_COMPONENT);
                p.getValue().setSide(photo.getSide());
                p.getValue().setImage(ImageConverter.convertToImage(photo.getImageData()));
                flowPaneImageContainer.getChildren().add(p.getKey());
            });
        }
    }

    @FXML
    private void onClickApproveDocumentation() throws IOException {
        String orderNumber = textFieldOrderNumber.getText();
        Collection<Photo> images = imageCache.get(photoDocumentation.getId());

        Pair<Parent, DocumentationPreviewController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.DOCUMENTATION_PREVIEW);
        DocumentationPreviewController controller = p.getValue();
        controller.setDetails(images, orderNumber, photoDocumentation);

        ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_PREVIEW, "BelSign");
    }

    @FXML
    private void onClickRejectDocumentation() {
        photoDocumentation.setStatus(Status.REJECTED);
        photoDocumentationManagementService.update(photoDocumentation);
        ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_DASHBOARD, "BelSign");

    }


    public void setServices(PhotoDocumentationManagementService photoDocumentationManagementService) {
        this.photoDocumentationManagementService = photoDocumentationManagementService;
    }

}
