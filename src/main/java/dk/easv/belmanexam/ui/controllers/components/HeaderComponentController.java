package dk.easv.belmanexam.ui.controllers.components;

import dk.easv.belmanexam.auth.UserSession;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HeaderComponentController {

    @FXML private Label usernameLabel;

    public void initialize(){
        usernameLabel.setText(UserSession.INSTANCE.getLoggedUser().getFirstName() + " " + UserSession.INSTANCE.getLoggedUser().getLastName() );

    }
    @FXML
    private void onClickLogout() {
        ViewManager.INSTANCE.showStage(FXMLPath.LOGIN_VIEW, "Login", false);
    }
}
