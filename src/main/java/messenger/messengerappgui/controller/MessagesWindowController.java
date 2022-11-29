package messenger.messengerappgui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    }

}
