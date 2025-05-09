package dk.easv.belmanexam.ui.controllers.qa.dashboards;

import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.repositories.utils.GoogleDriveManager;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

public class DocumentationDashboardController {
    private PhotoDocumentationManagementService photoDocumentationManagementService;
    private GoogleDriveManager googleDriveManager;

    @FXML
    private FlowPane flowPaneOrderList;

    private void initialize() {
//        Collection<PhotoDocumentation> documentations = photoDocumentationManagementService.getPhotoDocumentationListModel().getDocumentation();
//        documentations.forEach(documentation -> {
//            Pair<Parent, DocumentationListComponent> p = FXMLManager.INSTANCE.loadFXML(FXMLPath.DOCUMENTATION_LIST_COMPONENT);
//            p.getValue().setPhotoDocumentation(documentation);
//            flowPaneOrderList.getChildren().add(p.getKey());
//        });
    }

    public void setServices(PhotoDocumentationManagementService photoDocumentationManagementService, GoogleDriveManager googleDriveManager) {
        this.photoDocumentationManagementService = photoDocumentationManagementService;
        this.googleDriveManager = googleDriveManager;
        initialize();
    }
}
