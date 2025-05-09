package dk.easv.belmanexam.ui.controllers.admin.dashboards;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;

public class UsersDashboardController {

    public void switchToNewUser()
    {
        ViewManager.INSTANCE.showPopup(FXMLPath.USER_CREATOR_POPUP, "Create New User");
    }

    public void openUserEditor() {
        ViewManager.INSTANCE.showPopup(FXMLPath.USER_EDITOR_POPUP, "Edit User");
    }
}
