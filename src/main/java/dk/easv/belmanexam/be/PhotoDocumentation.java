package dk.easv.belmanexam.be;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "photo_documentation")
public class PhotoDocumentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @OneToMany(mappedBy = "photo_documentation", cascade = CascadeType.ALL)
    private List<Photo> photos = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documented_by_id")
    private User documented_by;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "approved_by_id")
    private User approved_by;

    public PhotoDocumentation() {

    }

    public int getID()
    {
        return ID;
    }

    public List<Photo> getPhotos()
    {
        return photos;
    }

    public void addPhoto(Photo photo){
        photos.add(photo);
    }

    public void removePhoto(Photo photo){
        photos.remove(photo);
    }

    public void setPhotos(List<Photo> photos)
    {
        this.photos = photos;
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

    public User getApproved_by()
    {
        return approved_by;
    }

    public void setApproved_by(User approved_by)
    {
        this.approved_by = approved_by;
    }
}
