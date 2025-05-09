package dk.easv.belmanexam.ui.controllers.dashboards.operator;

import dk.easv.belmanexam.be.Order;
import dk.easv.belmanexam.bll.interfaces.OrderManagementService;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.models.OrderModel;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.Optional;

public class EnterOrderDashboardController {

    private OrderManagementService orderManagementService;

    @FXML private TextField textFieldOrderNumber;

    @FXML
    private void showDocumentationCreationView() {
        Pair<Parent, DocumentationCreationDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD);
        String orderNumber = textFieldOrderNumber.getText();
        OrderModel matchingOrder = orderManagementService.getOrderListModel().getOrders()
                .stream()
                .filter(order -> orderNumber.equals(order.getOrderNumber()))
                .findFirst()
                .orElse(null);
        if(matchingOrder != null) {
            p.getValue().setDetails(matchingOrder, null);
            ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD, "BelSign");
        }
        else{
            // @ToDo -> Replace with proper exception handling not some bullshittery
            throw new IllegalStateException("No order found");
        }

    }
    @FXML
    private void showParentView(){
        ViewManager.INSTANCE.switchDashboard(FXMLPath.ORDERS_DASHBOARD, "BelSign");
    }

    public void setServices(OrderManagementService orderManagementService) {
        this.orderManagementService = orderManagementService;
    }
}
