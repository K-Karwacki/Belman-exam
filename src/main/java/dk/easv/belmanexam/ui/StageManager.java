package dk.easv.belmanexam.ui;

import dk.easv.belmanexam.exceptions.ViewException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class StageManager
{
    private final SceneManager sceneManager;
    private final StageSettings stageSettings;
    private final Map<String, Stage> stageCache = new HashMap<>();

    private final FXMLManager fxmlManager = FXMLManager.INSTANCE;

    private static class StageSettings{
        protected Stage currentStage = null;
        protected final boolean isResizable = false;
        protected String title = "";
    }

    public StageManager() {
        stageSettings = new StageSettings();
        sceneManager = new SceneManager(stageSettings.currentStage);
    }


    public void setCurrentStage(Stage stage)
    {
        stageSettings.currentStage = stage;
    }

    // Load a new stage only if it hasn't been loaded before
    public void loadStage(String fxmlFile, String title, boolean isModal) {
        if (!stageCache.containsKey(fxmlFile)) {
            Parent root = fxmlManager.getFXML(fxmlFile).getKey();
            Stage stage = new Stage();
            stage.setResizable(stageSettings.isResizable);
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.initModality(Modality.NONE);

            if (isModal) {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            stageCache.put(fxmlFile, stage); // Cache the stage
        }
    }

    // Get a reference to the cached stage
    public Stage getStage(String fxmlFile) throws ViewException
    {
        if(stageCache.get(fxmlFile) == null){
            throw new ViewException("Stage not loaded.");
        }
        sceneManager.setCurrentStage(stageCache.get(fxmlFile));
        return stageCache.get(fxmlFile);
    }

    // Show a cached stage
    public void showStage(String fxmlFile) {
        if(stageSettings.currentStage != null){
            hideCurrentStage();
        }

        try{
            stageSettings.currentStage = getStage(fxmlFile);
            stageSettings.currentStage.show();
        }
        catch (ViewException e) {
            e.printStackTrace();
        }
    }

    // Hide a stage instead of closing it
    public void hideCurrentStage() {
        if (stageSettings.currentStage != null) {
            stageSettings.currentStage.hide(); // Keeps it in memory, ready for reopening
        }
    }

    // Close and remove a stage from cache
    public void closeStage(String fxmlFile) {
        Stage stage = stageCache.remove(fxmlFile);
        if (stage != null) {
            stage.close();
        }
    }

    public void showPopup(String fxmlFile, String title){
        if(stageCache.get(fxmlFile)==null){
            loadStage(fxmlFile, title, false);
        }
        try{
            getStage(fxmlFile).show();
        }catch (ViewException e){
            System.out.println(e.getMessage());
        }
    }

    public void hidePopup(String fxml){
        try{
            getStage(fxml).hide();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public SceneManager getSceneManager()
    {
        return sceneManager;
    }


}
