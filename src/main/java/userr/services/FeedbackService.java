package userr.services;


import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import userr.controllers.LoginController;
import userr.exceptions.FeedbackAlreadyExistsException;
import userr.model.Feedback;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import userr.exceptions.FieldNotCompletedException;

import static userr.services.FileSystemService.getPathToFile;

public class FeedbackService {

    private static ObjectRepository<Feedback> feedbackRepository = FeedbackService.getFeedbackRepository();
    private static Nitrite database;

    public static void initDatabase() {
        FileSystemService.initDirectory();
         database = Nitrite.builder()
                .filePath(getPathToFile("feedback_database.db").toFile())
                .openOrCreate("test", "test");
        feedbackRepository = database.getRepository(Feedback.class);
    }
    public static List<Feedback> getAllFeedbacks(){
        return feedbackRepository.find().toList();
    }

    public static void addFeedback(String loggedUser,String description, String rate) throws FieldNotCompletedException, FeedbackAlreadyExistsException
    {
        checkAllFieldCompleted(description,rate);
        checkUniqueFeedback(loggedUser);
        feedbackRepository.insert(new Feedback(loggedUser, description,rate));
    }




    public static void checkUniqueFeedback(String username) throws FeedbackAlreadyExistsException {

        for( Feedback i : feedbackRepository.find())
            if (Objects.equals(i.getLoggedUser(),username))
            throw new FeedbackAlreadyExistsException(username);
        }



    public static void checkAllFieldCompleted(String description,String rate) throws FieldNotCompletedException {
        if (description.trim().isEmpty() ||( !(Objects.equals(rate,"10")) && !(Objects.equals(rate,"1")) && !(Objects.equals(rate,"2")) &&
                !(Objects.equals(rate,"3")) && !(Objects.equals(rate,"4"))&& !(Objects.equals(rate,"5"))
                        && !(Objects.equals(rate,"6")) && !(Objects.equals(rate,"7")) && !(Objects.equals(rate,"8"))
                        && !(Objects.equals(rate,"9"))))

            throw new FieldNotCompletedException();

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
    public static Nitrite getDatabase() {
        return database;
    }
}

