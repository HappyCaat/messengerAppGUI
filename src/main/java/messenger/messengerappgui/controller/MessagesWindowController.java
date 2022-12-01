package messenger.messengerappgui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import messenger.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessagesWindowController {
    private static String userId;
    private static String userName;

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
            userId = sendMessageTextField.getText();
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
               String[] array = answer.split(",");
               userId = array[0].substring(1);
               MessagesWindowController.userName = array[1].substring(0, array[1].length() - 1);
               usersTextField.setText(MessagesWindowController.userName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        sendMessageButton.setOnAction(actionEvent -> {
            try {
                String textMessage = sendMessageTextField.getText();
                System.out.println("User: " + userId + "\n" + "Message: " + textMessage);

                String answer = Main.sendMessage(userId,textMessage);
                System.out.println(answer);
                messagesTextField.setText(LoginController.getLogin() + ": " + textMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
