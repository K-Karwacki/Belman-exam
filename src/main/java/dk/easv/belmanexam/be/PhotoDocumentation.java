package dk.easv.belmanexam.be;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PhotoDocumentation")
public class PhotoDocumentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OrderID")
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserID")
    private User documented_by;


    public PhotoDocumentation() {

    }

    public int getID()
    {
        return ID;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public User getDocumented_by()
    {
        return documented_by;
    }

    public void setDocumented_by(User documented_by)
    {
        this.documented_by = documented_by;
    }

}
