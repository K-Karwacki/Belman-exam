package dk.easv.belmanexam.utils;
import dk.easv.belmanexam.Main;
import dk.easv.belmanexam.entities.Photo;
import javafx.scene.image.Image;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;

public class PDFGenerator {
    public static PdfFile createPdf(String title, String content, Collection<Photo> photos) throws IOException {
        Image logo = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/belman-logo.png")));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try (PDDocument document = new PDDocument()) {
            PdfFile pdfFile = new PdfFile();
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Set font for text
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                // Add logo
                addImage(contentStream, document, logo, 260, 720, .35); // Adjust x, y, width, height as needed

                // Add Title
                addText(contentStream, 50, 690, "Order number: " + title);
                // Add Date
                addText(contentStream, 50, 660, "Date: " + LocalDate.now().format(formatter));
                // Add Content
                addText(contentStream, 50, 630, content);

                // Add images vertically
                float xPosition1 = 50;  // Left column x-coordinate
                float xPosition2 = 350; // Right column x-coordinate
                float yPosition = 440;  // Starting y-coordinate
                int i = 0;

                for (Photo photo : photos) {
                    if (i % 2 == 0) {
                        // Add photo in left column
                        addPhoto(contentStream, document, photo, xPosition1, yPosition);
                    } else {
                        // Add photo in right column
                        addPhoto(contentStream, document, photo, xPosition2, yPosition);
                        yPosition -= 250; // Move down after placing images in both columns
                    }
                    i++;
                }

                // Add signature text
                addText(contentStream, 480, 80, "Signature");
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
        if (text != null && !text.isEmpty()) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.beginText();
            contentStream.newLineAtOffset(position1, position2);
            contentStream.showText(text);
            contentStream.endText();
        }
    }

    private static void addImage(PDPageContentStream contentStream, PDDocument document, Image image, float xPosition, float yPosition, double scale) throws IOException {
        // Convert JavaFX Image to BufferedImage
        BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils
                .fromFXImage(Objects.requireNonNull(image), null);

        // Convert BufferedImage to PDImageXObject
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", baos);
            PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, baos.toByteArray(), "png");
            // Draw image at specified position and size
            contentStream.drawImage(pdImage, xPosition, yPosition, (float) image.getWidth() * (float) scale, (float) image.getHeight()* (float) scale);
        }
    }

    private static void addPhoto(PDPageContentStream contentStream, PDDocument document, Photo photo, float xPosition, float yPosition) throws IOException {
        // Convert JavaFX Image to BufferedImage
        BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils
                .fromFXImage(Objects.requireNonNull(ImageConverter.convertToImage(photo.getImageData())), null);

        // Get original dimensions
        int originalWidth = bufferedImage.getWidth();
        int originalHeight = bufferedImage.getHeight();

        // Calculate scaled dimensions while maintaining aspect ratio
        float maxWidth = 200;
        float scaledWidth = Math.min(originalWidth, maxWidth); // Ensure width is no larger than 200
        float aspectRatio = (float) originalHeight / originalWidth;
        float scaledHeight = scaledWidth * aspectRatio; // Maintain aspect ratio

        // Convert BufferedImage to PDImageXObject
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", baos);
            PDImageXObject image = PDImageXObject.createFromByteArray(document, baos.toByteArray(), "png");
            // Draw image and add side text
            contentStream.drawImage(image, xPosition, yPosition, scaledWidth, scaledHeight);
            addText(contentStream, xPosition, yPosition + scaledHeight + 5, photo.getSide());
        }
    }
}