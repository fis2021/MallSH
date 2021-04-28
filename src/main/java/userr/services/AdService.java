package userr.services;


import userr.controllers.AdController;
import userr.controllers.LoginController;
import userr.exceptions.FieldNotCompletedException;
import userr.exceptions.WrongUsernameException;
import userr.exceptions.DuplicatedAdException;
import userr.model.Ad;
import userr.model.User;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static userr.services.FileSystemService.getPathToFile;
public class AdService {

    private static ObjectRepository<Ad> adRepository = AdService.getAdRepository();

    private static ObjectRepository<User> userRepository = UserService.getUsers();

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("ad_database.db").toFile())
                .openOrCreate("test", "test");

        adRepository = database.getRepository(Ad.class);
    }

    private static String loggedUser;

    public static void addAd(String title, String price, String description, boolean appliances,
                               boolean clothes, boolean cars, boolean furniture, String photoPath, String vusername)  throws FieldNotCompletedException, WrongUsernameException, DuplicatedAdException
    {
        checkAllFieldCompleted(title, price, description, appliances, clothes, cars, furniture,vusername);
        checkUsernameValidation(vusername);
        checkDuplicateAd(title,vusername);
        adRepository.insert(new Ad(title,price, description, appliances, clothes, cars, furniture, photoPath, vusername));
    }

    public static void checkUsernameValidation(String username) throws WrongUsernameException
    {
        loggedUser = LoginController.getLoggedUsername();
        int ok=0;
        if (Objects.equals(username, loggedUser))
        {
            ok = 1;
        }
        if(ok==0) {
            throw new WrongUsernameException();
        }
    }

    public static void checkDuplicateAd(String titl, String username) throws DuplicatedAdException
    {
        int ok = 0;
        for(Ad i : adRepository.find())
        {
            if (Objects.equals(username,i.getVusername()) && Objects.equals(titl, i.getTitle()))
            {
                ok = 1;
            }
        }
        if(ok==1) {
            throw new DuplicatedAdException();
        }
    }

    public static void checkAllFieldCompleted(String title, String price, String description, boolean appliances,
                                              boolean clothes, boolean cars, boolean furniture, String vusername) throws FieldNotCompletedException {
        if (title.trim().isEmpty() || price.trim().isEmpty()|| description.trim().isEmpty()|| vusername.trim().isEmpty() ||
                (!appliances && !clothes && !cars && !furniture)) {
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

}

