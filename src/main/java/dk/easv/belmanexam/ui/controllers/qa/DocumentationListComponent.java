package dk.easv.belmanexam.ui.controllers.qa;

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

public class DocumentationListComponent {

    private PhotoDocumentation photoDocumentation;

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

    private String orderNumber;

    @FXML
    private void onClickShowApproveDocumentationView() {
        Pair<Parent, ApproveDocumentationDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD);
        p.getValue().setDetails(orderNumber);
        ViewManager.INSTANCE.switchDashboard(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD, "BelSign");
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        lblOrderNumber.setText(orderNumber);
    }

    public void setDocumentedBy(String documentedBy) {
        this.lblDocumentedBy.setText(documentedBy);
    }
    public void setRecordDate(String recordDate) {
        this.lblRecordDate.setText(recordDate);
    }

    public void setPhotoDocumentation(PhotoDocumentation documentation) {
        this.photoDocumentation = photoDocumentation;
//        setOrderNumber(documentation.getOrder().getOrderNumber());
//        setDocumentedBy(documentation.getUser().getFirstName() + " " + documentation.getUser().getLastName());
//        String date = documentation.getDate().getDayOfMonth() + "/" + documentation.getDate().getMonthValue() + "/" + documentation.getDate().getYear();
//        setRecordDate(date);
    }
}
