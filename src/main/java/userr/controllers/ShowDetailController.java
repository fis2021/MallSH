package userr.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.ObjectRepository;
import userr.exceptions.*;
import userr.model.Ad;
import userr.model.User;
import userr.services.AdService;
import userr.services.FavoriteAdService;
import userr.services.UserService;

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
    private static ObjectRepository<Ad> favAdRepo = FavoriteAdService.getFavoriteRepository();

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
    Text NotFavMessage;

    @FXML
    public static Ad ad1 = new Ad();
    @FXML
    public void initialize() throws IOException {
        about.setText(SearchDetailController.ad1.getDescription());
        for(Ad ad : favAdRepo.find()) {
            if (Objects.equals(LoginController.getLoggedUsername(), ad.getOwnUsername()) && Objects.equals(ad.isFavorite(), true)
                    && Objects.equals(ad.getVusername(),SearchDetailController.ad1.getVusername()) && Objects.equals(ad.getTitle(),SearchDetailController.ad1.getTitle())) {
                saveAdPicture();
            }
        }
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
    public void handleSaveAdAction() throws IOException {
            loggedUser = LoginController.getLoggedUsername();
            if(!Objects.equals(Title.getText(),ad1.getTitle()) || !Objects.equals(loggedUser,ad1.getOwnUsername())
                    || !Objects.equals(User.getText(),ad1.getVusername()))
            {
                ad1.setFavorite(false);
            }
            for (Ad ad : adRepository.find()) {
                if (Objects.equals(Title.getText(), ad.getTitle()) && Objects.equals(User.getText(), ad.getVusername()) && Objects.equals(ad1.isFavorite(), false)) {
                    ad1 = ad;
                    ad1.setOwnUsername(loggedUser);
                    ad1.setFavorite(false);
                }
            }
            if (!Objects.equals(ad1.isFavorite(), false) && Objects.equals(ad1.getOwnUsername(), LoginController.getLoggedUsername())
                    && Objects.equals(ad1.getVusername(), User.getText()) && Objects.equals(ad1.getTitle(), Title.getText())) {
                FavoriteAdService.deleteFavorite(loggedUser, ad1.getTitle(), ad1.getVusername());
                ad1.setFavorite(false);
                undoSaveAdPicture();
            } else {
                String id = NitriteId.newId().toString();
                FavoriteAdService.addFavorite(id, loggedUser, ad1.getTitle(), ad1.getVusername(), true);
                ad1.setFavorite(true);
                saveAdPicture();
            }
    }

    private void saveAdPicture() throws MalformedURLException {
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
    private void undoSaveAdPicture() throws MalformedURLException {
        String pathUser = "src/main/resources/green_h.png";
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
