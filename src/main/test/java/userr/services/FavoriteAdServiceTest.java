package userr.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import userr.exceptions.*;
import userr.model.Ad;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FavoriteAdServiceTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER=".test-ad-database-D";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        AdService.initDatabase();

        FileSystemService.APPLICATION_FOLDER=".test-user-database-D";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();

        FileSystemService.APPLICATION_FOLDER=".test-fav-database-D";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        FavoriteAdService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        AdService.getDatabase().close();
        UserService.getDatabase().close();
        FavoriteAdService.getDatabase().close();
    }

    @Test
    @DisplayName("Database is initialize, there are no favorite ads")
    void DatabaseIsInitializeAndNoAdsArePersisted() {
        assertThat(AdService.getAllAds()).isNotNull();
        assertThat(AdService.getAllAds()).isEmpty();
    }

    @Test
    @DisplayName("Favorite Ad is successfully persisted to Database ")
    void testAdIsAddedToDatabase(){
        FavoriteAdService.addFavorite("5","dragos1", "mobila","dragos2",true);
        assertThat(FavoriteAdService.getAllFavAds()).isNotEmpty();
        assertThat(FavoriteAdService.getAllFavAds()).size().isEqualTo(1);
        Ad ad = FavoriteAdService.getAllFavAds().get(0);
        assertThat(ad).isNotNull();
        assertThat(ad.getOwnUsername()).isEqualTo("dragos1");
        assertThat(ad.getTitle()).isEqualTo("mobila");
        assertThat(ad.getVusername()).isEqualTo("dragos2");
        assertThat(ad.isFavorite()).isEqualTo(true);
    }

    @Test
    @DisplayName("Favorite Ad is successfully removed from Database")
    void testAdIsRemovedFromDatabase()
    {
        FavoriteAdService.addFavorite("5","dragos1", "mobila","dragos2",true);
        assertThat(FavoriteAdService.getAllFavAds()).isNotEmpty();
        assertThat(FavoriteAdService.getAllFavAds()).size().isEqualTo(1);
        FavoriteAdService.deleteFavorite("dragos1","mobila","dragos2");
        assertThat(FavoriteAdService.getAllFavAds()).size().isEqualTo(0);
    }

    @Test
    @DisplayName("The favorite ad has to be saved as favorite by logged user in order to remove it from favorite")
    void testCheckIfMyFavorite() throws UsernameAlreadyExistsException, FieldNotCompletedException, WeakPasswordException, PasswordConfirmationException, UsernameDoesNotExistsException, WrongPasswordException {
        UserService.addUser("dragos1","Dragos1!","Dragos1!","a","a","a","a","a");
        UserService.loginUser("dragos1","Dragos1!");
        FavoriteAdService.addFavorite("5","dragos1", "mobila","dragos2",true);
        Ad ad = FavoriteAdService.getAllFavAds().get(0);
        assertThat(FavoriteAdService.checkIfMyFavorite(ad,"dragos1")).isTrue();
    }
}