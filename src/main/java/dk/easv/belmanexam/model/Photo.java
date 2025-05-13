package dk.easv.belmanexam.model;


import javafx.beans.property.*;

public class Photo {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty photoName = new SimpleStringProperty();
    private ObjectProperty<PhotoDocumentation> photoDocumentation = new SimpleObjectProperty<>();
    private StringProperty comment = new SimpleStringProperty();

    public Photo(){}

    public Integer getId() {
        return id.get();
    }
    public void setId(Integer id) {
        this.id.set(id);
    }
    public String getPhotoName() {
        return photoName.get();
    }
    public void setPhotoName(String photoName) {
        this.photoName.set(photoName);
    }
    public ObjectProperty<PhotoDocumentation> getPhotoDocumentation() {
        return photoDocumentation;
    }
    public void setPhotoDocumentation(ObjectProperty<PhotoDocumentation> photoDocumentation) {
        this.photoDocumentation = photoDocumentation;
    }
    public String getComment() {
        return comment.get();
    }
    public void setComment(String comment) {
        this.comment.set(comment);
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public StringProperty photoNameProperty() {
        return photoName;
    }
    public StringProperty commentProperty() {
        return comment;
    }
    public ObjectProperty<PhotoDocumentation> photoDocumentationProperty() {
        return photoDocumentation;
    }
}
