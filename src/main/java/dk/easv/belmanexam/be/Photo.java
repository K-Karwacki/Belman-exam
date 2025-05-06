package dk.easv.belmanexam.be;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "comment")
    private String comment;


    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getComment() {
        return comment;
    }
}
