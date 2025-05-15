package dk.easv.belmanexam.ui.controllers.qa.dashboards;

import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.services.utils.Status;
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

    private PhotoDocumentationManagementService photoDocumentationManagementService;

    private PhotoDocumentation photoDocumentation;
    private List<Image> imageList;
    private String orderNumber;
    private String comment;


    public DocumentationPreviewController() {}

    public void onClickGoBack() {
        ViewManager.INSTANCE.switchDashboard(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD, "BelSign");
    }

    public void setDetails(List<Image> imageList, String orderNumber, String comment, PhotoDocumentation photoDocumentation) throws IOException {
        this.photoDocumentation = photoDocumentation;
        this.imageList = imageList;
        this.orderNumber = orderNumber;
        this.comment = comment;
        byte[] byteArray = PDFGenerator.createPdf(orderNumber, "", imageList);
        Image image = ImageConverter.convertPdfToImage(byteArray);
        imgViewPDFContainer.setImage(image);
    }


    @FXML
    private void onClickConfirm() {
        photoDocumentation.setStatus(Status.APPROVED);
        photoDocumentationManagementService.update(photoDocumentation);
        ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_DASHBOARD, "BelSign");
    }

    public void setServices(PhotoDocumentationManagementService photoDocumentationManagementService) {
        this.photoDocumentationManagementService = photoDocumentationManagementService;
    }
}
