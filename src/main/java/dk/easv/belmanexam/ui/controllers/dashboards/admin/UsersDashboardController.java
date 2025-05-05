package dk.easv.belmanexam.ui.controllers.dashboards.admin;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class UsersDashboardController {

    public void switchToNewUser(ActionEvent actionEvent)
    {
        ViewManager.INSTANCE.switchDashboard(FXMLPath.ENTER_ORDER_DASHBOARD, "New user");
    }
}
