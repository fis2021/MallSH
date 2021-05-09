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
    TextField titleField;
    @FXML
    TextField ownerField;
    @FXML
    ImageView Photopath;

    @FXML
    Text about;
    @FXML
    Text AddAdMessage;
    @FXML
    public void handleDetailsAction(javafx.event.ActionEvent homepage) throws IOException, InterruptedException {
        try {
            Ad ad1 = new Ad();
            AdService.detailsAd(titleField.getText(),ownerField.getText());
            for(Ad ad: adRepository.find()) {
                if (Objects.equals(titleField.getText(), ad.getTitle()) && Objects.equals(ownerField.getText(), ad.getVusername())) {
                    ad1 = ad;
                }
            }
            about.setText(ad1.getDescription());
            File file = new File(ad1.getPhotoPath());
            String localUrl = file.toURI().toURL().toExternalForm();
            Image profile = new Image(localUrl, false);
            Photopath.setImage(profile);
            Photopath.setFitHeight(175);
            Photopath.setFitWidth(125);
            Photopath.rotateProperty();

        } catch (FieldNotCompletedException e) {
            AddAdMessage.setText(e.getMessage());
            titleField.clear();
            ownerField.clear();

        } catch (TitleDoesNotMatchException e) {
            AddAdMessage.setText(e.getMessage());
            titleField.clear();
            ownerField.clear();
        } catch (WrongUsernameException e) {
            AddAdMessage.setText(e.getMessage());
            titleField.clear();
            ownerField.clear();

        }
    }

    public void goBackToHomepage(javafx.event.ActionEvent homepage) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("home_page.fxml"));
        Parent viewHomepage = Loader.load();
        Scene Homepagescene = new Scene(viewHomepage, 650, 450);
        Stage window = (Stage) ((Node) homepage.getSource()).getScene().getWindow();
        window.setScene(Homepagescene);
        window.show();

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
