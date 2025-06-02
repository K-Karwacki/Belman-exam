package dk.easv.belmanexam.services;

import dk.easv.belmanexam.utils.Env;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.util.List;

public class EmailSenderService {

    private static final String API_KEY = Env.get("API_KEY");
    private static final String FROM_EMAIL = Env.get("FROM_EMAIL");
    private static final String API_URL = "https://api.sendgrid.com/v3/mail/send";

    public boolean sendReport(String recipient, List<File> files, String subject) throws IOException {
        JSONObject[] attachments = new JSONObject[files.size()];
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            String encodedFile = Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath()));
            attachments[i] = new JSONObject()
                    .put("content", encodedFile)
                    .put("filename", file.getName())
                    .put("type", Files.probeContentType(file.toPath()));
        }

        return sendEmail(recipient, subject, "Please find the attached files.", attachments);
    }

    public boolean sendEmail(String recipient, String subject, String message, JSONObject[] attachments) throws IOException {
        JSONObject emailJson = new JSONObject();
        emailJson.put("from", new JSONObject().put("email", FROM_EMAIL));
        emailJson.put("subject", subject);

        JSONObject to = new JSONObject().put("email", recipient);
        emailJson.append("personalizations", new JSONObject().append("to", to));

        emailJson.append("content", new JSONObject()
                .put("type", "text/plain")
                .put("value", message));

        if (attachments != null && attachments.length > 0) {
            for (JSONObject attachment : attachments) {
                emailJson.append("attachments", attachment);
            }
        }

        HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(emailJson.toString().getBytes());
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == 202) {
            return true;
        } else {
            return false;
        }
    }
}