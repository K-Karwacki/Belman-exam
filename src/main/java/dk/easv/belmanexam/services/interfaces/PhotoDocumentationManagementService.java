package dk.easv.belmanexam.services.interfaces;

import dk.easv.belmanexam.exceptions.PhotoException;
import dk.easv.belmanexam.entities.*;
import dk.easv.belmanexam.ui.models.LogListModel;
import dk.easv.belmanexam.ui.models.PhotoDocumentationListModel;
import javafx.scene.image.Image;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

public interface PhotoDocumentationManagementService {
    Collection<PhotoDocumentation> getAll();
    PhotoDocumentationListModel getPhotoDocumentationListModel();
    void addLog(User user, PhotoDocumentation photoDocumentation);
    void update(PhotoDocumentation photoDocumentation);
    Optional<PhotoDocumentation> getById(long id);
    Optional<PhotoDocumentation> getByOrderNumber(String orderNumber);
    void add(PhotoDocumentation photoDocumentation);
    LogListModel getLogListModel();
    void addPhoto(byte[] data, long documentation_id, String side, String info);
    Collection<Photo> getAllImagesByDocumentationId(long id);
    void refresh();
}
