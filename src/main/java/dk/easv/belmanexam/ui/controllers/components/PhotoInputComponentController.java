package dk.easv.belmanexam.ui.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.File;

public class PhotoInputComponentController {

    private File file;

    private String side;

    @FXML
    private HBox hboxButtonsContainer;

    @FXML
    private StackPane stackPanePhotoContainer;

    @FXML
    private Label lblSide;

    public void setSideLabel(String sideLabel) {
        this.side = sideLabel;
        lblSide.setText(sideLabel);
    }

    @FXML
    private void onClickUploadPhotoFromDevice() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(lblSide.getScene().getWindow());
        if (selectedFile != null) {
            this.file = selectedFile;
            Image image = new Image(selectedFile.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(150);
            stackPanePhotoContainer.getChildren().add(imageView);
            hboxButtonsContainer.setManaged(false);
            hboxButtonsContainer.setVisible(false);
        }
    }

    @FXML
    private void onClickTakePhoto(){

    }

    public File getFile(){
        return file;
    }

    public String getSide(){
        return side;
    }

}
