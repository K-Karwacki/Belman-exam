package dk.easv.belmanexam.ui.controllers.admin;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminMenuComponent
{

  @FXML
  private Button btnUsers;

  @FXML
  private Button btnLogs;

  public void switchToLogs()
  {
    ViewManager.INSTANCE.switchDashboard(FXMLPath.LOGS_DASHBOARD, "Logs");
    updateButtonStyles(btnLogs);
  }

  public void switchToUsers()
  {
    ViewManager.INSTANCE.switchDashboard(FXMLPath.USERS_DASHBOARD, "Users");
    updateButtonStyles(btnUsers);
  }

  private void updateButtonStyles(Button activeButton) {
    Button[] buttons = {btnUsers, btnLogs};
    for (Button button : buttons) {
      button.getStyleClass().remove("active-menu-button");
      if (!button.getStyleClass().contains("disabled-menu-button")) {
        button.getStyleClass().add("disabled-menu-button");
      }
    }
    activeButton.getStyleClass().remove("disabled-menu-button");
    if (!activeButton.getStyleClass().contains("active-menu-button")) {
      activeButton.getStyleClass().add("active-menu-button");
    }
  }
}
