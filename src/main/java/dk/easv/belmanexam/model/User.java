package dk.easv.belmanexam.model;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private String passwordHash;

    // Default constructor
    public User() {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.role = null;
    }


    // Parameterized constructor
    public User(int id, String firstName, String lastName, String email, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    // Getters and setters (updated naming)

    public int getId()
    {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {this.email = email;}

}
