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
import userr.model.Ad;
import userr.model.User;
import userr.services.AdService;
import userr.services.UserService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class HomepageController {
    private static String username;
    private Ad ad;
    private User user1;
    @FXML
    private Text Account;
    @FXML
    private ImageView imageView;

    @FXML
    public ListView<String> title = new ListView<>();
    public ListView<String> price = new ListView<>();
    public ListView<String> appl = new ListView<>();
    public ListView<String> nume = new ListView<>();
    public ListView<String> phone = new ListView<>();
    private static String loggedUser;
    private static ObjectRepository<Ad> adRepository = AdService.getAdRepository();
    private static ObjectRepository<User> userRepository = UserService.getUsers();

    public void initialize(javafx.event.ActionEvent allad) throws IOException {
        /*loggedUser = LoginController.getLoggedUsername();
        Account.setText("Logged in as: " + loggedUser);
        for(User user: userRepository.find())
            if(Objects.equals(loggedUser,user.getUsername())) {
                user1=user;
            }
        if(user1.getPhotoPath()==null) {
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
        ObservableList<String> i = FXCollections.observableArrayList();
        for (Ad ad : adRepository.find()) {

                for(User user : userRepository.find()){
                    if(Objects.equals(ad.getVusername(),user.getUsername())){
                        i.add(user.getPhonenumber());
                        phone.setItems(i);
                    }
                }

        }*/
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("home_page.fxml"));
        Parent viewhomepage = Loader.load();
        Scene homepagescene = new Scene(viewhomepage, 650, 450);
        Stage window = (Stage) ((Node) allad.getSource()).getScene().getWindow();
        window.setScene(homepagescene);
        window.show();
    }

    @FXML
    public void handleAllAction(javafx.event.ActionEvent allad) throws IOException{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("allad.fxml"));
        Parent viewhomepage = Loader.load();
        Scene homepagescene = new Scene(viewhomepage, 650, 450);
        Stage window = (Stage) ((Node) allad.getSource()).getScene().getWindow();
        window.setScene(homepagescene);
        window.show();
    }
    @FXML
    public void handleAppliancesAction(javafx.event.ActionEvent appl) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("appl.fxml"));
        Parent viewhomepage = Loader.load();
        Scene homepagescene = new Scene(viewhomepage, 650, 450);
        Stage window = (Stage) ((Node) appl.getSource()).getScene().getWindow();
        window.setScene(homepagescene);
        window.show();

    }
    @FXML
    public void handleFurnitureAction(javafx.event.ActionEvent homepage) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("furniture.fxml"));
        Parent viewhomepage = Loader.load();
        Scene homepagescene = new Scene(viewhomepage, 650, 450);
        Stage window = (Stage) ((Node) homepage.getSource()).getScene().getWindow();
        window.setScene(homepagescene);
        window.show();
    }
    @FXML
    public void handleClothesAction(javafx.event.ActionEvent homepage) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("clothes.fxml"));
        Parent viewhomepage = Loader.load();
        Scene homepagescene = new Scene(viewhomepage, 650, 450);
        Stage window = (Stage) ((Node) homepage.getSource()).getScene().getWindow();
        window.setScene(homepagescene);
        window.show();
    }
    @FXML
    public void handleCarsAction(javafx.event.ActionEvent homepage) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("car.fxml"));
        Parent viewhomepage = Loader.load();
        Scene homepagescene = new Scene(viewhomepage, 650, 450);
        Stage window = (Stage) ((Node) homepage.getSource()).getScene().getWindow();
        window.setScene(homepagescene);
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
    public void goToCreateAd(javafx.event.ActionEvent create) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("create_ad.fxml"));
        Parent viewCreateAd = Loader.load();
        Scene createAdscene = new Scene(viewCreateAd, 650, 450);
        Stage window = (Stage) ((Node) create.getSource()).getScene().getWindow();
        window.setScene(createAdscene);
        window.show();
    }
    public void goToDeleteAd(javafx.event.ActionEvent delete) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("delete_ad.fxml"));
        Parent viewCreateAd = Loader.load();
        Scene deleteAdscene = new Scene(viewCreateAd, 650, 450);
        Stage window = (Stage) ((Node) delete.getSource()).getScene().getWindow();
        window.setScene(deleteAdscene);
        window.show();
    }
    public void goToMyList(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("my_ads.fxml"));
        Parent viewCreateAd = Loader.load();
        Scene createAdscene = new Scene(viewCreateAd, 650, 450);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(createAdscene);
        window.show();
    }

    public void goToSearch(javafx.event.ActionEvent login) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("search_ad.fxml"));
        Parent viewSearch = Loader.load();
        Scene Searchscene = new Scene(viewSearch, 650, 450);
        Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();
        window.setScene(Searchscene);
        window.show();
    }
    public void goToListFeedback(javafx.event.ActionEvent login) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("feedback_list.fxml"));
        Parent viewSearch = Loader.load();
        Scene Searchscene = new Scene(viewSearch, 650, 450);
        Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();
        window.setScene(Searchscene);
        window.show();
    }
    public void goToListFavorite(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("favorite_list.fxml"));
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
