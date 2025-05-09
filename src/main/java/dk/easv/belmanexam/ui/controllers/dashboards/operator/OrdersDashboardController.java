package dk.easv.belmanexam.ui.controllers.dashboards.operator;

import dk.easv.belmanexam.be.Order;
import dk.easv.belmanexam.bll.interfaces.OrderManagementService;
import dk.easv.belmanexam.dal.GoogleDriveManager;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.components.OrderListComponent;
import dk.easv.belmanexam.ui.models.OrderModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class OrdersDashboardController{
    private GoogleDriveManager googleDriveManager;
    private OrderManagementService orderManagementService;

    @FXML
    private FlowPane flowPaneOrderList;

    @FXML
    private TextField textFieldSearchBar;

    private void initialize() {
        Collection<OrderModel> orders = orderManagementService.getOrderListModel().getOrders();
        orders.forEach(order -> {
            Pair<Parent, OrderListComponent> p = FXMLManager.INSTANCE.loadFXML(FXMLPath.ORDER_LIST_COMPONENT);
            p.getValue().setOrderModel(order);
            flowPaneOrderList.getChildren().add(p.getKey());
        });
    }

    @FXML
    private void showEnterOrderDashboard(){
        ViewManager.INSTANCE.switchDashboard(FXMLPath.ENTER_ORDER_DASHBOARD, "BelSign");
    }

    @FXML
    private void filterByOrderNumber(){
    }

    public void setServices(OrderManagementService orderManagementService, GoogleDriveManager googleDriveManager) {
        this.googleDriveManager = googleDriveManager;
        this.orderManagementService = orderManagementService;
        initialize();
    }

}
