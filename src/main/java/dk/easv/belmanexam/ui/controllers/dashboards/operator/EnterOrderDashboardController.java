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
    private void onClickShowWeldsView() {
        Pair<Parent, WeldsDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.WELDS_DASHBOARD);
        p.getValue().setOrderNumber(textFieldOrderNumber.getText());
        ViewManager.INSTANCE.switchDashboard(FXMLPath.WELDS_DASHBOARD, "Welds logs");
    }
}
