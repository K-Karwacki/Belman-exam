package dk.easv.belmanexam.be;

import jakarta.persistence.*;

@Entity
@Table(name = "PhotoDocumentation")
public class PhotoDocumentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PhotoID")
    private Photo photo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OrderID")
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserID")
    private User user;

    public PhotoDocumentation() {

    }
}
