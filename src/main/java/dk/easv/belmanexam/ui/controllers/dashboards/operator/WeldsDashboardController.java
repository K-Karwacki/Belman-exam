package dk.easv.belmanexam.ui.controllers.dashboards.operator;

import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.components.WeldListComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

public class WeldsDashboardController implements Initializable {
    @FXML
    private FlowPane flowPanePaneWeldList;

    @FXML
    private TextField textFieldOrderNumber;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pair<Parent, WeldListComponent> p = FXMLManager.INSTANCE.getFXML(FXMLPath.WELD_LIST_COMPONENT);
        p.getValue().setWeldNumber("12-03/133245");
        flowPanePaneWeldList.getChildren().addAll(p.getKey());
    }
    @FXML
    private void showParentView(){
        ViewManager.INSTANCE.switchDashboard(FXMLPath.ENTER_ORDER_DASHBOARD, "Belsign");
    }

    public void setOrderNumber(String orderNumber){
        textFieldOrderNumber.setText(orderNumber);
    }

}
