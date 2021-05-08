package userr.services;


import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import userr.controllers.LoginController;
import userr.exceptions.DuplicatedAdException;
import userr.exceptions.FieldNotCompletedException;
import userr.exceptions.TitleDoesNotMatchException;
import userr.model.Ad;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static userr.services.FileSystemService.getPathToFile;

public class AdService {

    private static ObjectRepository<Ad> adRepository = AdService.getAdRepository();

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("ad_database.db").toFile())
                .openOrCreate("test", "test");
        adRepository = database.getRepository(Ad.class);
    }


    public static void addAd( String id,String price,String title, String description, boolean appliances,
                              boolean clothes, boolean cars, boolean furniture, String photoPath, String vusername) throws FieldNotCompletedException, DuplicatedAdException
    {
        checkAllFieldCompleted(price,title, description, appliances, clothes, cars, furniture);
        checkDuplicateAd(title,vusername);
        adRepository.insert(new Ad(id,price,title, description, appliances, clothes, cars, furniture, photoPath, vusername));
    }
    public static void checkDuplicateAd(String title, String username) throws DuplicatedAdException
    {
        for(Ad i : adRepository.find())
            if (Objects.equals(username, i.getVusername()) && Objects.equals(title, i.getTitle()) )
                throw new DuplicatedAdException();
    }

    public static void deleteAd(String title, String validationUsername) throws FieldNotCompletedException, TitleDoesNotMatchException{
        checkAllFieldCompleted(title,validationUsername);
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
            if (Objects.equals(title,i.getTitle())) {
                ok = 1;
            }
        }
        if (ok == 0)
        {
            throw new TitleDoesNotMatchException();
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

}

