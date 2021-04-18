package userr.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import userr.exceptions.UsernameAlreadyExistsException;
import userr.services.UserService;

import java.io.IOException;

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
    public void handleRegisterAction(javafx.event.ActionEvent login) throws IOException {
        try {
            UserService.addUser(usernameField.getText(), passwordField.getText(), passwordconfirmField.getText(),
                    firstnameField.getText(), secondnameField.getText(), phonenumberField.getText(),
                    addressField.getText());
            registrationMessage.setText("Account created successfully!");
            usernameField.clear();
            passwordField.clear();
            passwordconfirmField.clear();
            firstnameField.clear();
            secondnameField.clear();
            phonenumberField.clear();
            addressField.clear();
        } catch (UsernameAlreadyExistsException e) {
            registrationMessage.setText(e.getMessage());
            passwordField.clear();
            passwordconfirmField.clear();
        }
    }
}
