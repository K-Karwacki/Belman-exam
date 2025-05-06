package dk.easv.belmanexam.be;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "orderNumber")
    private String orderNumber;

    @Column(name = "date")
    private Date date;
    // @ ToDo -> replace with enum asap
    private String status;
}
