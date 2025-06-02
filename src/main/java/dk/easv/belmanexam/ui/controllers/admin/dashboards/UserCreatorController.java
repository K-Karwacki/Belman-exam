package dk.easv.belmanexam.ui.controllers.admin.dashboards;

import dk.easv.belmanexam.ui.models.UserModel;
import dk.easv.belmanexam.services.interfaces.UserManagementService;
import dk.easv.belmanexam.services.utils.RoleType;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserCreatorController {
    protected static UserManagementService userManagementService;

    @FXML
    private TextField firstNameField, lastNameField, emailField;

    @FXML
    private ComboBox<RoleType> roleComboBox;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label firstNameErrorLabel, lastNameErrorLabel, emailErrorLabel, passwordErrorLabel, roleErrorLabel;


    @FXML
    private void initialize() {
        roleComboBox.getItems().addAll(RoleType.ADMIN, RoleType.OPERATOR, RoleType.QA);
    }

    public void submitButton() {
        if (!validateUserInput()) return;

        UserModel createdUser = userManagementService.createUser(
                firstNameField.getText(),
                lastNameField.getText(),
                roleComboBox.getSelectionModel().getSelectedItem().name(),
                emailField.getText(),
                passwordField.getText());

        if (createdUser != null) {
            clearFields();
            ViewManager.INSTANCE.hidePopup(FXMLPath.USER_CREATOR_POPUP);
        }
    }

    private boolean validateUserInput() {
        boolean isValid = true;

        if (firstNameField.getText().isEmpty()) {
            firstNameErrorLabel.setText("First name is required");
            isValid = false;
        } else {
            firstNameErrorLabel.setText("");
        }

        if (lastNameField.getText().isEmpty()) {
            lastNameErrorLabel.setText("Last name is required");
            isValid = false;
        } else {
            lastNameErrorLabel.setText("");
        }

        if (emailField.getText().isEmpty()) {
            emailErrorLabel.setText("Email is required");
            isValid = false;
        } else {
            emailErrorLabel.setText("");
        }

        if (passwordField.getText().isEmpty()) {
            passwordErrorLabel.setText("Password is required");
            isValid = false;
        } else {
            passwordErrorLabel.setText("");
        }

        if (roleComboBox.getSelectionModel().isEmpty()) {
            roleErrorLabel.setText("Role must be selected");
            isValid = false;
        } else {
            roleErrorLabel.setText("");
        }

        return isValid;
    }

    public void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        roleComboBox.getSelectionModel().clearSelection();
        passwordField.clear();

    }

    public void setServices(UserManagementService userManagementService){
        UserCreatorController.userManagementService = userManagementService;
    }
}

