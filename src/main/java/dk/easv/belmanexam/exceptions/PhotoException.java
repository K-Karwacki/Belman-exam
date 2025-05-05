package dk.easv.belmanexam.exceptions;

public class PhotoException extends Exception {
    public PhotoException(Exception e) {
        super(e.getMessage());
    }
}
