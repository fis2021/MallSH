import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import userr.services.AdService;
import userr.services.FavoriteAdService;
import userr.services.FeedbackService;
import userr.services.FileSystemService;
import userr.services.UserService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        UserService.initDatabase();
        AdService.initDatabase();
        FavoriteAdService.initDatabase();
        FeedbackService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("user_login.fxml"));
        primaryStage.setTitle("Registration Example");
        primaryStage.setScene(new Scene(root, 650, 450));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}