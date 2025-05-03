package dk.easv.belmanexam.ui.controllers.dashboards.admin;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;

public class UsersDashboardController {
    @FXML
    private void onClickShowLogsView(){
        ViewManager.INSTANCE.switchDashboard(FXMLPath.LOGS_DASHBOARD, "Logs");
    }
}
