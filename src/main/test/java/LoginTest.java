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
import userr.services.FileSystemService;
import userr.services.UserService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class LoginTest {

    @AfterEach
    void tearDown() {
        UserService.getDatabase().close();
        AdService.getDatabase().close();
    }
    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER=".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        AdService.initDatabase();
    }
    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("user_login.fxml"));
        primaryStage.setTitle("Registration Example");
        primaryStage.setScene(new Scene(root, 650, 450));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    @Test
    void testRegistration(FxRobot robot) {
        robot.clickOn("#username1");
        robot.write("kristine");
        robot.clickOn("#password1");
        robot.write("Kristine17!");
        robot.clickOn("#loginbutton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText("An account with that username doesn't exists!");
        robot.clickOn("#register");

        robot.clickOn("#firstname");
        robot.write("");
        robot.clickOn("#secondname");
        robot.write("Senciuc");
        robot.clickOn("#adress");
        robot.write("Timisoara");
        robot.clickOn("#phone");
        robot.write("0744670830");
        robot.clickOn("#username");
        robot.write("kristine");
        robot.clickOn("#password");
        robot.write("Kristine17!");
        robot.clickOn("#passwordconfirm");
        robot.write("Kristine17");
        robot.clickOn("#buttonRegister");
        assertThat(robot.lookup("#registermessage").queryText()).hasText("Please complete all necessary fields!");

        robot.clickOn("#firstname");
        robot.write("Kristine");
        robot.clickOn("#password");
        robot.write("Kristine17!");
        robot.clickOn("#passwordconfirm");
        robot.write("Kristine17");
        robot.clickOn("#buttonRegister");
        assertThat(robot.lookup("#registermessage").queryText()).hasText("Password confirmation doesn`t match password!");
        robot.clickOn("#buttonRegister");

        robot.clickOn("#password");
        robot.write("kristine17!");
        robot.clickOn("#passwordconfirm");
        robot.write("kristine17!");
        robot.clickOn("#buttonRegister");
        assertThat(robot.lookup("#registermessage").queryText()).hasText(String.format("Password does not contain at least one upper case !" ));
        robot.clickOn("#buttonRegister");

        robot.clickOn("#password");
        robot.write("Kris17!");
        robot.clickOn("#passwordconfirm");
        robot.write("Kris17!");
        robot.clickOn("#buttonRegister");
        assertThat(robot.lookup("#registermessage").queryText()).hasText(String.format("Password does not contain at least 8 characters !" ));
        robot.clickOn("#buttonRegister");

        robot.clickOn("#password");
        robot.write("Kristine!");
        robot.clickOn("#passwordconfirm");
        robot.write("Kristine!");
        robot.clickOn("#buttonRegister");
        assertThat(robot.lookup("#registermessage").queryText()).hasText(String.format("Password does not contain at least one digit !" ));
        robot.clickOn("#buttonRegister");

        robot.clickOn("#password");
        robot.write("Kristine17!");
        robot.clickOn("#passwordconfirm");
        robot.write("Kristine17!");
        robot.clickOn("#buttonRegister");

        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        robot.clickOn("#register");
        robot.clickOn("#firstname");
        robot.write("Kris");
        robot.clickOn("#secondname");
        robot.write("Senciuc");
        robot.clickOn("#adress");
        robot.write("Bucuresti");
        robot.clickOn("#phone");
        robot.write("0744670830");
        robot.clickOn("#username");
        robot.write("kristine");
        robot.clickOn("#password");
        robot.write("Kristine17!");
        robot.clickOn("#passwordconfirm");
        robot.write("Kristine17");
        robot.clickOn("#buttonRegister");
        assertThat(robot.lookup("#registermessage").queryText()).hasText("An account with the username kristine already exists!");

        robot.clickOn("#buttonBackLogin");

        robot.clickOn("#username1");
        robot.write("kristin");
        robot.clickOn("#password1");
        robot.write("Kristine1");
        robot.clickOn("#loginbutton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText("An account with that username doesn't exists!");
        robot.clickOn("#username1");
        robot.write("kristine");
        robot.clickOn("#password1");
        robot.write("Kristine17");
        robot.clickOn("#loginbutton");
        assertThat(robot.lookup("#LoginMessage").queryText()).hasText("Wrong password!");
        robot.clickOn("#username1");
        robot.write("");
        robot.clickOn("#password1");
        robot.write("Kristine17!");
        robot.clickOn("#loginbutton");




    }
}