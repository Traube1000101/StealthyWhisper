package com.traube.stealthywhispergui;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static com.traube.stealthywhispergui.StealthyWhisperApplication.globalLoader;

public class StealthyWhisperController {
    @FXML AnchorPane anchorPane;

    @FXML private TextField encodedMessageTextField;
    @FXML private TextField visibleMessageTextField;
    @FXML private TextField hiddenMessageTextField;


    // Variables for switching scenes
    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    @FXML public void initialize() {
        anchorPane.setOnMouseClicked(event -> anchorPane.requestFocus());
    }

    final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    public void encrypt() {
        String visibleMessage = visibleMessageTextField.getText();
        String hiddenMessage = hiddenMessageTextField.getText();

        encodedMessageTextField.setText("");
        resetError();

        visibleMessageTextField.pseudoClassStateChanged(errorClass, visibleMessage.isEmpty());
        hiddenMessageTextField.pseudoClassStateChanged(errorClass, hiddenMessage.isEmpty());

        if (!(visibleMessage.isEmpty() || hiddenMessage.isEmpty())) {
            encodedMessageTextField.setText(StealthyWhisperAlgorithm.insertMessage(visibleMessage, StealthyWhisperAlgorithm.encodeMessage(hiddenMessage)));
        }
    }

    public void decrypt() {
        visibleMessageTextField.setText("");
        hiddenMessageTextField.setText("");
        resetError();

        if (encodedMessageTextField.getText().isEmpty())
            encodedMessageTextField.pseudoClassStateChanged(errorClass, true);
        else {
            encodedMessageTextField.pseudoClassStateChanged(errorClass, false);
            String encodedMessage = encodedMessageTextField.getText();
            String[] messages = StealthyWhisperAlgorithm.decodeMessage(encodedMessage);
            hiddenMessageTextField.setText(messages[0]);
            visibleMessageTextField.setText(messages[1]);
        }
    }

    public void clear() {
        encodedMessageTextField.setText("");
        visibleMessageTextField.setText("");
        hiddenMessageTextField.setText("");
        resetError();
    }

    public void resetError(){
        visibleMessageTextField.pseudoClassStateChanged(errorClass, false);
        hiddenMessageTextField.pseudoClassStateChanged(errorClass, false);
        encodedMessageTextField.pseudoClassStateChanged(errorClass, false);
    }

    public void switchToSettings(ActionEvent event) throws IOException {
            loadScene(event, getClass().getResource("settings.fxml"));
    }

    public static void loadScene(ActionEvent event, URL sceneUrl) throws IOException {
        try {

        // Switch to settings scene
        Locale locale = new Locale(SettingsManager.getSetting("locale", Locale.getDefault().getCountry()));

        globalLoader = new FXMLLoader();
        ResourceBundle langBundle = ResourceBundle.getBundle("com.traube.bundles.lang", locale);
        globalLoader.setResources(langBundle);
        globalLoader.setLocation(Objects.requireNonNull(StealthyWhisperController.class.getResource("settings.fxml")));
        root = globalLoader.load();

        stage = new Stage();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        /* // Use when triggered by menu item instead of button
        if (event != null) {
            MenuItem menuItem = (MenuItem) event.getSource();
            scene = menuItem.getParentPopup().getOwnerWindow().getScene();
            stage = (Stage) scene.getWindow();

        }
         */

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException e) {
            Throwable cause = e.getCause();
            cause.printStackTrace();
        }
    }

    static void loadScene(URL sceneUrl) throws IOException { loadScene(null,sceneUrl); }

    void loadScene(ActionEvent event) throws IOException { loadScene(event, globalLoader.getLocation()); }

    public void visibleMsgCopyButton() { copyFunction(visibleMessageTextField); }

    public void hiddenMsgCopyButton() { copyFunction(hiddenMessageTextField); }

    public void encodedMsgCopyButton() { copyFunction(encodedMessageTextField); }

    void copyFunction(TextField messageTextField) {
        messageTextField.requestFocus();
        StringSelection stringSelection = new StringSelection(messageTextField.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
