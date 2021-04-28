package userr.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import userr.exceptions.FieldNotCompletedException;
import userr.model.Ad;
import userr.model.User;
import userr.services.AdService;
import userr.services.FileSystemService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

public class AdController {

    @FXML
    public Text AddAdMessage;
    @FXML
    public TextField titleField;
    @FXML
    public TextField priceField;
    @FXML
    public TextField descriptionField;
    @FXML
    public CheckBox appliancesBox;
    @FXML
    public CheckBox clothesBox;
    @FXML
    public CheckBox carsBox;
    @FXML
    public CheckBox furnitureBox;

    @FXML
    public File file;
    @FXML
    public String path ;
    @FXML
    ImageView photoPath;

    @FXML
    void handleAddPhoto() throws MalformedURLException {
        Stage stage = new Stage();
        stage.setTitle("Add Photo");
        FileChooser filechooser = new FileChooser();
        filechooser.setInitialDirectory(new File("D:\\"));
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg files","*.jpg"));
        file = filechooser.showOpenDialog(stage);
        path = file.getAbsolutePath();
        filechooser.setInitialDirectory(file.getParentFile());
        File file = new File(path);
        String localUrl = file.toURI().toURL().toExternalForm();
        Image photoAd = new Image(localUrl, false);
        photoPath.setImage(photoAd);
        photoPath.setFitHeight(100);
        photoPath.setFitWidth(150);
        photoPath.rotateProperty();
    }


    @FXML
    public void handleCheckBoxAction(){
        if(appliancesBox.isSelected()==true)
        {
            carsBox.setSelected(false);
            clothesBox.setSelected(false);
            furnitureBox.setSelected(false);
        }
        if(clothesBox.isSelected()==true)
        {
            carsBox.setSelected(false);
            appliancesBox.setSelected(false);
            furnitureBox.setSelected(false);
        }
        if(carsBox.isSelected()==true)
        {
            appliancesBox.setSelected(false);
            clothesBox.setSelected(false);
            furnitureBox.setSelected(false);
        }
        if(furnitureBox.isSelected()==true)
        {
            carsBox.setSelected(false);
            appliancesBox.setSelected(false);
            clothesBox.setSelected(false);
        }
    }
    @FXML
    public void handleCreateAdAction(javafx.event.ActionEvent homepage) throws IOException {
        try {
            AdService.addAd(titleField.getText(), priceField.getText(), descriptionField.getText(),
                      appliancesBox.isSelected(), clothesBox.isSelected(), carsBox.isSelected(), furnitureBox.isSelected() ,path);
            AddAdMessage.setText("Announcement created sucessfully!");
            titleField.clear();
            priceField.clear();
            descriptionField.clear();
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
            AddAdMessage.setText(e.getMessage());
            titleField.clear();
            priceField.clear();
            descriptionField.clear();
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
