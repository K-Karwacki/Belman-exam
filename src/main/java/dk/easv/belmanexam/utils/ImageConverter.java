package dk.easv.belmanexam.utils;

import javafx.scene.image.Image; // JavaFX Image
import javafx.embed.swing.SwingFXUtils; // For conversion
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

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
            bis.close();

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
}