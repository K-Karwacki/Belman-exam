package dk.easv.belmanexam.ui.controllers.components;

import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.dashboards.operator.DocumentationCreationDashboardController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class OrderListComponent {

    @FXML
    private Label lblOrderNumber;

    @FXML
    private HBox hboxContainer;

    @FXML
    private Label lblOrderStatus;

    @FXML
    private Label lblOrderComment;

    private String orderNumber;

    @FXML
    private void onClickShowNewDocumentationView() {
        Pair<Parent, DocumentationCreationDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD);
        p.getValue().setDetails(orderNumber, this);
        ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD, "BelSign");
    }

    public void setOrderNumber(String orderNumber)
    {
        this.orderNumber = orderNumber;
        lblOrderNumber.setText(orderNumber);
    }

    public void changeStatus() {
        hboxContainer.getStyleClass().add("sent");
        lblOrderStatus.setText("Sent");
        lblOrderComment.setText("Waiting to be approved...");
    }
}
