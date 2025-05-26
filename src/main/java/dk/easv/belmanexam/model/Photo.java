package dk.easv.belmanexam.model;


import javafx.beans.property.*;

public class Photo {

    private LongProperty id = new SimpleLongProperty();
    private ObjectProperty<byte[]> imageData = new SimpleObjectProperty<>();
    private LongProperty documentationId = new SimpleLongProperty();
    private StringProperty side = new SimpleStringProperty();
    private StringProperty info = new SimpleStringProperty();

    public Photo(){}

    public Long getId() {
        return id.get();
    }
    public void setId(Long id) {
        this.id.set(id);
    }
    public String getinfo() {
        return info.get();
    }
    public void setinfo(String info) {
        this.info.set(info);
    }
    public LongProperty idProperty() {
        return id;
    }
    public StringProperty infoProperty() {
        return info;
    }
    public byte[] getImageData() {
        return imageData.get();
    }
    public void setImageData(byte[] imageData) {
        this.imageData.set(imageData);
    }
    public ObjectProperty<byte[]> imageDataProperty() {
        return imageData;
    }
    public Long getDocumentationId() {
        return documentationId.get();
    }
    public void setDocumentationId(Long documentationId) {
        this.documentationId.set(documentationId);
    }
    public LongProperty documentationIdProperty() {
        return documentationId;
    }
    public String getSide() {
        return side.get();
    }
    public void setSide(String side) {
        this.side.set(side);
    }
    public StringProperty sideProperty() {
        return side;
    }

}
