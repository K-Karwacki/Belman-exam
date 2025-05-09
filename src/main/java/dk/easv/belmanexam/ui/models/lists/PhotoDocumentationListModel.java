package dk.easv.belmanexam.ui.models.lists;

import dk.easv.belmanexam.be.PhotoDocumentation;
import dk.easv.belmanexam.ui.models.PhotoDocumentationModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;

public class PhotoDocumentationListModel {
    private final ObservableList<PhotoDocumentationModel> documentation = FXCollections.observableArrayList();

    public PhotoDocumentationListModel() {
    }

    public void setDocumentation(Collection<PhotoDocumentationModel> documentation) {
        this.documentation.clear();
        this.documentation.setAll(documentation);
    }
    public ObservableList<PhotoDocumentationModel> getDocumentation() {
        return documentation;
    }
}
