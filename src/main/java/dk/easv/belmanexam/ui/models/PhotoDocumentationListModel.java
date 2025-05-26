package dk.easv.belmanexam.ui.models;

import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.services.utils.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.print.Doc;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public ObservableList<PhotoDocumentation> getDocumentationForStatus(Status status) {
        return FXCollections.observableArrayList(
                documentation.stream()
                        .filter(doc -> doc.getStatus() == status)
                        .collect(Collectors.toList())
        );
    }
    public ObservableList<PhotoDocumentation> getDocumentationForInput(Status status, String input) {
        return FXCollections.observableArrayList(
                documentation.stream()
                        .filter(doc -> doc.getStatus() == status)
//                        .filter(doc -> doc.getOrderNumber().contains(input))
                        .collect(Collectors.toList())
        );
    }

    public void update(PhotoDocumentation photoDocumentation) {
        PhotoDocumentation documentationToRemove = null;
        for(PhotoDocumentation doc:documentation){
            if (Objects.equals(photoDocumentation.getId(), doc.getId())){
                documentationToRemove = doc;
            }
        }
        documentation.remove(documentationToRemove);
        documentation.add(photoDocumentation);
    }
    public void add(PhotoDocumentation photoDocumentation){
        documentation.add(photoDocumentation);
    }
}
