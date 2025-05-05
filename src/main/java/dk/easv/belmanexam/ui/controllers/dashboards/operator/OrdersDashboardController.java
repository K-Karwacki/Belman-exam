package dk.easv.belmanexam.ui.controllers.dashboards.operator;

import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.components.OrderListComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

public class OrdersDashboardController implements Initializable {
    @FXML
    private FlowPane flowPaneOrderList;

    @FXML
    private TextField textFieldSearchBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // @ToDo -> Remove mock data later
        Pair<Parent, OrderListComponent> p1 = FXMLManager.INSTANCE.loadFXML(FXMLPath.ORDER_LIST_COMPONENT);
        p1.getValue().setOrderNumber("2024");
        Pair<Parent, OrderListComponent> p2 = FXMLManager.INSTANCE.loadFXML(FXMLPath.ORDER_LIST_COMPONENT);
        p2.getValue().setOrderNumber("2025");
        Pair<Parent, OrderListComponent> p3 = FXMLManager.INSTANCE.loadFXML(FXMLPath.ORDER_LIST_COMPONENT);
        p3.getValue().setOrderNumber("2025/21/1231");
        Pair<Parent, OrderListComponent> p4 = FXMLManager.INSTANCE.loadFXML(FXMLPath.ORDER_LIST_COMPONENT);
        p4.getValue().setOrderNumber("2025/11/1331");
        Pair<Parent, OrderListComponent> p5 = FXMLManager.INSTANCE.loadFXML(FXMLPath.ORDER_LIST_COMPONENT);
        p5.getValue().setOrderNumber("2022/10/101");
        flowPaneOrderList.getChildren().addAll(p1.getKey(), p2.getKey(), p3.getKey(), p4.getKey(), p5.getKey());
    }

    @FXML
    private void showEnterOrderDashboard(){
        ViewManager.INSTANCE.switchDashboard(FXMLPath.ENTER_ORDER_DASHBOARD, "BelSign");
    }

    @FXML
    private void filterByOrderNumber(){
    }

}
