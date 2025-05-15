package dk.easv.belmanexam.ui.controllers.admin.dashboards;

import dk.easv.belmanexam.model.UserModel;
import dk.easv.belmanexam.services.interfaces.UserManagementService;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;


public class UserCreatorController {
    protected static UserManagementService userManagementService;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;


    @FXML
    private void initialize() {
        roleComboBox.getItems().addAll("Operator", "Quality Assurance", "Admin");
    }

    public void submitButton() {
        UserModel createdUser = userManagementService.createUser(firstNameField.getText(), lastNameField.getText(), roleComboBox.getSelectionModel().getSelectedItem(), emailField.getText());
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

