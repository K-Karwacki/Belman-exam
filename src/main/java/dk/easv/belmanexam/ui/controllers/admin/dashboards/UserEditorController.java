package dk.easv.belmanexam.ui.controllers.admin.dashboards;

import dk.easv.belmanexam.model.UserModel;
import dk.easv.belmanexam.services.interfaces.UserManagementService;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import static dk.easv.belmanexam.ui.FXMLPath.USER_EDITOR_POPUP;


public class UserEditorController {
    protected UserManagementService userManagementService;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;


    @FXML
    private void initialize() {
        roleComboBox.getItems().addAll("Operator", "Quality Assurance", "Admin");
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
        this.roleComboBox.getSelectionModel().select(userModel.getRole());
    }

    public void setServices(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @FXML
    private void submitButton() {

        userModel.setFirstName(firstNameField.getText());
        userModel.setLastName(lastNameField.getText());
        userModel.setEmail(emailField.getText());
        String selectedRole = roleComboBox.getSelectionModel().getSelectedItem();
        if (selectedRole != null) {
            userModel.setRole(selectedRole);
        }


        userManagementService.updateUser(userModel);


        new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Platform.runLater(() -> {
                        ViewManager.INSTANCE.hidePopup(USER_EDITOR_POPUP);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }