module messenger.messengerappgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens messenger.messengerappgui to javafx.fxml;
    exports messenger.messengerappgui;
    exports messenger.messengerappgui.controller;
    opens messenger.messengerappgui.controller to javafx.fxml;
}