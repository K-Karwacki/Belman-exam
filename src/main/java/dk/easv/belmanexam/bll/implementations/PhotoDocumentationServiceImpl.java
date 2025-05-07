package dk.easv.belmanexam.bll.implementations;

import com.google.api.services.drive.model.File;
import dk.easv.belmanexam.bll.factories.RepositoryService;
import dk.easv.belmanexam.bll.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.dal.GoogleDriveManager;
import dk.easv.belmanexam.dal.repositories.PhotoDocumentationRepository;
import dk.easv.belmanexam.exceptions.PhotoException;
import dk.easv.belmanexam.ui.models.PhotoDocumentationListModel;
import dk.easv.belmanexam.utils.ImageConverter;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class PhotoDocumentationServiceImpl implements PhotoDocumentationManagementService {
    private final RepositoryService repositoryService;
    private final PhotoDocumentationRepository photoDocumentationRepository;
    private final PhotoDocumentationListModel photoDocumentationListModel;
    private final GoogleDriveManager googleDriveManager = new GoogleDriveManager();

    public PhotoDocumentationServiceImpl(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
        this.photoDocumentationRepository = repositoryService.getRepository(PhotoDocumentationRepository.class);
        this.photoDocumentationListModel = new PhotoDocumentationListModel();
        initialize();
    }

    public List<Image> getAllImagesByOrderNumber(String orderNumber) throws PhotoException {
        List<Image> images = new ArrayList<>();
        List<File> cloudFiles = googleDriveManager.listFilesInFolder(orderNumber);
        List<byte[]> filesData = googleDriveManager.downloadFilesContent(cloudFiles);
        for(byte[] fileData: filesData){
            images.add(ImageConverter.convertToImage(fileData));
        }
        return images;
    }

    public void saveFileInFolder(java.io.File file, String folder) throws PhotoException {
        googleDriveManager.saveFileInFolder(file,folder);
    }


    private void initialize(){
        if (photoDocumentationRepository == null || photoDocumentationListModel == null || repositoryService == null) {
            throw new RuntimeException("Load dependencies for OrderManagementService");
        }
        photoDocumentationListModel.setDocumentation(photoDocumentationRepository.getAll());
    }

    public PhotoDocumentationListModel getPhotoDocumentationListModel() {
        return photoDocumentationListModel;
    }

}
