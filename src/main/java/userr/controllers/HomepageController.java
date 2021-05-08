package userr.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import userr.exceptions.*;
import userr.model.User;
import userr.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import userr.model.Ad;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import userr.services.AdService;

import java.io.File;

import static userr.services.UserService.getUsers;

import static userr.services.FileSystemService.getPathToFile;
import javafx.scene.image.Image;


public class HomepageController {
    private Ad ad;
    @FXML
    private Text Account;
    @FXML
    private ImageView imageView;
    @FXML
    private User user;
    private User user1;


    @FXML
    public ListView<String> title = new ListView<>();
    public ListView<String> price = new ListView<>();
    public ListView<String> description = new ListView<>();
    public ListView<String> appl = new ListView<>();
    public ListView<String> nume = new ListView<>();
    private static String loggedUser;
    private static ObjectRepository<Ad> adRepository = AdService.getAdRepository();
    private static ObjectRepository<User> userRepository = UserService.getUsers();

    @FXML
    public void initialize() throws IOException {
        loggedUser = LoginController.getLoggedUsername();
        Account.setText("Logged in as: " + loggedUser);
        for(User user: userRepository.find())
            if(Objects.equals(loggedUser,user.getUsername())) {
                user1=user;
            }
        File file = new File(user1.getPhotoPath());
        String localUrl = file.toURI().toURL().toExternalForm();
        Image profile = new Image(localUrl, false);
        imageView.setImage(profile);
        imageView.setFitHeight(175);
        imageView.setFitWidth(125);
        imageView.rotateProperty();

        ObservableList<String> a = FXCollections.observableArrayList();
        for (Ad ad : adRepository.find()) {
            {
                a.add(ad.getTitle());
                title.setItems(a);
            }
        }
        ObservableList<String> b = FXCollections.observableArrayList();
        for (Ad ad : adRepository.find()) {
            {
                b.add(ad.getPrice());
                price.setItems(b);
            }
        }
        ObservableList<String> c = FXCollections.observableArrayList();
        for (Ad ad : adRepository.find()) {
            {
                c.add(ad.getDescription());
                description.setItems(c);
            }
        }
        ObservableList<String> d = FXCollections.observableArrayList();
        for (Ad ad : adRepository.find()) {
            {
                if(ad.isAppliances()==true)
                    d.add("Appliances");
                else if(ad.isFurniture()==true)
                    d.add("Furniture");
                else if(ad.isClothes()==true)
                    d.add("Clothes");
                else if(ad.isCars()==true)
                    d.add("Cars");
                appl.setItems(d);
            }
        }

        ObservableList<String> h = FXCollections.observableArrayList();
        for (Ad ad : adRepository.find()) {
            {
                h.add(ad.getVusername());
                nume.setItems(h);
            }
        }


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
    public void goToCreateAd(javafx.event.ActionEvent create) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("create_ad.fxml"));
        Parent viewCreateAd = Loader.load();
        Scene createAdscene = new Scene(viewCreateAd, 650, 450);
        Stage window = (Stage) ((Node) create.getSource()).getScene().getWindow();
        window.setScene(createAdscene);
        window.show();
    }
    public void minimizeWindow(javafx.event.ActionEvent min) {
        Stage window = (Stage) ((Node) min.getSource()).getScene().getWindow();
        window.setIconified(true);
    }

    public void closeWindow(javafx.event.ActionEvent close) {
        Stage window = (Stage) ((Node) close.getSource()).getScene().getWindow();
        window.close();
    }
}
