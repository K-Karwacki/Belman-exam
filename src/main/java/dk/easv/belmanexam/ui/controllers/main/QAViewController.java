package dk.easv.belmanexam.ui.controllers.main;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class QAViewController
{
    @FXML
    private BorderPane root;


    public void initialize(){
        ViewManager.INSTANCE.setStageRoot(root);
        ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_DASHBOARD, "BelSign");
    }
}
