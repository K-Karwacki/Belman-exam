package dk.easv.belmanexam.ui.controllers.components;



import dk.easv.belmanexam.model.UserModel;
import dk.easv.belmanexam.services.interfaces.UserManagementService;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.admin.dashboards.UserEditorController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.util.Pair;

import static dk.easv.belmanexam.ui.FXMLPath.USER_EDITOR_POPUP;


public class UserCardComponentController
{

    private static UserManagementService userManagementService;

    @FXML
    private Label txtUserFirstName;
    @FXML
    private Label txtUserLastName;
    @FXML
    private Label txtUserEmail;
    @FXML
    private Label txtUserRole;
    private UserModel userModel;


    public void setUserModel(UserModel userModel)
    {
        this.userModel = userModel;
        this.txtUserEmail.textProperty().bind(userModel.emailProperty());
        this.txtUserRole.textProperty().bind(userModel.roleProperty().asString());
        this.txtUserFirstName.textProperty().bind(userModel.firstNameProperty());
        this.txtUserLastName.textProperty().bind(userModel.lastNameProperty());

    }

    @FXML
    private void onClickEditUser()
    {
        Pair<Parent, UserEditorController> p = FXMLManager.INSTANCE.getFXML(USER_EDITOR_POPUP);
        p.getValue().setUserModel(userModel);
        ViewManager.INSTANCE.showPopup(USER_EDITOR_POPUP, "User Editor");
    }

    @FXML
    private void onClickDeleteUser()
    {
        userManagementService.deleteUser(this.userModel);
    }

    public void setServices(UserManagementService userManagementService) {
        UserCardComponentController.userManagementService = userManagementService;
    }

}