package dk.easv.belmanexam.ui.controllers.admin.dashboards;

import dk.easv.belmanexam.ui.models.UserModel;
import dk.easv.belmanexam.services.interfaces.UserManagementService;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.components.UserCardComponentController;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;

import static dk.easv.belmanexam.ui.FXMLPath.USER_CARD_COMPONENT;

public class UsersDashboardController {
    private UserManagementService userManagementService;

    @FXML
    private FlowPane userListRoot;

    @FXML
    private void initialize(){
    }

    public void setServices(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;

        ObservableSet<UserModel> userModelObservableList = userManagementService.getUserListModel().getUsersObservable();

        userModelObservableList.addListener((SetChangeListener<UserModel>) change ->{
            if(change.wasAdded() || change.wasRemoved()){
                loadUserCards();
            }
        });
        loadUserCards();
    }

    @FXML
    private void onClickOpenCreateUser(){
        ViewManager.INSTANCE.showPopup(FXMLPath.USER_CREATOR_POPUP, "Create new user");
        UserCreatorController userCreatorController = (UserCreatorController) FXMLManager.INSTANCE.getFXML(FXMLPath.USER_CREATOR_POPUP).getValue();
        userCreatorController.setServices(userManagementService);
    }

    public void loadUserCards(){
        if(userManagementService.getUserListModel().getUsersObservable().isEmpty()){
            userListRoot.getChildren().add(new Label("No users to show"));
        }

        userListRoot.getChildren().clear();

        userManagementService.getUserListModel().getUsersObservable().forEach(userModel -> {
            Pair<Parent, UserCardComponentController> p = FXMLManager.INSTANCE.loadFXML(USER_CARD_COMPONENT);
            p.getValue().setUserModel(userModel);
            p.getValue().setServices(userManagementService);
            userListRoot.getChildren().add(p.getKey());
        });
    }
}
