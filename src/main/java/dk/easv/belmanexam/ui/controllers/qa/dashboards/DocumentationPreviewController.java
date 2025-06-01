package dk.easv.belmanexam.ui.controllers.qa.dashboards;

import dk.easv.belmanexam.auth.UserSession;
import dk.easv.belmanexam.entities.Photo;
import dk.easv.belmanexam.entities.PhotoDocumentation;
import dk.easv.belmanexam.services.EmailSenderService;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.services.utils.Status;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.utils.ImageConverter;
import dk.easv.belmanexam.utils.PDFGenerator;
import dk.easv.belmanexam.utils.PdfFile;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.geometry.Insets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class DocumentationPreviewController {
    @FXML
    private FlowPane flowPanePDFContainer;

    @FXML
    private TextField txtFieldEmail;

    @FXML
    private HBox btnSendEmail;

    @FXML
    private Label lblSend;

    private PhotoDocumentationManagementService photoDocumentationManagementService;
    private EmailSenderService emailSenderService;

    private List<PdfFile> pdfFiles;
    private PhotoDocumentation photoDocumentation;
    private HashMap<String, Image> imageList;
    private String orderNumber;
    private String comment;

    public DocumentationPreviewController() {
        pdfFiles = new ArrayList<>();
    }

    public void onClickGoBack() {
        ViewManager.INSTANCE.switchDashboard(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD, "BelSign");
    }

    public void setDetails(Collection<Photo> photos, String orderNumber, String comment, PhotoDocumentation photoDocumentation) throws IOException {
        this.photoDocumentation = photoDocumentation;
        this.orderNumber = orderNumber;
        this.comment = comment;
        btnSendEmail.setDisable(false);
        lblSend.setText("Send");

        String approvedBy = "Approved by: " + UserSession.INSTANCE.getLoggedUser().getFirstName() + " " + UserSession.INSTANCE.getLoggedUser().getLastName();

        // Clear previous PDFs
        pdfFiles.clear();

        List<Photo> photoList = new ArrayList<>(photos);

        // Split photos into chunks of 4
        List<List<Photo>> photoChunks = splitPhotosIntoChunks(photoList, 4);

        // Create PDF for each chunk of 4 photos
        for (int i = 0; i < photoChunks.size(); i++) {
            List<Photo> chunk = photoChunks.get(i);

            PdfFile pdfFile = PDFGenerator.createPdf(orderNumber, approvedBy, chunk, (i + 1) + "/" + photoChunks.size());
            pdfFiles.add(pdfFile);
        }

        // Display all PDFs in separate ImageViews
        displayPdfsInFlowPane();
    }

    private List<List<Photo>> splitPhotosIntoChunks(List<Photo> photos, int chunkSize) {
        List<List<Photo>> chunks = new ArrayList<>();
        for (int i = 0; i < photos.size(); i += chunkSize) {
            int endIndex = Math.min(i + chunkSize, photos.size());
            chunks.add(photos.subList(i, endIndex));
        }
        return chunks;
    }

    private void displayPdfsInFlowPane() throws IOException {
        if (pdfFiles.isEmpty()) return;

        // Clear any existing content
        flowPanePDFContainer.getChildren().clear();

        // Create an ImageView for each PDF
        for (int i = 0; i < pdfFiles.size(); i++) {
            PdfFile pdfFile = pdfFiles.get(i);

            // Convert PDF to image
            Image pdfImage = ImageConverter.convertPdfToImage(pdfFile.getByteData());

            // Create ImageView for this PDF
            ImageView imageView = new ImageView(pdfImage);
            imageView.setFitWidth(950);
            imageView.setFitHeight(1900);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);

            // Add margin between PDFs (bottom margin for vertical spacing)
            FlowPane.setMargin(imageView, new Insets(0, 0, 20, 0));

            // Add to FlowPane
            flowPanePDFContainer.getChildren().add(imageView);
        }
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
            if (pdfFiles.size() == 1) {
                // Single PDF download
                downloadSinglePdf();
            } else {
                // Multiple PDFs download
                downloadMultiplePdfs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickSendEmail() throws IOException {
        String email = txtFieldEmail.getText();
        List<File> files = new ArrayList<>();
        for (PdfFile pdfFile : pdfFiles) {
            files.add(pdfFile.getFile());
        }
        if(emailSenderService.sendReport(email, files, "Report")){
            btnSendEmail.setDisable(true);
            lblSend.setText("Sent");
        }
    }

    private void downloadSinglePdf() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.setInitialFileName(orderNumber + ".pdf");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );

        File destination = fileChooser.showSaveDialog(flowPanePDFContainer.getScene().getWindow());
        if (destination != null) {
            Files.copy(pdfFiles.get(0).getFile().toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void downloadMultiplePdfs() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory to Save PDFs");

        File selectedDirectory = directoryChooser.showDialog(flowPanePDFContainer.getScene().getWindow());
        if (selectedDirectory != null) {
            for (int i = 0; i < pdfFiles.size(); i++) {
                PdfFile pdfFile = pdfFiles.get(i);
                String safeOrderNumber = orderNumber.replace("/", "");
                String fileName = safeOrderNumber + "_Part_" + (i + 1) + ".pdf";
                File destination = new File(selectedDirectory, fileName);

                // Create parent directories if they don't exist (still needed for selectedDirectory)
                Files.createDirectories(destination.getParentFile().toPath());

                Files.copy(pdfFile.getFile().toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public void setServices(PhotoDocumentationManagementService photoDocumentationManagementService, EmailSenderService emailSenderService) {
        this.photoDocumentationManagementService = photoDocumentationManagementService;
        this.emailSenderService = emailSenderService;
    }
}