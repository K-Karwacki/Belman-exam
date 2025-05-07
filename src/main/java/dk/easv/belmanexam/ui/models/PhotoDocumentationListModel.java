package dk.easv.belmanexam.ui.models;

import dk.easv.belmanexam.be.Order;
import dk.easv.belmanexam.be.PhotoDocumentation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;

public class PhotoDocumentationListModel {
    private final ObservableList<PhotoDocumentation> documentation = FXCollections.observableArrayList();

    public PhotoDocumentationListModel() {
    }

    public void setDocumentation(Collection<PhotoDocumentation> documentation) {
        this.documentation.clear();
        this.documentation.setAll(documentation);
    }
    public ObservableList<PhotoDocumentation> getDocumentation() {
        return documentation;
    }
}
