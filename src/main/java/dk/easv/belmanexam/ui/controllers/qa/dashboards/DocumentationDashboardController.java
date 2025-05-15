package dk.easv.belmanexam.ui.controllers.qa.dashboards;

import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.repositories.utils.GoogleDriveManager;
import dk.easv.belmanexam.services.utils.Status;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.controllers.qa.DocumentationListComponent;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;

import java.util.Collection;

public class DocumentationDashboardController {
    private PhotoDocumentationManagementService photoDocumentationManagementService;
    private Status status = Status.PENDING;
    private GoogleDriveManager googleDriveManager;

    @FXML private TextField textFieldOrderNumber;

    @FXML private Button btnApprovedDocumentation;

    @FXML private Button btnRejectedDocumentation;

    @FXML private Button btnPendingDocumentation;

    @FXML
    private FlowPane flowPaneDocumentationList;

    private void initialize() {
        addDocumentation(status);
        photoDocumentationManagementService.getPhotoDocumentationListModel()
                .getDocumentation()
                .addListener((ListChangeListener<PhotoDocumentation>) change -> {
                    addDocumentation(status);
                });}

    public void setServices(PhotoDocumentationManagementService photoDocumentationManagementService, GoogleDriveManager googleDriveManager) {
        this.photoDocumentationManagementService = photoDocumentationManagementService;
        this.googleDriveManager = googleDriveManager;
        initialize();
    }


    private void addDocumentation(Status status) {
        String input = textFieldOrderNumber.getText();
        Collection<PhotoDocumentation> documentations = photoDocumentationManagementService.getPhotoDocumentationListModel().getDocumentationForInput(status, input);
        flowPaneDocumentationList.getChildren().clear();
        documentations.forEach(documentation -> {
            Pair<Parent, DocumentationListComponent> p = FXMLManager.INSTANCE.loadFXML(FXMLPath.DOCUMENTATION_LIST_COMPONENT);
            p.getValue().setPhotoDocumentation(documentation);
            flowPaneDocumentationList.getChildren().add(p.getKey());
        });
    }

    @FXML
    private void onClickShowApprovedDocumentation() {
        addDocumentation(Status.APPROVED);
        status = Status.APPROVED;
        updateButtonStyles(btnApprovedDocumentation);
    }

    @FXML
    private void onClickShowRejectedDocumentation() {
        addDocumentation(Status.REJECTED);
        status = Status.REJECTED;
        updateButtonStyles(btnRejectedDocumentation);
    }

    @FXML
    private void onClickShowPendingDocumentation() {
        addDocumentation(Status.PENDING);
        status = Status.PENDING;
        updateButtonStyles(btnPendingDocumentation);
    }

    @FXML
    private void onClickSearch(){
        flowPaneDocumentationList.getChildren().clear();
        addDocumentation(status);
    }

    private void updateButtonStyles(Button activeButton) {
        Button[] buttons = {btnApprovedDocumentation, btnRejectedDocumentation, btnPendingDocumentation};
        for (Button button : buttons) {
            button.getStyleClass().remove("green-button");
            if (!button.getStyleClass().contains("disabled-menu-button")) {
                button.getStyleClass().add("disabled-menu-button");
            }
        }
        activeButton.getStyleClass().remove("disabled-menu-button");
        if (!activeButton.getStyleClass().contains("green-button")) {
            activeButton.getStyleClass().add("green-button");
        }
    }

}
