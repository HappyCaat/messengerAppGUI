package messenger.messengerappgui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import messenger.Main;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

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
    private TextArea messagesTextField;

    @FXML
    private Button readMessagesButton;

    @FXML
    private Button sendMessageButton;

    @FXML
    private TextField sendMessageTextField;

    @FXML
    private Button serverTimeButton;

    @FXML
    private TextArea usersTextField;

    @FXML
    void initialize() {
        usersTextField.setEditable(false);
        usersTextField.setWrapText(true);
        messagesTextField.setEditable(false);
        messagesTextField.setWrapText(true);

        serverTimeButton.setOnAction(actionEvent -> {
            try {
                String answer = Main.getServerTime();
                System.out.println("Server time: " + answer);
                messagesTextField.setText("Current server time: " + answer + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        getUserByIdButton.setOnAction(actionEvent -> {
            String userId = sendMessageTextField.getText();
            System.out.println("userId: " + userId);

            try {
                String answer = Main.getUserById(userId);
                usersTextField.setText(answer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        getUserNameByLoginButton.setOnAction(actionEvent -> {
            String userName = sendMessageTextField.getText();
            try {
               String answer = Main.getUserByLogin(userName);
               usersTextField.setText(answer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        sendMessageButton.setOnAction(actionEvent -> {
            try {
                String userName = Main.getUserByLogin(usersTextField.getText());
                String textMessage = sendMessageTextField.getText();
                System.out.println("User: " + userName + "\n" + "Message: " + textMessage);

                String answer = Main.sendMessage(userName,textMessage);
                System.out.println(answer);
                messagesTextField.setText("From: " + textMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

}
