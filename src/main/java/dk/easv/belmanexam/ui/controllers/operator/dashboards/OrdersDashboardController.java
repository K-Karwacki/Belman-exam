package dk.easv.belmanexam.ui.controllers.operator.dashboards;

import dk.easv.belmanexam.services.interfaces.OrderManagementService;
import dk.easv.belmanexam.repositories.utils.GoogleDriveManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class OrdersDashboardController{
    private GoogleDriveManager googleDriveManager;
    private OrderManagementService orderManagementService;

    @FXML
    private FlowPane flowPaneOrderList;

    @FXML
    private TextField textFieldSearchBar;

    private void initialize() {
//        Collection<Order> orders = orderManagementService.getOrderListModel().getOrders();
//        orders.forEach(order -> {
//            Pair<Parent, OrderListComponent> p = FXMLManager.INSTANCE.loadFXML(FXMLPath.ORDER_LIST_COMPONENT);
//            p.getValue().setOrderNumber(order.getOrderNumber());
//            p.getValue().setStatus(order.getStatus());
//            flowPaneOrderList.getChildren().add(p.getKey());
//        });
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
