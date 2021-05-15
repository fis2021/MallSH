package userr.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
import userr.services.AdService;
import userr.services.FavoriteAdService;
import userr.services.FileSystemService;
import userr.services.UserService;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class SearchDetailControllerTest {
    @AfterAll
    static void afterAll() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-D";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        AdService.initDatabase();
        AdService.addAd("8","200","table","veche",false,false,false,true,"","kristine",false);
        AdService.addAd("9","1200","cuptor","noua",true,false,false,false,"","kristine",false);
        AdService.addAd("10","2200","clio","noua",false,false,true,false,"","karina",false);
        AdService.addAd("11","22","tricou","roz",false,true,false,false,"","karina",false);
        UserService.initDatabase();
        UserService.addUser("kristine","Kristine17!","Kristine17!","Kristine","Senciuc","0744670830","Timisoara",null);
        UserService.addUser("karina","Karina25!","Karina25!","Karina","Senciuc","0789123456","Timisoara",null);
        FavoriteAdService.initDatabase();
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
    void testDetails(FxRobot robot)
    {
        AdService.initDatabase();
        robot.clickOn("#username1");
        robot.write("kristine");
        robot.clickOn("#password1");
        robot.write("Kristine17!");
        robot.clickOn("#loginbutton");
        robot.clickOn("#searchButton");
        robot.clickOn("#searchHomepage");
        robot.clickOn("#searchButton");
        robot.clickOn("#searchTitle");
        robot.clickOn("#searchOwner");
        robot.write("karina");
        robot.clickOn("#displayButton");
        assertThat(robot.lookup("#searchMsg").queryText()).hasText("Please complete all necessary fields!");

        robot.clickOn("#searchTitle");
        robot.write("notAProduct");
        robot.clickOn("#searchOwner");
        robot.write("karina");
        robot.clickOn("#displayButton");
        assertThat(robot.lookup("#searchMsg").queryText()).hasText("an ad with this title was not created");

        robot.clickOn("#searchTitle");
        robot.write("table");
        robot.clickOn("#searchOwner");
        robot.write("karina");
        robot.clickOn("#displayButton");
        assertThat(robot.lookup("#searchMsg").queryText()).hasText("an ad with this title was not created");

        robot.clickOn("#searchTitle");
        robot.write("table");
        robot.clickOn("#searchOwner");
        robot.write("notAnOwner");
        robot.clickOn("#displayButton");
        assertThat(robot.lookup("#searchMsg").queryText()).hasText("username validation failed!");

        robot.clickOn("#searchTitle");
        robot.write("table");
        robot.clickOn("#searchOwner");
        robot.write("kristine");
        robot.clickOn("#displayButton");
        robot.clickOn("#showBack");
        robot.clickOn("#searchTitle");
        robot.write("table");
        robot.clickOn("#searchOwner");
        robot.write("kristine");
        robot.clickOn("#displayButton");
        robot.clickOn("#saveButton");
        assertThat(FavoriteAdService.getAllFavAds()).size().isEqualTo(1);
        robot.clickOn("#saveButton");
        assertThat(FavoriteAdService.getAllFavAds()).size().isEqualTo(0);
    }

}