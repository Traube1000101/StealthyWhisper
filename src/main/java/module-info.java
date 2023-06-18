module com.stealthywhispergui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;


    opens com.stealthywhispergui to javafx.fxml;
    exports com.stealthywhispergui;
}