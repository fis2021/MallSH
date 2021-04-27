package userr.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import userr.exceptions.FieldNotCompletedException;
import userr.exceptions.PasswordConfirmationException;
import userr.exceptions.UsernameAlreadyExistsException;
import userr.exceptions.WeakPasswordException;
import userr.services.UserService;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class RegistrationController {
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Text registrationMessage = null;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    PasswordField passwordconfirmField;
    @FXML
    TextField firstnameField;
    @FXML
    TextField secondnameField;
    @FXML
    TextField phonenumberField;
    @FXML
    TextField addressField;
    @FXML
    public File file;
    @FXML
    public String path ;
    @FXML
    ImageView photoPath;


    @FXML
    public void handleRegisterAction(javafx.event.ActionEvent login) throws IOException {
        try {
            UserService.addUser(usernameField.getText(), passwordField.getText(), passwordconfirmField.getText(),
                    firstnameField.getText(), secondnameField.getText(), phonenumberField.getText(),
                    addressField.getText(), path);
            registrationMessage.setText("Account created successfully!");
            usernameField.clear();
            passwordField.clear();
            passwordconfirmField.clear();
            firstnameField.clear();
            secondnameField.clear();
            phonenumberField.clear();
            addressField.clear();
            clearProfilePicture();
            {
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getClassLoader().getResource("user_login.fxml"));
                Parent viewuserlogin = Loader.load();
                Scene loginscene = new Scene(viewuserlogin, 650, 465);
                Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();
                window.setScene(loginscene);
                window.show();
            }
        } catch (UsernameAlreadyExistsException e) {
            registrationMessage.setText(e.getMessage());
            passwordField.clear();
            passwordconfirmField.clear();
            clearProfilePicture();
        }catch (FieldNotCompletedException e) {
            registrationMessage.setText(e.getMessage());
            passwordField.clear();
            passwordconfirmField.clear();
            clearProfilePicture();
        }catch (WeakPasswordException e) {
            registrationMessage.setText(e.getMessage());
            passwordField.clear();
            passwordconfirmField.clear();
            clearProfilePicture();
        }catch (PasswordConfirmationException e) {
            registrationMessage.setText(e.getMessage());
            passwordField.clear();
            passwordconfirmField.clear();
            clearProfilePicture();
        }
    }

    public void goBackToLogin(javafx.event.ActionEvent back) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("user_login.fxml"));
        Parent viewuserLogin = Loader.load();
        Scene Loginscene = new Scene(viewuserLogin, 650, 465);
        Stage window = (Stage) ((Node) back.getSource()).getScene().getWindow();
        window.setScene(Loginscene);
        window.show();

    }
    private void clearProfilePicture() throws MalformedURLException {
        String pathUser = "src/main/resources/user_registration.png";
        File file = new File(pathUser);
        String localUrl = file.toURI().toURL().toExternalForm();
        Image profile = new Image(localUrl, false);
        photoPath.setLayoutY(113.0);
        photoPath.setLayoutX(14.0);
        photoPath.setFitWidth(112.0);
        photoPath.setFitHeight(150.0);
        photoPath.setImage(profile);
    }

    @FXML
    void handleAddPhoto() throws MalformedURLException {
        Stage stage = new Stage();
        stage.setTitle("Add Photo");
        FileChooser filechooser = new FileChooser();
        filechooser.setInitialDirectory(new File("C:\\"));
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg files","*.jpg"));
        file = filechooser.showOpenDialog(stage);
        path = file.getAbsolutePath();
        filechooser.setInitialDirectory(file.getParentFile());
        File file = new File(path);
        String localUrl = file.toURI().toURL().toExternalForm();
        Image profile = new Image(localUrl, false);
        photoPath.setImage(profile);
        photoPath.setFitHeight(100);
        photoPath.setFitWidth(150);
        photoPath.rotateProperty();
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
