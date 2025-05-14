package dk.easv.belmanexam.ui.controllers.qa.dashboards;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.util.List;

public class DocumentationPreviewController {
    @FXML private ScrollPane previewContainerPane;
    private List<Image> imageList;


    public DocumentationPreviewController() {}

    public void onClickGoBack(MouseEvent mouseEvent) {
        ViewManager.INSTANCE.switchDashboard(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD, "BelSign");
    }

    public void onClickConfirm(MouseEvent mouseEvent) {
    }

    public void setDetails(List<Image> imageList){
        this.imageList = imageList;
        updateImagePreview();
    }

    public void updateImagePreview(){
        if(imageList == null || imageList.isEmpty()) {
            return;
        }

        VBox pageContainer = new VBox();
        pageContainer.setSpacing(20);

        for (Image image: imageList) {
            AnchorPane a4page = createA4Page(image);
            pageContainer.getChildren().add(a4page);
        }

        previewContainerPane.setContent(pageContainer);
    }

    private AnchorPane createA4Page(Image image) {
        AnchorPane anchor = new AnchorPane();
        anchor.setPrefHeight(950);
        anchor.setPrefWidth(1334.369748);

        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);

        // Fit the image *inside* the A4 pane without overflow
        imageView.setFitWidth(950);
        imageView.setFitHeight(1344.369748);

        // Center image inside anchor
        AnchorPane.setTopAnchor(imageView, 0.0);
        AnchorPane.setLeftAnchor(imageView, 0.0);
        AnchorPane.setRightAnchor(imageView, 0.0);
        AnchorPane.setBottomAnchor(imageView, 0.0);

        anchor.getChildren().add(imageView);
        return anchor;
    }

}
