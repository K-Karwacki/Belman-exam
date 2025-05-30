package dk.easv.belmanexam.ui.controllers.admin.dashboards;

import dk.easv.belmanexam.entities.Log;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.controllers.components.LogComponentController;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;

public class LogsDashboardController {
    private PhotoDocumentationManagementService photoDocumentationManagementService;

    @FXML
    private FlowPane flowPaneLogsContainer;

    public void setServices(PhotoDocumentationManagementService photoDocumentationManagementService){
        this.photoDocumentationManagementService = photoDocumentationManagementService;
        initialize();
    }
    private void initialize() {
        addLogs();
        photoDocumentationManagementService.getLogListModel()
                .getLogs()
                .addListener((ListChangeListener<Log>) change -> {
                    addLogs();
                });}

    private void addLogs() {
        flowPaneLogsContainer.getChildren().clear();
        photoDocumentationManagementService.getLogListModel().getLogs().forEach(log ->{
            Pair<Parent, LogComponentController> p = FXMLManager.INSTANCE.loadFXML(FXMLPath.LOG_COMPONENT);
            p.getValue().setLog(log);
            flowPaneLogsContainer.getChildren().add(p.getKey());
        });
    }
}
