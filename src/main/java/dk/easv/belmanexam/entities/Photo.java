package dk.easv.belmanexam.entities;

public class Photo {
    private Long id;

    private byte[] imageData;

    private Long documentationId;

    private String side;

    private String info;

    public Photo() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Long getDocumentationId() {
        return documentationId;
    }

    public void setDocumentationId(Long documentationId) {
        this.documentationId = documentationId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}