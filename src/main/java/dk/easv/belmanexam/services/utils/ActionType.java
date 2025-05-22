package dk.easv.belmanexam.services.utils;

public enum ActionType {
    SUBMISSION("SUBMISSION"),
    REJECTION("REJECTION"),
    APPROVAL("APPROVAL");

    private final String name;

    ActionType(String name){
        this.name = name;
    }

    public static ActionType fromString(String roleName){
        for(ActionType actionType: ActionType.values()){
            if(actionType.name.equalsIgnoreCase(roleName)){
                return actionType;
            }
        }
        throw new IllegalArgumentException("Invalid role name");
    }
}
