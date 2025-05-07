package dk.easv.belmanexam.ui.controllers.dashboards.qa;

import dk.easv.belmanexam.be.Order;
import dk.easv.belmanexam.be.PhotoDocumentation;
import dk.easv.belmanexam.bll.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.dal.GoogleDriveManager;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.controllers.components.DocumentationListComponent;
import dk.easv.belmanexam.ui.controllers.components.OrderListComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class DocumentationDashboardController {
    private PhotoDocumentationManagementService photoDocumentationManagementService;
    private GoogleDriveManager googleDriveManager;

    @FXML
    private FlowPane flowPaneOrderList;

    private void initialize() {
        Collection<PhotoDocumentation> documentations = photoDocumentationManagementService.getPhotoDocumentationListModel().getDocumentation();
        documentations.forEach(documentation -> {
            Pair<Parent, DocumentationListComponent> p = FXMLManager.INSTANCE.loadFXML(FXMLPath.DOCUMENTATION_LIST_COMPONENT);
            p.getValue().setOrderNumber(documentation.getOrder().getOrderNumber());
            p.getValue().setDocumentedBy(documentation.getUser().getFirstName() + " " + documentation.getUser().getLastName());
            // ToDo -> Replace this with something more elegant
            String date = documentation.getDate().getDayOfMonth() + "/" + documentation.getDate().getMonthValue() + "/" + documentation.getDate().getYear();
            p.getValue().setRecordDate(date);
            flowPaneOrderList.getChildren().add(p.getKey());
        });
    }

    public void setServices(PhotoDocumentationManagementService photoDocumentationManagementService, GoogleDriveManager googleDriveManager) {
        this.photoDocumentationManagementService = photoDocumentationManagementService;
        this.googleDriveManager = googleDriveManager;
        initialize();
    }
}
