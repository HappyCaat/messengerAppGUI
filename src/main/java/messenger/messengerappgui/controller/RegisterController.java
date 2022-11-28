package messenger.messengerappgui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import messenger.Main;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField loginField;

    @FXML
    private Button okButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    void initialize() {
        okButton.setOnAction(a -> {
            String name = loginField.getText();
            String password = passwordField.getText();
            System.out.println("login=" + name + " password=" + password);

            String register = Main.registerUser(name, password);
            System.out.println("register answer=" + register);
        });

        cancelButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });

    }

}
