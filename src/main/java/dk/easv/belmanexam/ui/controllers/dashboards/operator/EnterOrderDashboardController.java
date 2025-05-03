package dk.easv.belmanexam.ui.controllers.dashboards.operator;

import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class EnterOrderDashboardController {

    @FXML private TextField textFieldOrderNumber;

    @FXML
    private void onClickShowDocumentationCreationView() {
        Pair<Parent, DocumentationCreationDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD);
        // @ToDo -> Needs some validation later (check if order no. exist etc.)
        p.getValue().setOrderNumber(textFieldOrderNumber.getText());
        ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD, "Photo Documentation");
    }
}
