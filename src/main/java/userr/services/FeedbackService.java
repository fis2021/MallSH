package userr.services;


import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import userr.controllers.LoginController;
import userr.exceptions.FeedbackAlreadyExistsException;
import userr.model.Feedback;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import userr.exceptions.FieldNotCompletedException;

import static userr.services.FileSystemService.getPathToFile;

public class FeedbackService {

    private static ObjectRepository<Feedback> feedbackRepository = FeedbackService.getFeedbackRepository();

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("feedback_database.db").toFile())
                .openOrCreate("test", "test");
        feedbackRepository = database.getRepository(Feedback.class);
    }


    public static void addFeedback(String loggedUser,String description, String rate) throws FieldNotCompletedException, FeedbackAlreadyExistsException
    {
        checkAllFieldCompleted(description);
        checkUniqueFeedback(loggedUser);
        feedbackRepository.insert(new Feedback(loggedUser, description,rate));
    }




    public static void checkUniqueFeedback(String username) throws FeedbackAlreadyExistsException {

        for( Feedback i : feedbackRepository.find())
            if (Objects.equals(i.getLoggedUser(),username))
            throw new FeedbackAlreadyExistsException(username);
        }



    public static void checkAllFieldCompleted(String description) throws FieldNotCompletedException {
        if (description.trim().isEmpty())
        {
            throw new FieldNotCompletedException();
        }
    }


    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    public static ObjectRepository<Feedback>  getFeedbackRepository() {
        return feedbackRepository;
    }

}

