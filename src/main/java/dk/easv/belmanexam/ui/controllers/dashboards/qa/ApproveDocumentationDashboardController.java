package dk.easv.belmanexam.ui.controllers.dashboards.qa;

import dk.easv.belmanexam.be.Order;
import dk.easv.belmanexam.be.PhotoDocumentation;
import dk.easv.belmanexam.bll.implementations.PhotoDocumentationServiceImpl;
import dk.easv.belmanexam.bll.interfaces.OrderManagementService;
import dk.easv.belmanexam.bll.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.bll.utils.Status;
import dk.easv.belmanexam.exceptions.PhotoException;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.models.OrderModel;
import dk.easv.belmanexam.ui.models.PhotoDocumentationModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApproveDocumentationDashboardController {

    private PhotoDocumentationManagementService photoDocumentationManagementService;
    private PhotoDocumentationModel photoDocumentationModel;
    private OrderManagementService orderManagementService;
    private final Map<String, List<Image>> imageCache = new HashMap<>();

    private OrderModel orderModel;

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

    @FXML
    private void addPhoto(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setFitWidth(200);
        flowPaneImageContainer.getChildren().add(imageView);
    }

    public void setDetails(PhotoDocumentationModel photoDocumentationModel) {
        this.photoDocumentationModel = photoDocumentationModel;
        this.textFieldOrderNumber.setText(photoDocumentationModel.getOrder().getOrderNumber());
        imgViewLoadingGif.setVisible(true);
        Thread t = new Thread(() -> {
            try {
                loadImages(photoDocumentationModel.getOrder().getOrderNumber());
            } catch (PhotoException e) {
                throw new RuntimeException(e);
            } finally {
                Platform.runLater(() -> imgViewLoadingGif.setVisible(false));
            }
        });
        t.start();
    }

    public void loadImages(String orderNumber) throws PhotoException {
        Platform.runLater(() -> flowPaneImageContainer.getChildren().clear());
        if(imageCache.containsKey(orderNumber)) {
            List<Image> photos = imageCache.get(orderNumber);
            photos.forEach(photo -> Platform.runLater(() -> addPhoto(photo)));
        }
        else{
            List<Image> photos = photoDocumentationManagementService.getAllImagesByOrderNumber(orderNumber);
            imageCache.put(orderNumber, photos);
            photos.forEach(photo -> Platform.runLater(() -> addPhoto(photo)));
        }

    }

    @FXML
    private void onClickApproveDocumentation() {
        OrderModel orderModel = photoDocumentationModel.getOrder();
        orderModel.setStatus(Status.APPROVED);
        orderManagementService.updateOrder(orderModel);
        ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_DASHBOARD, "BelSign");
    }

    @FXML
    private void onClickRejectDocumentation() {
        OrderModel orderModel = photoDocumentationModel.getOrder();
        orderModel.setStatus(Status.REJECTED);
        orderManagementService.updateOrder(orderModel);
        ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_DASHBOARD, "BelSign");

    }

    public void setServices(PhotoDocumentationManagementService photoDocumentationManagementService, OrderManagementService orderManagementService) {
        this.photoDocumentationManagementService = photoDocumentationManagementService;
        this.orderManagementService = orderManagementService;
    }
}