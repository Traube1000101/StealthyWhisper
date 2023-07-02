package com.traube.stealthywhispergui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.traube.stealthywhispergui.StealthyWhisperApplication.loader;

public class SettingsController {
    @FXML
    AnchorPane anchorPane;

    // Settings Scene
    @FXML private TextField cipherKeyTextField;

    // Variables for switching scenes
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize() {
        anchorPane.setOnMouseClicked(event -> anchorPane.requestFocus());

        cipherKeyTextField.setText(SettingsManager.getSetting("cipherKey", ""));
    }

    public void switchToMain(ActionEvent event) throws IOException {
        SettingsManager.saveSetting("cipherKey", cipherKeyTextField.getText());

        Locale locale = new Locale(SettingsManager.getSetting("locale", Locale.getDefault().getCountry()));
        loader = new FXMLLoader();

        loader.setResources(ResourceBundle.getBundle("com.traube.bundles.lang", locale));
        loader.setLocation(Objects.requireNonNull(getClass().getResource("stealthy-whisper.fxml")));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}