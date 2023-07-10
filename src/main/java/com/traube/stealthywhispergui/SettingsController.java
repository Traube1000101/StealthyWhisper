package com.traube.stealthywhispergui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;


import static com.traube.stealthywhispergui.StealthyWhisperApplication.globalLoader;

public class SettingsController {
    @FXML
    AnchorPane anchorPane;

    // Settings Scene
    @FXML private TextField cipherKeyTextField;

    @FXML private ChoiceBox languageSelector;

    // Variables for switching scenes
    private Stage stage;
    private Scene scene;
    private Parent root;

    private List<Locale> supportedLocales = new ArrayList<>();
    private String savedLanguageCode; // Language code retrieved from SettingsManager

    @FXML
    public void initialize() throws NoSuchFieldException, IllegalAccessException {
        anchorPane.setOnMouseClicked(event -> anchorPane.requestFocus());

        cipherKeyTextField.setText(SettingsManager.getSetting("cipherKey", ""));

        // Language Selector
        populateSupportedLocales();

        savedLanguageCode = SettingsManager.getSetting("locale", "de"); // Retrieve the saved language code

        // Set the initially selected item based on the saved language
        for (Locale locale : supportedLocales) {
            if (locale.getLanguage().equals(savedLanguageCode)) {
                languageSelector.getSelectionModel().select(locale.getDisplayName());
                break;
            }
        }

        for (Locale locale : supportedLocales) {
            languageSelector.getItems().add(locale.getDisplayName());
        }

        languageSelector.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    int selectedIndex = languageSelector.getSelectionModel().getSelectedIndex();
                    Locale selectedLocale = supportedLocales.get(selectedIndex);
                    SettingsManager.saveSetting("locale", selectedLocale.getLanguage());
                }
        );
    }

    private void populateSupportedLocales() throws NoSuchFieldException, IllegalAccessException {
        supportedLocales.add(Locale.forLanguageTag("de"));
        supportedLocales.add(Locale.forLanguageTag("en"));
        supportedLocales.add(new Locale("yama"));

        // Loads all the available languages of the lang bundle
        // Doesn't work when compiled into a JAR file
        /*
        try {
            URL resourceURL = getClass().getClassLoader().getResource("com/traube/bundles");
            if (resourceURL != null) {
                Path resourcePath = Paths.get(resourceURL.toURI());
                if (Files.isDirectory(resourcePath)) {
                    try (DirectoryStream<Path> stream = Files.newDirectoryStream(resourcePath, "lang_*.properties")) {
                        for (Path filePath : stream) {
                            String fileName = filePath.getFileName().toString();
                            String languageCode = fileName.substring(5, fileName.lastIndexOf('.'));
                            Locale locale = Locale.forLanguageTag(languageCode);
                            supportedLocales.add(locale);
                        }
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        */
    }

    public void switchToMain(ActionEvent event) throws IOException {
        SettingsManager.saveSetting("cipherKey", cipherKeyTextField.getText());

        Locale locale = new Locale(SettingsManager.getSetting("locale", Locale.getDefault().getCountry()));
        globalLoader = new FXMLLoader();

        globalLoader.setResources(ResourceBundle.getBundle("com.traube.bundles.lang", locale));
        globalLoader.setLocation(Objects.requireNonNull(getClass().getResource("stealthy-whisper.fxml")));
        root = globalLoader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}