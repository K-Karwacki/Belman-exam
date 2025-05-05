package dk.easv.belmanexam.ui.controllers.dashboards.operator;

import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import javax.swing.text.View;

public class EnterOrderDashboardController {

    @FXML private TextField textFieldOrderNumber;

    @FXML
    private void onClickShowDocumentationCreationView() {
//        Pair<Parent, DocumentationCreationDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD);
//        Pair<Parent, OrdersDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.ORDERS_DASHBOARD);
        // @ToDo -> Needs some validation later (check if order no. exist etc.)
//        p.getValue().setOrderNumber(textFieldOrderNumber.getText());
//        ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD, "Photo Documentation");
        ViewManager.INSTANCE.switchDashboard(FXMLPath.ORDERS_DASHBOARD, "Orders Number");
    }
}
