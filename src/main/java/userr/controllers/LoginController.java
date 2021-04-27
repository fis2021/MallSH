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


public class LoginController {

    @FXML
    private Text loginMessage;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private static String loggedUsername;

    @FXML
    public void handleLoginAction(javafx.event.ActionEvent homepage) throws IOException {
        try {
            UserService.loginUser(usernameField.getText(), passwordField.getText());
            loginMessage.setText("Login successfully!");
            for (User user : getUsers().find()) {
                if (Objects.equals(usernameField.getText(), user.getUsername())) {
                    this.loggedUsername = user.getUsername();
                }
            }
            {
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getClassLoader().getResource("home_page.fxml"));
                Parent viewhomepage = Loader.load();
                Scene homepagescene = new Scene(viewhomepage, 650, 450);
                Stage window = (Stage) ((Node) homepage.getSource()).getScene().getWindow();
                window.setScene(homepagescene);
                window.show();
            }

        } catch (UsernameDoesNotExistsException e) {
            loginMessage.setText(e.getMessage());
            usernameField.clear();
            passwordField.clear();
        } catch (WrongPasswordException e) {
            loginMessage.setText(e.getMessage());
            passwordField.clear();
        }
    }
    public void goBackToRegistration(javafx.event.ActionEvent register) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("user_registration.fxml"));
        Parent viewuserRegister = Loader.load();
        Scene Registerscene = new Scene(viewuserRegister, 650, 465);
        Stage window = (Stage) ((Node) register.getSource()).getScene().getWindow();
        window.setScene(Registerscene);
        window.show();

    }
    public static String getLoggedUsername(){return loggedUsername;}
    public void minimizeWindow(javafx.event.ActionEvent min) {
        Stage window = (Stage) ((Node) min.getSource()).getScene().getWindow();
        window.setIconified(true);
    }

    public void closeWindow(javafx.event.ActionEvent close) {
        Stage window = (Stage) ((Node) close.getSource()).getScene().getWindow();
        window.close();
    }
    }
