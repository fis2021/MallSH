package userr.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import userr.exceptions.*;
import userr.model.User;
import userr.services.UserService;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static userr.services.UserService.getUsers;

import static userr.services.FileSystemService.getPathToFile;


public class HomepageController {

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
