package dk.easv.belmanexam.be;

import dk.easv.belmanexam.bll.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "OrderNumber")
    private String orderNumber;

    @Column(name = "Date")
    private LocalDateTime date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "Status")
    private Status status;
}
