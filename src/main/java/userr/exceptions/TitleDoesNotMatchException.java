package userr.exceptions;

public class TitleDoesNotMatchException extends Exception{
    public TitleDoesNotMatchException() {
        super("an ad with this title was not created");
    }
}
