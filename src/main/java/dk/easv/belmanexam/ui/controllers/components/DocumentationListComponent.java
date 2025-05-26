package dk.easv.belmanexam.ui.controllers.components;

import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.qa.dashboards.ApproveDocumentationDashboardController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.time.LocalDateTime;

public class DocumentationListComponent {

    private PhotoDocumentation photoDocumentation;

    @FXML
    private Label lblOrderNumber;

    @FXML
    private Label lblOrderStatus;

    @FXML
    private HBox hboxContainer;

    @FXML
    private Button btnSeeDocumentation;

    @FXML
    private Label lblRecordDate;

    @FXML
    private Label lblDocumentedBy;


    @FXML
    private void onClickShowApproveDocumentationView() {
        Pair<Parent, ApproveDocumentationDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD);
        p.getValue().setDetails(photoDocumentation);
        ViewManager.INSTANCE.switchDashboard(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD, "BelSign");
    }


    public void setPhotoDocumentation(PhotoDocumentation documentation) {
        this.photoDocumentation = documentation;
        lblOrderNumber.setText(documentation.getOrderNumber());
        String s = documentation.getStatus().toString().toLowerCase();
        String capitalized = Character.toUpperCase(s.charAt(0)) + s.substring(1);
        lblOrderStatus.setText(capitalized);
        LocalDateTime localDateTime = documentation.getDateTime();
        lblRecordDate.setText(localDateTime.getDayOfMonth() + "/" + localDateTime.getMonthValue() + "/" + localDateTime.getYear());
//        lblDocumentedBy.setText(documentation.getUser().getFirstName() + " " + documentation.getUser().getLastName());
    }
}
