package dk.easv.belmanexam.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {
    public static byte[] createPdf(String title, String content, List<Image> images) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Set font for text
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                // Add Title
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText(title);
                contentStream.endText();

                // Add Content
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 650);
                contentStream.showText(content);
                contentStream.endText();

                // Add images vertically
                float xPosition = 350; // Fixed x-coordinate
                float xPosition2 = 50;
                float yPosition = 500; // Starting y-coordinate
                int i = 0;

                for (Image fxImage : images) {
                    // Convert JavaFX Image to BufferedImage
                    BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils.fromFXImage(fxImage, null);

                    // Convert BufferedImage to PDImageXObject
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        ImageIO.write(bufferedImage, "png", baos);
                        PDImageXObject image = PDImageXObject.createFromByteArray(document, baos.toByteArray(), "png");
                        // Scale image to half size
                        float scaledWidth = 200;
                        float scaledHeight = 140;
                        if(i%2 == 1){
                            contentStream.drawImage(image, xPosition, yPosition, scaledWidth, scaledHeight);
                            addText(contentStream, xPosition, yPosition + scaledHeight + 5, i);
                            yPosition -= 250;
                        }
                        else{
                            contentStream.drawImage(image, xPosition2, yPosition, scaledWidth, scaledHeight);
                            addText(contentStream, xPosition2, yPosition + scaledHeight + 5, i);
                        }
                    }
                    i++;
                }
            }

            // Save to ByteArrayOutputStream instead of file
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                document.save(byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }
        }
    }
    private static void addText(PDPageContentStream contentStream, float position1, float position2, int photoNumber) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        contentStream.beginText();
        contentStream.newLineAtOffset(position1, position2);
        contentStream.showText("Photo #" + (photoNumber + 1));
        contentStream.endText();
    }
}