package userr.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
import userr.model.Feedback;
import userr.services.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class ListFeedbackControllerTest {
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
        FileSystemService.APPLICATION_FOLDER = ".test-fb-database";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        FeedbackService.initDatabase();
        FileSystemService.APPLICATION_FOLDER = ".test-userr-database";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        UserService.addUser("kristine","Kristine17!","Kristine17!","Kristine","Senciuc","0744670830","Timisoara","");
        UserService.addUser("karina","Karina25!","Karina25!","Karina","Senciuc","0789123456","Timisoara","");
        FileSystemService.APPLICATION_FOLDER = ".test-fav-database";
        String loggedUser = LoginController.getLoggedUsername();
    }
    @AfterEach
    void tearDown() throws IOException {
        UserService.getDatabase().close();
        FeedbackService.getDatabase().close();
        AdService.getDatabase().close();
    }
    @Start
    void start(Stage stage) throws IOException {
        UserService.initDatabase();
        FeedbackService.initDatabase();
        AdService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("user_login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        UserService.getDatabase().close();
        FeedbackService.getDatabase().close();
        AdService.getDatabase().close();
    }
    @Test
    void testFeedback(FxRobot robot) {
        robot.clickOn("#username1");
        robot.write("kristine");
        robot.clickOn("#password1");
        robot.write("Kristine17!");
        robot.clickOn("#loginbutton");
        robot.clickOn("#feedback");
        robot.clickOn("#buttongotofb");
        robot.clickOn("#description");
        robot.write("foarte faina aplicatia");
        robot.clickOn("#rate");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#givefb");
        robot.clickOn("#logout");

        robot.clickOn("#username1");
        robot.write("kristine");
        robot.clickOn("#password1");
        robot.write("Kristine17!");
        robot.clickOn("#loginbutton");
        robot.clickOn("#feedback");
        robot.clickOn("#buttongotofb");
        robot.clickOn("#description");
        robot.write("nu mi-a placut asa mult");
        robot.clickOn("#rate");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#givefb");
        assertThat(robot.lookup("#unique").queryText()).hasText("Feedback given by username kristine already exists!");
        robot.clickOn("#homepage");
        robot.clickOn("#logout");

        robot.clickOn("#username1");
        robot.write("karina");
        robot.clickOn("#password1");
        robot.write("Karina25!");
        robot.clickOn("#loginbutton");
        robot.clickOn("#feedback");
        robot.clickOn("#buttongotofb");
        robot.clickOn("#description");
        robot.write("");
        robot.clickOn("#rate");
        robot.clickOn("#givefb");
        assertThat(robot.lookup("#unique").queryText()).hasText("Please complete all necessary fields!");
        robot.clickOn("#description");
        robot.write("nu mi-a placut asa mult");
        robot.clickOn("#rate");
        robot.clickOn("#givefb");
        assertThat(robot.lookup("#unique").queryText()).hasText("Please complete all necessary fields!");
        robot.clickOn("#description");
        robot.write("nu mi-a placut asa mult");
        robot.clickOn("#rate");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#givefb");
        robot.clickOn("#home");
        robot.clickOn("#logout");
    }

}