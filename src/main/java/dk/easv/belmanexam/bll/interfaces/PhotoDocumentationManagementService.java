package dk.easv.belmanexam.bll.interfaces;

import dk.easv.belmanexam.exceptions.PhotoException;
import dk.easv.belmanexam.ui.models.PhotoDocumentationListModel;
import javafx.scene.image.Image;

import java.util.List;

public interface PhotoDocumentationManagementService {
    void saveFileInFolder(java.io.File file, String folder) throws PhotoException;
    List<Image> getAllImagesByOrderNumber(String orderNumber) throws PhotoException;
    PhotoDocumentationListModel getPhotoDocumentationListModel();
}
