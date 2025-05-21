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
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class PDFGenerator {
    public static PdfFile createPdf(String title, String content, HashMap<String, Image> images) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try (PDDocument document = new PDDocument()) {
            PdfFile pdfFile = new PdfFile();
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Set font for text
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                // Add Title
                addText(contentStream, 50, 730, title);
                //Add Date
                addText(contentStream, 50, 700, LocalDate.now().format(formatter));
                // Add Content
                addText(contentStream, 50, 670, content);

                // Add images vertically
                float xPosition = 350; // Fixed x-coordinate
                float xPosition2 = 50;
                float yPosition = 480; // Starting y-coordinate
                int i = 0;

                for (Map.Entry<String, Image> entry : images.entrySet()) {
                    // Convert JavaFX Image to BufferedImage
                    BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils.fromFXImage(entry.getValue(), null);

                    // Convert BufferedImage to PDImageXObject
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        ImageIO.write(bufferedImage, "png", baos);
                        PDImageXObject image = PDImageXObject.createFromByteArray(document, baos.toByteArray(), "png");
                        // Scale image to half size
                        float scaledWidth = 200;
                        float scaledHeight = 140;
                        if(i%2 == 1){
                            contentStream.drawImage(image, xPosition, yPosition, scaledWidth, scaledHeight);
                            addText(contentStream, xPosition, yPosition + scaledHeight + 5, entry.getKey());
                            yPosition -= 250;
                        }
                        else{
                            contentStream.drawImage(image, xPosition2, yPosition, scaledWidth, scaledHeight);
                            addText(contentStream, xPosition2, yPosition + scaledHeight + 5, entry.getKey());
                        }
                    }
                    i++;
                }
            }

            // Save to ByteArrayOutputStream instead of file
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                document.save(byteArrayOutputStream);
                pdfFile.setByteData(byteArrayOutputStream.toByteArray());
            }
            File tempFile = File.createTempFile("report_", ".pdf");
            document.save(tempFile);
            pdfFile.setFile(tempFile);

            return pdfFile;
        }
    }
    private static void addText(PDPageContentStream contentStream, float position1, float position2, String text) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        contentStream.beginText();
        contentStream.newLineAtOffset(position1, position2);
        contentStream.showText(text);
        contentStream.endText();
    }
}