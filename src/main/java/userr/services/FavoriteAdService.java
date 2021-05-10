package userr.services;


import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import userr.controllers.LoginController;
import userr.exceptions.*;
import userr.model.Ad;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static userr.services.FileSystemService.getPathToFile;


public class FavoriteAdService {

    private static ObjectRepository<Ad> favoriteRepository = AdService.getAdRepository();

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("favorite_database.db").toFile())
                .openOrCreate("test", "test");
        favoriteRepository = database.getRepository(Ad.class);
    }

    public static void addFavorite(String id,String ownUsername,String title, String sellerUsername,boolean favorite)
    {
        favoriteRepository.insert(new Ad(id,ownUsername,title,sellerUsername,true));
    }

    public static void deleteFavorite(String ownUsername, String title, String vusername)
    {
        Ad auxAd = new Ad();
        for(Ad i : favoriteRepository.find()) {
            if (Objects.equals(ownUsername, i.getOwnUsername()) && Objects.equals(title, i.getTitle())
                    && (Objects.equals(vusername,i.getVusername()))) {
                auxAd = i;
            }
        }
        favoriteRepository.remove(auxAd);
    }

    public static ObjectRepository<Ad>  getFavoriteRepository() {
        return favoriteRepository;
    }
}
