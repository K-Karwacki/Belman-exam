package dk.easv.belmanexam.services.interfaces;

import dk.easv.belmanexam.exceptions.PhotoException;
import dk.easv.belmanexam.model.Log;
import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.model.User;
import dk.easv.belmanexam.model.UserModel;
import dk.easv.belmanexam.ui.models.LogListModel;
import dk.easv.belmanexam.ui.models.PhotoDocumentationListModel;
import javafx.scene.image.Image;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface PhotoDocumentationManagementService {
    void saveFileInFolder(java.io.File file, String folder) throws PhotoException;
    String createFolder(String folderName) throws PhotoException;
    String createFolderInFolder(String folderName, String parentFolderId) throws PhotoException;
    HashMap<String, Image> getAllImagesByOrderNumber(String orderNumber) throws PhotoException;
    Collection<PhotoDocumentation> getAll();
    PhotoDocumentationListModel getPhotoDocumentationListModel();
    void addLog(User user, PhotoDocumentation photoDocumentation);
    void update(PhotoDocumentation photoDocumentation);

    void add(PhotoDocumentation photoDocumentation);

    LogListModel getLogListModel();
}
