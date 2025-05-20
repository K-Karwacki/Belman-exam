package dk.easv.belmanexam.services.utils;

public enum RoleType
{
    ADMIN("ADMIN"),
    QA("QA"),
    OPERATOR("OPERATOR");


    private final String name;

    RoleType(String name){
        this.name = name;
    }

    public static RoleType fromString(String roleName){
        for(RoleType roleType: RoleType.values()){
            if(roleType.name.equalsIgnoreCase(roleName)){
                return roleType;
            }
        }
        throw new IllegalArgumentException("Invalid role name");
    }
}
