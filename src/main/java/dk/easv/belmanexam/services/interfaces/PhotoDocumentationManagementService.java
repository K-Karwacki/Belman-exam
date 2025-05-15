package dk.easv.belmanexam.services.interfaces;

import dk.easv.belmanexam.exceptions.PhotoException;
import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.ui.models.PhotoDocumentationListModel;
import javafx.scene.image.Image;

import java.util.Collection;
import java.util.List;

public interface PhotoDocumentationManagementService {
    void saveFileInFolder(java.io.File file, String folder) throws PhotoException;
    List<Image> getAllImagesByOrderNumber(String orderNumber) throws PhotoException;
    Collection<PhotoDocumentation> getAll();
    PhotoDocumentationListModel getPhotoDocumentationListModel();

    void update(PhotoDocumentation photoDocumentation);

    void add(PhotoDocumentation photoDocumentation);
}
