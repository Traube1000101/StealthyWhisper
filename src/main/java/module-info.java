module com.stealthywhispergui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;
    requires java.prefs;

    opens com.traube.stealthywhisper to javafx.fxml;
    exports com.traube.stealthywhisper;

}