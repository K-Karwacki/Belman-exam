package dk.easv.belmanexam.ui.controllers.components;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import javax.swing.text.View;

public class HeaderComponentController {

    public void initialize(){

    }
    @FXML
    private void onClickLogout() {
        ViewManager.INSTANCE.showStage(FXMLPath.LOGIN_VIEW, "Login", false);
    }
}
