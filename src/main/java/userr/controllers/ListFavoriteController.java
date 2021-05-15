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

public class ListFavoriteController {

    private static String loggedUser;
    private static ObjectRepository<Ad> adRepository = AdService.getAdRepository();
    private static ObjectRepository<User> userRepository = UserService.getUsers();
    private static ObjectRepository<Ad> favRepository = FavoriteAdService.getFavoriteRepository();
    @FXML
    public ListView<String> favtitle = new ListView<>();
    public ListView<String> favprice = new ListView<>();
    public ListView<String> favcategory = new ListView<>();
    public ListView<String> favname = new ListView<>();
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
        for(User user: userRepository.find())
            if(Objects.equals(loggedUser,user.getUsername())) {
                user1=user;
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


        ObservableList<String> i = FXCollections.observableArrayList ();
        for (Ad ad : favRepository.find()) {
            if( FavoriteAdService.checkIfMyFavorite(ad,loggedUser)){
                i.add(ad.getTitle());
                favtitle.setItems(i);
            }
        }
        ObservableList<String> j = FXCollections.observableArrayList ();
        for(Ad ad1: favRepository.find()){
            if (FavoriteAdService.checkIfMyFavorite(ad1, loggedUser)){
                for (Ad ad : adRepository.find()) {
                        if (Objects.equals(ad1.getVusername(), ad.getVusername()) && Objects.equals(ad1.getTitle(), ad.getTitle())) {
                            j.add(ad.getPrice());
                            favprice.setItems(j);
                        }
                }
        }
        }
        ObservableList<String> l = FXCollections.observableArrayList ();
        for (Ad ad1 : favRepository.find()) {
            if(  FavoriteAdService.checkIfMyFavorite(ad1,loggedUser)){
                for (Ad ad : adRepository.find()) {
                    if (Objects.equals(ad1.getVusername(), ad.getVusername()) && Objects.equals(ad1.getTitle(), ad.getTitle())) {
                        if(ad.isAppliances()==true)
                            l.add("Appliances");
                        else if(ad.isFurniture()==true)
                            l.add("Furniture");
                        else if(ad.isClothes()==true)
                            l.add("Clothes");
                        else if(ad.isCars()==true)
                            l.add("Cars");
                        favcategory.setItems(l);
                    }
                }

            }
        }
        ObservableList<String> m = FXCollections.observableArrayList ();
        for (Ad ad : favRepository.find()) {
            if(  FavoriteAdService.checkIfMyFavorite(ad,loggedUser)){
                m.add(ad.getVusername());
                favname.setItems(m);
            }
        }
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
    public void goBackToLogin(javafx.event.ActionEvent login) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("user_login.fxml"));
        Parent viewuserLogin = Loader.load();
        Scene Loginscene = new Scene(viewuserLogin, 650, 450);
        Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();
        window.setScene(Loginscene);
        window.show();

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

}