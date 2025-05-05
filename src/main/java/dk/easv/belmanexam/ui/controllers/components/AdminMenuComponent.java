package dk.easv.belmanexam.ui.controllers.components;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.event.ActionEvent;

public class AdminMenuComponent
{
  public void switchToLogs(ActionEvent actionEvent)
  {
    ViewManager.INSTANCE.switchDashboard(FXMLPath.LOGS_DASHBOARD, "Logs");
  }

  public void switchToUsers(ActionEvent actionEvent)
  {
    ViewManager.INSTANCE.switchDashboard(FXMLPath.USERS_DASHBOARD, "Users");
  }
}
