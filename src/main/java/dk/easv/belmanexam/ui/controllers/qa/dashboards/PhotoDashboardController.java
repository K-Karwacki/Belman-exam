package dk.easv.belmanexam.ui.controllers.qa.dashboards;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PhotoDashboardController {

    @FXML
    private ImageView imgViewPhoto;

    @FXML
    private void showParentView(){
        ViewManager.INSTANCE.switchDashboard(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD, "BelSign");
    }

    public void setPhoto(Image photo) {
        imgViewPhoto.setImage(photo);
    }
}

