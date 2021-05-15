package userr.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import userr.exceptions.FieldNotCompletedException;
import userr.exceptions.TitleDoesNotMatchException;
import userr.exceptions.WrongUsernameException;
import userr.model.Ad;
import userr.model.User;
import userr.services.AdService;
import userr.services.UserService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SearchDetailController {
    private String loggedUser;
    private static ObjectRepository<Ad> adRepository = AdService.getAdRepository();
    private static ObjectRepository<User> userRepository = UserService.getUsers();

    @FXML
    TextField titleField;
    @FXML
    TextField ownerField;
    @FXML
    Text AddAdMessage;

    public static Ad ad1 = new Ad();
    @FXML
    public void handleDetailsAction(javafx.event.ActionEvent details) throws IOException {
        try {
            AdService.detailsAd(titleField.getText(),ownerField.getText());
            for(Ad ad: adRepository.find()) {
                if (Objects.equals(titleField.getText(), ad.getTitle()) && Objects.equals(ownerField.getText(), ad.getVusername())) {
                    ad1 = ad;
                }
            }
            {
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getClassLoader().getResource("details_ad.fxml"));
                Parent viewDetail = Loader.load();
                Scene Detailscene = new Scene(viewDetail, 650, 450);
                Stage window1 = (Stage) ((Node) details.getSource()).getScene().getWindow();
                window1.setScene(Detailscene);
                window1.show();
            }
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
