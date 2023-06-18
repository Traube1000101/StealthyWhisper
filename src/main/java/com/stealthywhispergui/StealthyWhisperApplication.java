package com.stealthywhispergui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StealthyWhisperApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("stealthy-whisper-main.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("stealthy-whisper-main.css").toExternalForm());

            Image icon = new Image(getClass().getResourceAsStream("app-icon.png"));
            stage.getIcons().add(icon);

            stage.setTitle("StealthyWhisper GUI");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}