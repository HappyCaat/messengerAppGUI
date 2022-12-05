package messenger.messengerappgui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import messenger.Main;
import messenger.Message;
import messenger.User;
import messenger.messengerappgui.Application;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class MessagesWindowController {
    private List<Message> allMessages = new ArrayList<>();
    private static List<String> users = new ArrayList<String>();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addUserButton;

    @FXML
    private AnchorPane mainWindow;

    @FXML
    private TextArea messagesTextField;

    @FXML
    private Button sendMessageButton;

    @FXML
    private TextField sendMessageTextField;

    @FXML
    private Button serverTimeButton;

    @FXML
    private VBox vBox;

    @FXML
    private Button user1;

    @FXML
    private Button user2;

    @FXML
    private Button user3;


    @FXML
    void initialize() {
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

        addUserButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("enter_username.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

        sendMessageButton.setOnAction(actionEvent -> {
            try {
                String textMessage = sendMessageTextField.getText();
                System.out.println("User: " + User.getUserId() + "\n" + "Message: " + textMessage);

                String answer = Main.sendMessage(User.getUserId(), textMessage);
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

                                           if (users.isEmpty()) {
                                               users.add(getUserName(message));
                                           }
                                       }
                                       System.out.println(users);
                                       System.out.println();
                                       messagesTextField.setText(messagesAsString);

                                   } catch (IOException e) {
                                       throw new RuntimeException(e);
                                   }
                               });
                           }
                       }, 0, 1000);
        user1.setText(User.getUserName());
        vBox.getChildren().add(user1);
        user1.setOnAction(actionEvent -> {

        });

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
