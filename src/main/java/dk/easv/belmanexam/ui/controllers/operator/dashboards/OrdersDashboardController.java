package dk.easv.belmanexam.ui.controllers.operator.dashboards;

import dk.easv.belmanexam.auth.UserSession;
import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.repositories.utils.GoogleDriveManager;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.FXMLPath;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class OrdersDashboardController  {
    private GoogleDriveManager googleDriveManager;
    private PhotoDocumentationManagementService photoDocumentationManagementService;

    @FXML
    private FlowPane flowPaneOrderList;

    @FXML
    private TextField textFieldSearchBar;

    @FXML
    public void initialize() {
        System.out.println("Initializing OrdersDashboardController...");
        loadOperatorDocumentations(textFieldSearchBar.getText().trim().toLowerCase());
    }

    @FXML
    public void showEnterOrderDashboard() {
        ViewManager.INSTANCE.switchDashboard(FXMLPath.ENTER_ORDER_DASHBOARD, "BelSign");
    }

    @FXML
    public void filterByOrderNumber() {
        String filter = textFieldSearchBar.getText().trim().toLowerCase();
        loadOperatorDocumentations(filter);
    }

    private void loadOperatorDocumentations(String filter) {
        if (photoDocumentationManagementService == null || UserSession.INSTANCE.getLoggedUser() == null) return;

        String operatorId = String.valueOf(UserSession.INSTANCE.getLoggedUser().getId());

        Collection<PhotoDocumentation> allDocs = photoDocumentationManagementService.getAll();
        List<PhotoDocumentation> filteredDocs = allDocs.stream()
                .filter(doc -> Objects.equals(doc.getOperatorID(), operatorId))
                .filter(doc -> doc.getOrderNumber().toLowerCase().contains(filter))
                .toList();

        flowPaneOrderList.getChildren().clear();

        for (PhotoDocumentation doc : filteredDocs) {
            Node docNode = createDocumentationNode(doc);
            flowPaneOrderList.getChildren().add(docNode);
        }
    }



    private Node createDocumentationNode(PhotoDocumentation doc) {
        VBox box = new VBox(5);
        box.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-background-color: white; -fx-border-radius: 6;");
        Label orderLabel = new Label("Order: " + doc.getOrderNumber());
        Label statusLabel = new Label("Status: " + doc.getStatus());
        Label dateLabel = new Label("Date: " + doc.getDateTime().toString());
        box.getChildren().addAll(orderLabel, statusLabel, dateLabel);
        return box;
    }

    public void setServices(PhotoDocumentationManagementService photoDocumentationManagementService, GoogleDriveManager googleDriveManager) {
        this.googleDriveManager = googleDriveManager;
        this.photoDocumentationManagementService = photoDocumentationManagementService;
        loadOperatorDocumentations(textFieldSearchBar.getText().trim().toLowerCase());
    }
}
