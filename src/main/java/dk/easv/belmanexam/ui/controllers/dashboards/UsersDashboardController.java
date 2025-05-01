package dk.easv.belmanexam.ui.controllers.dashboards;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class UsersDashboardController {
    @FXML
    private void onClickShowLogsView(){
        ViewManager.INSTANCE.switchDashboard(FXMLPath.LOGS_DASHBOARD, "Logs");
    }
}
