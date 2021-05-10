package userr.exceptions;

public class FeedbackAlreadyExistsException extends Throwable {
    private String username;

    public FeedbackAlreadyExistsException(String username) {
        super(String.format("Feedback given by username %s already exists!", username));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
