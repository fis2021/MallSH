package userr.exceptions;

public class WrongUsernameException extends Exception {

    public WrongUsernameException() {
        super("username validation failed!");
    }
}