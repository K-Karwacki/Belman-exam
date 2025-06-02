package dk.easv.belmanexam.ui.controllers.admin.dashboards;

import dk.easv.belmanexam.ui.models.UserModel;
import dk.easv.belmanexam.services.interfaces.UserManagementService;
import dk.easv.belmanexam.services.utils.RoleType;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static dk.easv.belmanexam.ui.FXMLPath.USER_EDITOR_POPUP;


public class UserEditorController {
    protected UserManagementService userManagementService;

    @FXML
    private ComboBox<RoleType> roleComboBox;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private Label firstNameErrorLabel, lastNameErrorLabel, emailErrorLabel, roleErrorLabel;


    @FXML
    private void initialize() {
        roleComboBox.getItems().addAll(RoleType.ADMIN, RoleType.OPERATOR, RoleType.QA);
    }

    private UserModel userModel;

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
        setFields();
    }

    private void setFields() {
        this.firstNameField.setText(userModel.getFirstName());
        this.lastNameField.setText(userModel.getLastName());
        this.emailField.setText(userModel.getEmail());
        this.roleComboBox.getSelectionModel().select(RoleType.fromString(userModel.getRole()));
    }

    public void setServices(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    private boolean validateInput() {
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

        if (roleComboBox.getSelectionModel().isEmpty()) {
            roleErrorLabel.setText("Role must be selected");
            isValid = false;
        } else {
            roleErrorLabel.setText("");
        }

        return isValid;
    }


    @FXML
    private void submitButton() {
        if (!validateInput()) return;

        userModel.setFirstName(firstNameField.getText());
        userModel.setLastName(lastNameField.getText());
        userModel.setEmail(emailField.getText());

        String selectedRole = roleComboBox.getSelectionModel().getSelectedItem().name();
        if (selectedRole != null) {
            userModel.setRole(selectedRole);
            userManagementService.updateUser(userModel);

            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Platform.runLater(() -> ViewManager.INSTANCE.hidePopup(USER_EDITOR_POPUP));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
