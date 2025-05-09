package dk.easv.belmanexam.ui.controllers.components;

import dk.easv.belmanexam.be.PhotoDocumentation;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.dashboards.operator.DocumentationCreationDashboardController;
import dk.easv.belmanexam.ui.controllers.dashboards.qa.ApproveDocumentationDashboardController;
import dk.easv.belmanexam.ui.models.PhotoDocumentationModel;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class DocumentationListComponent {

    private PhotoDocumentationModel photoDocumentation;

    @FXML
    private Label lblOrderNumber;

    @FXML
    private HBox hboxContainer;

    @FXML
    private Button btnSeeDocumentation;

    @FXML
    private Label lblRecordDate;

    @FXML
    private Label lblDocumentedBy;

    @FXML
    private Label lblOrderStatus;


    @FXML
    private void onClickShowApproveDocumentationView() {
        Pair<Parent, ApproveDocumentationDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD);
        p.getValue().setDetails(photoDocumentation);
        ViewManager.INSTANCE.switchDashboard(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD, "BelSign");
    }



    public void setPhotoDocumentation(PhotoDocumentationModel documentation) {
        this.photoDocumentation = documentation;
        lblOrderNumber.textProperty().bind(photoDocumentation.getOrder().orderNumberProperty());
        lblDocumentedBy.textProperty().bind(photoDocumentation.userProperty().asString());
        lblRecordDate.textProperty().bind(photoDocumentation.dateProperty().asString());
        lblOrderStatus.textProperty().bind(photoDocumentation.getOrder().statusProperty().asString());
    }
}
