package userr.services;


import userr.exceptions.FieldNotCompletedException;
import userr.model.Ad;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static userr.services.FileSystemService.getPathToAd;
public class AdService {

    private static ObjectRepository<Ad> adRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToAd("ad_database.db").toFile())
                .openOrCreate("test", "test");

        adRepository = database.getRepository(Ad.class);
    }

    public static void addAd(String title, String price, String description, boolean appliances,
                               boolean clothes, boolean cars, boolean furniture, String photoPath)  throws FieldNotCompletedException
    {
        checkAllFieldCompleted(title, price, description, appliances, clothes, cars, furniture);
        adRepository.insert(new Ad(title,price, description, appliances, clothes, cars, furniture, photoPath));
    }


    public static void checkAllFieldCompleted(String title, String price, String description, boolean appliances,
                                              boolean clothes, boolean cars, boolean furniture) throws FieldNotCompletedException {
        if (title.trim().isEmpty() || price.trim().isEmpty()|| description.trim().isEmpty()||
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

