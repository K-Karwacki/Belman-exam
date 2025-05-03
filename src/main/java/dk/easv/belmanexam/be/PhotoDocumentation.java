package dk.easv.belmanexam.be;

public class PhotoDocumentation {

    public final int id;
    public String title;
    public String author;
    public int authorId;
    public String orderNumber;

    public PhotoDocumentation(int id, String title, String author, int authorId, String orderNumber) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.authorId = authorId;
        this.orderNumber = orderNumber;
    }
}
