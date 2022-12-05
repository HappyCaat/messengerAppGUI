package messenger.messengerappgui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import messenger.Main;
import messenger.Message;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class MessagesWindowController {
    private static String userId;
    private static String userName;
    private List<Message> allMessages = new ArrayList<>();

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
                messagesTextField.setText(messagesTextField.getText() + "Current server time: " + answer + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        getUserByIdButton.setOnAction(actionEvent -> {
            userId = sendMessageTextField.getText();
            System.out.println("userId: " + userId);

            String answer = Main.getUserById(userId);
            usersTextField.setText(usersTextField.getText() + " " + answer + "\n");
        });

        getUserNameByLoginButton.setOnAction(actionEvent -> {
            String userName = sendMessageTextField.getText();
            try {
                String answer = Main.getUserByLogin(userName);
                String[] array = answer.split(",");
                userId = array[0].substring(1);
                MessagesWindowController.userName = array[1].substring(0, array[1].length() - 1);
                usersTextField.setText(usersTextField.getText() + MessagesWindowController.userName + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        sendMessageButton.setOnAction(actionEvent -> {
            try {
                String textMessage = sendMessageTextField.getText();
                System.out.println("User: " + userId + "\n" + "Message: " + textMessage);

                String answer = Main.sendMessage(userId, textMessage);
                System.out.println("send attempt answer=" + answer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               Platform.runLater(() -> {
                                   try {
                                       SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                       String sinceDate = "0";
                                       if (allMessages.size() > 0) {
                                           sinceDate = ((allMessages.get(allMessages.size() - 1)).date + 1) + "";
                                       }
                                       System.out.println("read messages since_date=" + sinceDate);
                                       ArrayList<Message> newMessages = Main.readMessages(sinceDate);
                                       if (newMessages.isEmpty()) {
                                           return;
                                       }
                                       allMessages.addAll(newMessages);
                                       System.out.println("new messages=" + newMessages);

                                       String messagesAsString = "";
                                       for (Message message : allMessages) {
                                           messagesAsString = getUserName(message) + ": " + dateFormat.format(new Date(message.date))
                                                   + " " + message.message + "\n" + messagesAsString;
                                       }
                                       System.out.println();
                                       messagesTextField.setText(messagesAsString);
                                   } catch (IOException e) {
                                       throw new RuntimeException(e);
                                   }
                               });
                           }
                       }, 0,
                1000);
    }

    static HashMap<Integer, String> userNameCache = new HashMap<>();

    private static String getUserName(Message message) {
        int fromUserId = message.fromUserId;

        String username = userNameCache.get(fromUserId);

        if (username == null) {
            username = Main.getUserById(fromUserId + "");
            userNameCache.put(fromUserId, username);
        }
        return username;
    }
}
