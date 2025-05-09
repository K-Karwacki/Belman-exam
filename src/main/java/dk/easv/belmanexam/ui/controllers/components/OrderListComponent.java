package dk.easv.belmanexam.ui.controllers.components;

import dk.easv.belmanexam.be.Order;
import dk.easv.belmanexam.bll.utils.Status;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.dashboards.operator.DocumentationCreationDashboardController;
import dk.easv.belmanexam.ui.models.OrderModel;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class OrderListComponent {

    private OrderModel orderModel;

    @FXML
    private Label lblOrderNumber;

    @FXML
    private HBox hboxContainer;

    @FXML
    private Button btnAddDocumentation;

    @FXML
    private Label lblOrderStatus;

    @FXML
    private Label lblOrderComment;

    @FXML
    private void onClickShowNewDocumentationView() {
        Pair<Parent, DocumentationCreationDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD);
        p.getValue().setDetails(orderModel, this);
        ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD, "BelSign");
    }

    public void changeStatus() {
        hboxContainer.getStyleClass().add("sent");
        lblOrderStatus.setText("Sent");
        lblOrderComment.setText("Waiting to be approved...");
        btnAddDocumentation.setVisible(false);
    }

    private void setDetails(){
        lblOrderNumber.textProperty().bind(orderModel.orderNumberProperty());
        lblOrderStatus.textProperty().bind(orderModel.statusProperty().asString());
        if(orderModel.getStatus() == Status.APPROVED){
            btnAddDocumentation.setVisible(false);
        }

        orderModel.statusProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == Status.APPROVED){
                btnAddDocumentation.setVisible(false);
            }
        });
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
        setDetails();
    }
}
