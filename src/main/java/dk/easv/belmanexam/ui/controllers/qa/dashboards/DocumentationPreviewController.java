package dk.easv.belmanexam.ui.controllers.qa.dashboards;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.utils.PDFGenerator;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DocumentationPreviewController {
    @FXML private ScrollPane previewContainerPane;
    private List<Image> imageList;
    private String orderNumber;
    private String comment;


    public DocumentationPreviewController() {}

    public void onClickGoBack(MouseEvent mouseEvent) {
        ViewManager.INSTANCE.switchDashboard(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD, "BelSign");
    }

    public void onClickConfirm(MouseEvent mouseEvent) {
        if(imageList != null && !imageList.isEmpty()) return;

        // Prompt User to choose where to save File
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setInitialFileName("documentation_" + orderNumber + ".pdf");

        File outputFile = fileChooser.showSaveDialog(previewContainerPane.getScene().getWindow());
        if(outputFile == null) return;

        try
        {
            // Convert Image to a temporary file list
            List<File> imageFiles = convertImagesToTempFiles(imageList);

            // Generate a pdf
            PDFGenerator.createPdf("Order #" + orderNumber, comment, imageFiles, outputFile.getAbsolutePath());

            System.out.println("PDF created at: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<File> convertImagesToTempFiles(List<Image> images) throws IOException {
        List<File> tempFiles = new java.util.ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            Image fxImage = images.get(i);
            BufferedImage bImage = SwingFXUtils.fromFXImage(fxImage, null);
            File tempFile = File.createTempFile("img_" + i, ".png");
            ImageIO.write(bImage, "png", tempFile);
            tempFiles.add(tempFile);
        }
        return tempFiles;
    }


    public void setDetails(List<Image> imageList, String orderNumber, String comment){
        this.imageList = imageList;
        this.orderNumber = orderNumber;
        this.comment = comment;
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
