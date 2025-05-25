package dk.easv.belmanexam.ui.models;

import dk.easv.belmanexam.model.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;


public class LogListModel {
    private ObservableList<Log> logs = FXCollections.observableArrayList();

    public void setLogs(Collection<Log> logs) {
        this.logs.clear();
        this.logs.setAll(logs);
    }
    public ObservableList<Log> getLogs() {
        return logs;
    }

    public void add(Log log) {
        logs.add(log);
    }
}
