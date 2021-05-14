package userr.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import userr.exceptions.DuplicatedAdException;
import userr.exceptions.FieldNotCompletedException;
import userr.model.User;
import userr.services.AdService;
import userr.services.FavoriteAdService;
import userr.services.FileSystemService;
import userr.services.UserService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class HomepageControllerTest {
    @AfterAll
    static void afterAll() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        AdService.initDatabase();
        AdService.addAd("8","200","birou","veche",false,false,false,true,"","kristine",true);
        AdService.addAd("9","1200","cuptor","noua",true,false,false,false,"","kristine",false);
        AdService.addAd("10","2200","clio","noua",false,false,true,false,"","karina",true);
        AdService.addAd("11","22","tricou","roz",false,true,false,false,"","karina",true);
        UserService.initDatabase();
        UserService.addUser("kristine","Kristine17!","Kristine17!","Kristine","Senciuc","0744670830","Timisoara","");
        UserService.addUser("karina","Karina25!","Karina25!","Karina","Senciuc","0789123456","Timisoara","");
       FavoriteAdService.initDatabase();
        FavoriteAdService.addFavorite("1","kristine","clio","karina",true);
        FavoriteAdService.addFavorite("2","kristine","birou","kristine",true);
        FavoriteAdService.addFavorite("3","karina","birou","kristine",true);
        String loggedUser = LoginController.getLoggedUsername();
        AdService.getDatabase().close();
    }

    @AfterEach
    void tearDown() throws IOException {
        AdService.getDatabase().close();
        UserService.getDatabase().close();
        FavoriteAdService.getDatabase().close();
    }
    @Start
    void start(Stage stage) throws IOException {
        AdService.initDatabase();
        UserService.initDatabase();
        FavoriteAdService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("user_login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        AdService.getDatabase().close();
        UserService.getDatabase().close();
        FavoriteAdService.getDatabase().close();
    }

    @Test
    void testlistOfAds(FxRobot robot) throws DuplicatedAdException, FieldNotCompletedException {
        AdService.initDatabase();
        AdService.addAd("12","200","birouu","veche",false,false,false,true,"","kristine",true);
        AdService.addAd("13","1200","cuptoru","noua",true,false,false,false,"","kristine",false);
        AdService.addAd("14","2200","cliou","noua",false,false,true,false,"","karina",true);
        AdService.addAd("15","22","tricouu","roz",false,true,false,false,"","karina",true);

        robot.clickOn("#username1");
        robot.write("kristine");
        robot.clickOn("#password1");
        robot.write("Kristine17!");
        robot.clickOn("#loginbutton");
        robot.clickOn("#all");
        robot.clickOn("#home");
        robot.clickOn("#appl");
        robot.clickOn("#home");
        robot.clickOn("#furniture");
        robot.clickOn("#homee");
        robot.clickOn("#clothes");
        robot.clickOn("#homee");
        robot.clickOn("#car");
        robot.clickOn("#homee");
        robot.clickOn("#all");
        robot.clickOn("#buttonMyList");
        robot.clickOn("#buttongotohomepage");
        robot.clickOn("#buttonfavoritelist");
        robot.clickOn("#buttontohome");
        robot.clickOn("#logout");
        robot.clickOn("#username1");
        robot.write("karina");
        robot.clickOn("#password1");
        robot.write("Karina25!");
        robot.clickOn("#loginbutton");
        robot.clickOn("#all");
        robot.clickOn("#home");
        robot.clickOn("#appl");
        robot.clickOn("#home");
        robot.clickOn("#furniture");
        robot.clickOn("#homee");
        robot.clickOn("#clothes");
        robot.clickOn("#homee");
        robot.clickOn("#car");
        robot.clickOn("#homee");
        robot.clickOn("#all");
        robot.clickOn("#buttonMyList");
        robot.clickOn("#buttongotohomepage");
        robot.clickOn("#buttonfavoritelist");
        robot.clickOn("#buttontohome");

    }
}