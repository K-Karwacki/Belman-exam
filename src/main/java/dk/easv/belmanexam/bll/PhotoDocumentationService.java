package dk.easv.belmanexam.bll;

import com.google.api.services.drive.model.File;
import dk.easv.belmanexam.dal.GoogleDriveManager;
import dk.easv.belmanexam.utils.ImageConverter;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class PhotoDocumentationService {
    private final GoogleDriveManager googleDriveManager = new GoogleDriveManager();

    public List<Image> getAllImagesByOrderNumber(String orderNumber) throws GeneralSecurityException, IOException {
        List<Image> images = new ArrayList<>();
        List<File> cloudFiles = googleDriveManager.listFilesInFolder(orderNumber);
        List<byte[]> filesData = googleDriveManager.downloadFilesContent(cloudFiles);
        for(byte[] fileData: filesData){
            images.add(ImageConverter.convertToImage(fileData));
        }
        return images;
    }

}
