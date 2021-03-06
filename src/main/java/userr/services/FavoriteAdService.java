package userr.services;


import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import userr.controllers.LoginController;
import userr.exceptions.*;
import userr.model.Ad;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import static userr.services.FileSystemService.getPathToFile;


public class FavoriteAdService {

    private static ObjectRepository<Ad> favoriteRepository = AdService.getAdRepository();
private static Nitrite database;
    public static void initDatabase() {
         database = Nitrite.builder()
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
    public static boolean checkIfMyFavorite(Ad ad, String loggedUser) {
        if(Objects.equals(ad.getOwnUsername(),loggedUser))
            return true;
        else return false;
    }

    public static List<Ad> getAllFavAds(){
        return favoriteRepository.find().toList();
    }
    public static ObjectRepository<Ad>  getFavoriteRepository() {
        return favoriteRepository;
    }
    public static Nitrite getDatabase() {
        return database;
    }
}
