package dk.easv.belmanexam.bll.utils;

public enum Status
{
    PENDING("PENDING"),
    REJECTED("REJECTED"),
    APPROVED("APPROVED");


    private final String name;

    Status(String name){
        this.name = name;
    }

    public static Status fromString(String roleName){
        for(Status status: Status.values()){
            if(status.name.equalsIgnoreCase(roleName)){
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid role name");
    }
}
