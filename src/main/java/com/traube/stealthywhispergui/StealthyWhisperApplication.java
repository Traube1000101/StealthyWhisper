package com.traube.stealthywhispergui;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class StealthyWhisperApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static FXMLLoader loader;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Locale locale = new Locale(SettingsManager.getSetting("locale", Locale.getDefault().getCountry()));
            ResourceBundle langBundle = ResourceBundle.getBundle("com.traube.bundles.lang", locale);

            loader = new FXMLLoader();
            loader.setResources(langBundle);
            loader.setLocation(Objects.requireNonNull(getClass().getResource("stealthy-whisper.fxml")));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stealthy-whisper.css")).toExternalForm());

            stage.setScene(scene);
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("app-icon.png")));
            stage.getIcons().add(icon);
            stage.setTitle("Stealthy Whisper");
            stage.setResizable(false);
            stage.initStyle(StageStyle.DECORATED);
            stage.show();
        } catch (Exception e) {
            throw new IOException("Error: ", e);
        }
    }
}