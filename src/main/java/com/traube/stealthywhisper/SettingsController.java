package com.traube.stealthywhisper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingsController {
    @FXML
    AnchorPane anchorPane;

    @FXML private TextField cipherKeyTextField;
    @FXML private ChoiceBox languageSelector;

    private final List<Locale> supportedLocales = new ArrayList<>();

    @FXML
    public void initialize() {
        anchorPane.setOnMouseClicked(event -> anchorPane.requestFocus());

        cipherKeyTextField.setText(SettingsManager.getSetting("cipherKey", ""));

        // Language Selector
        populateSupportedLocales();

        // Language code retrieved from SettingsManager
        String savedLanguageCode = SettingsManager.getSetting("locale", "de"); // Retrieve the saved language code

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

    private void populateSupportedLocales() {
        supportedLocales.add(Locale.forLanguageTag("de"));
        supportedLocales.add(Locale.forLanguageTag("en"));
        supportedLocales.add(new Locale("yama"));

        // Loads all the available languages of the lang bundle
        // Sadly doesn't work when compiled into a JAR file
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

    public void saveSettings() {
        SettingsManager.saveSetting("cipherKey", cipherKeyTextField.getText());
    }

    public void closeSettings(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

}