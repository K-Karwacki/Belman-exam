package dk.easv.belmanexam.ui.controllers.main;

import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.dashboards.admin.UsersDashboardController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import javax.swing.text.View;

public class AdminViewController {
  @FXML private BorderPane root;


  public void initialize(){
    ViewManager.INSTANCE.setStageRoot(root);
    ViewManager.INSTANCE.switchDashboard(FXMLPath.USERS_DASHBOARD, "Users");
  }
}
