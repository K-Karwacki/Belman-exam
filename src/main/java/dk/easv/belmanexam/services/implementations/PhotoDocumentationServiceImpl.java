package dk.easv.belmanexam.services.implementations;

import dk.easv.belmanexam.auth.UserSession;
import dk.easv.belmanexam.entities.*;
import dk.easv.belmanexam.services.factories.RepositoryService;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.repositories.interfaces.PhotoDocumentationRepository;
import dk.easv.belmanexam.ui.models.LogListModel;
import dk.easv.belmanexam.ui.models.PhotoDocumentationListModel;

import java.util.*;

public class PhotoDocumentationServiceImpl implements PhotoDocumentationManagementService {
    private final RepositoryService repositoryService;
    private final PhotoDocumentationRepository photoDocumentationRepository;
    private final PhotoDocumentationListModel photoDocumentationListModel;
    private final LogListModel logListModel;

    public PhotoDocumentationServiceImpl(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
        this.photoDocumentationRepository = repositoryService.getRepository(PhotoDocumentationRepository.class);
        this.photoDocumentationListModel = new PhotoDocumentationListModel();
        this.logListModel = new LogListModel();
        initialize();
    }

    public Collection<PhotoDocumentation> getAll(){
        return photoDocumentationRepository.getAll();
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
    public Optional<PhotoDocumentation> getById(long id) {
        return photoDocumentationRepository.getById(id);
    }

    @Override
    public Optional<PhotoDocumentation> getByOrderNumber(String orderNumber) {
        return photoDocumentationRepository.getByOrderNumber(orderNumber);
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
    public void addPhoto(byte[] data, long documentation_id, String side, String info) {
        photoDocumentationRepository.addPhoto(data, documentation_id, side, info);
    }

    @Override
    public Collection<Photo> getAllImagesByDocumentationId(long id) {
        return photoDocumentationRepository.getAllImagesByDocumentationId(id);
    }


    @Override
    public void refresh() {
        photoDocumentationListModel.setDocumentation(photoDocumentationRepository.getAll());
    }

    @Override
    public void addLog(User user, PhotoDocumentation photoDocumentation){
        Log log = photoDocumentationRepository.addLog(user, photoDocumentation);
        logListModel.add(log);
    }

}
