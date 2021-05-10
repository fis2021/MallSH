package userr.services;


import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import userr.controllers.LoginController;
import userr.exceptions.DuplicatedAdException;
import userr.exceptions.FieldNotCompletedException;
import userr.exceptions.TitleDoesNotMatchException;
import userr.exceptions.WrongUsernameException;
import userr.model.Ad;

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

    public static void addFavorite( String id,String ownUsername,String title, String sellerUsername,boolean favorite)
    {
        favoriteRepository.insert(new Ad(id,ownUsername,title,sellerUsername,true));
    }

    public static ObjectRepository<Ad>  getFavoriteRepository() {
        return favoriteRepository;
    }
}
