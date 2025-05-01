package dk.easv.belmanexam.ui.controllers.main;

import dk.easv.belmanexam.Main;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class LoginViewController {
    @FXML
    private TextField textFieldPassword;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private TextField textFieldEmail;

    @FXML private ImageView imgEyeIcon;

    private boolean isPasswordVisible = false;
    private final Image VISIBLE_ICON = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/visible-icon.png")));
    private final Image INVISIBLE_ICON = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/invisible-icon.png")));

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

    @FXML
    private void togglePasswordVisibility() {
        if (!isPasswordVisible) {
            textFieldPassword.setText(passwordFieldPassword.getText());
            textFieldPassword.setVisible(true);
            textFieldPassword.setManaged(true);
            passwordFieldPassword.setVisible(false);
            passwordFieldPassword.setManaged(false);
            imgEyeIcon.setImage(INVISIBLE_ICON);
            isPasswordVisible = true;
        } else {
            passwordFieldPassword.setText(textFieldPassword.getText());
            passwordFieldPassword.setVisible(true);
            passwordFieldPassword.setManaged(true);
            textFieldPassword.setVisible(false);
            textFieldPassword.setManaged(false);
            imgEyeIcon.setImage(VISIBLE_ICON);
            isPasswordVisible = false;
        }
    }
}
