package dk.easv.belmanexam.ui.controllers.qa.dashboards;

import dk.easv.belmanexam.auth.UserSession;
import dk.easv.belmanexam.entities.Photo;
import dk.easv.belmanexam.entities.PhotoDocumentation;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.services.utils.Status;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.utils.ImageConverter;
import dk.easv.belmanexam.utils.PDFGenerator;
import dk.easv.belmanexam.utils.PdfFile;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;

public class DocumentationPreviewController {
    @FXML
    private ImageView imgViewPDFContainer;

    private PhotoDocumentationManagementService photoDocumentationManagementService;

    private PdfFile pdfFile;
    private PhotoDocumentation photoDocumentation;
    private HashMap<String, Image> imageList;
    private String orderNumber;
    private String comment;


    public DocumentationPreviewController() {}

    public void onClickGoBack() {
        ViewManager.INSTANCE.switchDashboard(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD, "BelSign");
    }

    public void setDetails(Collection<Photo> photos, String orderNumber, String comment, PhotoDocumentation photoDocumentation) throws IOException {
        this.photoDocumentation = photoDocumentation;
        this.orderNumber = orderNumber;
        this.comment = comment;
        String approvedBy = "Approved by: " + UserSession.INSTANCE.getLoggedUser().getFirstName() + " " + UserSession.INSTANCE.getLoggedUser().getLastName();
        pdfFile = PDFGenerator.createPdf(orderNumber, approvedBy, photos);
        Image image = ImageConverter.convertPdfToImage(pdfFile.getByteData());
        imgViewPDFContainer.setImage(image);
    }


    @FXML
    private void onClickConfirm() {
        photoDocumentation.setStatus(Status.APPROVED);
        photoDocumentationManagementService.update(photoDocumentation);
        ViewManager.INSTANCE.switchDashboard(FXMLPath.DOCUMENTATION_DASHBOARD, "BelSign");
    }

    @FXML
    private void onClickDownload(){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.setInitialFileName("report.pdf");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
            );

            File destination = fileChooser.showSaveDialog(imgViewPDFContainer.getScene().getWindow());
            if (destination != null) {
                Files.copy(pdfFile.getFile().toPath(), destination.toPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setServices(PhotoDocumentationManagementService photoDocumentationManagementService) {
        this.photoDocumentationManagementService = photoDocumentationManagementService;
    }
}
