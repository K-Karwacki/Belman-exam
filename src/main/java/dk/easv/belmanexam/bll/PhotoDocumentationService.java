package dk.easv.belmanexam.bll;

import com.google.api.services.drive.model.File;
import dk.easv.belmanexam.dal.GoogleDriveManager;
import dk.easv.belmanexam.exceptions.PhotoException;
import dk.easv.belmanexam.utils.ImageConverter;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class PhotoDocumentationService {
    private final GoogleDriveManager googleDriveManager = new GoogleDriveManager();

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

}
