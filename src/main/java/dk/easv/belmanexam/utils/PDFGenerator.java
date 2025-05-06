package dk.easv.belmanexam.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {
    public static void createPdf(String title, String content, List<File> images, String outputPath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Add Title
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText(title);
                contentStream.endText();

                // Add Content
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 650);
                contentStream.showText(content);
                contentStream.endText();

                // @ToDo -> Replace with hashmap of File + Comment later (or create a Photo model)
                for (File imageFile : images) {
                    PDImageXObject image = PDImageXObject.createFromFile(imageFile.getAbsolutePath(), document);
                    contentStream.drawImage(image, 400, 300, image.getWidth() / 2, image.getHeight() / 2); // Scale image
                }
            }

            // Save the PDF
            document.save(outputPath);
        }
    }
}