package dk.easv.belmanexam.ui.controllers.main;

import dk.easv.belmanexam.Main;
import dk.easv.belmanexam.auth.AuthService;
import dk.easv.belmanexam.auth.UserSession;
import dk.easv.belmanexam.services.utils.RoleType;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.operator.dashboards.PersonalDocumentationDashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.util.Objects;

import static javax.swing.JOptionPane.showMessageDialog;

public class LoginViewController {
    private AuthService authService;
    private UserSession userSession = UserSession.INSTANCE;

    @FXML private TextField textFieldPassword;
    @FXML private PasswordField passwordFieldPassword;
    @FXML private TextField textFieldEmail;
    @FXML private ImageView imgEyeIcon;
    @FXML private Label emailErrorLabel, passwordErrorLabel;

    private boolean isPasswordVisible = false;
    private final Image VISIBLE_ICON = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/visible-icon.png")));
    private final Image INVISIBLE_ICON = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/invisible-icon.png")));

    private final ViewManager viewManager = ViewManager.INSTANCE;
    public void onClickLogin(ActionEvent actionEvent) {
        String email = textFieldEmail.getText();
        String password = isPasswordVisible ? textFieldPassword.getText() : passwordFieldPassword.getText();

        blankMessageInput(email, password);

        if (email.isEmpty() || password.isEmpty()) {
            return;
        }

        if (authService.authenticateWithPassword(email, password)) {
            RoleType role = RoleType.fromString(userSession.getLoggedUser().getRole());
            System.out.println(userSession.getLoggedUser().getRole());
            switch(role){
                case QA -> goToQAPage();
                case ADMIN -> goToAdminPage();
                case OPERATOR -> goToOperatorPage();
                default -> throw new IllegalStateException("Unexpected value: " + role);
            }
        }
        else {
            showAlert("Login Failed", "The login failed. Please try again.\n");
        }

        /** Test Purposes **/
//        if(textFieldEmail.getText().equals("qa")){
//            goToQAPage();
//
//        } else if(textFieldEmail.getText().equals("admin")){
//            goToAdminPage();
//
//        } else if(textFieldEmail.getText().equals("operator")){
//            goToOperatorPage();
//        }
    }

    private void blankMessageInput(String email, String password) {
        if (email.isEmpty()) {
            emailErrorLabel.setText("Email is required");
        } else {
            emailErrorLabel.setText("");
        }

        if (password.isEmpty()) {
            passwordErrorLabel.setText("Password is required");
        } else {
            passwordErrorLabel.setText("");
        }
    }

    private void goToOperatorPage(){
        Pair<Parent, PersonalDocumentationDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.PERSONAL_DOCUMENTATION_DASHBOARD);
        p.getValue().addDocumentation();
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

    public void setServices(AuthService authService){
        this.authService = authService;
    }

    public void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
