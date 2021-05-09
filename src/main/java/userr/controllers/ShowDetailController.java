package userr.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.ObjectRepository;
import userr.exceptions.DuplicatedAdException;
import userr.exceptions.FieldNotCompletedException;
import userr.exceptions.TitleDoesNotMatchException;
import userr.exceptions.WrongUsernameException;
import userr.model.Ad;
import userr.model.User;
import userr.services.AdService;
import userr.services.MyAdsService;
import userr.services.UserService;
import userr.controllers.RegistrationController;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Objects;
import userr.model.Ad;
import javafx.scene.image.ImageView;


import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class ShowDetailController {
    private String loggedUser;
    private static ObjectRepository<Ad> adRepository = AdService.getAdRepository();
    private static ObjectRepository<User> userRepository = UserService.getUsers();

    @FXML
    Text Title;
    @FXML
    Text User;
    @FXML
    ImageView Photopath;

    @FXML
    ImageView savePath;

    @FXML
    Text about;
    @FXML
    Text AddAdMessage;
    @FXML
    public void initialize() throws IOException {
            about.setText(SearchDetailController.ad1.getDescription());
            if(SearchDetailController.ad1.getPhotoPath()==null)
            {
                String pathUser = "src/main/resources/no_image.png";
                File file = new File(pathUser);
                String localUrl = file.toURI().toURL().toExternalForm();
                Image profile = new Image(localUrl, false);
                Photopath.setImage(profile);
                Photopath.setFitHeight(350);
                Photopath.setFitWidth(250);
                Photopath.rotateProperty();
                Title.setText(SearchDetailController.ad1.getTitle());
                User.setText(SearchDetailController.ad1.getVusername());
            }
            else {
                File file = new File(SearchDetailController.ad1.getPhotoPath());
                String localUrl = file.toURI().toURL().toExternalForm();
                Image adImg = new Image(localUrl, false);
                Photopath.setImage(adImg);
                Photopath.setFitHeight(350);
                Photopath.setFitWidth(250);
                Photopath.rotateProperty();
                Title.setText(SearchDetailController.ad1.getTitle());
                User.setText(SearchDetailController.ad1.getVusername());
            }
    }

    public void goBackToSearch(javafx.event.ActionEvent search) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("search_ad.fxml"));
        Parent viewSearch = Loader.load();
        Scene Searchscene = new Scene(viewSearch, 650, 450);
        Stage window = (Stage) ((Node) search.getSource()).getScene().getWindow();
        window.setScene(Searchscene);
        window.show();
    }
    public static Ad ad1 = new Ad();
    public void handleSaveAdAction(javafx.event.ActionEvent search) throws IOException {
        for(Ad ad: adRepository.find()) {
            if (Objects.equals(Title.getText(), ad.getTitle()) && Objects.equals(User.getText(), ad.getVusername())) {
                ad1 = ad;
            }
        }
        saveAdPicture();
    }

    private void saveAdPicture() throws MalformedURLException {
        ad1.setFavorite(true);
        String pathUser = "src/main/resources/red_h.png";
        File file = new File(pathUser);
        String localUrl = file.toURI().toURL().toExternalForm();
        Image saveImg = new Image(localUrl, false);
        savePath.setLayoutY(396);
        savePath.setLayoutX(594);
        savePath.setFitWidth(74);
        savePath.setFitHeight(53);
        savePath.setImage(saveImg);
    }
    public void minimizeWindow(javafx.event.ActionEvent min) {
        Stage window = (Stage) ((Node)min.getSource()).getScene().getWindow();
        window.setIconified(true);
    }

    public void closeWindow(javafx.event.ActionEvent close) {
        Stage window = (Stage) ((Node)close.getSource()).getScene().getWindow();
        window.close();
    }
}
