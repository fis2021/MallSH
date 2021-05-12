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
        FileSystemService.APPLICATION_FOLDER = ".test-ad-database";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        AdService.initDatabase();
        AdService.addAd("8","200","birou","veche",false,false,false,true,"","kristine",false);
        AdService.addAd("9","1200","cuptor","noua",true,false,false,false,"","kristine",false);
        AdService.addAd("10","2200","clio","noua",false,false,true,false,"","karina",false);
        AdService.addAd("11","22","tricou","roz",false,true,false,false,"","karina",false);
        FileSystemService.APPLICATION_FOLDER = ".test-user-database";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        UserService.addUser("kristine","Kristine17!","Kristine17!","Kristine","Senciuc","0744670830","Timisoara","");
        UserService.addUser("karina","Karina25!","Karina25!","Karina","Senciuc","0789123456","Timisoara","");
        String loggedUser = LoginController.getLoggedUsername();
        loggedUser="kristine";
    }

    @AfterEach
    void tearDown() throws IOException {
        AdService.getDatabase().close();
        UserService.getDatabase().close();
    }
    @Start
    void start(Stage stage) throws IOException {
        AdService.initDatabase();
        UserService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("user_login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        AdService.getDatabase().close();
        UserService.getDatabase().close();
    }

    @Test
    void testlistOfAds(FxRobot robot) {
        robot.clickOn("#username1");
        robot.write("kristine");
        robot.clickOn("#password1");
        robot.write("Kristine17!");
        robot.clickOn("#loginbutton");
        robot.clickOn("#all");
        robot.clickOn("#appl");
        robot.clickOn("#furniture");
        robot.clickOn("#clothes");
        robot.clickOn("#car");
        robot.clickOn("#all");
    }
}