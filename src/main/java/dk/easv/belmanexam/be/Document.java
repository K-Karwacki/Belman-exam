package dk.easv.belmanexam.be;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "UserID")
    private int user_id;

    @Column(name = "RoleID")
    private String role;

    @Column(name = "Date")
    private Date date;

    public Document() {}

    public Document(int id, int user_id, String role, Date date) {
        this.id = id;
        this.user_id = user_id;
        this.role = role;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public int getUserId() {
        return user_id;
    }
    public String getRole() {
        return role;
    }
    public Date getDate() {
        return date;
    }

    public void setId(long id) {}
    public void setUserId(int user_id) {}
    public void setRole(String role) {}
    public void setDate(Date date) {}
}
