package dk.easv.belmanexam.ui.controllers.operator.dashboards;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageDisplayDashboardController {
    private Image current_photo;
    private DocumentationCreationDashboardController parentController;

    @FXML
    private ImageView imgViewSelectedPhoto;


    public void setDetails(Image image) {
        current_photo = image;
        imgViewSelectedPhoto.setImage(image);
    }

    @FXML
    public void showParentView(){
        ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD, "Photo Documentation");
    }
}
