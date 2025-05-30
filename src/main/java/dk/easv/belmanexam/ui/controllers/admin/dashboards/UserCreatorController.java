package dk.easv.belmanexam.ui.controllers.admin.dashboards;

import dk.easv.belmanexam.ui.models.UserModel;
import dk.easv.belmanexam.services.interfaces.UserManagementService;
import dk.easv.belmanexam.services.utils.RoleType;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
    private void initialize() {
        roleComboBox.getItems().addAll(RoleType.ADMIN, RoleType.OPERATOR, RoleType.QA);
    }

    public void submitButton() {
        UserModel createdUser = userManagementService.createUser(firstNameField.getText(), lastNameField.getText(), roleComboBox.getSelectionModel().getSelectedItem().name(), emailField.getText(), passwordField.getText());
        if(createdUser != null) {
            clearFields();
            ViewManager.INSTANCE.hidePopup(FXMLPath.USER_CREATOR_POPUP);
        }
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

