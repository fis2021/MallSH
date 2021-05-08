package userr.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.ObjectRepository;
import userr.exceptions.FieldNotCompletedException;
import userr.exceptions.TitleDoesNotMatchException;
import userr.model.User;
import userr.services.AdService;
import userr.services.UserService;

import java.io.File;
import java.io.IOException;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class DeleteAdController {

    private static ObjectRepository<User> repo = UserService.getUsers();
    private static String username,id;

    @FXML
    public Text DeleteAdMessage;
    @FXML
    public TextField titleField1;
    @FXML
    public TextField validationUsernameField;
    @FXML
    public File file;


    private String loggedUser;
    @FXML
    public void handleDeleteAdAction(javafx.event.ActionEvent homepage) throws IOException {
        try {
            loggedUser = LoginController.getLoggedUsername();
            User loggedInUser=repo.find(eq("username",username)).firstOrDefault();
            String id = NitriteId.newId().toString();
            AdService.deleteAd(titleField1.getText(), validationUsernameField.getText());
            DeleteAdMessage.setText("Announcement deleted sucessfully!");
            titleField1.clear();
            validationUsernameField.clear();
            {
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getClassLoader().getResource("home_page.fxml"));
                Parent viewhomepage = Loader.load();
                Scene homepagescene = new Scene(viewhomepage, 650, 450);
                Stage window = (Stage) ((Node) homepage.getSource()).getScene().getWindow();
                window.setScene(homepagescene);
                window.show();
            }
        } catch (FieldNotCompletedException e) {
            DeleteAdMessage.setText(e.getMessage());
            titleField1.clear();
            validationUsernameField.clear();
        } catch (TitleDoesNotMatchException e) {
            DeleteAdMessage.setText(e.getMessage());
            titleField1.clear();
            validationUsernameField.clear();
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
