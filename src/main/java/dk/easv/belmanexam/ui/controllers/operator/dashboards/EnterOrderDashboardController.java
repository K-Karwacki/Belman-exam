package dk.easv.belmanexam.ui.controllers.operator.dashboards;

import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class EnterOrderDashboardController {

    @FXML private TextField textFieldOrderNumber;

    @FXML
    private void showDocumentationCreationView() {
        Pair<Parent, DocumentationCreationDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD);
        p.getValue().setDetails(textFieldOrderNumber.getText(), null);
        ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD, "BelSign");
    }
    @FXML
    private void showParentView(){
        ViewManager.INSTANCE.switchDashboard(FXMLPath.ORDERS_DASHBOARD, "BelSign");
    }

}
