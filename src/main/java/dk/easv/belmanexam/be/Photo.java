package dk.easv.belmanexam.be;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "Comment")
    private String comment;

    @Column(name = "Name")
    private String name;

    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getComment() {
        return comment;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
