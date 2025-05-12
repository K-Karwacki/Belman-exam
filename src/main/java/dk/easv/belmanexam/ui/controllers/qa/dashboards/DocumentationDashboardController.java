package dk.easv.belmanexam.ui.controllers.qa.dashboards;

import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.repositories.utils.GoogleDriveManager;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.controllers.qa.DocumentationListComponent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;

import java.util.Collection;

public class DocumentationDashboardController {
    private PhotoDocumentationManagementService photoDocumentationManagementService;
    private GoogleDriveManager googleDriveManager;

    @FXML
    private FlowPane flowPaneDocumentationList;

    private void initialize() {
        Collection<PhotoDocumentation> documentations = photoDocumentationManagementService.getAll();
//        Collection<PhotoDocumentation> documentations = photoDocumentationManagementService.getPhotoDocumentationListModel().getDocumentation();
        documentations.forEach(documentation -> {
            Pair<Parent, DocumentationListComponent> p = FXMLManager.INSTANCE.loadFXML(FXMLPath.DOCUMENTATION_LIST_COMPONENT);
            p.getValue().setPhotoDocumentation(documentation);
            flowPaneDocumentationList.getChildren().add(p.getKey());
        });
    }

    public void setServices(PhotoDocumentationManagementService photoDocumentationManagementService, GoogleDriveManager googleDriveManager) {
        this.photoDocumentationManagementService = photoDocumentationManagementService;
        this.googleDriveManager = googleDriveManager;
        initialize();
    }
}
