package messenger.messengerappgui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import messenger.Main;

public class MessagesWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button getUserByIdButton;

    @FXML
    private Button getUserNameByLoginButton;

    @FXML
    private TextField messagesTextField;

    @FXML
    private Button readMessagesButton;

    @FXML
    private Button sendMessageButton;

    @FXML
    private TextField sendMessageTextField;

    @FXML
    private Button serverTimeButton;

    @FXML
    private TextField usersTextField;

    @FXML
    void initialize() {
        usersTextField.setEditable(false);
        messagesTextField.setEditable(false);

        serverTimeButton.setOnAction(actionEvent -> {
            try {
                String answer = Main.getServerTime();
                System.out.println("Server time: " + answer);
                messagesTextField.setText("Current server time: " + answer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        getUserByIdButton.setOnAction(actionEvent -> {
            String userId = sendMessageTextField.getText();
            System.out.println("userId: " + userId);

            try {
                String answer = Main.getUserById(userId);
                usersTextField.setText("Username: " + answer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

}
