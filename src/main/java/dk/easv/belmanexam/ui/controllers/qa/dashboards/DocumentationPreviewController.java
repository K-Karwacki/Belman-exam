package dk.easv.belmanexam.ui.controllers.qa.dashboards;

import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.utils.ImageConverter;
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
    @FXML
    private ImageView imgViewPDFContainer;
    private List<Image> imageList;
    private String orderNumber;
    private String comment;


    public DocumentationPreviewController() {}

    public void onClickGoBack(MouseEvent mouseEvent) {
        ViewManager.INSTANCE.switchDashboard(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD, "BelSign");
    }

    public void setDetails(List<Image> imageList, String orderNumber, String comment) throws IOException {
        this.imageList = imageList;
        this.orderNumber = orderNumber;
        this.comment = comment;
        byte[] byteArray = PDFGenerator.createPdf(orderNumber, "", imageList);
        Image image = ImageConverter.convertPdfToImage(byteArray);
        imgViewPDFContainer.setImage(image);
    }


    @FXML
    private void onClickConfirm(MouseEvent mouseEvent) {
    }
}
