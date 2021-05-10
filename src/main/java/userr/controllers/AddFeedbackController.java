package userr.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.NitriteId;
import userr.exceptions.DuplicatedAdException;
import userr.exceptions.FeedbackAlreadyExistsException;
import userr.exceptions.FieldNotCompletedException;
import userr.model.User;
import userr.services.FeedbackService;

import java.io.IOException;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class AddFeedbackController {

    @FXML
    public TextField feedback;
    @FXML
    public Text errorMessage;
    @FXML
    public ChoiceBox RateBox;
    @FXML
    public void initialize(){
        RateBox.getItems().addAll("1","2","3","4","5","6","7","8","9","10");

    }
    private String loggedUser;
    @FXML
    public void handleGiveFeedbackAdAction(javafx.event.ActionEvent homepage) throws IOException {
        try {
            loggedUser = LoginController.getLoggedUsername();
            FeedbackService.addFeedback(loggedUser,feedback.getText(),(String) RateBox.getValue());
            {
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getClassLoader().getResource("feedback_list.fxml"));
                Parent viewhomepage = Loader.load();
                Scene homepagescene = new Scene(viewhomepage, 650, 450);
                Stage window = (Stage) ((Node) homepage.getSource()).getScene().getWindow();
                window.setScene(homepagescene);
                window.show();
            }
        } catch (FieldNotCompletedException e) {
            errorMessage.setText(e.getMessage());
            feedback.clear();
        } catch (FeedbackAlreadyExistsException e) {
            errorMessage.setText(e.getMessage());
            feedback.clear();
        }
    }

    @FXML
    public void minimizeWindow(javafx.event.ActionEvent min) {
        Stage window = (Stage) ((Node) min.getSource()).getScene().getWindow();
        window.setIconified(true);
    }

    public void closeWindow(javafx.event.ActionEvent close) {
        Stage window = (Stage) ((Node) close.getSource()).getScene().getWindow();
        window.close();
    }
    public void goBackToHomepage(javafx.event.ActionEvent login) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getClassLoader().getResource("home_page.fxml"));
        Parent viewuserLogin = Loader.load();
        Scene Loginscene = new Scene(viewuserLogin, 650, 450);
        Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();
        window.setScene(Loginscene);
        window.show();

    }
}
