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
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import userr.model.Ad;
import userr.services.AdService;
import userr.services.FavoriteAdService;
import userr.model.User;
import javafx.scene.text.Text;
import userr.services.UserService;


import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ClothesControllers {

    private static String loggedUser;
    private static ObjectRepository<Ad> adRepository = AdService.getAdRepository();
    private static ObjectRepository<User> userRepository = UserService.getUsers();
    private static ObjectRepository<Ad> favRepository = FavoriteAdService.getFavoriteRepository();
    @FXML
    public ListView<String> title = new ListView<>();
    public ListView<String> price = new ListView<>();
    public ListView<String> appl = new ListView<>();
    public ListView<String> nume = new ListView<>();
    public ListView<String> phone = new ListView<>();
    @FXML
    private Text Account;
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
        if (user1.getPhotoPath() == null) {
            String pathUser = "src/main/resources/no_image.png";
            File file = new File(pathUser);
            String localUrl = file.toURI().toURL().toExternalForm();
            Image profile = new Image(localUrl, false);
            imageView.setImage(profile);
            imageView.setFitHeight(175);
            imageView.setFitWidth(125);
            imageView.rotateProperty();

        } else {
            File file = new File(user1.getPhotoPath());
            String localUrl = file.toURI().toURL().toExternalForm();
            Image profile = new Image(localUrl, false);
            imageView.setImage(profile);
            imageView.setFitHeight(175);
            imageView.setFitWidth(125);
            imageView.rotateProperty();
        }
        ObservableList<String> a = FXCollections.observableArrayList();
        for (Ad ad : adRepository.find()) {
            {
                if(ad.isClothes()==true)
                    a.add(ad.getTitle());
                title.setItems(a);
            }
        }
        ObservableList<String> b = FXCollections.observableArrayList();
        for (Ad ad : adRepository.find()) {
            {
                if(ad.isClothes()==true)
                    b.add(ad.getPrice());
                price.setItems(b);
            }
        }

        ObservableList<String> d = FXCollections.observableArrayList();
        for (Ad ad : adRepository.find()) {
            {
                if (ad.isClothes() == true)
                    d.add("Clothes");
                appl.setItems(d);
            }
        }

        ObservableList<String> h = FXCollections.observableArrayList();
        for (Ad ad : adRepository.find()) {
            {
                if (ad.isClothes() == true)
                    h.add(ad.getVusername());
                nume.setItems(h);
            }
        }

        ObservableList<String> i = FXCollections.observableArrayList();
        for (Ad ad : adRepository.find()) {
            if (ad.isClothes() == true) {
                for (User user : userRepository.find()) {
                    if (Objects.equals(ad.getVusername(), user.getUsername())) {
                        i.add(user.getPhonenumber());
                        phone.setItems(i);
                    }
                }
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
    @FXML
    public void goToHomePage(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("home_page.fxml"));
        Parent viewSearch = Loader.load();
        Scene Searchscene = new Scene(viewSearch, 650, 450);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(Searchscene);
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