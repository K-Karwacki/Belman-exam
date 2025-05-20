package dk.easv.belmanexam.repositories.utils;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import dk.easv.belmanexam.exceptions.PhotoException;
import dk.easv.belmanexam.utils.Env;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GoogleDriveManager {

    private static final String APPLICATION_NAME = "Photo Report App";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Initializes and returns a Google Drive service instance with OAuth authentication.
     *
     * @return Drive service instance
     */
    private Drive getDriveService() throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        // Create client secrets from environment variables
        String clientId = Env.get("CLIENT_ID");
        String clientSecret = Env.get("CLIENT_SECRET");

        if (clientId == null || clientSecret == null) {
            throw new IOException("CLIENT_ID and CLIENT_SECRET must be set in .env file");
        }

        // Create a JSON string with the client credentials
        String clientSecretsJson = String.format(
                "{\n" +
                        "  \"installed\": {\n" +
                        "    \"client_id\": \"%s\",\n" +
                        "    \"client_secret\": \"%s\",\n" +
                        "    \"redirect_uris\": [\"http://localhost\", \"urn:ietf:wg:oauth:2.0:oob\"],\n" +
                        "    \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                        "    \"token_uri\": \"https://oauth2.googleapis.com/token\"\n" +
                        "  }\n" +
                        "}", clientId, clientSecret);

        // Load client secrets from the JSON string
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                GsonFactory.getDefaultInstance(), new StringReader(clientSecretsJson));

        // Create the flow for authorization with updated scope
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, GsonFactory.getDefaultInstance(), clientSecrets,
                Collections.singletonList("https://www.googleapis.com/auth/drive")) // Changed scope
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .build();

        // Get the credential
        Credential credential = new com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp(
                flow, new com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver()).authorize("user");

        // Build and return the Drive service
        return new Drive.Builder(httpTransport, GsonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Finds the ID of a folder in Google Drive by its name.
     *
     * @param folderName Name of the folder (e.g., "21/2015/AE31")
     * @return Folder ID if found, null otherwise
     */
    public String findFolderId(String folderName) throws IOException, GeneralSecurityException {
        Drive service = getDriveService();
        FileList result = service.files().list()
                .setQ("name = '" + folderName.replace("'", "\\'") + "' and mimeType = 'application/vnd.google-apps.folder' and trashed = false")
                .setFields("files(id)")
                .execute();
        List<File> folders = result.getFiles();
        return (folders != null && !folders.isEmpty()) ? folders.get(0).getId() : null;
    }

    /**
     * Lists the names of image files in a Google Drive folder corresponding to the order number.
     *
     * @return List of file names in the folder
     * @throws PhotoException if there are issues with the API request or security
     */
    public List<File> listFilesInFolder(String folderId) throws PhotoException {
        try {
            Drive service = getDriveService();

            // Find folder ID
            if (folderId == null) {
                throw new IOException("Folder not found");
            }

            // List image files in the folder
            FileList result = service.files().list()
                    .setQ("'" + folderId + "' in parents and mimeType contains 'image/' and trashed = false")
                    .setFields("files(id, name)")
                    .execute();

            return new ArrayList<>(result.getFiles());
        } catch (IOException | GeneralSecurityException e) {
            throw new PhotoException(e);
        }
    }
    public HashMap<String, String> listFoldersInFolder(String folderName) throws PhotoException {
        try {
            Drive service = getDriveService();

            String folderId = findFolderId(folderName);
            if (folderId == null) {
                throw new IOException("Folder not found");
            }

            // List folders in the folder
            FileList result = service.files().list()
                    .setQ("'" + folderId + "' in parents and mimeType = 'application/vnd.google-apps.folder' and trashed = false")
                    .setFields("files(id, name)") // Include name field
                    .execute();

            HashMap<String, String> folders = new HashMap<>();
            for (File file : result.getFiles()) {
                folders.put(file.getId(), file.getName());
                System.out.println(file.getName());
            }
            return folders;
        } catch (IOException | GeneralSecurityException e) {
            throw new PhotoException(e);
        }
    }

    /**
     * Downloads the content of a Google Drive file as a byte array.
     *
     * @param fileId ID of the file to download
     * @return Byte array containing the file content
     * @throws PhotoException if there are issues with the API request or security
     */
    public byte[] downloadFileContent(String fileId) throws PhotoException {
        try {
            Drive service = getDriveService();
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                service.files().get(fileId).executeMediaAndDownloadTo(outputStream);
                return outputStream.toByteArray();
            }
        } catch (IOException | GeneralSecurityException e) {
            throw new PhotoException(e);
        }
    }

    /**
     * Downloads the content of a list of Google Drive files as a list of byte arrays.
     *
     * @param files List of File objects to download
     * @return List of byte arrays (one per file), with null for any failed downloads
     * @throws PhotoException if there are issues with the API request or security
     */
    public List<byte[]> downloadFilesContent(List<File> files) throws PhotoException {
        List<byte[]> fileContents = new ArrayList<>();
        for (File file : files) {
            byte[] content = downloadFileContent(file.getId());
            fileContents.add(content);
        }
        return fileContents;
    }

    /**
     * Ensures that the tokens directory exists.
     * Call this method before using the GoogleDriveManager.
     */
    public void ensureTokensDirectoryExists() {
        java.io.File tokensDir = new java.io.File(TOKENS_DIRECTORY_PATH);
        if (!tokensDir.exists()) {
            tokensDir.mkdirs();
        }
    }


    public void saveFileInFolder(java.io.File file, String folderId) throws PhotoException {
        try {
            Drive service = getDriveService();

            // Create file metadata
            File fileMetadata = new File();
            fileMetadata.setName(file.getName());
            fileMetadata.setParents(Collections.singletonList(folderId));

            // Determine MIME type
            String mimeType = java.nio.file.Files.probeContentType(file.toPath());
            if (mimeType == null) {
                mimeType = "application/octet-stream"; // Default MIME type
            }

            // Create file content
            FileContent mediaContent = new FileContent(mimeType, file);

            // Upload the file
            service.files().create(fileMetadata, mediaContent)
                    .setFields("id, name")
                    .execute();

        } catch (IOException | GeneralSecurityException e) {
            throw new PhotoException(e);
        }
    }

    public String createFolder(String folderName) throws PhotoException {
        try {
            Drive service = getDriveService();
            String folderId = findFolderId(folderName);
            if(folderId == null) {
                File folderMetadata = new File();
                folderMetadata.setName(folderName);
                folderMetadata.setMimeType("application/vnd.google-apps.folder");

                File folder = service.files().create(folderMetadata)
                        .setFields("id")
                        .execute();
                return folder.getId();
            }
            else{
                return folderId;
            }

        } catch (IOException | GeneralSecurityException e) {
            throw new PhotoException(e);
        }
    }
    public String createFolderInFolder(String folderName, String parentFolderId) throws PhotoException {
        try {
            Drive service = getDriveService();
            String folderId = findFolderId(folderName);
            if (folderId == null) {
                File folderMetadata = new File();
                folderMetadata.setName(folderName);
                folderMetadata.setMimeType("application/vnd.google-apps.folder");

                if (parentFolderId == null) {
                    parentFolderId = createFolder(folderName);
                }
                folderMetadata.setParents(Collections.singletonList(parentFolderId));

                File folder = service.files().create(folderMetadata)
                        .setFields("id")
                        .execute();
                return folder.getId();
            }
            return folderId;

        } catch (IOException | GeneralSecurityException e) {
            throw new PhotoException(e);
        }
    }
}