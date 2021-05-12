package userr.services;


import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import userr.controllers.LoginController;
import userr.exceptions.DuplicatedAdException;
import userr.exceptions.FieldNotCompletedException;
import userr.exceptions.TitleDoesNotMatchException;
import userr.exceptions.WrongUsernameException;
import userr.model.Ad;
import userr.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static userr.services.FileSystemService.getPathToFile;

public class AdService {

    private static ObjectRepository<Ad> adRepository = AdService.getAdRepository();
    private static Nitrite database;
    public static void initDatabase() {
        database = Nitrite.builder()
                .filePath(getPathToFile("ad_database.db").toFile())
                .openOrCreate("test", "test");
        adRepository = database.getRepository(Ad.class);
    }


    public static void addAd( String id,String price,String title, String description, boolean appliances,
                              boolean clothes, boolean cars, boolean furniture, String photoPath, String vusername, boolean favorite) throws FieldNotCompletedException, DuplicatedAdException
    {
        checkAllFieldCompleted(price,title, description, appliances, clothes, cars, furniture);
        checkDuplicateAd(title,vusername);
        adRepository.insert(new Ad(id,price,title, description, appliances, clothes, cars, furniture, photoPath, vusername));
    }
    private static ObjectRepository<User> userRepository = UserService.getUsers();

    public static boolean checkIfMyAd(Ad ad, String loggedUser) {
        if(Objects.equals(ad.getVusername(),loggedUser))
            return true;
        else return false;
    }
    public static void checkDuplicateAd(String title, String username) throws DuplicatedAdException
    {
        for(Ad i : adRepository.find())
            if (Objects.equals(username, i.getVusername()) && Objects.equals(title, i.getTitle()) )
                throw new DuplicatedAdException();
    }
    public static void detailsAd(String title, String username) throws FieldNotCompletedException, TitleDoesNotMatchException, WrongUsernameException
    {
        checkAllFieldCompleted1(title,username);
        checkUsernameMatch1(username);
        checkTitleMatch1(title,username);
    }


    public static void checkUsernameMatch1(String username) throws WrongUsernameException {
        int ok = 0;
        for( Ad i : adRepository.find())
            if (Objects.equals(i.getVusername(),username)) {
                ok = 1;
            }
        if (ok == 0)
        {
            throw new WrongUsernameException();
        }
    }
    public static void checkTitleMatch1(String title,String username) throws TitleDoesNotMatchException {
        int ok = 0;
        for(Ad i : adRepository.find()) {
            if (Objects.equals(title,i.getTitle()) && Objects.equals(username,i.getVusername())) {
                ok = 1;
            }
        }
        if (ok == 0)
        {
            throw new TitleDoesNotMatchException();
        }
    }


    public static void checkAllFieldCompleted1(String title, String username) throws FieldNotCompletedException {
        if (title.trim().isEmpty() || username.trim().isEmpty())
        {
            throw new FieldNotCompletedException();
        }
    }


    public static void deleteAd(String title, String validationUsername) throws FieldNotCompletedException, TitleDoesNotMatchException, WrongUsernameException
    {
        checkAllFieldCompleted(title,validationUsername);
        checkUsernameMatch(validationUsername);
        checkTitleMatch(title);
        Ad auxAd = new Ad();
        for(Ad i : adRepository.find()) {
            if (Objects.equals(title, i.getTitle()) && Objects.equals(validationUsername, i.getVusername())) {
                auxAd = i;
            }
        }
        adRepository.remove(auxAd);
    }
    public static void checkTitleMatch(String title) throws TitleDoesNotMatchException {
        int ok = 0;
        for(Ad i : adRepository.find()) {
            if (Objects.equals(title,i.getTitle()) && Objects.equals(LoginController.getLoggedUsername(),i.getVusername())) {
                ok = 1;
            }
        }
        if (ok == 0)
        {
            throw new TitleDoesNotMatchException();
        }
    }
    public static void checkUsernameMatch(String username) throws WrongUsernameException {
        int ok = 0;
        if (Objects.equals(LoginController.getLoggedUsername(),username)) {
            ok = 1;
        }
        if (ok == 0)
        {
            throw new WrongUsernameException();
        }
    }

    public static void checkAllFieldCompleted(String price,String title, String description, boolean appliances,
                                              boolean clothes, boolean cars, boolean furniture) throws FieldNotCompletedException {
        if (title.trim().isEmpty() || price.trim().isEmpty()|| description.trim().isEmpty()||
                (!appliances && !clothes && !cars && !furniture)) {
            throw new FieldNotCompletedException();
        }
    }
    public static void checkAllFieldCompleted(String title, String validationUsername) throws FieldNotCompletedException {
        if (title.trim().isEmpty() || validationUsername.trim().isEmpty())
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

    public static ObjectRepository<Ad>  getAdRepository() {
        return adRepository;
    }
    public static Nitrite getDatabase() {
        return database;
    }
}

