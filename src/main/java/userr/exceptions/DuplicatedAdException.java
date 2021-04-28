package userr.exceptions;

public class DuplicatedAdException extends Exception {

    public DuplicatedAdException() {
        super("You can't add the same product twice!");
    }
}