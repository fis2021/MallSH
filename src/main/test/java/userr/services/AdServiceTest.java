package userr.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import userr.exceptions.*;
import userr.model.Ad;
import userr.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AdServiceTest {

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
    }

    @AfterEach
    void tearDown() {

        AdService.getDatabase().close();
        UserService.getDatabase().close();
    }

    @Test
    @DisplayName("Database is initialize, there are no users")
    void DatabaseIsInitializeAndNoAdsArePersisted() {
        assertThat(AdService.getAllAds()).isNotNull();
        assertThat(AdService.getAllAds()).isEmpty();
    }

    @Test
    @DisplayName("Ad is successfully persisted to Database ")
    void testAdIsAddedToDatabase() throws FieldNotCompletedException, DuplicatedAdException {
        AdService.addAd("5", "500", "fridge", "Description", true, false, false, false, "abc", "dragos1", false);
        assertThat(AdService.getAllAds()).isNotEmpty();
        assertThat(AdService.getAllAds()).size().isEqualTo(1);
        Ad ad = AdService.getAllAds().get(0);
        assertThat(ad).isNotNull();
        assertThat(ad.getVusername()).isEqualTo("dragos1");
        assertThat(ad.getDescription()).isEqualTo("Description");
        assertThat(ad.getPhotoPath()).isEqualTo("abc");
        assertThat(ad.getTitle()).isEqualTo("fridge");
        assertThat(ad.getPrice()).isEqualTo("500");
        assertThat(ad.isCars()).isEqualTo(false);
        assertThat(ad.isAppliances()).isEqualTo(true);
        assertThat(ad.isFurniture()).isEqualTo(false);
        assertThat(ad.isClothes()).isEqualTo(false);
        assertThat(ad.isFavorite()).isEqualTo(false);
    }

    @Test
    @DisplayName("Ad can't be added twice")
    void testCheckDuplicateAd(){
        assertThrows(DuplicatedAdException.class,()->{
            AdService.addAd("5","500","fridge","description",true,false,false,false,"abc","dragos1",true);
            AdService.addAd("5","500","fridge","description",true,false,false,false,"abc","dragos1",true);
        });
    }

    @Test
    @DisplayName("Username have to match if you add an ad")
    void testCheckUsernameMatch(){
        assertThrows(WrongUsernameException.class,()->{
            AdService.addAd("5","500","fridge","description",true,false,false,false,"abc","dragos1",true);
            AdService.checkUsernameMatch("notDragos1");
        });
    }

    @Test
    @DisplayName("Username have to match if you add an ad")
    void testCheckTitleMatch() throws UsernameAlreadyExistsException, FieldNotCompletedException, WeakPasswordException, PasswordConfirmationException, UsernameDoesNotExistsException, WrongPasswordException {
        UserService.addUser("dragos1","Dragos1!","Dragos1!","a","a","a","a","a");
        UserService.loginUser("dragos1","Dragos1!");
        assertThrows(TitleDoesNotMatchException.class, () -> {
            AdService.addAd("5", "500", "fridge", "description", true, false, false, false, "abc", "dragos1", true);
            AdService.checkTitleMatch("notFridge");
        });
    }
    @Test
    @DisplayName("All fields have to be completed when adding an ad")
    void testFieldsCompleted() throws FieldNotCompletedException{
        assertThrows(FieldNotCompletedException.class, () -> {
            AdService.checkAllFieldCompleted("", "","",true,true,true,true);
        });
    }

    @Test
    @DisplayName("All fields have to be completed for validation when deleting an ad")
    void testFieldsCompleted1() throws FieldNotCompletedException{
        assertThrows(FieldNotCompletedException.class, () -> {
            AdService.checkAllFieldCompleted("", "");
        });
    }
}