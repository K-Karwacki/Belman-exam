package dk.easv.belmanexam.ui.controllers.main;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginViewController {
    @FXML
    private TextField textFieldPassword;
    @FXML
    private TextField textFieldEmail;

    private final ViewManager viewManager = ViewManager.INSTANCE;
    public void onClickLogin(ActionEvent actionEvent) {
        if(textFieldEmail.getText().equals("qa")){
            goToQAPage();
        }
        else if(textFieldEmail.getText().equals("admin")){
            goToAdminPage();
        }
        else if(textFieldEmail.getText().equals("operator")){
            goToOperatorPage();
        }
    }
    private void goToOperatorPage(){
        viewManager.showStage(FXMLPath.OPERATOR_VIEW, "BelSign", true);
    }
    private void goToAdminPage(){
        viewManager.showStage(FXMLPath.ADMIN_VIEW, "BelSign", true);
    }
    private void goToQAPage(){
        viewManager.showStage(FXMLPath.QUALITY_ASSURANCE_VIEW, "BelSign", true);
    }

}
