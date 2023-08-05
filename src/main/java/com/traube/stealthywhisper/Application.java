package com.traube.stealthywhisper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Application extends javafx.application.Application {
    public static final boolean DEBUG = false;
    public static final String version = "1.0.1";

    public static void main(String[] args) {
        launch(args);
    }

    public static FXMLLoader globalLoader;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Locale locale = new Locale(SettingsManager.getSetting("locale", Locale.getDefault().getCountry()));
            ResourceBundle langBundle = ResourceBundle.getBundle("com.traube.bundles.lang", locale);

            globalLoader = new FXMLLoader();
            globalLoader.setResources(langBundle);
            globalLoader.setLocation(Objects.requireNonNull(getClass().getResource("stealthy-whisper.fxml")));

            Parent root = globalLoader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stealthy-whisper.css")).toExternalForm());

            stage.setScene(scene);
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("app-icon.png")));
            stage.getIcons().add(icon);
            stage.setTitle("StealthyWhisper v" + version);
            stage.setResizable(false);
            stage.initStyle(StageStyle.DECORATED);
            stage.show();
        } catch (Exception e) {
            throw new IOException("Error: ", e);
        }
    }
}