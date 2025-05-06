package dk.easv.belmanexam.be;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.Date;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "order_number")
    private String orderNumber;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documentation_id")
    private PhotoDocumentation photoDocumentation;

    @Enumerated(EnumType.STRING)
    private String status;
}
