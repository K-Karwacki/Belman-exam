package dk.easv.belmanexam.ui.models;

import dk.easv.belmanexam.entities.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class UserModel
{
    private final SimpleIntegerProperty ID = new SimpleIntegerProperty();
    private final SimpleObjectProperty<String> role = new SimpleObjectProperty<>();
    private final SimpleStringProperty firstName = new SimpleStringProperty();
    private final SimpleStringProperty lastName = new SimpleStringProperty();
    private final SimpleStringProperty email = new SimpleStringProperty();


//    public UserModel(){
//        ID.set(-1);
//        role.set(null);
//        firstName.set(null);
//        lastName.set(null);
//        email.set(null);
//
//    }

    public UserModel(User user){
        ID.set(user.getId());
        role.set(user.getRole());
        firstName.set(user.getFirstName());
        lastName.set(user.getLastName());
        email.set(user.getEmail());
    }

    public long getID()
    {
        return ID.get();
    }

    public String getRole()
    {
        return role.get();
    }

    public SimpleObjectProperty<String> roleProperty()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role.set(role);
    }

    public String getFirstName()
    {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty()
    {
        return firstName;
    }

    public void setFirstName(String firstName) { this.firstName.set(firstName); }

    public String getLastName()
    {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName.set(lastName);
    }

    public String getEmail()
    {
        return email.get();
    }

    public SimpleStringProperty emailProperty() { return email; }

    public void setEmail(String email)
    {
        this.email.set(email);
    }



    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof UserModel userModel))
            return false;
        return getID() == userModel.getID();
    }

    @Override public int hashCode()
    {
        return Objects.hash(getID());
    }
}
