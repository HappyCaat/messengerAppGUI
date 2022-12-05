package messenger.messengerappgui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import messenger.Main;
import messenger.User;
import messenger.messengerappgui.Application;

public class LoginController {
    private static String login;

    public static String getLogin() {
        return login;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void initialize() {
        loginButton.setOnAction(actionEvent -> {
            String userName = loginField.getText();
            String password = passwordField.getText();
            System.out.println("login=" + userName + " password=" + password);
            try {
                String answer = Main.loginUser(userName, password);
                System.out.println("login answer=" + answer);
                if (!answer.equals("true")) {
                    loginButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader(Application.class.getResource("login_fail.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();

                } else {
                    FXMLLoader loader = new FXMLLoader(Application.class.getResource("message_window.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        cancelButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });

    }

}
