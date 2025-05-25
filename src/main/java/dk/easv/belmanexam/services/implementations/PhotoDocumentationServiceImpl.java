package dk.easv.belmanexam.services.implementations;

import com.google.api.services.drive.model.File;
import dk.easv.belmanexam.auth.UserSession;
import dk.easv.belmanexam.model.*;
import dk.easv.belmanexam.services.factories.RepositoryService;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.repositories.utils.GoogleDriveManager;
import dk.easv.belmanexam.repositories.interfaces.PhotoDocumentationRepository;
import dk.easv.belmanexam.exceptions.PhotoException;
import dk.easv.belmanexam.ui.models.LogListModel;
import dk.easv.belmanexam.ui.models.PhotoDocumentationListModel;
import dk.easv.belmanexam.utils.ImageConverter;
import javafx.scene.image.Image;

import java.util.*;

public class PhotoDocumentationServiceImpl implements PhotoDocumentationManagementService {
    private final RepositoryService repositoryService;
    private final PhotoDocumentationRepository photoDocumentationRepository;
    private final PhotoDocumentationListModel photoDocumentationListModel;
    private final LogListModel logListModel;
    private final GoogleDriveManager googleDriveManager = new GoogleDriveManager();

    public PhotoDocumentationServiceImpl(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
        this.photoDocumentationRepository = repositoryService.getRepository(PhotoDocumentationRepository.class);
        this.photoDocumentationListModel = new PhotoDocumentationListModel();
        this.logListModel = new LogListModel();
        initialize();
    }

    public HashMap<String, Image> getAllImagesByOrderNumber(String orderNumber) throws PhotoException {
        HashMap<String, Image> images = new HashMap<>();
        HashMap<String, String> folders = googleDriveManager.listFoldersInFolder(orderNumber);
        for(Map.Entry<String, String> folder : folders.entrySet()) {
            File file = googleDriveManager.listFilesInFolder(folder.getKey()).get(0);
            String side = folders.get(folder.getKey());
            byte[] fileData = googleDriveManager.downloadFileContent(file.getId());
            Image image = ImageConverter.convertToImage(fileData);
            System.out.println(folder);
            images.put(side, image);
        }
        return images;
    }
    public Collection<PhotoDocumentation> getAll(){
        return photoDocumentationRepository.getAll();
    }

    public void saveFileInFolder(java.io.File file, String folderId) throws PhotoException {
        googleDriveManager.saveFileInFolder(file,folderId);
    }
    public String createFolderInFolder(String folderName, String parentFolderId) throws PhotoException {
        return googleDriveManager.createFolderInFolder(folderName, parentFolderId);
    }
    public String createFolder(String folderName) throws PhotoException {
        return googleDriveManager.createFolder(folderName);
    }


    private void initialize(){
        if (photoDocumentationRepository == null || photoDocumentationListModel == null || repositoryService == null || logListModel == null) {
            throw new RuntimeException("Failed to load dependencies for PhotoDocumentationService");
        }
        photoDocumentationListModel.setDocumentation(photoDocumentationRepository.getAll());
        logListModel.setLogs(photoDocumentationRepository.getAllLogs());
    }

    public PhotoDocumentationListModel getPhotoDocumentationListModel() {
        return photoDocumentationListModel;
    }

    @Override
    public void update(PhotoDocumentation photoDocumentation) {
        photoDocumentationRepository.update(photoDocumentation);
        photoDocumentationListModel.update(photoDocumentation);
        addLog(UserSession.INSTANCE.getLoggedUser(), photoDocumentation);
    }

    @Override
    public void add(PhotoDocumentation photoDocumentation) {
        photoDocumentationRepository.add(photoDocumentation);
        photoDocumentationListModel.add(photoDocumentation);
        addLog(UserSession.INSTANCE.getLoggedUser(), photoDocumentation);
    }

    @Override
    public LogListModel getLogListModel() {
        return logListModel;
    }

    @Override
    public void addLog(User user, PhotoDocumentation photoDocumentation){
        Log log = photoDocumentationRepository.addLog(user, photoDocumentation);
        logListModel.add(log);
    }
}
