package dk.easv.belmanexam.model;


public class User {

    private long id;
    private String firstName;
    private String lastName;
    private String role;
    private String email;

    // Default constructor
    public User() {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.role = null;
    }


//    // Parameterized constructor
    public User(long id, String firstName, String lastName, String email, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    // Getters and setters (updated naming)

    public long getId()
    {
        return id;
    }

    public long setId(long id) {
        return id;
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


//    public Set<User> getAllUsers() {
//        return users;
//    }


//    public void assignUserToRole(Role role) {
//        this.coordinatingEvents.add(role);
//    }

//    public void removeUserRole(Role role){
//        this.coordinatingEvents.remove(role);
//    }



    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {this.email = email;}

}
