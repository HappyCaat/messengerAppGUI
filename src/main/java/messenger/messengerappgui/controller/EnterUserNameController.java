package messenger.messengerappgui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import messenger.Main;
import messenger.User;

public class EnterUserNameController {
    private User user = new User();


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
    void initialize() {

        okButton.setOnAction(actionEvent -> {
            String userName = loginField.getText();
            try {
                String answer = Main.getUserByLogin(userName);
                String[] array = answer.split(",");
                User.setUserId(array[0].substring(1));
                User.setUserName(array[1].substring(0, array[1].length() - 1));
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
