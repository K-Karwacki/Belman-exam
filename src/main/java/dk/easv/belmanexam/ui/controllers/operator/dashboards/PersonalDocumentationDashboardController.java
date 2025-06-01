package dk.easv.belmanexam.ui.controllers.operator.dashboards;

import dk.easv.belmanexam.auth.UserSession;
import dk.easv.belmanexam.entities.Photo;
import dk.easv.belmanexam.entities.PhotoDocumentation;
import dk.easv.belmanexam.entities.User;
import dk.easv.belmanexam.services.interfaces.OrderManagementService;
import dk.easv.belmanexam.repositories.utils.GoogleDriveManager;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.services.utils.Status;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.components.DocumentationListComponent;
import dk.easv.belmanexam.ui.controllers.components.PersonalDocumentationComponent;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;

import java.util.Collection;

public class PersonalDocumentationDashboardController {
    private PhotoDocumentationManagementService photoDocumentationManagementService;

    @FXML
    private FlowPane flowPaneDocumentationList;

    @FXML
    private TextField textFieldSearchBar;

    private void initialize() {
        addDocumentation();
        photoDocumentationManagementService.getPhotoDocumentationListModel()
                .getDocumentation()
                .addListener((ListChangeListener<PhotoDocumentation>) change -> {
                    addDocumentation();
                });}



    public void addDocumentation() {
        String input = textFieldSearchBar.getText();
        User currentUser = UserSession.INSTANCE.getLoggedUser();
        if(currentUser != null) {
            Collection<PhotoDocumentation> documentations = photoDocumentationManagementService.getPhotoDocumentationListModel().getDocumentationForOperator(currentUser, input);
            flowPaneDocumentationList.getChildren().clear();
            documentations.forEach(documentation -> {
                Pair<Parent, PersonalDocumentationComponent> p = FXMLManager.INSTANCE.loadFXML(FXMLPath.PERSONAL_DOCUMENTATION_COMPONENT);
                p.getValue().setPhotoDocumentation(documentation);
                flowPaneDocumentationList.getChildren().add(p.getKey());
            });
        }
    }
    @FXML
    public void showEnterOrderDashboard(){
        ViewManager.INSTANCE.switchDashboard(FXMLPath.ENTER_ORDER_DASHBOARD, "BelSign");
    }

    @FXML
    public void filterByOrderNumber(){
    }

    public void setServices(PhotoDocumentationManagementService photoDocumentationManagementService) {
        this.photoDocumentationManagementService = photoDocumentationManagementService;
        initialize();
    }

}
