package dk.easv.belmanexam.ui.controllers.components;

import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.qa.dashboards.PhotoDashboardController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class PhotoOutputComponentController{
    @FXML
    private Label lblSide;

    @FXML
    private VBox vboxContainer;

    public void setSide(String side) {
        lblSide.setText(side);
    }
    public void setImage(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        vboxContainer.getChildren().add(imageView);

        imageView.setOnMouseClicked(event -> {
            Pair<Parent, PhotoDashboardController> p = FXMLManager.INSTANCE.getFXML(FXMLPath.PHOTO_DASHBOARD);
            p.getValue().setPhoto(image);
            ViewManager.INSTANCE.switchDashboard(FXMLPath.PHOTO_DASHBOARD, "BelSign");
        });
    }



}
