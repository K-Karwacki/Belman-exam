package dk.easv.belmanexam.ui.controllers.dashboards;


import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;


public class NewDocumentationDashboardController {
    private final ViewManager viewManager = ViewManager.INSTANCE;

    private void goBackButton(){
        viewManager.showStage(FXMLPath.OPERATOR_VIEW, "BelSign", true);
    }
}
