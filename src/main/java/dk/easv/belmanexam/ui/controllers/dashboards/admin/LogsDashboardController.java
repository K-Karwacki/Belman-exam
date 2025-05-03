package dk.easv.belmanexam.ui.controllers.dashboards.admin;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;

public class LogsDashboardController {
    @FXML
    private void onClickShowUsersView(){
        ViewManager.INSTANCE.switchDashboard(FXMLPath.USERS_DASHBOARD, "Users");
    }
}
