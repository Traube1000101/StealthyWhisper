package com.traube.stealthywhispergui;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StealthyWhisperApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("stealthy-whisper-main.fxml")));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stealthy-whisper-main.css")).toExternalForm());

            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("app-icon.png")));
            stage.getIcons().add(icon);

            stage.setTitle("Stealthy Whisper");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new IOException("Error: ", e);
        }
    }
}