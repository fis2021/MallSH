package userr.exceptions;

public class FieldNotCompletedException extends Exception {

    public FieldNotCompletedException() {
        super("Please complete all necessary fields!");
    }
}