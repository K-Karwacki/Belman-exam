package dk.easv.belmanexam.ui.controllers.dashboards.operator;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;

public class OrdersDashboardController {
    @FXML
    private void onClickShowEnterOrderNumberView(){
        ViewManager.INSTANCE.switchDashboard(FXMLPath.ENTER_ORDER_DASHBOARD, "Enter Order Number");
    }
}
