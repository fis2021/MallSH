package userr.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import userr.model.Feedback;
import userr.model.User;
import userr.services.FeedbackService;
import userr.services.UserService;


import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ListFeedbackController {
    private static String loggedUser;
    private static ObjectRepository<Feedback> feedbackRepository = FeedbackService.getFeedbackRepository();
    private static ObjectRepository<User> userRepository = UserService.getUsers();
    @FXML
    public ListView<String> username = new ListView<>();
    public ListView<String> feedback = new ListView<>();
    public ListView<String> rate = new ListView<>();
    @FXML
    private Text Account;
    @FXML
    private Text media;
    @FXML
    private ImageView imageView;
    @FXML
    private User user1;

    @FXML
    public void initialize() throws IOException {
        loggedUser = LoginController.getLoggedUsername();
        Account.setText("Logged in as: " + loggedUser);
        for (User user : userRepository.find())
            if (Objects.equals(loggedUser, user.getUsername())) {
                user1 = user;
            }
        if(user1.getPhotoPath()==null)
        {
            String pathUser = "src/main/resources/no_image.png";
            File file = new File(pathUser);
            String localUrl = file.toURI().toURL().toExternalForm();
            Image profile = new Image(localUrl, false);
            imageView.setImage(profile);
            imageView.setFitHeight(175);
            imageView.setFitWidth(125);
            imageView.rotateProperty();
        }
        else {
            File file = new File(user1.getPhotoPath());
            String localUrl = file.toURI().toURL().toExternalForm();
            Image profile = new Image(localUrl, false);
            imageView.setImage(profile);
            imageView.setFitHeight(175);
            imageView.setFitWidth(125);
            imageView.rotateProperty();
        }
        ObservableList<String> i = FXCollections.observableArrayList();
        for (Feedback fb : feedbackRepository.find()) {
            i.add(fb.getLoggedUser());
            username.setItems(i);

        }
        ObservableList<String> j = FXCollections.observableArrayList();
        for (Feedback fb : feedbackRepository.find()) {
            j.add(fb.getDescription());
            feedback.setItems(j);

        }
        ObservableList<String> k = FXCollections.observableArrayList();
        for (Feedback fb : feedbackRepository.find()) {
            k.add(fb.getRate());
            rate.setItems(k);

        }
        media.setText("Rating of the app: " + String.valueOf(mediaAritmetica()));
    }
    public float mediaAritmetica()
    {
        float s = 0,contor = 0;
        for(Feedback fb: feedbackRepository.find())
        {
            s = s + Float.parseFloat(fb.getRate());
            contor++;
        }
        return s/contor;
    }
    public void goToGiveFeedback(javafx.event.ActionEvent login) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("give_feedback.fxml"));
        Parent viewuserLogin = Loader.load();
        Scene Loginscene = new Scene(viewuserLogin, 650, 450);
        Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();
        window.setScene(Loginscene);
        window.show();

    }
    @FXML
    public void minimizeWindow(javafx.event.ActionEvent min) {
        Stage window = (Stage) ((Node) min.getSource()).getScene().getWindow();
        window.setIconified(true);
    }

    public void closeWindow(javafx.event.ActionEvent close) {
        Stage window = (Stage) ((Node) close.getSource()).getScene().getWindow();
        window.close();
    }
    public void goBackToHomepage(javafx.event.ActionEvent login) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("home_page.fxml"));
        Parent viewuserLogin = Loader.load();
        Scene Loginscene = new Scene(viewuserLogin, 650, 450);
        Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();
        window.setScene(Loginscene);
        window.show();

    }
    @FXML
    public void goBackToLogin(javafx.event.ActionEvent login) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("user_login.fxml"));
        Parent viewuserLogin = Loader.load();
        Scene Loginscene = new Scene(viewuserLogin, 650, 450);
        Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();
        window.setScene(Loginscene);
        window.show();

    }
}
