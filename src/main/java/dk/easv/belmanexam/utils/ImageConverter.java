package dk.easv.belmanexam.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import javafx.scene.image.Image; // JavaFX Image
import javafx.embed.swing.SwingFXUtils; // For conversion
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageConverter {
    public static Image convertToImage(byte[] imageBytes) {
        try{
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage bufferedImage = ImageIO.read(bis);
            try{
                Metadata metadata = ImageMetadataReader.readMetadata(new ByteArrayInputStream(imageBytes));
                ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

                if (directory != null && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
                    int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
                    bis.close();
                    return SwingFXUtils.toFXImage(rotateImageAccordingToOrientation(bufferedImage, orientation), null);
                }

            }catch (IOException | MetadataException | ImageProcessingException e){
                e.printStackTrace();
            }
            // Convert BufferedImage to JavaFX Image
            return SwingFXUtils.toFXImage(bufferedImage, null);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] convertToByteArray(Image fxImage){
        if (fxImage == null) {
            throw new IllegalArgumentException("Image is null");
        }
        try {
            // Convert JavaFX Image to BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(fxImage, null);
            // Write BufferedImage to byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos); // Use "png" or "jpg" based on your needs
            baos.close();

            return baos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public static Image convertPdfToImage(byte[] pdfBytes) {
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            // Render the first page (index 0) to a BufferedImage
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(0, 300); // 300 DPI for good quality
            // Convert BufferedImage to JavaFX Image
            return SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static BufferedImage rotateImageAccordingToOrientation(BufferedImage image, int orientation) {
        // Orientation values per EXIF standard:
        // 1 = Normal
        // 3 = Rotate 180
        // 6 = Rotate 90 CW
        // 8 = Rotate 270 CW (or 90 CCW)
        switch (orientation) {
            case 3:
                return rotate(image, 180);
            case 6:
                return rotate(image, 90);
            case 8:
                return rotate(image, 270);
            default:
                return image;
        }
    }

    private static BufferedImage rotate(BufferedImage img, int angle) {
        // rotate the image by the given angle (90, 180, 270 degrees)
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage rotatedImage;
        if (angle == 90 || angle == 270) {
            rotatedImage = new BufferedImage(h, w, img.getType());
        } else {
            rotatedImage = new BufferedImage(w, h, img.getType());
        }

        Graphics2D g2d = rotatedImage.createGraphics();
        switch (angle) {
            case 90:
                g2d.translate(h, 0);
                g2d.rotate(Math.toRadians(90));
                break;
            case 180:
                g2d.translate(w, h);
                g2d.rotate(Math.toRadians(180));
                break;
            case 270:
                g2d.translate(0, w);
                g2d.rotate(Math.toRadians(270));
                break;
            default:
                break;
        }
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return rotatedImage;
    }
}